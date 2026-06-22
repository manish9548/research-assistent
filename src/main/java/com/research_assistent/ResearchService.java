package com.research_assistent;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class ResearchService {

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public ResearchService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    public String processContent(ResearchRequest request) {

        // Build the prompt
        String prompt = buildPrompt(request);

        // Request body for Gemini API
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of(
                                "parts", new Object[]{
                                        Map.of("text", prompt)
                                }
                        )
                }
        );

        // Call Gemini API
        String response = webClient.post()
                .uri(geminiApiUrl + geminiApiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // Extract and return generated text
        return extractTextFromResponse(response);
    }

    private String extractTextFromResponse(String response) {
        try {
            GeminiResponse geminiResponse =
                    objectMapper.readValue(response, GeminiResponse.class);

            if (geminiResponse.getCandidateList() != null
                    && !geminiResponse.getCandidateList().isEmpty()) {

                GeminiResponse.Candidate firstCandidate =
                        geminiResponse.getCandidateList().get(0);

                if (firstCandidate.getContent() != null
                        && firstCandidate.getContent().getParts() != null
                        && !firstCandidate.getContent().getParts().isEmpty()) {

                    return firstCandidate.getContent()
                            .getParts()
                            .get(0)
                            .getText();
                }
            }

        } catch (Exception e) {
            return "Error Parsing Response: " + e.getMessage();
        }

        return "No response generated";
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
                throw new IllegalArgumentException(
                        "Unknown operation: " + request.getOperation()
                );
        }

        prompt.append(request.getContent());

        return prompt.toString();
    }
}