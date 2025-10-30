package com.loopus.loopus_be.service.IService;

import com.loopus.loopus_be.dto.BankDto;
import com.loopus.loopus_be.model.Bank;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface IBankService {
    void deleteBank(UUID bankId);

    BankDto createBank(BankDto bank);

    BankDto updateBank(BankDto updatedBank);

    List<BankDto> getAllBanks();

    BankDto getBankById(UUID bankId);

}
