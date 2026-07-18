package com.research_assistent.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResearchRequest {

    @NotBlank(message = "Content cannot be empty")
    private String content;

    @NotBlank(message = "Operation cannot be empty")
    private String operation;
}
