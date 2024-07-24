package com.finance.app.repository;

import com.finance.app.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    // 1. Mengambil data expense dengan amount maksimum berdasarkan user
    @Query(value = "SELECT * FROM expense WHERE user_id = :userId AND amount = (SELECT MAX(amount) FROM expense WHERE user_id = :userId)", nativeQuery = true)
    Optional<Expense> findMaxAmountExpenseByUserId(@Param("userId") Long userId);

    // 2. Mengambil data expense dengan amount minimum berdasarkan user
    @Query(value = "SELECT * FROM expense WHERE user_id = :userId AND amount = (SELECT MIN(amount) FROM expense WHERE user_id = :userId)", nativeQuery = true)
    Optional<Expense> findMinAmountExpenseByUserId(@Param("userId") Long userId);

    // 3. Mengambil data expense dengan jumlah tertentu (lebih dari) berdasarkan user
    @Query(value = "SELECT * FROM expense WHERE user_id = :userId AND amount > :amount", nativeQuery = true)
    List<Expense> findExpensesGreaterThanAmountByUserId(@Param("userId") Long userId, @Param("amount") Double amount);

    // 4. Mengambil data expense dengan jumlah tertentu (kurang dari) berdasarkan user
    @Query(value = "SELECT * FROM expense WHERE user_id = :userId AND amount < :amount", nativeQuery = true)
    List<Expense> findExpensesLessThanAmountByUserId(@Param("userId") Long userId, @Param("amount") Double amount);

    // 5. Mengambil data expense berdasarkan deskripsi yang mengandung kata kunci tertentu (case insensitive) berdasarkan user
    @Query(value = "SELECT * FROM expense WHERE user_id = :userId AND LOWER(description) LIKE LOWER(CONCAT('%', :keyword, '%'))", nativeQuery = true)
    List<Expense> findExpensesByDescriptionContainingIgnoreCase(@Param("userId") Long userId, @Param("keyword") String keyword);

    // 6. Menghitung total amount dari semua expense untuk user tertentu
    @Query(value = "SELECT COALESCE(SUM(amount), 0) FROM expense WHERE user_id = :userId", nativeQuery = true)
    Double findTotalAmountByUserId(@Param("userId") Long userId);

    // 7. Menghitung jumlah expense berdasarkan bulan untuk user tertentu dan mengurutkannya
    @Query(value = "SELECT EXTRACT(MONTH FROM date) AS month, COUNT(*) FROM expense WHERE user_id = :userId GROUP BY EXTRACT(MONTH FROM date) ORDER BY month", nativeQuery = true)
    List<Object[]> countExpensesByMonthForUser(@Param("userId") Long userId);

    // 8. Menghitung jumlah expense berdasarkan tahun untuk user tertentu dan mengurutkannya
    @Query(value = "SELECT EXTRACT(YEAR FROM date) AS year, COUNT(*) FROM expense WHERE user_id = :userId GROUP BY EXTRACT(YEAR FROM date) ORDER BY year", nativeQuery = true)
    List<Object[]> countExpensesByYearForUser(@Param("userId") Long userId);
}
