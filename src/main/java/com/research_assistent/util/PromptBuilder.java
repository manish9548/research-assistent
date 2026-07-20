package com.research_assistent.util;

import com.research_assistent.dto.ResearchRequest;
import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {

    public String buildPrompt(ResearchRequest request) {

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