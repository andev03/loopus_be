package com.loopus.loopus_be.service;

import com.loopus.loopus_be.dto.ExpenseDto;
import com.loopus.loopus_be.dto.ExpenseParticipantDto;
import com.loopus.loopus_be.dto.request.CreateExpenseParticipantRequest;
import com.loopus.loopus_be.dto.request.CreateExpenseRequest;
import com.loopus.loopus_be.dto.request.UpdateExpenseParticipantRequest;
import com.loopus.loopus_be.dto.request.UpdateExpenseRequest;
import com.loopus.loopus_be.mapper.ExpenseMapper;
import com.loopus.loopus_be.mapper.ExpenseParticipantMapper;
import com.loopus.loopus_be.model.Expense;
import com.loopus.loopus_be.model.ExpenseParticipant;
import com.loopus.loopus_be.repository.ExpenseParticipantRepository;
import com.loopus.loopus_be.repository.ExpenseRepository;
import com.loopus.loopus_be.repository.GroupRepository;
import com.loopus.loopus_be.repository.UserRepository;
import com.loopus.loopus_be.service.IService.IExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseService implements IExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final ExpenseParticipantRepository expenseParticipantRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final ExpenseParticipantMapper expenseParticipantMapper;

    @Override
    public List<ExpenseDto> getAllByGroupId(UUID groupId) {
        return expenseMapper.toDtoList(expenseRepository.findAllByGroup_GroupId(groupId));
    }

    @Override
    public ExpenseDto createExpense(CreateExpenseRequest request) {

        Expense expense = null;

        if (request.getType().equalsIgnoreCase("equal")) {

            expense = expenseRepository.save(
                    Expense.builder()
                            .description(request.getDescription())
                            .amount(request.getAmount())
                            .group(groupRepository.getReferenceById(request.getGroupId()))
                            .paidBy(userRepository.getReferenceById(request.getPaidById()))
                            .build()
            );

            expense.setParticipants(saveExpenseParticipantsForEquals(request, expense));

        } else {

            BigDecimal otherShareAmount = calculateTotalShareAmount(request.getExpenseParticipant());

            expense = expenseRepository.save(
                    Expense.builder()
                            .description(request.getDescription())
                            .amount(otherShareAmount)
                            .group(groupRepository.getReferenceById(request.getGroupId()))
                            .paidBy(userRepository.getReferenceById(request.getPaidById()))
                            .build()
            );

            expense.setParticipants(saveExpenseParticipantsForOther(request, expense));
        }


        return expenseMapper.toDto(expense);
    }

    @Override
    public ExpenseDto updateExpense(UpdateExpenseRequest request) {
        Expense expense = null;

        if (request.getType().equalsIgnoreCase("equal")) {
            expense = expenseRepository.getReferenceById(request.getExpenseId());

            expense.setPaidBy(userRepository.getReferenceById(request.getPaidById()));
            expense.setDescription(request.getDescription());
            expense.setAmount(request.getAmount());

            expense = expenseRepository.save(expense);

            expense.setParticipants(updateExpenseParticipantsForEquals(request));
        } else {

            BigDecimal otherShareAmount = calculateTotalShareAmountUpdate(request.getExpenseParticipant());

            expense = expenseRepository.getReferenceById(request.getExpenseId());

            expense.setPaidBy(userRepository.getReferenceById(request.getPaidById()));
            expense.setDescription(request.getDescription());
            expense.setAmount(otherShareAmount);

            expense = expenseRepository.save(expense);

            expense.setParticipants(updateExpenseParticipantsForOther(request));
        }

        return expenseMapper.toDto(expense);
    }

    @Override
    public void deleteExpense(UUID expenseId) {
        Expense expense = expenseRepository.getReferenceById(expenseId);

        expenseRepository.delete(expense);
    }

    @Override
    public List<ExpenseParticipantDto> getExpenseToDebtReminder(UUID expenseId) {

        Expense expense = expenseRepository.getReferenceById(expenseId);

        return expenseParticipantMapper.toDtoList(expense.getParticipants());
    }

    @Override
    public List<ExpenseParticipantDto> getExpenseToDebtReminderIndividual(UUID userId, UUID payerId) {
        List<Expense> expenses = expenseRepository.findAllByPaidBy_UserId(userId);

        List<ExpenseParticipant> expenseParticipants = expenseParticipantRepository.findAllByExpenseInAndUser_UserId(expenses, payerId);

        return expenseParticipantMapper.toDtoList(expenseParticipants);
    }

    private List<ExpenseParticipant> updateExpenseParticipantsForEquals(
            UpdateExpenseRequest request
    ) {
        List<ExpenseParticipant> expenseParticipants = new ArrayList<>();

        BigDecimal equalShareAmount = calculateTotalShareAmountForEqualUpdate(request);

        for (UpdateExpenseParticipantRequest updateExpenseParticipantRequest : request.getExpenseParticipant()) {
            ExpenseParticipant expenseParticipant =
                    expenseParticipantRepository.getReferenceById(updateExpenseParticipantRequest.getExpenseParticipantId());

            expenseParticipant.setShareAmount(equalShareAmount);

            expenseParticipants.add(expenseParticipantRepository.save(expenseParticipant));
        }

        return expenseParticipants;
    }


    private List<ExpenseParticipant> updateExpenseParticipantsForOther(
            UpdateExpenseRequest request
    ) {
        List<ExpenseParticipant> expenseParticipants = new ArrayList<>();

        for (UpdateExpenseParticipantRequest updateExpenseParticipantRequest : request.getExpenseParticipant()) {
            ExpenseParticipant expenseParticipant =
                    expenseParticipantRepository.getReferenceById(updateExpenseParticipantRequest.getExpenseParticipantId());
            expenseParticipant.setShareAmount(updateExpenseParticipantRequest.getShareAmount());

            expenseParticipants.add(expenseParticipantRepository.save(expenseParticipant));
        }

        return expenseParticipants;
    }

    private List<ExpenseParticipant> saveExpenseParticipantsForEquals(
            CreateExpenseRequest request, Expense expense
    ) {
        List<ExpenseParticipant> expenseParticipants = new ArrayList<>();

        BigDecimal equalShareAmount = calculateTotalShareAmountForEqual(request);

        for (CreateExpenseParticipantRequest createExpenseParticipantRequest : request.getExpenseParticipant()) {
            expenseParticipants.add(
                    expenseParticipantRepository.save(
                            ExpenseParticipant.builder()
                                    .expense(expense)
                                    .user(userRepository.getReferenceById(createExpenseParticipantRequest.getUserId()))
                                    .shareAmount(equalShareAmount)
                                    .build()
                    )
            );
        }

        return expenseParticipants;
    }

    private List<ExpenseParticipant> saveExpenseParticipantsForOther(
            CreateExpenseRequest request, Expense expense
    ) {
        List<ExpenseParticipant> expenseParticipants = new ArrayList<>();

        for (CreateExpenseParticipantRequest createExpenseParticipantRequest : request.getExpenseParticipant()) {
            expenseParticipants.add(
                    expenseParticipantRepository.save(
                            ExpenseParticipant.builder()
                                    .expense(expense)
                                    .user(userRepository.getReferenceById(createExpenseParticipantRequest.getUserId()))
                                    .shareAmount(createExpenseParticipantRequest.getShareAmount())
                                    .build()
                    )
            );
        }

        return expenseParticipants;
    }

    private BigDecimal calculateTotalShareAmount(List<CreateExpenseParticipantRequest> request) {

        BigDecimal totalShareAmount = BigDecimal.ZERO;

        for (CreateExpenseParticipantRequest expenseParticipantRequest : request) {
            totalShareAmount = totalShareAmount.add(expenseParticipantRequest.getShareAmount());
        }

        return totalShareAmount;
    }

    private BigDecimal calculateTotalShareAmountForEqual(CreateExpenseRequest request) {

        return request.getAmount().divide(BigDecimal.valueOf(request.getExpenseParticipant().size()), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTotalShareAmountUpdate(List<UpdateExpenseParticipantRequest> request) {

        BigDecimal totalShareAmount = BigDecimal.ZERO;

        for (UpdateExpenseParticipantRequest expenseParticipantRequest : request) {
            totalShareAmount = totalShareAmount.add(expenseParticipantRequest.getShareAmount());
        }

        return totalShareAmount;
    }

    private BigDecimal calculateTotalShareAmountForEqualUpdate(UpdateExpenseRequest request) {

        return request.getAmount().divide(BigDecimal.valueOf(request.getExpenseParticipant().size()), 2, RoundingMode.HALF_UP);
    }
}
