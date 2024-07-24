package com.finance.app.service;

import com.finance.app.dto.request.UserRequest;
import com.finance.app.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse getUserById(Long id);
    UserResponse getUserByUsername(String username);
    UserResponse createUser(UserRequest userRequest);
    UserResponse updateUser(Long id, UserRequest userRequest);
    void deleteUser(Long id);
    List<UserResponse> getAllUsers();
}
