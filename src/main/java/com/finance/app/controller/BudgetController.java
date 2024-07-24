package com.finance.app.controller;

import com.finance.app.dto.request.BudgetRequest;
import com.finance.app.dto.response.BudgetResponse;
import com.finance.app.dto.response.CommonResponse;
import com.finance.app.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;

    /**
     * Mendapatkan semua budget berdasarkan ID user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<CommonResponse<List<BudgetResponse>>> getAllBudgetsByUserId(@PathVariable Long userId) {
        List<BudgetResponse> budgets = budgetService.getAllBudgetsByUserId(userId);
        CommonResponse<List<BudgetResponse>> response = CommonResponse.<List<BudgetResponse>>builder()
                .status("success")
                .message("Budgets retrieved successfully")
                .data(budgets)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * Mendapatkan budget berdasarkan ID budget dan ID user.
     */
    @GetMapping("/{budgetId}/user/{userId}")
    public ResponseEntity<CommonResponse<BudgetResponse>> getBudgetByIdAndUserId(
            @PathVariable Long budgetId, @PathVariable Long userId) {
        BudgetResponse budget = budgetService.getBudgetByIdAndUserId(budgetId, userId);
        CommonResponse<BudgetResponse> response = CommonResponse.<BudgetResponse>builder()
                .status("success")
                .message("Budget retrieved successfully")
                .data(budget)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * Membuat budget baru.
     */
    @PostMapping
    public ResponseEntity<CommonResponse<BudgetResponse>> createBudget(@RequestBody BudgetRequest budgetRequest) {
        BudgetResponse budget = budgetService.createBudget(budgetRequest);
        CommonResponse<BudgetResponse> response = CommonResponse.<BudgetResponse>builder()
                .status("success")
                .message("Budget created successfully")
                .data(budget)
                .code(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Mengupdate budget yang ada.
     */
    @PutMapping("/{budgetId}")
    public ResponseEntity<CommonResponse<BudgetResponse>> updateBudget(
            @PathVariable Long budgetId, @RequestBody BudgetRequest budgetRequest) {
        BudgetResponse budget = budgetService.updateBudget(budgetId, budgetRequest);
        CommonResponse<BudgetResponse> response = CommonResponse.<BudgetResponse>builder()
                .status("success")
                .message("Budget updated successfully")
                .data(budget)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * Menghapus budget berdasarkan ID.
     */
    @DeleteMapping("/{budgetId}")
    public ResponseEntity<CommonResponse<Void>> deleteBudget(@PathVariable Long budgetId) {
        budgetService.deleteBudget(budgetId);
        CommonResponse<Void> response = CommonResponse.<Void>builder()
                .status("success")
                .message("Budget deleted successfully")
                .data(null)
                .code(HttpStatus.NO_CONTENT.value())
                .build();
        return ResponseEntity.noContent().build();
    }

    // Native Query Operations

    @GetMapping("/user/{userId}/max-amount")
    public ResponseEntity<CommonResponse<BudgetResponse>> getMaxAmountBudgetByUserId(@PathVariable Long userId) {
        BudgetResponse budget = budgetService.getMaxAmountBudgetByUserId(userId);
        CommonResponse<BudgetResponse> response = CommonResponse.<BudgetResponse>builder()
                .status("success")
                .message("Max amount budget retrieved successfully")
                .data(budget)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}/min-amount")
    public ResponseEntity<CommonResponse<BudgetResponse>> getMinAmountBudgetByUserId(@PathVariable Long userId) {
        BudgetResponse budget = budgetService.getMinAmountBudgetByUserId(userId);
        CommonResponse<BudgetResponse> response = CommonResponse.<BudgetResponse>builder()
                .status("success")
                .message("Min amount budget retrieved successfully")
                .data(budget)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}/greater-than/{amount}")
    public ResponseEntity<CommonResponse<List<BudgetResponse>>> getBudgetsGreaterThanAmountByUserId(
            @PathVariable Long userId, @PathVariable Double amount) {
        List<BudgetResponse> budgets = budgetService.getBudgetsGreaterThanAmountByUserId(userId, amount);
        CommonResponse<List<BudgetResponse>> response = CommonResponse.<List<BudgetResponse>>builder()
                .status("success")
                .message("Budgets greater than amount retrieved successfully")
                .data(budgets)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}/less-than/{amount}")
    public ResponseEntity<CommonResponse<List<BudgetResponse>>> getBudgetsLessThanAmountByUserId(
            @PathVariable Long userId, @PathVariable Double amount) {
        List<BudgetResponse> budgets = budgetService.getBudgetsLessThanAmountByUserId(userId, amount);
        CommonResponse<List<BudgetResponse>> response = CommonResponse.<List<BudgetResponse>>builder()
                .status("success")
                .message("Budgets less than amount retrieved successfully")
                .data(budgets)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/description/{keyword}")
    public ResponseEntity<CommonResponse<List<BudgetResponse>>> getBudgetsByDescriptionContaining(@PathVariable String keyword) {
        List<BudgetResponse> budgets = budgetService.getBudgetsByDescriptionContaining(keyword);
        CommonResponse<List<BudgetResponse>> response = CommonResponse.<List<BudgetResponse>>builder()
                .status("success")
                .message("Budgets by description retrieved successfully")
                .data(budgets)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}/total-amount")
    public ResponseEntity<CommonResponse<Double>> getTotalAmountByUserId(@PathVariable Long userId) {
        Double totalAmount = budgetService.getTotalAmountByUserId(userId);
        CommonResponse<Double> response = CommonResponse.<Double>builder()
                .status("success")
                .message("Total amount retrieved successfully")
                .data(totalAmount)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}/budget-count-by-month")
    public ResponseEntity<CommonResponse<List<Object[]>>> getBudgetCountByMonthForUser(@PathVariable Long userId) {
        List<Object[]> budgetCounts = budgetService.getBudgetCountByMonthForUser(userId);
        CommonResponse<List<Object[]>> response = CommonResponse.<List<Object[]>>builder()
                .status("success")
                .message("Budget count by month retrieved successfully")
                .data(budgetCounts)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}/budget-count-by-category")
    public ResponseEntity<CommonResponse<List<Object[]>>> getBudgetCountByCategoryForUser(@PathVariable Long userId) {
        List<Object[]> budgetCounts = budgetService.getBudgetCountByCategoryForUser(userId);
        CommonResponse<List<Object[]>> response = CommonResponse.<List<Object[]>>builder()
                .status("success")
                .message("Budget count by category retrieved successfully")
                .data(budgetCounts)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }
}
