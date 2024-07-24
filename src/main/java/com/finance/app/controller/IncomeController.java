package com.finance.app.controller;

import com.finance.app.dto.request.IncomeRequest;
import com.finance.app.dto.response.CommonResponse;
import com.finance.app.dto.response.IncomeResponse;
import com.finance.app.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incomes")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IncomeController {
    private final IncomeService incomeService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<CommonResponse<List<IncomeResponse>>> getAllIncomesByUserId(@PathVariable Long userId) {
        List<IncomeResponse> incomes = incomeService.getAllIncomesByUserId(userId);
        return ResponseEntity.ok(
                CommonResponse.<List<IncomeResponse>>builder()
                        .status("success")
                        .message("Fetched all incomes for user id: " + userId)
                        .data(incomes)
                        .code(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/{incomeId}/user/{userId}")
    public ResponseEntity<CommonResponse<IncomeResponse>> getIncomeByIdAndUserId(@PathVariable Long incomeId, @PathVariable Long userId) {
        IncomeResponse income = incomeService.getIncomeByIdAndUserId(incomeId, userId);
        return ResponseEntity.ok(
                CommonResponse.<IncomeResponse>builder()
                        .status("success")
                        .message("Fetched income with id: " + incomeId + " for user id: " + userId)
                        .data(income)
                        .code(HttpStatus.OK.value())
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<CommonResponse<IncomeResponse>> createIncome(@RequestBody IncomeRequest incomeRequest) {
        IncomeResponse createdIncome = incomeService.createIncome(incomeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.<IncomeResponse>builder()
                        .status("success")
                        .message("Income created successfully")
                        .data(createdIncome)
                        .code(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @PutMapping("/{incomeId}")
    public ResponseEntity<CommonResponse<IncomeResponse>> updateIncome(@PathVariable Long incomeId, @RequestBody IncomeRequest incomeRequest) {
        IncomeResponse updatedIncome = incomeService.updateIncome(incomeId, incomeRequest);
        return ResponseEntity.ok(
                CommonResponse.<IncomeResponse>builder()
                        .status("success")
                        .message("Income updated successfully")
                        .data(updatedIncome)
                        .code(HttpStatus.OK.value())
                        .build()
        );
    }

    @DeleteMapping("/{incomeId}")
    public ResponseEntity<CommonResponse<Void>> deleteIncome(@PathVariable Long incomeId) {
        incomeService.deleteIncome(incomeId);
        return ResponseEntity.ok(
                CommonResponse.<Void>builder()
                        .status("success")
                        .message("Income deleted successfully")
                        .code(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/max/user/{userId}")
    public ResponseEntity<CommonResponse<IncomeResponse>> getMaxAmountIncomeByUserId(@PathVariable Long userId) {
        IncomeResponse maxIncome = incomeService.getMaxAmountIncomeByUserId(userId);
        return ResponseEntity.ok(
                CommonResponse.<IncomeResponse>builder()
                        .status("success")
                        .message("Fetched max amount income for user id: " + userId)
                        .data(maxIncome)
                        .code(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/min/user/{userId}")
    public ResponseEntity<CommonResponse<IncomeResponse>> getMinAmountIncomeByUserId(@PathVariable Long userId) {
        IncomeResponse minIncome = incomeService.getMinAmountIncomeByUserId(userId);
        return ResponseEntity.ok(
                CommonResponse.<IncomeResponse>builder()
                        .status("success")
                        .message("Fetched min amount income for user id: " + userId)
                        .data(minIncome)
                        .code(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/greater-than/user/{userId}/{amount}")
    public ResponseEntity<CommonResponse<List<IncomeResponse>>> getIncomesGreaterThanAmountByUserId(@PathVariable Long userId, @PathVariable Double amount) {
        List<IncomeResponse> incomes = incomeService.getIncomesGreaterThanAmountByUserId(userId, amount);
        return ResponseEntity.ok(
                CommonResponse.<List<IncomeResponse>>builder()
                        .status("success")
                        .message("Fetched incomes greater than amount " + amount + " for user id: " + userId)
                        .data(incomes)
                        .code(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/less-than/user/{userId}/{amount}")
    public ResponseEntity<CommonResponse<List<IncomeResponse>>> getIncomesLessThanAmountByUserId(@PathVariable Long userId, @PathVariable Double amount) {
        List<IncomeResponse> incomes = incomeService.getIncomesLessThanAmountByUserId(userId, amount);
        return ResponseEntity.ok(
                CommonResponse.<List<IncomeResponse>>builder()
                        .status("success")
                        .message("Fetched incomes less than amount " + amount + " for user id: " + userId)
                        .data(incomes)
                        .code(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/description/user/{userId}")
    public ResponseEntity<CommonResponse<List<IncomeResponse>>> getIncomesByDescriptionContainingIgnoreCase(@PathVariable Long userId, @RequestParam String keyword) {
        List<IncomeResponse> incomes = incomeService.getIncomesByDescriptionContainingIgnoreCase(userId, keyword);
        return ResponseEntity.ok(
                CommonResponse.<List<IncomeResponse>>builder()
                        .status("success")
                        .message("Fetched incomes containing keyword '" + keyword + "' for user id: " + userId)
                        .data(incomes)
                        .code(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/total/user/{userId}")
    public ResponseEntity<CommonResponse<Double>> getTotalAmountByUserId(@PathVariable Long userId) {
        Double totalAmount = incomeService.getTotalAmountByUserId(userId);
        return ResponseEntity.ok(
                CommonResponse.<Double>builder()
                        .status("success")
                        .message("Fetched total amount for user id: " + userId)
                        .data(totalAmount)
                        .code(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/sum-by-month/user/{userId}")
    public ResponseEntity<CommonResponse<List<Object[]>>> getIncomeSumByMonthForUser(@PathVariable Long userId) {
        List<Object[]> incomeSumByMonth = incomeService.getIncomeSumByMonthForUser(userId);
        return ResponseEntity.ok(
                CommonResponse.<List<Object[]>>builder()
                        .status("success")
                        .message("Fetched income sum by month for user id: " + userId)
                        .data(incomeSumByMonth)
                        .code(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/sum-by-source/user/{userId}")
    public ResponseEntity<CommonResponse<List<Object[]>>> getIncomeSumBySourceForUser(@PathVariable Long userId) {
        List<Object[]> incomeSumBySource = incomeService.getIncomeSumBySourceForUser(userId);
        return ResponseEntity.ok(
                CommonResponse.<List<Object[]>>builder()
                        .status("success")
                        .message("Fetched income sum by source for user id: " + userId)
                        .data(incomeSumBySource)
                        .code(HttpStatus.OK.value())
                        .build()
        );
    }
}
