package com.finance.app.service;

import com.finance.app.dto.request.ExpenseRequest;
import com.finance.app.dto.response.ExpenseResponse;

import java.util.List;

public interface ExpanseService {

    // CRUD Operations

    /**
     * Mendapatkan semua expense berdasarkan user
     * @param userId ID dari user
     * @return List of ExpenseResponse yang berisi detail expense untuk user tertentu
     */
    List<ExpenseResponse> getAllExpensesByUserId(Long userId);

    /**
     * Mendapatkan expense berdasarkan ID expense dan user
     * @param expenseId ID dari expense
     * @param userId ID dari user
     * @return ExpenseResponse yang berisi detail expense untuk user tertentu
     */
    ExpenseResponse getExpenseByIdAndUserId(Long expenseId, Long userId);

    /**
     * Membuat expense berdasarkan user
     * @param expenseRequest Request yang berisi data expense
     * @return ExpenseResponse yang berisi detail expense yang baru dibuat
     */
    ExpenseResponse createExpense(ExpenseRequest expenseRequest);

    /**
     * Mengupdate expense berdasarkan user
     * @param expenseId ID dari expense yang akan diupdate
     * @param expenseRequest Request yang berisi data expense yang akan diupdate
     * @return ExpenseResponse yang berisi detail expense yang telah diupdate
     */
    ExpenseResponse updateExpense(Long expenseId, ExpenseRequest expenseRequest);

    /**
     * Menghapus expense berdasarkan user
     * @param expenseId ID dari expense yang akan dihapus
     */
    void deleteExpense(Long expenseId);

    // Native Query Operations

    /**
     * Mengambil data expense dengan amount maksimum berdasarkan user
     * @param userId ID dari user
     * @return ExpenseResponse yang berisi detail expense dengan amount maksimum
     */
    ExpenseResponse getMaxAmountExpenseByUserId(Long userId);

    /**
     * Mengambil data expense dengan amount minimum berdasarkan user
     * @param userId ID dari user
     * @return ExpenseResponse yang berisi detail expense dengan amount minimum
     */
    ExpenseResponse getMinAmountExpenseByUserId(Long userId);

    /**
     * Mengambil data expense dengan jumlah tertentu (lebih dari) berdasarkan user
     * @param userId ID dari user
     * @param amount Jumlah yang digunakan untuk filter
     * @return List of ExpenseResponse yang berisi detail expense dengan jumlah lebih dari nilai tertentu
     */
    List<ExpenseResponse> getExpensesGreaterThanAmountByUserId(Long userId, Double amount);

    /**
     * Mengambil data expense dengan jumlah tertentu (kurang dari) berdasarkan user
     * @param userId ID dari user
     * @param amount Jumlah yang digunakan untuk filter
     * @return List of ExpenseResponse yang berisi detail expense dengan jumlah kurang dari nilai tertentu
     */
    List<ExpenseResponse> getExpensesLessThanAmountByUserId(Long userId, Double amount);

    /**
     * Mengambil data expense berdasarkan deskripsi yang mengandung kata kunci tertentu (case insensitive) berdasarkan user
     * @param userId ID dari user
     * @param keyword Kata kunci yang digunakan untuk filter deskripsi
     * @return List of ExpenseResponse yang berisi detail expense dengan deskripsi yang mengandung kata kunci tertentu
     */
    List<ExpenseResponse> getExpensesByDescriptionContainingIgnoreCase(Long userId, String keyword);

    /**
     * Menghitung total amount dari semua expense untuk user tertentu
     * @param userId ID dari user
     * @return Total amount dari semua expense untuk user tertentu
     */
    Double getTotalAmountByUserId(Long userId);

    /**
     * Menghitung jumlah expense berdasarkan bulan untuk user tertentu dan mengurutkannya
     * @param userId ID dari user
     * @return List yang berisi array objek dengan bulan dan jumlah expense untuk user tertentu
     */
    List<Object[]> getExpenseCountByMonthForUser(Long userId);

    /**
     * Menghitung jumlah expense berdasarkan tahun untuk user tertentu dan mengurutkannya
     * @param userId ID dari user
     * @return List yang berisi array objek dengan tahun dan jumlah expense untuk user tertentu
     */
    List<Object[]> getExpenseCountByYearForUser(Long userId);
}
