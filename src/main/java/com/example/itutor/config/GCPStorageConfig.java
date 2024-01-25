package com.example.itutor.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class GCPStorageConfig {
    @Bean
    public static Storage getStorage() throws IOException {
        // JSON
        String jsonPath = "/Users/carinahauser/Documents/Studium/Semester7/iTutor/durable-stack-411920-251cd0179708.json";

        // Configure Google Cloud Storage with JSON-Key
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath))
                .createScoped("https://www.googleapis.com/auth/cloud-platform");

        return StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }
}
