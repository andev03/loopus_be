package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.UsersDto;
import com.loopus.loopus_be.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    List<UsersDto> toDtoList(List<Users> models);

    @Mapping(source = "bank.bankId", target = "bankId")
    UsersDto toDto(Users model);
}
