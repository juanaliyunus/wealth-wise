package com.finance.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Kelas ini digunakan untuk memberikan struktur standar dalam merespons permintaan dari klien.
 *
 * @param <T> Tipe data yang akan dikirimkan dalam respons.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {
    private String status;      // Status respons (misalnya: "success" atau "error")
    private String message;     // Pesan yang menjelaskan status respons
    private T data;             // Data yang dikirimkan dalam respons (bisa berupa objek, list, dll.)
    private int code;           // Kode status HTTP (misalnya: 200, 400, 500)
}