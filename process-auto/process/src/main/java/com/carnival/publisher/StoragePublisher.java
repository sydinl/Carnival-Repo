//package com.carnival.publisher;
//
//import com.azure.storage.blob.BlobClient;
//import com.azure.storage.blob.BlobContainerClient;
//import com.azure.storage.blob.BlobServiceClient;
//import com.azure.storage.blob.BlobServiceClientBuilder;
//import com.azure.storage.blob.models.BlobErrorCode;
//import com.azure.storage.blob.models.BlobStorageException;
//
//import java.io.File;
//
//public class StoragePublisher {
//
//    public static void publishStorage(File file){
//        String yourSasToken = "<insert-your-sas-token>";
//        String blobName = null;
//        /* Create a new BlobServiceClient with a SAS Token */
//        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
//                .endpoint("https://your-storage-account-url.storage.windows.net")
//                .sasToken(yourSasToken)
//                .buildClient();
//
//        /* Create a new container client */
//        try {
//            BlobContainerClient containerClient = blobServiceClient.createBlobContainer("my-container-name");
//            /* Upload the file to the container */
//            BlobClient blobClient = containerClient.getBlobClient(blobName);
//                    blobClient.uploadFromFile(file.getPath());
//        } catch (BlobStorageException ex) {
//            // The container may already exist, so don't throw an error
//            if (!ex.getErrorCode().equals(BlobErrorCode.CONTAINER_ALREADY_EXISTS)) {
//                throw ex;
//            }
//        }
//
//
//    }
//}
