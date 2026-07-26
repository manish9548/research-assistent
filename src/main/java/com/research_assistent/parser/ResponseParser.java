package com.research_assistent.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.research_assistent.dto.GeminiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResponseParser {

    private final ObjectMapper objectMapper;

    public String extractText(String response) {

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
            return "Error Parsing Response : " + e.getMessage();
        }
    }
}