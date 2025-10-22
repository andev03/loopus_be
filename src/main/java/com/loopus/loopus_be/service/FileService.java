package com.loopus.loopus_be.service;

import com.loopus.loopus_be.service.IService.IFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileService implements IFileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.url-prefix}")
    private String prefixUrl;

    @Override
    public String uploadFileUrl(MultipartFile file) {
        String filename = UUID.randomUUID() + ".png";
        Path path = Paths.get(uploadDir, filename);

        try {
            Files.createDirectories(path.getParent());

            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return prefixUrl + filename;
    }

    @Override
    public String uploadFileApkUrl(MultipartFile file) {
        String filename = ".apk";
        Path path = Paths.get(uploadDir, filename);

        try {
            Files.createDirectories(path.getParent());

            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return prefixUrl + filename;
    }
}
