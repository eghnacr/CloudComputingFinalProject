package com.egehan.cloudcomputingfinalproject.service;

import lombok.RequiredArgsConstructor;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@Component
@RequiredArgsConstructor
public final class FormRecognizer {
    private static final String key = "6942c4b854d1446cb7ab1d660ec35a61";
    private static final String endpoint = "https://final-project-ai-part-form-recognize.cognitiveservices.azure.com/";

    private final AzureBlobService azureBlobService;
    private final LogService logService;

    public String recognize(MultipartFile file) {
        /*String blobUrl = azureBlobService.uploadBlob(file);
        if (blobUrl == null) {
            return null;
        }*/

        //String operationLocation = sendRecognizingRequest(blobUrl);
        String operationLocation = "https://final-project-ai-part-form-recognize.cognitiveservices.azure.com/formrecognizer/v2.1/layout/analyzeResults/41742ed9-dbf8-4eb2-bf73-2173afb7b26e";
        System.out.println(operationLocation);
        logService.save(operationLocation);

        String result = getProceedResult(operationLocation);
        return extractTcknFromResult(result);
    }

    private String extractTcknFromResult(String result) {
        return result;
    }

    private String getProceedResult(String operationLocation) {
        HttpClient httpclient = HttpClients.createDefault();

        try {
            URIBuilder builder = new URIBuilder(operationLocation);

            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", key);

            HttpResponse response = httpclient.execute(request);


            HttpEntity entity = response.getEntity();
            if (entity != null)
                return EntityUtils.toString(entity);

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        return null;
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
