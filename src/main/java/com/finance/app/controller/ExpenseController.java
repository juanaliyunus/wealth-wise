package com.finance.app.controller;

import com.finance.app.dto.request.ExpenseRequest;
import com.finance.app.dto.response.CommonResponse;
import com.finance.app.dto.response.ExpenseResponse;
import com.finance.app.service.ExpanseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExpenseController {

    private final ExpanseService expanseService;

    /**
     * Mendapatkan semua expense berdasarkan user
     * @param userId ID dari user
     * @return CommonResponse yang berisi daftar expense untuk user tertentu
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<CommonResponse<List<ExpenseResponse>>> getAllExpensesByUserId(@PathVariable Long userId) {
        List<ExpenseResponse> expenses = expanseService.getAllExpensesByUserId(userId);
        CommonResponse<List<ExpenseResponse>> response = CommonResponse.<List<ExpenseResponse>>builder()
                .status("success")
                .message("Expenses fetched successfully")
                .data(expenses)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * Mendapatkan expense berdasarkan ID expense dan user
     * @param expenseId ID dari expense
     * @param userId ID dari user
     * @return CommonResponse yang berisi detail expense untuk user tertentu
     */
    @GetMapping("/{expenseId}/user/{userId}")
    public ResponseEntity<CommonResponse<ExpenseResponse>> getExpenseByIdAndUserId(@PathVariable Long expenseId, @PathVariable Long userId) {
        ExpenseResponse expense = expanseService.getExpenseByIdAndUserId(expenseId, userId);
        CommonResponse<ExpenseResponse> response = CommonResponse.<ExpenseResponse>builder()
                .status("success")
                .message("Expense fetched successfully")
                .data(expense)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * Membuat expense berdasarkan user
     * @param expenseRequest Request yang berisi data expense
     * @return CommonResponse yang berisi detail expense yang baru dibuat
     */
    @PostMapping
    public ResponseEntity<CommonResponse<ExpenseResponse>> createExpense(@RequestBody ExpenseRequest expenseRequest) {
        ExpenseResponse expense = expanseService.createExpense(expenseRequest);
        CommonResponse<ExpenseResponse> response = CommonResponse.<ExpenseResponse>builder()
                .status("success")
                .message("Expense created successfully")
                .data(expense)
                .code(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Mengupdate expense berdasarkan user
     * @param expenseId ID dari expense yang akan diupdate
     * @param expenseRequest Request yang berisi data expense yang akan diupdate
     * @return CommonResponse yang berisi detail expense yang telah diupdate
     */
    @PutMapping("/{expenseId}")
    public ResponseEntity<CommonResponse<ExpenseResponse>> updateExpense(@PathVariable Long expenseId, @RequestBody ExpenseRequest expenseRequest) {
        ExpenseResponse expense = expanseService.updateExpense(expenseId, expenseRequest);
        CommonResponse<ExpenseResponse> response = CommonResponse.<ExpenseResponse>builder()
                .status("success")
                .message("Expense updated successfully")
                .data(expense)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * Menghapus expense berdasarkan user
     * @param expenseId ID dari expense yang akan dihapus
     * @return CommonResponse yang menandakan bahwa expense telah dihapus
     */
    @DeleteMapping("/{expenseId}")
    public ResponseEntity<CommonResponse<Void>> deleteExpense(@PathVariable Long expenseId) {
        expanseService.deleteExpense(expenseId);
        CommonResponse<Void> response = CommonResponse.<Void>builder()
                .status("success")
                .message("Expense deleted successfully")
                .data(null)
                .code(HttpStatus.NO_CONTENT.value())
                .build();
        return ResponseEntity.noContent().build();
    }

    // Native Query Operations

    @GetMapping("/max/{userId}")
    public ResponseEntity<CommonResponse<ExpenseResponse>> getMaxAmountExpenseByUserId(@PathVariable Long userId) {
        ExpenseResponse expense = expanseService.getMaxAmountExpenseByUserId(userId);
        CommonResponse<ExpenseResponse> response = CommonResponse.<ExpenseResponse>builder()
                .status("success")
                .message("Max amount expense fetched successfully")
                .data(expense)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/min/{userId}")
    public ResponseEntity<CommonResponse<ExpenseResponse>> getMinAmountExpenseByUserId(@PathVariable Long userId) {
        ExpenseResponse expense = expanseService.getMinAmountExpenseByUserId(userId);
        CommonResponse<ExpenseResponse> response = CommonResponse.<ExpenseResponse>builder()
                .status("success")
                .message("Min amount expense fetched successfully")
                .data(expense)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/greater-than/{userId}/{amount}")
    public ResponseEntity<CommonResponse<List<ExpenseResponse>>> getExpensesGreaterThanAmountByUserId(@PathVariable Long userId, @PathVariable Double amount) {
        List<ExpenseResponse> expenses = expanseService.getExpensesGreaterThanAmountByUserId(userId, amount);
        CommonResponse<List<ExpenseResponse>> response = CommonResponse.<List<ExpenseResponse>>builder()
                .status("success")
                .message("Expenses greater than amount fetched successfully")
                .data(expenses)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/less-than/{userId}/{amount}")
    public ResponseEntity<CommonResponse<List<ExpenseResponse>>> getExpensesLessThanAmountByUserId(@PathVariable Long userId, @PathVariable Double amount) {
        List<ExpenseResponse> expenses = expanseService.getExpensesLessThanAmountByUserId(userId, amount);
        CommonResponse<List<ExpenseResponse>> response = CommonResponse.<List<ExpenseResponse>>builder()
                .status("success")
                .message("Expenses less than amount fetched successfully")
                .data(expenses)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/description/{userId}/{keyword}")
    public ResponseEntity<CommonResponse<List<ExpenseResponse>>> getExpensesByDescriptionContainingIgnoreCase(@PathVariable Long userId, @PathVariable String keyword) {
        List<ExpenseResponse> expenses = expanseService.getExpensesByDescriptionContainingIgnoreCase(userId, keyword);
        CommonResponse<List<ExpenseResponse>> response = CommonResponse.<List<ExpenseResponse>>builder()
                .status("success")
                .message("Expenses with description containing keyword fetched successfully")
                .data(expenses)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/total/{userId}")
    public ResponseEntity<CommonResponse<Double>> getTotalAmountByUserId(@PathVariable Long userId) {
        Double totalAmount = expanseService.getTotalAmountByUserId(userId);
        CommonResponse<Double> response = CommonResponse.<Double>builder()
                .status("success")
                .message("Total amount fetched successfully")
                .data(totalAmount)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count-by-month/{userId}")
    public ResponseEntity<CommonResponse<List<Object[]>>> getExpenseCountByMonthForUser(@PathVariable Long userId) {
        List<Object[]> countByMonth = expanseService.getExpenseCountByMonthForUser(userId);
        CommonResponse<List<Object[]>> response = CommonResponse.<List<Object[]>>builder()
                .status("success")
                .message("Expense count by month fetched successfully")
                .data(countByMonth)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count-by-year/{userId}")
    public ResponseEntity<CommonResponse<List<Object[]>>> getExpenseCountByYearForUser(@PathVariable Long userId) {
        List<Object[]> countByYear = expanseService.getExpenseCountByYearForUser(userId);
        CommonResponse<List<Object[]>> response = CommonResponse.<List<Object[]>>builder()
                .status("success")
                .message("Expense count by year fetched successfully")
                .data(countByYear)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }
}
