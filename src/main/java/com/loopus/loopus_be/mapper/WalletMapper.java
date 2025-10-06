package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.WalletDto;
import com.loopus.loopus_be.model.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class, WalletTransactionMapper.class})
public interface WalletMapper {
    List<WalletDto> toDtoList(List<Wallet> models);

    WalletDto toDto(Wallet model);
}
