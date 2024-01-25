package com.example.itutor.service.impl;

import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GCPStorageService {
    private final Storage storage;

    @Autowired
    public GCPStorageService(Storage storage) {
        this.storage = storage;
    }

    public String uploadDocument(String bucketName, String fileName, byte[] content) throws IOException {
        BlobId blobId = BlobId.of(bucketName, fileName);
        Blob blob = storage.create(BlobInfo.newBuilder(blobId).build(), content);
        System.out.println(blob.getMediaLink());
        return blob.getMediaLink();
    }

}
