package com.loopus.loopus_be.service.IService;

import com.loopus.loopus_be.dto.ExpenseDto;
import com.loopus.loopus_be.dto.request.CreateExpenseRequest;
import com.loopus.loopus_be.dto.request.UpdateExpenseParticipantRequest;
import com.loopus.loopus_be.dto.request.UpdateExpenseRequest;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface IExpenseService {

    List<ExpenseDto> getAllByGroupId(UUID groupId);

    ExpenseDto createExpense(@Valid CreateExpenseRequest request);

    ExpenseDto updateExpense(@Valid UpdateExpenseRequest request);

    void deleteExpense(@Valid UUID expenseId);
}
