package com.loopus.loopus_be.service.IService;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    String uploadFileUrl(MultipartFile file);
    String uploadFileApkUrl(MultipartFile file);
}
