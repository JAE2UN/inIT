package com.ssafy.init.api.service;

import org.springframework.web.multipart.MultipartFile;

public interface AwsS3Service {
    String uploadFileOnlyOne(MultipartFile multipartFile, int flag, int userId);

    void deleteFile(String url, int userId);
}
