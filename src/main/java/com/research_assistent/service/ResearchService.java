package com.research_assistent.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.research_assistent.dto.GeminiResponse;
import com.research_assistent.dto.ResearchRequest;
import com.research_assistent.dto.ResearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public interface ResearchService {

    ResearchResponse processContent(ResearchRequest request);
    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;


}