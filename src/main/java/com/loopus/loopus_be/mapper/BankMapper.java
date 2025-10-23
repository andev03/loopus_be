package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.BankDto;
import com.loopus.loopus_be.model.Bank;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,uses = UserMapper.class)
public interface BankMapper {
    List<BankDto> toDtoList(List<Bank> models);

    BankDto toDto(Bank model);
}
