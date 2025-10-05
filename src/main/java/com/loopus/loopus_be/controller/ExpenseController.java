package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.request.CreateExpenseRequest;
import com.loopus.loopus_be.dto.request.UpdateExpenseRequest;
import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.service.IService.IExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class ExpenseController {

    private final IExpenseService iExpenseService;

    @GetMapping("/expenses")
    public ResponseDto<Object> getExpensesByGroupId(@RequestParam UUID groupId) {
        return ResponseDto.builder()
                .data(iExpenseService.getAllByGroupId(groupId))
                .status(HttpStatus.OK.value())
                .message("Lấy danh sách chi tiêu thành công")
                .build();
    }

    @PostMapping("/expense")
    @Operation(summary = "Tạo chi tiêu", description = "Type là 'equal' cho chia đều hoặc 'exact' cho chia tiền theo số tiền đã nhập")
    public ResponseDto<Object> createExpensesByGroupId(@RequestBody @Valid CreateExpenseRequest request) {
        return ResponseDto.builder()
                .data(iExpenseService.createExpense(request))
                .status(HttpStatus.OK.value())
                .message("Tạo chi tiêu thành công")
                .build();
    }

    @PutMapping("/expense")
    public ResponseDto<Object> updateExpensesByGroupId(@RequestBody @Valid UpdateExpenseRequest request) {
        return ResponseDto.builder()
                .data(iExpenseService.updateExpense(request))
                .status(HttpStatus.OK.value())
                .message("Cập nhật chi tiêu thành công")
                .build();
    }

    @DeleteMapping("/expense")
    public ResponseDto<Object> deleteExpensesByGroupId(@RequestParam UUID expenseId) {

        iExpenseService.deleteExpense(expenseId);

        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Xoá chi tiêu thành công")
                .build();
    }

    @GetMapping("/expense/debt-reminder-group")
    public ResponseDto<Object> getExpensesByExpenseId(@RequestParam UUID expenseId) {
        return ResponseDto.builder()
                .data(iExpenseService.getExpenseToDebtReminder(expenseId))
                .status(HttpStatus.OK.value())
                .message("Lấy danh sách chi tiêu thành công")
                .build();
    }

    @GetMapping("/expense/debt-reminder-individual")
    @Operation(summary = "Lấy danh sách chi tiêu cá nhân trong nhóm", description = "userId là người đang đăng nhập, payerId là người cần trả những khoản nợ")
    public ResponseDto<Object> getExpensesByExpenseIdAndGroupId(
            @RequestParam UUID userId, @RequestParam UUID payerId
    ) {
        return ResponseDto.builder()
                .data(iExpenseService.getExpenseToDebtReminderIndividual(userId, payerId))
                .status(HttpStatus.OK.value())
                .message("Lấy danh sách chi tiêu thành công")
                .build();
    }
}
