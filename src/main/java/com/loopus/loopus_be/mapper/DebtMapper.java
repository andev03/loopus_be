package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.DebtDto;
import com.loopus.loopus_be.model.Debt;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,uses = UserMapper.class)
public interface DebtMapper {
    List<DebtDto> toDtoList(List<Debt> models);

    DebtDto toDto(Debt model);
}
