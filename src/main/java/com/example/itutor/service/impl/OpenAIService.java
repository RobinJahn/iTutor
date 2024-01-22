package com.example.itutor.service.impl;

import com.example.itutor.domain.AiMessages;
import com.example.itutor.domain.User;
import com.example.itutor.repository.AiMessagesRepositoryI;
import com.example.itutor.service.OpenAIServiceI;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Optional;

@Service
public class OpenAIService implements OpenAIServiceI {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${api.key}")
    private String apiKey;

    private final AiMessagesRepositoryI aiMessagesRepository;


    public OpenAIService(AiMessagesRepositoryI aiMessagesRepository) {
        this.aiMessagesRepository = aiMessagesRepository;
    }

    @Override
    public AiMessages saveMessages(AiMessages aiMessages) {
        //check if message with this id exists
        if (aiMessages.getId() != null) {
            Optional<AiMessages> aiMessagesOptional = aiMessagesRepository.findById(aiMessages.getId());
            if (aiMessagesOptional.isPresent()) {
                AiMessages aiMessagesFromDb = aiMessagesOptional.get();
                aiMessagesFromDb.setMessages(aiMessages.getMessages());
                return aiMessagesRepository.save(aiMessagesFromDb);
            }
        }
        return aiMessagesRepository.save(aiMessages);
    }

    @Override
    public AiMessages getMessagesById(Long id) {
        return aiMessagesRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteMessages(AiMessages aiMessages) {
        aiMessagesRepository.delete(aiMessages);
    }

    @Override
    public String getChatResponse(AiMessages aiMessages) {
        String parsedMessage = parseAiMessage(aiMessages);
        System.out.println("parsedMessage" + parsedMessage);
        String response = makeApiRequest(parsedMessage);
        System.out.println("response" + response);
        String message = getMessageOutOfResponse(response);
        System.out.println("message" + message);
        return message;
    }

    private String parseAiMessage(AiMessages aiMessages) {
        StringBuilder messagesJson = new StringBuilder();
        messagesJson.append("[");

        for (int i = 0; i < aiMessages.getMessages().size(); i++) {
            String messageContent = aiMessages.getMessages().get(i).replace("\n", "\\n");
            String role = (i % 2 == 0) ? "system" : "user";
            messagesJson.append(String.format("{ \"role\": \"%s\", \"content\": \"%s\" }", role, messageContent));
            if (i < aiMessages.getMessages().size() - 1) {
                messagesJson.append(", ");
            }
        }

        messagesJson.append("]");
        return messagesJson.toString();
    }

    private String makeApiRequest(String messagesList) {
        String openAIUrl = "https://api.openai.com/v1/chat/completions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        String requestBody = "{\n" +
                "    \"model\": \"gpt-3.5-turbo\",\n" +
                "    \"messages\": " + messagesList + "\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        try {
            return restTemplate.postForObject(openAIUrl, entity, String.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private String getMessageOutOfResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray choices = jsonResponse.getJSONArray("choices");
            if (choices.length() > 0) {
                JSONObject firstChoice = choices.getJSONObject(0);
                if (firstChoice.has("message")) {
                    JSONObject message = firstChoice.getJSONObject("message");
                    return message.optString("content", "No response found");
                }
            }
            return "No message in response";
        } catch (Exception e) {
            System.out.println("Error parsing response: " + e.getMessage());
            return "Error in response";
        }
    }


    @Override
    public AiMessages getMessagesForUser(User user) {
        //return new AiMessages() or result of findByUserUsername
        Optional<AiMessages> aiMessages = aiMessagesRepository.findByUser(user);
        return aiMessages.orElseGet(() -> aiMessagesRepository.save(new AiMessages(user)));
    }

    @Override
    public AiMessages resetMessagesForUser(User user) {
        AiMessages aiMessages = getMessagesForUser(user);
        aiMessages.clearMessages();
        aiMessagesRepository.save(aiMessages);
        return aiMessages;
    }

}
