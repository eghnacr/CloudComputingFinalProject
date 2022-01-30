package com.egehan.cloudcomputingfinalproject.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
public final class AzureBlobService {

    private final String containerName = "bulut-final";
    private final String connectionString = "DefaultEndpointsProtocol=https;AccountName=egehanbulut;AccountKey=gsc4MH44r/wfVLtXYRNN8nxoZ2jsJZkKFH5Tegp0qFQRTxDvzjIwCwzK8Ljq5DfIyz34x5kiXlewVuOE/Y4scQ==;EndpointSuffix=core.windows.net";
    private final String containerUrl = "https://egehanbulut.blob.core.windows.net/bulut-final/";

    public String uploadBlob(MultipartFile file) {

        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        String fileName = UUID.randomUUID() + "." + "jpeg";
        BlobClient blobClient = containerClient.getBlobClient(fileName);

        try {
            blobClient.upload(file.getInputStream(), file.getSize());
            return containerUrl + fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
