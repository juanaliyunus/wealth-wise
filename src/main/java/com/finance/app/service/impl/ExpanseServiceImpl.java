package com.finance.app.service.impl;

import com.finance.app.dto.request.ExpenseRequest;
import com.finance.app.dto.response.ExpenseResponse;
import com.finance.app.exception.ExpenseNotFoundException;
import com.finance.app.exception.UserNotFoundException;
import com.finance.app.model.Expense;
import com.finance.app.model.User;
import com.finance.app.repository.ExpenseRepository;
import com.finance.app.repository.UserRepository;
import com.finance.app.service.ExpanseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExpanseServiceImpl implements ExpanseService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    @Override
    public List<ExpenseResponse> getAllExpensesByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return expenseRepository.findAll().stream()
                .filter(expense -> expense.getUser().getId().equals(userId))
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ExpenseResponse getExpenseByIdAndUserId(Long expenseId, Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found with id: " + expenseId));

        if (!expense.getUser().getId().equals(userId)) {
            throw new ExpenseNotFoundException("Expense with id " + expenseId + " does not belong to user with id: " + userId);
        }

        return convertToResponse(expense);
    }

    @Override
    @Transactional
    public ExpenseResponse createExpense(ExpenseRequest expenseRequest) {
        User user = userRepository.findById(expenseRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + expenseRequest.getUserId()));

        Expense expense = Expense.builder()
                .description(expenseRequest.getDescription())
                .amount(expenseRequest.getAmount())
                .date(expenseRequest.getDate())
                .user(user)
                .build();

        expenseRepository.save(expense);
        return convertToResponse(expense);
    }

    @Override
    @Transactional
    public ExpenseResponse updateExpense(Long expenseId, ExpenseRequest expenseRequest) {
        User user = userRepository.findById(expenseRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + expenseRequest.getUserId()));

        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found with id: " + expenseId));

        if (!expense.getUser().getId().equals(expenseRequest.getUserId())) {
            throw new ExpenseNotFoundException("Expense with id " + expenseId + " does not belong to user with id: " + expenseRequest.getUserId());
        }

        expense.setDescription(expenseRequest.getDescription());
        expense.setAmount(expenseRequest.getAmount());
        expense.setDate(expenseRequest.getDate());
        expense.setUser(user);

        expenseRepository.save(expense);
        return convertToResponse(expense);
    }

    @Override
    @Transactional
    public void deleteExpense(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found with id: " + expenseId));

        expenseRepository.delete(expense);
    }

    @Override
    public ExpenseResponse getMaxAmountExpenseByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return expenseRepository.findMaxAmountExpenseByUserId(userId)
                .map(this::convertToResponse)
                .orElseThrow(() -> new ExpenseNotFoundException("No expenses found for user with id: " + userId));
    }

    @Override
    public ExpenseResponse getMinAmountExpenseByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return expenseRepository.findMinAmountExpenseByUserId(userId)
                .map(this::convertToResponse)
                .orElseThrow(() -> new ExpenseNotFoundException("No expenses found for user with id: " + userId));
    }

    @Override
    public List<ExpenseResponse> getExpensesGreaterThanAmountByUserId(Long userId, Double amount) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return expenseRepository.findExpensesGreaterThanAmountByUserId(userId, amount).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExpenseResponse> getExpensesLessThanAmountByUserId(Long userId, Double amount) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return expenseRepository.findExpensesLessThanAmountByUserId(userId, amount).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExpenseResponse> getExpensesByDescriptionContainingIgnoreCase(Long userId, String keyword) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return expenseRepository.findExpensesByDescriptionContainingIgnoreCase(userId, keyword).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Double getTotalAmountByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return expenseRepository.findTotalAmountByUserId(userId);
    }


    @Override
    public List<Object[]> getExpenseCountByMonthForUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return expenseRepository.countExpensesByMonthForUser(userId);
    }

    @Override
    public List<Object[]> getExpenseCountByYearForUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return expenseRepository.countExpensesByYearForUser(userId);
    }

    private ExpenseResponse convertToResponse(Expense expense) {
        return ExpenseResponse.builder()
                .id(expense.getId())
                .amount(expense.getAmount())
                .date(expense.getDate())
                .description(expense.getDescription())
                .userId(expense.getUser().getId())
                .username(expense.getUser().getUsername())
                .build();
    }
}
