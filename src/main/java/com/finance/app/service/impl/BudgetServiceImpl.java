package com.finance.app.service.impl;

import com.finance.app.dto.request.BudgetRequest;
import com.finance.app.dto.response.BudgetResponse;
import com.finance.app.exception.BudgetNotFoundException;
import com.finance.app.exception.UserNotFoundException;
import com.finance.app.model.Budget;
import com.finance.app.model.User;
import com.finance.app.repository.BudgetRepository;
import com.finance.app.repository.UserRepository;
import com.finance.app.service.BudgetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    // CRUD Operations

    @Override
    public List<BudgetResponse> getAllBudgetsByUserId(Long userId) {
        // Memastikan user ada
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        // Mendapatkan semua budget dan memfilter berdasarkan userId
        return budgetRepository.findAll().stream()
                .filter(budget -> budget.getUser().getId().equals(userId))
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BudgetResponse getBudgetByIdAndUserId(Long budgetId, Long userId) {
        // Memastikan user ada
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        // Mengambil budget berdasarkan ID
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new BudgetNotFoundException("Budget not found with id: " + budgetId));

        // Memeriksa apakah budget tersebut milik user yang diberikan
        if (!budget.getUser().getId().equals(userId)) {
            throw new BudgetNotFoundException("Budget with id " + budgetId + " does not belong to user with id: " + userId);
        }

        return convertToResponse(budget);
    }


    @Override
    @Transactional
    public BudgetResponse createBudget(BudgetRequest budgetRequest) {
        User user = userRepository.findById(budgetRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + budgetRequest.getUserId()));

        Budget budget = Budget.builder()
                .description(budgetRequest.getDescription())
                .category(budgetRequest.getCategory())
                .amount(budgetRequest.getAmount())
                .month(budgetRequest.getMonth())
                .user(user)
                .build();

        budgetRepository.save(budget);
        return convertToResponse(budget);
    }

    @Override
    @Transactional
    public BudgetResponse updateBudget(Long budgetId, BudgetRequest budgetRequest) {
        User user = userRepository.findById(budgetRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + budgetRequest.getUserId()));

        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new BudgetNotFoundException("Budget not found with id: " + budgetId));

        budget.setDescription(budgetRequest.getDescription());
        budget.setCategory(budgetRequest.getCategory());
        budget.setAmount(budgetRequest.getAmount());
        budget.setMonth(budgetRequest.getMonth());
        budget.setUser(user);

        budgetRepository.save(budget);
        return convertToResponse(budget);
    }

    @Override
    @Transactional
    public void deleteBudget(Long budgetId) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new BudgetNotFoundException("Budget not found with id: " + budgetId));

        budgetRepository.delete(budget);
    }

    // Native Query Operations

    @Override
    public BudgetResponse getMaxAmountBudgetByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return convertToResponse(budgetRepository.findMaxAmountBudgetByUserId(userId));
    }

    @Override
    public BudgetResponse getMinAmountBudgetByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return convertToResponse(budgetRepository.findMinAmountBudgetByUserId(userId));
    }

    @Override
    public List<BudgetResponse> getBudgetsGreaterThanAmountByUserId(Long userId, Double amount) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return budgetRepository.findBudgetsGreaterThanAmountByUserId(userId, amount).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BudgetResponse> getBudgetsLessThanAmountByUserId(Long userId, Double amount) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return budgetRepository.findBudgetsLessThanAmountByUserId(userId, amount).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BudgetResponse> getBudgetsByDescriptionContaining(String keyword) {
        return budgetRepository.findBudgetsByDescriptionContaining(keyword).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Double getTotalAmountByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return budgetRepository.findTotalAmountByUserId(userId);
    }

    @Override
    public List<Object[]> getBudgetCountByMonthForUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return budgetRepository.countBudgetsByMonthForUser(userId);
    }

    @Override
    public List<Object[]> getBudgetCountByCategoryForUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return budgetRepository.countBudgetsByCategoryForUser(userId);
    }

    private BudgetResponse convertToResponse(Budget budget) {
        return BudgetResponse.builder()
                .id(budget.getId())
                .description(budget.getDescription())
                .category(budget.getCategory())
                .amount(budget.getAmount())
                .month(budget.getMonth())
                .userId(budget.getUser().getId())
                .username(budget.getUser().getUsername())
                .build();
    }
}
