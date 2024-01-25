package com.example.itutor.service.impl;

import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class GCPStorageService {
    private final Optional<Storage> storage;

    @Autowired
    public GCPStorageService(Optional<Storage> storage) {
        this.storage = storage;
    }

    public String uploadDocument(String bucketName, String fileName, byte[] content) throws IOException {
        if (!storage.isPresent()) {
            return "error";
        }

        BlobId blobId = BlobId.of(bucketName, fileName);
        Blob blob = storage.get().create(BlobInfo.newBuilder(blobId).build(), content);
        System.out.println(blob.getMediaLink());
        return blob.getMediaLink();
    }

}
