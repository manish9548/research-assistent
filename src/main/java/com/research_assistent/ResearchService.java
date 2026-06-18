package com.research_assistent;

import org.springframework.stereotype.Service;

@Service
public class ResearchService {

    public String processContent(ResearchRequest request) {

        // Build the prompt
        String prompt = buildPrompt(request);

        // Query AI Model API
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

            case "analyze":
                prompt.append("Analyze the following text and provide key insights:\n\n");
                break;

            default:
                prompt.append("Process the following text:\n\n");
        }

        prompt.append(request.getContent());

        return prompt.toString();
    }
}