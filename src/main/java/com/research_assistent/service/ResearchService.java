package com.research_assistent.service;

import com.research_assistent.dto.ResearchRequest;
import com.research_assistent.dto.ResearchResponse;

public interface ResearchService {

    ResearchResponse processContent(ResearchRequest request);

}