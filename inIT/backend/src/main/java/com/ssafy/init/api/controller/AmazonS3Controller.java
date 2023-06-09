package com.ssafy.init.api.controller;

import com.ssafy.init.api.service.AwsS3Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api("아마존 S3 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class AmazonS3Controller {
    private final AwsS3Service awsS3Service;

    /**
     * Amazon S3에 파일 업로드
     * return 성공 시 200 Success와 함께 업로드 된 url반환
     */
    @ApiOperation(value = "Amazon S3에 파일 업로드", notes = "Amazon S3에 파일 업로드 ")
    @PostMapping(value = "/file/{userId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> uploadFile(@ApiParam(value="(단일) 이미지, 동영상 업로드", required = true) @RequestPart(value="file") MultipartFile multipartFile, @PathVariable int userId) {
        System.out.println("[ 파일 업로드 ]");
        System.out.println("userID : " +  userId + "   " + multipartFile.getContentType(  ));

        if (multipartFile.getContentType().startsWith("image")){
            System.out.println(">>> 이미지 파일 업로드 : " + multipartFile.getOriginalFilename());
            String result = awsS3Service.uploadFileOnlyOne(multipartFile, 0, userId);
            if ("fail".equals(result)) {
                System.out.println(">>> 이미지 업로드 실패");
                return (new ResponseEntity<String>("fail", HttpStatus.INTERNAL_SERVER_ERROR));
            }
            else {
                System.out.println(">>> 이미지 업로드 성공");
                return (new ResponseEntity<String>(result, HttpStatus.OK));
            }
        }
        else if (multipartFile.getContentType().startsWith("video")) {
            System.out.println(">>> 동영상 파일 업로드 : " + multipartFile.getOriginalFilename());
            String result = awsS3Service.uploadFileOnlyOne(multipartFile, 1, userId);
            if ("fail".equals(result)) {
                System.out.println(">>> 동영상 업로드 실패");
                return (new ResponseEntity<String>("fail", HttpStatus.INTERNAL_SERVER_ERROR));
            }
            else {
                System.out.println("동영상 업로드 성공");
                return (new ResponseEntity<String>(result, HttpStatus.OK));
            }
        }
        else {
            System.out.println(">>> 기타 파일 업로드");
            return (new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST));
        }
    }

    /**
     * Amazon S3에 업로드 된 파일을 삭제
     * return 성공 시 200 Success
     */
    @ApiOperation(value = "Amazon S3에 업로드 된 파일을 삭제", notes = "Amazon S3에 업로드된 파일 삭제")
    @DeleteMapping("/file/{userId}")
    public ResponseEntity<Void> deleteFile(@ApiParam(value="파일 하나 삭제(url 전달)", required = true) @RequestParam String url, @PathVariable int userId) {
        System.out.println("[ 파일 삭제 ] : " + url);
        System.out.println("userId : " + userId);

        awsS3Service.deleteFile(url, userId);
        return (new ResponseEntity<Void>(HttpStatus.OK));
    }
}
