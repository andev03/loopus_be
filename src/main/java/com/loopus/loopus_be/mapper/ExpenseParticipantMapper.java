package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.ExpenseParticipantDto;
import com.loopus.loopus_be.model.ExpenseParticipant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,uses = UserMapper.class)
public interface ExpenseParticipantMapper {
    List<ExpenseParticipantDto> toDtoList(List<ExpenseParticipant> models);

    ExpenseParticipantDto toDto(ExpenseParticipant model);
}
