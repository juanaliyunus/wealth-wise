package com.finance.app.repository;

import com.finance.app.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    //Native Query
    // 1. Mengambil data income dengan amount maksimum berdasarkan user
    @Query(value = "SELECT * FROM income WHERE user_id = :userId ORDER BY amount DESC LIMIT 1", nativeQuery = true)
    Optional<Income> findMaxAmountIncomeByUserId(@Param("userId") Long userId);

    // 2. Mengambil data income dengan amount minimum berdasarkan user
    @Query(value = "SELECT * FROM income WHERE user_id = :userId ORDER BY amount ASC LIMIT 1", nativeQuery = true)
    Optional<Income> findMinAmountIncomeByUserId(@Param("userId") Long userId);

    // 3. Mengambil data income dengan jumlah tertentu (lebih dari) berdasarkan user
    @Query(value = "SELECT * FROM income WHERE user_id = :userId AND amount > :amount", nativeQuery = true)
    List<Income> findIncomesGreaterThanAmountByUserId(@Param("userId") Long userId, @Param("amount") Double amount);

    // 4. Mengambil data income dengan jumlah tertentu (kurang dari) berdasarkan user
    @Query(value = "SELECT * FROM income WHERE user_id = :userId AND amount < :amount", nativeQuery = true)
    List<Income> findIncomesLessThanAmountByUserId(@Param("userId") Long userId, @Param("amount") Double amount);

    // 5. Mengambil data income berdasarkan deskripsi yang mengandung kata kunci tertentu (case insensitive)
    @Query(value = "SELECT * FROM income WHERE user_id = :userId AND LOWER(description) LIKE LOWER(CONCAT('%', :keyword, '%'))", nativeQuery = true)
    List<Income> findIncomesByDescriptionContainingIgnoreCase(@Param("userId") Long userId, @Param("keyword") String keyword);

    // 6. Menghitung total amount dari semua income untuk user tertentu
    @Query(value = "SELECT SUM(amount) FROM income WHERE user_id = :userId", nativeQuery = true)
    Double findTotalAmountByUserId(@Param("userId") Long userId);

    // 7. Menghitung jumlah income berdasarkan bulan untuk user tertentu dan mengurutkannya
    @Query(value = "SELECT EXTRACT(MONTH FROM date) AS month, SUM(amount) FROM income WHERE user_id = :userId GROUP BY EXTRACT(MONTH FROM date) ORDER BY month", nativeQuery = true)
    List<Object[]> findIncomeSumByMonthForUser(@Param("userId") Long userId);

    // 8. Menghitung jumlah income yang ada untuk setiap source untuk user tertentu
    @Query(value = "SELECT source, SUM(amount) FROM income WHERE user_id = :userId GROUP BY source", nativeQuery = true)
    List<Object[]> findIncomeSumBySourceForUser(@Param("userId") Long userId);
}
