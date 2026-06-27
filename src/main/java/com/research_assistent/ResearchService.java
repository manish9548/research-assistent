package com.research_assistent;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class ResearchService {

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public ResearchService(WebClient.Builder builder, ObjectMapper objectMapper) {
        this.webClient = builder.build();
        this.objectMapper = objectMapper;
    }

    public String processContent(ResearchRequest request) {

        String prompt = buildPrompt(request);

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of(
                                "parts", List.of(
                                        Map.of("text", prompt)
                                )
                        )
                )
        );

        String response = webClient.post()
                .uri(geminiApiUrl + geminiApiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("========== GEMINI RESPONSE ==========");
        System.out.println(response);
        System.out.println("=====================================");

        return extractTextFromResponse(response);
    }

    private String extractTextFromResponse(String response) {

        try {

            GeminiResponse geminiResponse =
                    objectMapper.readValue(response, GeminiResponse.class);

            if (geminiResponse.getCandidates() != null &&
                    !geminiResponse.getCandidates().isEmpty()) {

                GeminiResponse.Candidate candidate =
                        geminiResponse.getCandidates().get(0);

                if (candidate.getContent() != null &&
                        candidate.getContent().getParts() != null &&
                        !candidate.getContent().getParts().isEmpty()) {

                    return candidate.getContent()
                            .getParts()
                            .get(0)
                            .getText();
                }
            }

            return "No response generated.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error Parsing Response : " + e.getMessage();
        }
    }

    private String buildPrompt(ResearchRequest request) {

        StringBuilder prompt = new StringBuilder();

        switch (request.getOperation().toLowerCase()) {

            case "summarize":
                prompt.append("Summarize the following content:\n\n");
                break;

            case "suggest":
                prompt.append("Give suggestions to improve the following content:\n\n");
                break;

            default:
                throw new IllegalArgumentException("Invalid Operation");
        }

        prompt.append(request.getContent());

        return prompt.toString();
    }
}