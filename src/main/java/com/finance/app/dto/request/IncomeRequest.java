package com.finance.app.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncomeRequest {
    private String source;
    private Double amount;
    private String date;
    private String description;
    private Long userId;
}
