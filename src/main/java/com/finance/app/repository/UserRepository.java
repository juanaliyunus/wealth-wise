package com.finance.app.repository;


import com.finance.app.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Menemukan pengguna berdasarkan username.
     * @param username Nama pengguna yang dicari.
     * @return Optional<User> Objek pengguna jika ditemukan, kosong jika tidak ditemukan.
     */
    @Query(value = "SELECT * FROM app_user WHERE username = :username", nativeQuery = true)
    Optional<User> findByUsername(@Param("username") String username);
    /**
     * Menghapus pengguna berdasarkan ID.
     * @param id ID pengguna yang akan dihapus.
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM app_user WHERE id = :id", nativeQuery = true)
    void deleteById(@Param("id") Long id);
    /**
     * Menyimpan pengguna baru ke dalam database.
     * @param username Nama pengguna yang akan disimpan.
     * @param password Kata sandi pengguna yang akan disimpan.
     * @param email Alamat email pengguna yang akan disimpan.
     */
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO app_user (username, password, email) VALUES (:username, :password, :email)", nativeQuery = true)
    void saveUser(@Param("username") String username, @Param("password") String password, @Param("email") String email);

    /**
     * Memperbarui informasi pengguna berdasarkan ID.
     * @param id ID pengguna yang akan diperbarui.
     * @param username Nama pengguna baru.
     * @param password Kata sandi baru.
     * @param email Alamat email baru.
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE app_user SET username = :username, password = :password, email = :email WHERE id = :id", nativeQuery = true)
    void updateUser(@Param("id") Long id, @Param("username") String username, @Param("password") String password, @Param("email") String email);
}
