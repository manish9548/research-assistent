package com.research_assistent.controller;

import com.research_assistent.dto.ResearchRequest;
import com.research_assistent.dto.ResearchResponse;
import com.research_assistent.service.ResearchService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/research")
@CrossOrigin(origins ="*")
@AllArgsConstructor
public class ResearchController {
    private final ResearchService researchService;

    @PostMapping
    public ResponseEntity<ResearchResponse> processContent(
            @RequestBody @Valid ResearchRequest request){
        return ResponseEntity.ok(
                researchService.processContent(request)
        );
    }




}
