package com.egehan.cloudcomputingfinalproject.service;

import lombok.RequiredArgsConstructor;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@Component
@RequiredArgsConstructor
public final class FormRecognizer {
    private static final String key = "6942c4b854d1446cb7ab1d660ec35a61";
    private static final String endpoint = "https://final-project-ai-part-form-recognize.cognitiveservices.azure.com/";

    private final LogService logService;
    private final AzureBlobService azureBlobService;
    private final ResultProcessorService resultProcessorService;

    public String recognize(MultipartFile file) {
        String blobUrl = azureBlobService.uploadBlob(file);
        if (blobUrl == null) {
            return null;
        }

        String operationLocation = sendRecognizingRequest(blobUrl);
        logService.save(operationLocation);

        resultProcessorService.writeRequestToQueue(operationLocation);
        return resultProcessorService.readResultFromQueue();
    }

    private String sendRecognizingRequest(String imageUrl) {
        HttpClient httpclient = HttpClients.createDefault();

        try {
            URIBuilder builder
                    = new URIBuilder(endpoint + "formrecognizer/v2.1/layout/analyze");

            builder.setParameter("pages", "1");
            builder.setParameter("locale", "tr-TR");

            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", key);

            StringEntity body = new StringEntity("{\"source\":\"" + imageUrl + "\"}", ContentType.APPLICATION_JSON);
            request.setEntity(body);

            HttpResponse response = httpclient.execute(request);

            Header[] headers = response.getHeaders("Operation-Location");

            if (headers.length == 0) {
                return null;
            }
            return response.getHeaders("Operation-Location")[0].getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
