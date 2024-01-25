package com.example.itutor.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class GCPStorageConfig {

    @Value("${gcp.storage.credentials.path:}")
    private String jsonPath;

    @Bean
    @ConditionalOnProperty(name = "gcp.storage.credentials.path")
    public Storage storage() throws IOException {
        try {
            // Configure Google Cloud Storage with JSON-Key
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath))
                    .createScoped("https://www.googleapis.com/auth/cloud-platform");

            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            return storage;
        } catch (Exception e) {
            System.err.println(e);
            throw e;
        }
    }
}
