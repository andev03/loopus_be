package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.GroupAlbumDto;
import com.loopus.loopus_be.model.GroupAlbum;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class})
public interface GroupAlbumMapper {
    List<GroupAlbumDto> toDtoList(List<GroupAlbum> models);

    GroupAlbumDto toDto(GroupAlbum model);
}
