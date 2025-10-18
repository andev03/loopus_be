package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.request.CreateAlbumRequest;
import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.service.IService.IAlbumService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/albums")
@RequiredArgsConstructor
@Validated
public class AlbumController {

    private final IAlbumService iAlbumService;

    @PostMapping
    @Operation(summary = "Tạo album nhóm")
    public ResponseDto<Object> createAlbum(@RequestBody @Valid CreateAlbumRequest request) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iAlbumService.createAlbum(request))
                .message("Tạo album thành công")
                .build();
    }

    @DeleteMapping("/{albumId}")
    @Operation(summary = "Xoá album")
    public ResponseDto<Object> deleteStoryByAlbumId(@PathVariable UUID albumId) {

        iAlbumService.deleteAlbum(albumId);

        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Xoá album thành công!")
                .build();
    }

    @GetMapping("/{groupId}")
    @Operation(summary = "Xem tất cả album của 1 nhóm!")
    public ResponseDto<Object> getAllGroupId(@PathVariable UUID groupId) {

        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iAlbumService.getAllGroupId(groupId))
                .message("Xem album thành công!")
                .build();
    }

    @PutMapping("/{albumId}")
    @Operation(summary = "Cập nhật album")
    public ResponseDto<Object> updateStoryByAlbumId(
            @PathVariable UUID albumId,
            @RequestBody @Valid @NotNull(message = "Vui lòng nhập name") String name
    ) {

        if (name != null && name.startsWith("\"") && name.endsWith("\"")) {
            name = name.substring(1, name.length() - 1);
        }

        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .data(iAlbumService.updateAlbum(albumId, name))
                .message("Cập nhật album thành công!")
                .build();
    }
}
