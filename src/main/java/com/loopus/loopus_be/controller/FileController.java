package com.loopus.loopus_be.controller;

import com.loopus.loopus_be.dto.response.ResponseDto;
import com.loopus.loopus_be.service.IService.IFileService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

//@RestController
//@RequestMapping("/api/files")
//@RequiredArgsConstructor
public class FileController {
//
//    private final IFileService iFileService;
//
//    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseDto<Object> uploadFile(@RequestPart("file") MultipartFile file) {
//
//        return ResponseDto.builder()
//                .data(iFileService.uploadFileApkUrl(file))
//                .status(HttpStatus.OK.value())
//                .message("Cập nhật avatar thành công")
//                .build();
//    }
}
