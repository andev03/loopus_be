package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.WalletTransactionDto;
import com.loopus.loopus_be.model.WalletTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class})
public interface WalletTransactionMapper {
    List<WalletTransactionDto> toDtoList(List<WalletTransaction> models);

    WalletTransactionDto toDto(WalletTransaction model);
}
