package com.ssafy.init.api.controller;

import com.ssafy.init.api.request.WebrtcReq;
import com.ssafy.init.api.response.BoardRes;
import com.ssafy.init.api.response.WebrtcRes;
import com.ssafy.init.api.service.WebrtcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("Openvidu 처리 API")
@RestController
@RequestMapping("/webrtc")
public class WebrtcController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    WebrtcService webrtcService;

    @ApiOperation(value="동영상 code 저장", notes = "동영상 code 하나 씩 저장", response = Boolean.class)
    @PostMapping
    public ResponseEntity<?> upload(@RequestBody WebrtcReq webrtcReq) {
        logger.debug("[ 동영상 code 업로드 호출 ]");
        System.out.println(">>> WebrtcController upload : " + webrtcReq);

        boolean result = webrtcService.upload(webrtcReq);

        if (result) {
            System.out.println(">>> 동영상 데이터 업로드 성공");
            return (new ResponseEntity<String>(SUCCESS, HttpStatus.OK));
        }
        else {
            System.out.println(">>> 동영상 데이터 업로드 실패");
            return (new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @ApiOperation(value="면접일지 묶음으로 동영상 urlList 정보 얻기", notes = "면접일지 묶음으로 동영상 urlList 정보 얻기(동영상 저장 순으로 반환)", response = WebrtcRes.class)
    @GetMapping("/{reportId}/{userId}")
    public ResponseEntity<List<WebrtcRes>> getUrlList(@PathVariable int reportId, @PathVariable int userId) {
        logger.debug("[ 면접일지 동영상 url리스트 정보 얻기 호출 ]");
        System.out.println(">>> WebrtcController getUrlList : reportId = " + reportId);

        List<WebrtcRes> webrtcResList = webrtcService.getUrlList(reportId, userId);

        if (webrtcResList != null) {
            System.out.println(">>> 동영상 url리스트 정보 얻기 성공");
            webrtcResList.forEach(System.out::println);
            return (new ResponseEntity<List<WebrtcRes>>(webrtcResList, HttpStatus.OK));
        }
        else {
            System.out.println(">>> 동영상 url리스트 정보 얻기 실패");
            return (new ResponseEntity<List<WebrtcRes>>(webrtcResList, HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}
