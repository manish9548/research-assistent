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

            case "translate":
                prompt.append("Translate the following text into English:\n\n");
                break;

            case "grammar":
                prompt.append("Correct all grammar and spelling mistakes in the following text:\n\n");
                break;

            case "keywords":
                prompt.append("Extract important keywords from the following text:\n\n");
                break;

            case "questions":
                prompt.append("Generate interview questions based on the following text:\n\n");
                break;

            case "sentiment":
                prompt.append("Perform sentiment analysis on the following text and identify whether it is positive, negative, or neutral:\n\n");
                break;

            case "explain":
                prompt.append("Explain the following text in simple and easy-to-understand language:\n\n");
                break;

            case "paraphrase":
                prompt.append("Rewrite the following text in different words while preserving its meaning:\n\n");
                break;

            case "title":
                prompt.append("Generate a suitable title for the following content:\n\n");
                break;

            case "bulletpoints":
                prompt.append("Convert the following text into concise bullet points:\n\n");
                break;

            default:
                prompt.append("Process the following text:\n\n");
        }

        prompt.append(request.getContent());

        return prompt.toString();
    }
}