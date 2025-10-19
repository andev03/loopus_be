package com.loopus.loopus_be.service;

import com.loopus.loopus_be.dto.TransactionDto;
import com.loopus.loopus_be.enums.TransactionType;
import com.loopus.loopus_be.mapper.TransactionMapper;
import com.loopus.loopus_be.model.Transaction;
import com.loopus.loopus_be.repository.TransactionRepository;
import com.loopus.loopus_be.repository.UserRepository;
import com.loopus.loopus_be.service.IService.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final UserRepository userRepository;

    @Override
    public List<TransactionDto> getAllByType(String transactionType) {

        if (transactionType == null) {

            return transactionMapper.toDtoList(transactionRepository.findAll());
        }

        List<Transaction> transactions =
                transactionRepository.findAllByTransactionType(TransactionType.valueOf(transactionType));

        return transactionMapper.toDtoList(transactions);
    }

    @Override
    public List<TransactionDto> getAllOfUserAndType(UUID userId, String transactionType) {

        if (transactionType != null) {
            List<Transaction> transactions =
                    transactionRepository.findAllByUser_UserIdAndTransactionType(
                            userId, TransactionType.valueOf(transactionType));
            return transactionMapper.toDtoList(transactions);
        }

        List<Transaction> transactions = transactionRepository.findAllByUser_UserId(userId);

        return transactionMapper.toDtoList(transactions);
    }

    @Override
    public TransactionDto getTransactionDetail(UUID transactionId) {
        return transactionMapper.toDto(transactionRepository.getReferenceById(transactionId));
    }
}
