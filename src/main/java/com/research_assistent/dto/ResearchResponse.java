package com.research_assistent.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResearchResponse {

    private String operation;

    private String result;

    private Long processTime;


}
