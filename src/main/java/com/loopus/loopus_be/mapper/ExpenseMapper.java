package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.ExpenseDto;
import com.loopus.loopus_be.model.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserMapper.class, ExpenseMapper.class})
public interface ExpenseMapper {
    List<ExpenseDto> toDtoList(List<Expense> models);

    ExpenseDto toDto(Expense model);
}
