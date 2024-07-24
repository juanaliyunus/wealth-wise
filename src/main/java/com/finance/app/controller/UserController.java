package com.finance.app.controller;

import com.finance.app.dto.request.UserRequest;
import com.finance.app.dto.response.CommonResponse;
import com.finance.app.dto.response.UserResponse;
import com.finance.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Mendapatkan user berdasarkan ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<UserResponse>> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                .status("success")
                .message("User retrieved successfully")
                .data(user)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * Mendapatkan user berdasarkan username.
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<CommonResponse<UserResponse>> getUserByUsername(@PathVariable String username) {
        UserResponse user = userService.getUserByUsername(username);
        CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                .status("success")
                .message("User retrieved successfully")
                .data(user)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * Membuat user baru.
     */
    @PostMapping
    public ResponseEntity<CommonResponse<UserResponse>> createUser(@RequestBody UserRequest userRequest) {
        UserResponse user = userService.createUser(userRequest);
        CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                .status("success")
                .message("User created successfully")
                .data(user)
                .code(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Mengupdate user yang ada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<UserResponse>> updateUser(
            @PathVariable Long id, @RequestBody UserRequest userRequest) {
        UserResponse user = userService.updateUser(id, userRequest);
        CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                .status("success")
                .message("User updated successfully")
                .data(user)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * Menghapus user berdasarkan ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        CommonResponse<Void> response = CommonResponse.<Void>builder()
                .status("success")
                .message("User deleted successfully")
                .data(null)
                .code(HttpStatus.NO_CONTENT.value())
                .build();
        return ResponseEntity.noContent().build();
    }

    /**
     * Mendapatkan semua user.
     */
    @GetMapping
    public ResponseEntity<CommonResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        CommonResponse<List<UserResponse>> response = CommonResponse.<List<UserResponse>>builder()
                .status("success")
                .message("Users retrieved successfully")
                .data(users)
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }
}
