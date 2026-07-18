package com.research_assistent.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.research_assistent.client.GeminiClient;
import com.research_assistent.dto.GeminiResponse;
import com.research_assistent.dto.ResearchRequest;
import com.research_assistent.util.PromptBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResearchServiceImpl implements ResearchService{

    private final GeminiClient geminiClient;

    private final PromptBuilder promptBuilder;

}

