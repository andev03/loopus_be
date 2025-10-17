package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.TransactionDto;
import com.loopus.loopus_be.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class})
public interface TransactionMapper {
    List<TransactionDto> toDtoList(List<Transaction> models);

    TransactionDto toDto(Transaction model);
}
