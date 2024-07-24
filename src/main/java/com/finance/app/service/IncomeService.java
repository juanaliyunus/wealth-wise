package com.finance.app.service;

import com.finance.app.dto.request.IncomeRequest;
import com.finance.app.dto.response.IncomeResponse;

import java.util.List;

public interface IncomeService {
    // CRUD operations using JpaRepository
    List<IncomeResponse> getAllIncomesByUserId(Long userId);

    IncomeResponse getIncomeByIdAndUserId(Long incomeId, Long userId);

    IncomeResponse createIncome(IncomeRequest incomeRequest);

    IncomeResponse updateIncome(Long incomeId, IncomeRequest incomeRequest);

    void deleteIncome(Long incomeId);

    // Native Query methods
    IncomeResponse getMaxAmountIncomeByUserId(Long userId);

    IncomeResponse getMinAmountIncomeByUserId(Long userId);

    List<IncomeResponse> getIncomesGreaterThanAmountByUserId(Long userId, Double amount);

    List<IncomeResponse> getIncomesLessThanAmountByUserId(Long userId, Double amount);

    List<IncomeResponse> getIncomesByDescriptionContainingIgnoreCase(Long userId, String keyword);

    Double getTotalAmountByUserId(Long userId);

    List<Object[]> getIncomeSumByMonthForUser(Long userId);

    List<Object[]> getIncomeSumBySourceForUser(Long userId);
}
