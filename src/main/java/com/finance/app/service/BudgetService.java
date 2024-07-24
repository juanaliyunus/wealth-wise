package com.finance.app.service;

import com.finance.app.dto.request.BudgetRequest;
import com.finance.app.dto.response.BudgetResponse;

import java.util.List;

public interface BudgetService {
    // CRUD Operations

    // Mendapatkan semua budget berdasarkan user
    List<BudgetResponse> getAllBudgetsByUserId(Long userId);

    // Mendapatkan budget berdasarkan ID budget dan user
    BudgetResponse getBudgetByIdAndUserId(Long budgetId, Long userId);

    // Membuat budget
    BudgetResponse createBudget(BudgetRequest budgetRequest);

    // Mengupdate budget
    BudgetResponse updateBudget(Long budgetId, BudgetRequest budgetRequest);

    // Menghapus budget
    void deleteBudget(Long budgetId);

    // Native Query Operations

    // Mengambil data budget dengan amount maksimum berdasarkan user
    BudgetResponse getMaxAmountBudgetByUserId(Long userId);

    // Mengambil data budget dengan amount minimum berdasarkan user
    BudgetResponse getMinAmountBudgetByUserId(Long userId);

    // Mengambil data budget dengan jumlah tertentu (lebih dari) berdasarkan user
    List<BudgetResponse> getBudgetsGreaterThanAmountByUserId(Long userId, Double amount);

    // Mengambil data budget dengan jumlah tertentu (kurang dari) berdasarkan user
    List<BudgetResponse> getBudgetsLessThanAmountByUserId(Long userId, Double amount);

    // Mengambil data budget berdasarkan deskripsi yang mengandung kata kunci tertentu
    List<BudgetResponse> getBudgetsByDescriptionContaining(String keyword);

    // Menghitung total amount dari semua budget untuk user tertentu
    Double getTotalAmountByUserId(Long userId);

    // Menghitung jumlah budget berdasarkan bulan untuk user tertentu dan mengurutkannya
    List<Object[]> getBudgetCountByMonthForUser(Long userId);

    // Menghitung jumlah budget yang ada untuk setiap kategori untuk user tertentu
    List<Object[]> getBudgetCountByCategoryForUser(Long userId);
}
