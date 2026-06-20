package com.research_assistent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class ResearchService {
    @Value("${gemini.api.url}")
    private String geminiApiUrl;
    private String geminiApiKey;

    private  final WebClient webClient;

    public ResearchService(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.build();
    }

    public String processContent(ResearchRequest request) {

        // Build the prompt
        String prompt = buildPrompt(request);

        // Query AI Model API
        Map<String , Object> requestBody= Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                            Map.of("text",prompt)
                })
                }
        );
        String response = webClient.post()
                .uri(geminiApiUrl+geminiApiKey)
                .bodyValue(requestBody)
                .retrieve().bodyToMono(String.class).block();
        // Parse Response

        // For now return prompt

        return prompt;
    }

    private String buildPrompt(ResearchRequest request) {

        StringBuilder prompt = new StringBuilder();

        switch (request.getOperation()) {

            case "summarize":
                prompt.append("Provide a clear and concise summary of the following text:\n\n");
                break;

            case "suggest":
                prompt.append("Provide suggestions and improvements for the following text:\n\n");
                break;
            default:
                throw new IllegalArgumentException("Unknown operation :" + request.getOperation());

        }

        prompt.append(request.getContent());

        return prompt.toString();
    }
}