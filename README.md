# WealthWise

WealthWise adalah aplikasi API yang dirancang untuk membantu pengguna mengelola keuangan pribadi dengan lebih bijak. Dengan fitur utama—pendapatan, pengeluaran, dan anggaran—WealthWise memungkinkan pengguna untuk memantau dan merencanakan keuangan dengan mudah.

## Penjelasan 3 poin penting
### Spring IoC
Penerapan IoC (Inversion of Control) dalam proyek ini dilakukan dengan menggunakan anotasi stereotype untuk menyuntikkan dependensi ke dalam kelas-kelas yang memerlukannya. Berikut ini adalah diagram dependency-nya
![incomeController](https://github.com/user-attachments/assets/119a8e08-c11a-4a8e-b9aa-4bcf5f04ec32)

### Java Stream
Java Stream digunakan di kelas "service" untuk menjalankan logika bisnis dengan cara yang deklaratif.

### Native Query
Native Query digunakan pada kelas repository dengan menggunakan anotasi @Query untuk menulis kueri SQL "native" yang dieksekusi langsung terhadap basis data.

## Prasyarat

Sebelum menggunakan API ini, pastikan memiliki:
- Java 17 atau lebih tinggi
- Maven 3.6 atau lebih tinggi
- PostgreSQL 14.12 atau lebih tinggi

## Instalasi

Untuk menginstal dan menjalankan WealthWise, ikuti langkah-langkah berikut:

1. Clone repositori ini:
    ```bash
    https://github.com/juanaliyunus/wealth-wise.git
    ```

2. Masuk ke direktori proyek:
    ```bash
    cd repository
    ```

3. Instal dependensi menggunakan Maven:
    ```bash
    mvn install
    ```

4. Konfigurasikan file `application.properties` dengan informasi basis data Anda.

5. Jalankan aplikasi menggunakan Maven:
    ```bash
    mvn spring-boot:run
    ```

## Penggunaan

Buka Swagger UI dengan link berikut:
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Setelah aplikasi berjalan, Anda dapat mengakses endpoint API berikut:

### Pengguna (User)

- **GET** `/api/users/{id}`: Mengambil data pengguna berdasarkan ID.
- **GET** `/api/users/username/{username}`: Mengambil data pengguna berdasarkan username.
- **POST** `/api/users`: Membuat pengguna baru.
- **PUT** `/api/users/{id}`: Memperbarui data pengguna yang ada.
- **DELETE** `/api/users/{id}`: Menghapus data pengguna berdasarkan ID.
- **GET** `/api/users`: Mengambil semua data pengguna.

### Anggaran

- **GET** `/api/budgets/user/{userId}`: Mengambil semua data anggaran berdasarkan ID pengguna.
- **GET** `/api/budgets/{budgetId}/user/{userId}`: Mengambil data anggaran berdasarkan ID anggaran dan ID pengguna.
- **POST** `/api/budgets`: Membuat anggaran baru.
- **PUT** `/api/budgets/{budgetId}`: Memperbarui anggaran yang ada.
- **DELETE** `/api/budgets/{budgetId}`: Menghapus anggaran berdasarkan ID anggaran.
- **GET** `/api/budgets/user/{userId}/max-amount`: Mengambil anggaran dengan jumlah maksimum untuk pengguna tertentu.
- **GET** `/api/budgets/user/{userId}/min-amount`: Mengambil anggaran dengan jumlah minimum untuk pengguna tertentu.
- **GET** `/api/budgets/user/{userId}/greater-than/{amount}`: Mengambil anggaran dengan jumlah lebih besar dari nilai yang ditentukan untuk pengguna tertentu.
- **GET** `/api/budgets/user/{userId}/less-than/{amount}`: Mengambil anggaran dengan jumlah lebih kecil dari nilai yang ditentukan untuk pengguna tertentu.
- **GET** `/api/budgets/description/{keyword}`: Mengambil anggaran berdasarkan kata kunci dalam deskripsi.
- **GET** `/api/budgets/user/{userId}/total-amount`: Mengambil total jumlah anggaran untuk pengguna tertentu.
- **GET** `/api/budgets/user/{userId}/budget-count-by-month`: Mengambil jumlah anggaran per bulan untuk pengguna tertentu.
- **GET** `/api/budgets/user/{userId}/budget-count-by-category`: Mengambil jumlah anggaran per kategori untuk pengguna tertentu.

### Pengeluaran (Expense)

- **GET** `/api/expenses/user/{userId}`: Mengambil semua data pengeluaran berdasarkan ID pengguna.
- **GET** `/api/expenses/{expenseId}/user/{userId}`: Mengambil data pengeluaran berdasarkan ID pengeluaran dan ID pengguna.
- **POST** `/api/expenses`: Membuat pengeluaran baru.
- **PUT** `/api/expenses/{expenseId}`: Memperbarui pengeluaran yang ada.
- **DELETE** `/api/expenses/{expenseId}`: Menghapus pengeluaran berdasarkan ID pengeluaran.
- **GET** `/api/expenses/max/{userId}`: Mengambil pengeluaran dengan jumlah maksimum untuk pengguna tertentu.
- **GET** `/api/expenses/min/{userId}`: Mengambil pengeluaran dengan jumlah minimum untuk pengguna tertentu.
- **GET** `/api/expenses/greater-than/{userId}/{amount}`: Mengambil pengeluaran dengan jumlah lebih besar dari nilai yang ditentukan untuk pengguna tertentu.
- **GET** `/api/expenses/less-than/{userId}/{amount}`: Mengambil pengeluaran dengan jumlah lebih kecil dari nilai yang ditentukan untuk pengguna tertentu.
- **GET** `/api/expenses/description/{userId}/{keyword}`: Mengambil pengeluaran berdasarkan kata kunci dalam deskripsi untuk pengguna tertentu.
- **GET** `/api/expenses/total/{userId}`: Mengambil total jumlah pengeluaran untuk pengguna tertentu.
- **GET** `/api/expenses/count-by-month/{userId}`: Mengambil jumlah pengeluaran per bulan untuk pengguna tertentu.
- **GET** `/api/expenses/count-by-year/{userId}`: Mengambil jumlah pengeluaran per tahun untuk pengguna tertentu.

### Pendapatan (Income)

- **GET** `/api/incomes/user/{userId}`: Mengambil semua data pendapatan berdasarkan ID pengguna.
- **GET** `/api/incomes/{incomeId}/user/{userId}`: Mengambil data pendapatan berdasarkan ID pendapatan dan ID pengguna.
- **POST** `/api/incomes`: Membuat pendapatan baru.
- **PUT** `/api/incomes/{incomeId}`: Memperbarui data pendapatan yang ada.
- **DELETE** `/api/incomes/{incomeId}`: Menghapus data pendapatan berdasarkan ID pendapatan.
- **GET** `/api/incomes/max/user/{userId}`: Mengambil pendapatan dengan jumlah maksimum untuk pengguna tertentu.
- **GET** `/api/incomes/min/user/{userId}`: Mengambil pendapatan dengan jumlah minimum untuk pengguna tertentu.
- **GET** `/api/incomes/greater-than/user/{userId}/{amount}`: Mengambil pendapatan dengan jumlah lebih besar dari nilai yang ditentukan untuk pengguna tertentu.
- **GET** `/api/incomes/less-than/user/{userId}/{amount}`: Mengambil pendapatan dengan jumlah lebih kecil dari nilai yang ditentukan untuk pengguna tertentu.
- **GET** `/api/incomes/description/user/{userId}`: Mengambil pendapatan berdasarkan kata kunci dalam deskripsi untuk pengguna tertentu.
- **GET** `/api/incomes/total/user/{userId}`: Mengambil total jumlah pendapatan untuk pengguna tertentu.
- **GET** `/api/incomes/sum-by-month/user/{userId}`: Mengambil jumlah pendapatan per bulan untuk pengguna tertentu.
- **GET** `/api/incomes/sum-by-source/user/{userId}`: Mengambil jumlah pendapatan berdasarkan sumber untuk pengguna tertentu.

