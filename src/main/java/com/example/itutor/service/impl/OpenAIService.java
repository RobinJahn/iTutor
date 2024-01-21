package com.example.itutor.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAIService {

    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${api.key}")
    private String apiKey; // Injecting the API key from application properties

    private final String openAIUrl = "https://api.openai.com/v1/chat/completions";

    public String getChatResponse(String userMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        String requestBody = "{\n" +
                "    \"model\": \"gpt-3.5-turbo\",\n" +
                "    \"messages\": [\n" +
                "      {\n" +
                "        \"role\": \"system\",\n" +
                "        \"content\": \"You are a tutor for undergraduate students.\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"role\": \"user\",\n" +
                "        \"content\": \"" + userMessage + "\"\n" +
                "      }\n" +
                "    ]\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        String response = "";
        try {
            response = restTemplate.postForObject(openAIUrl, entity, String.class);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return response;
    }

}
