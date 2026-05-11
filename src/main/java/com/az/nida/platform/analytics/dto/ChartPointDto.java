package com.az.nida.platform.analytics.dto;


import java.time.LocalDate;

public record ChartPointDto(
        LocalDate date,
        int correct,
        int wrong,
        int total,
        double percentage
) {}