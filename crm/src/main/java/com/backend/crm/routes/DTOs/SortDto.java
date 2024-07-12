package com.backend.crm.routes.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class SortDto {
    private int limit;
    private int page;
    private String search;
    private boolean paranoid;
    private List<SortField> sort;
}

