package com.finance.app.repository;

import com.finance.app.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    // Mengambil data budget dengan amount maksimum berdasarkan user
    @Query(value = "SELECT * FROM budget WHERE user_id = :userId AND amount = (SELECT MAX(amount) FROM budget WHERE user_id = :userId)", nativeQuery = true)
    Budget findMaxAmountBudgetByUserId(@Param("userId") Long userId);

    // Mengambil data budget dengan amount minimum berdasarkan user
    @Query(value = "SELECT * FROM budget WHERE user_id = :userId AND amount = (SELECT MIN(amount) FROM budget WHERE user_id = :userId)", nativeQuery = true)
    Budget findMinAmountBudgetByUserId(@Param("userId") Long userId);

    // Mengambil data budget dengan jumlah tertentu (lebih dari) berdasarkan user
    @Query(value = "SELECT * FROM budget WHERE user_id = :userId AND amount > :amount", nativeQuery = true)
    List<Budget> findBudgetsGreaterThanAmountByUserId(@Param("userId") Long userId, @Param("amount") Double amount);

    // Mengambil data budget dengan jumlah tertentu (kurang dari) berdasarkan user
    @Query(value = "SELECT * FROM budget WHERE user_id = :userId AND amount < :amount", nativeQuery = true)
    List<Budget> findBudgetsLessThanAmountByUserId(@Param("userId") Long userId, @Param("amount") Double amount);

    // Mengambil data budget berdasarkan deskripsi yang mengandung kata kunci tertentu
    @Query(value = "SELECT * FROM budget WHERE description LIKE %:keyword%", nativeQuery = true)
    List<Budget> findBudgetsByDescriptionContaining(@Param("keyword") String keyword);

    // Menghitung total amount dari semua budget untuk user tertentu
    @Query(value = "SELECT SUM(amount) FROM budget WHERE user_id = :userId", nativeQuery = true)
    Double findTotalAmountByUserId(@Param("userId") Long userId);

    // Menghitung jumlah budget berdasarkan bulan untuk user tertentu dan mengurutkannya
    @Query(value = "SELECT month, COUNT(*) as count FROM budget WHERE user_id = :userId GROUP BY month ORDER BY month", nativeQuery = true)
    List<Object[]> countBudgetsByMonthForUser(@Param("userId") Long userId);

    // Menghitung jumlah budget yang ada untuk setiap kategori untuk user tertentu
    @Query(value = "SELECT category, COUNT(*) as count FROM budget WHERE user_id = :userId GROUP BY category", nativeQuery = true)
    List<Object[]> countBudgetsByCategoryForUser(@Param("userId") Long userId);
}
