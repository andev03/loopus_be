package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.service.IService.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final IFileService iFileService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto<Object> uploadFile(@RequestPart("file") MultipartFile file) {

        return ResponseDto.builder()
                .data(iFileService.uploadFileApkUrl(file))
                .status(HttpStatus.OK.value())
                .message("Cập nhật avatar thành công")
                .build();
    }
}
