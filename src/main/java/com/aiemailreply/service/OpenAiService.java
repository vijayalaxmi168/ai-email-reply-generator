package com.aiemailreply.service;

import com.aiemailreply.config.OpenAiConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAiService {

    private final RestTemplate restTemplate;
    private final OpenAiConfig openAiConfig;

    public String generateEmailReply(String emailContent, String tone) {
        String prompt = buildPrompt(emailContent, tone);
        log.debug("Sending prompt to Gemini for tone: {}", tone);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String urlWithKey = openAiConfig.getApiUrl() + "?key=" + openAiConfig.getApiKey();

        Map<String, Object> part = Map.of("text", prompt);
        Map<String, Object> content = Map.of("parts", List.of(part));
        Map<String, Object> requestBody = Map.of("contents", List.of(content));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    urlWithKey, request, Map.class
            );
            return extractGeminiReply(response.getBody());
        } catch (Exception e) {
            log.error("Gemini API error: {}", e.getMessage());
            throw new RuntimeException("Failed to generate reply. Error: " + e.getMessage());
        }
    }

    private String buildPrompt(String emailContent, String tone) {
        String toneInstruction = switch (tone.toLowerCase()) {
            case "professional" -> "formal and professional, suitable for business communication";
            case "friendly"     -> "warm, friendly, and approachable";
            case "short"        -> "brief and to the point, no more than 3-4 sentences";
            default             -> "professional";
        };

        return String.format("""
                You are a professional email assistant.
                Generate a %s reply to the following email.
                Keep the reply concise and appropriate.
                Do not include a subject line. Start directly with the greeting.

                Original Email:
                %s

                Reply:
                """, toneInstruction, emailContent);
    }

    @SuppressWarnings("unchecked")
    private String extractGeminiReply(Map body) {
        if (body == null) throw new RuntimeException("Empty response from Gemini");
        List<Map> candidates = (List<Map>) body.get("candidates");
        Map content = (Map) candidates.get(0).get("content");
        List<Map> parts = (List<Map>) content.get("parts");
        return parts.get(0).get("text").toString().trim();
    }
}