package com.loopus.loopus_be.service;

import com.loopus.loopus_be.dto.BankDto;
import com.loopus.loopus_be.mapper.BankMapper;
import com.loopus.loopus_be.model.Bank;
import com.loopus.loopus_be.repository.BankRepository;
import com.loopus.loopus_be.service.IService.IBankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankService implements IBankService {

    private final BankRepository bankRepository;
    private final BankMapper bankMapper;

    @Override
    public void deleteBank(UUID bankId) {
        Bank bank = bankRepository.getReferenceById(bankId);

        bankRepository.delete(bank);
    }

    @Override
    public BankDto createBank(BankDto bank) {
        return bankMapper.toDto(bankRepository.save(
                Bank.builder()
                        .bankName(bank.getBankName())
                        .binCode(bank.getBinCode())
                        .build()
        ));
    }

    @Override
    public BankDto updateBank(BankDto updatedBank) {
        Bank bank = bankRepository.getReferenceById(updatedBank.getBankId());

        bank.setBankName(updatedBank.getBankName());
        bank.setBinCode(updatedBank.getBankName());

        bankRepository.save(bank);

        return bankMapper.toDto(bank);
    }

    @Override
    public List<BankDto> getAllBanks() {
        return bankMapper.toDtoList(bankRepository.findAll());
    }

    @Override
    public BankDto getBankById(UUID bankId) {
        return bankMapper.toDto(bankRepository.getReferenceById(bankId));
    }
}
