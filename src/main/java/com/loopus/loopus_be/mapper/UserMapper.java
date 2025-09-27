package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.UsersDto;
import com.loopus.loopus_be.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    List<UsersDto> toDtoList(List<Users> models);

    UsersDto toDto(Users model);
}
