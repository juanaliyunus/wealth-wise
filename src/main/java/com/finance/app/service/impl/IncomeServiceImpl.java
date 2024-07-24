
package com.finance.app.service.impl;

import com.finance.app.dto.request.IncomeRequest;
import com.finance.app.dto.response.IncomeResponse;
import com.finance.app.exception.IncomeNotFoundException;
import com.finance.app.exception.UserNotFoundException;
import com.finance.app.model.Income;
import com.finance.app.model.User;
import com.finance.app.repository.IncomeRepository;
import com.finance.app.repository.UserRepository;
import com.finance.app.service.IncomeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IncomeServiceImpl implements IncomeService {
    private final IncomeRepository incomeRepository;
    private final UserRepository userRepository;

    @Override
    public List<IncomeResponse> getAllIncomesByUserId(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return incomeRepository.findAll().stream()
                .filter(income -> income.getUser().getId().equals(userId))
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public IncomeResponse getIncomeByIdAndUserId(Long incomeId, Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Income income = incomeRepository.findById(incomeId)
                .orElseThrow(() -> new IncomeNotFoundException("Income not found with id: " + incomeId));

        if (!income.getUser().getId().equals(userId)) {
            throw new IncomeNotFoundException("Income with id " + incomeId + " does not belong to user with id: " + userId);
        }

        return convertToResponse(income);
    }

    @Override
    @Transactional
    public IncomeResponse createIncome(IncomeRequest incomeRequest) {
        User user = userRepository.findById(incomeRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + incomeRequest.getUserId()));

        Income income = Income.builder()
                .description(incomeRequest.getDescription())
                .source(incomeRequest.getSource())
                .amount(incomeRequest.getAmount())
                .date(incomeRequest.getDate())
                .user(user)
                .build();

        incomeRepository.save(income);
        return convertToResponse(income);
    }

    @Override
    @Transactional
    public IncomeResponse updateIncome(Long incomeId, IncomeRequest incomeRequest) {
        User user = userRepository.findById(incomeRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + incomeRequest.getUserId()));

        Income income = incomeRepository.findById(incomeId)
                .orElseThrow(() -> new IncomeNotFoundException("Income not found with id: " + incomeId));

        if (!income.getUser().getId().equals(incomeRequest.getUserId())) {
            throw new IncomeNotFoundException("Income with id " + incomeId + " does not belong to user with id: " + incomeRequest.getUserId());
        }

        income.setDescription(incomeRequest.getDescription());
        income.setSource(incomeRequest.getSource());
        income.setAmount(incomeRequest.getAmount());
        income.setDate(incomeRequest.getDate());
        income.setUser(user);

        incomeRepository.save(income);
        return convertToResponse(income);
    }

    @Override
    @Transactional
    public void deleteIncome(Long incomeId) {
        Income income = incomeRepository.findById(incomeId)
                .orElseThrow(() -> new IncomeNotFoundException("Income not found with id: " + incomeId));

        incomeRepository.delete(income);
    }

    @Override
    public IncomeResponse getMaxAmountIncomeByUserId(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return incomeRepository.findMaxAmountIncomeByUserId(userId)
                .map(this::convertToResponse)
                .orElseThrow(() -> new IncomeNotFoundException("No incomes found for user with id: " + userId));
    }

    @Override
    public IncomeResponse getMinAmountIncomeByUserId(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return incomeRepository.findMinAmountIncomeByUserId(userId)
                .map(this::convertToResponse)
                .orElseThrow(() -> new IncomeNotFoundException("No incomes found for user with id: " + userId));
    }

    @Override
    public List<IncomeResponse> getIncomesGreaterThanAmountByUserId(Long userId, Double amount) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return incomeRepository.findIncomesGreaterThanAmountByUserId(userId, amount).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<IncomeResponse> getIncomesLessThanAmountByUserId(Long userId, Double amount) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return incomeRepository.findIncomesLessThanAmountByUserId(userId, amount).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<IncomeResponse> getIncomesByDescriptionContainingIgnoreCase(Long userId, String keyword) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return incomeRepository.findIncomesByDescriptionContainingIgnoreCase(userId, keyword).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Double getTotalAmountByUserId(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return incomeRepository.findTotalAmountByUserId(userId);
    }

    @Override
    public List<Object[]> getIncomeSumByMonthForUser(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return incomeRepository.findIncomeSumByMonthForUser(userId);
    }

    @Override
    public List<Object[]> getIncomeSumBySourceForUser(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return incomeRepository.findIncomeSumBySourceForUser(userId);
    }

    private IncomeResponse convertToResponse(Income income) {
        return IncomeResponse.builder()
                .id(income.getId())
                .source(income.getSource())
                .amount(income.getAmount())
                .date(income.getDate())
                .userId(income.getUser().getId())
                .description(income.getDescription())
                .username(income.getUser().getUsername())
                .build();
    }

}
