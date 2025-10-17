package com.loopus.loopus_be.service.IService;

import com.loopus.loopus_be.dto.TransactionDto;

import java.util.List;
import java.util.UUID;

public interface ITransactionService {

    List<TransactionDto> getAllByType(String transactionType);

    List<TransactionDto> getAllOfUserAndType(UUID userId, String transactionType);

    TransactionDto getTransactionDetail(UUID transactionId);
}
