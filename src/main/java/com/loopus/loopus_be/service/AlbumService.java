package com.loopus.loopus_be.service;

import com.loopus.loopus_be.dto.GroupAlbumDto;
import com.loopus.loopus_be.dto.request.CreateAlbumRequest;
import com.loopus.loopus_be.exception.GroupException;
import com.loopus.loopus_be.mapper.GroupAlbumMapper;
import com.loopus.loopus_be.model.Group;
import com.loopus.loopus_be.model.GroupAlbum;
import com.loopus.loopus_be.model.Users;
import com.loopus.loopus_be.repository.GroupAlbumRepository;
import com.loopus.loopus_be.repository.GroupRepository;
import com.loopus.loopus_be.repository.UserRepository;
import com.loopus.loopus_be.service.IService.IAlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlbumService implements IAlbumService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final GroupAlbumRepository groupAlbumRepository;
    private final GroupAlbumMapper groupAlbumMapper;

    @Override
    @Transactional
    public GroupAlbumDto createAlbum(CreateAlbumRequest request) {
        Group group = groupRepository.findById(request.getGroupId()).orElseThrow(
                () -> new GroupException("Không tìm thấy nhóm")
        );

        Users user = userRepository.findById(request.getCreatedBy()).orElseThrow(
                () -> new GroupException("Không tìm thấy nhóm")
        );

        GroupAlbum groupAlbum = groupAlbumRepository.save(
                GroupAlbum.builder()
                        .group(group)
                        .createdBy(user)
                        .name(request.getName())
                        .build()
        );

        return groupAlbumMapper.toDto(groupAlbum);
    }

    @Override
    @Transactional
    public GroupAlbumDto updateAlbum(UUID albumId, String name) {

        GroupAlbum groupAlbum = groupAlbumRepository.findById(albumId).orElseThrow(
                () -> new GroupException("Không tìm thấy album")
        );

        groupAlbum.setName(name);

        return groupAlbumMapper.toDto(groupAlbum);
    }

    @Override
    @Transactional
    public void deleteAlbum(UUID albumId) {
        GroupAlbum groupAlbum = groupAlbumRepository.findById(albumId).orElseThrow(
                () -> new GroupException("Không tìm thấy album")
        );

        groupAlbumRepository.delete(groupAlbum);
    }

    @Override
    public List<GroupAlbumDto> getAllGroupId(UUID groupId) {

        List<GroupAlbum> groupAlbums = groupAlbumRepository.findAllByGroup_GroupId(groupId);

        return groupAlbumMapper.toDtoList(groupAlbums);
    }
}
