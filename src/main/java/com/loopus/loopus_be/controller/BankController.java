package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.BankDto;
import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.service.IService.IBankService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/banks")
@RequiredArgsConstructor
@Validated
public class BankController {

    private final IBankService iBankService;

    // 🟢 Thêm ngân hàng mới
    @PostMapping
    public ResponseDto<Object> createBank(@Valid @RequestBody BankDto bank) {
        return ResponseDto.builder()
                .data(iBankService.createBank(bank))
                .status(HttpStatus.CREATED.value())
                .message("Thêm ngân hàng thành công")
                .build();
    }

    // 🟡 Cập nhật thông tin ngân hàng
    @PutMapping("/{bankId}")
    public ResponseDto<Object> updateBank(
            @Valid @RequestBody BankDto updatedBank) {
        return ResponseDto.builder()
                .data(iBankService.updateBank(updatedBank))
                .status(HttpStatus.OK.value())
                .message("Cập nhật ngân hàng thành công")
                .build();
    }

    @DeleteMapping("/{bankId}")
    public ResponseDto<Object> deleteBank(@PathVariable UUID bankId) {
        iBankService.deleteBank(bankId);
        return ResponseDto.builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Xoá ngân hàng thành công")
                .build();
    }

    @GetMapping
    public ResponseDto<Object> getAllBanks() {
        List<BankDto> banks = iBankService.getAllBanks();
        return ResponseDto.builder()
                .data(banks)
                .status(HttpStatus.OK.value())
                .message("Danh sách ngân hàng")
                .build();
    }

    @GetMapping("/{bankId}")
    public ResponseDto<Object> getBankById(@PathVariable UUID bankId) {
        return ResponseDto.builder()
                .data(iBankService.getBankById(bankId))
                .status(HttpStatus.OK.value())
                .message("Chi tiết ngân hàng")
                .build();
    }
}
