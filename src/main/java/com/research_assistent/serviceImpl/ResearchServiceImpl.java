package com.research_assistent.serviceImpl;

import com.research_assistent.client.GeminiClient;
import com.research_assistent.dto.ResearchRequest;
import com.research_assistent.dto.ResearchResponse;
import com.research_assistent.service.ResearchService;
import com.research_assistent.util.PromptBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResearchServiceImpl implements ResearchService {

    private final GeminiClient geminiClient;
    private final PromptBuilder promptBuilder;

    @Override
    public ResearchResponse processContent(ResearchRequest request) {

        long startTime = System.currentTimeMillis();

        String prompt = promptBuilder.buildPrompt(request);

        String response = geminiClient.generateContent(prompt);

        long endTime = System.currentTimeMillis();

        return ResearchResponse.builder()
                .operation(request.getOperation())
                .result(response)   // Abhi raw JSON return hoga
                .processingTime(endTime - startTime)
                .build();
    }
}