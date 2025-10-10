package com.loopus.loopus_be.service.IService;

import com.loopus.loopus_be.dto.GroupAlbumDto;
import com.loopus.loopus_be.dto.request.CreateAlbumRequest;
import jakarta.validation.Valid;

import java.util.UUID;

public interface IAlbumService {
    GroupAlbumDto createAlbum(@Valid CreateAlbumRequest request);

    GroupAlbumDto updateAlbum(UUID albumId, String description);

    void deleteAlbum(UUID albumId);
}
