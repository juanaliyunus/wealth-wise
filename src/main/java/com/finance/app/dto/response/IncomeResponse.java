package com.finance.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncomeResponse {
    private Long id;
    private String source;
    private Double amount;
    private String date;
    private Long userId;
    private String description;
    private String username;
}
