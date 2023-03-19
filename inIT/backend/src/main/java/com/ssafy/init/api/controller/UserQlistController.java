package com.ssafy.init.api.controller;

import com.ssafy.init.api.request.UserQlistReq;
import com.ssafy.init.api.response.UserQlistRes;
import com.ssafy.init.api.service.UserQlistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api("나만의 질문 리스트 API")
@RestController
@RequestMapping("/userQuestion")
public class UserQlistController {
    private static final Logger logger = LoggerFactory.getLogger(UserQlistController.class);
    private static final String SUCCESS = "success";
//    private static final String FAIL = "fail";

    @Autowired
    private UserQlistService userQlistService;  
    
    
	// [smj] 질문 추가
    @ApiOperation(value="나만의 질문 추가", notes = "id 제외 4개 데이터 필수 입력", response = UserQlistRes.class)
    @PostMapping("/insert")
    public ResponseEntity<UserQlistRes> insert(@RequestBody UserQlistReq userQuestion) {
        logger.debug("[ 나만의 질문 추가 프로세스 호출 ]");

        UserQlistRes result = userQlistService.insertUserQuestion(userQuestion);
        
        if(result != null) {
        	return new ResponseEntity<UserQlistRes>(result, HttpStatus.CREATED);
        } else {
        	return new ResponseEntity<UserQlistRes>(result, HttpStatus.BAD_REQUEST);
        }
    }

    // [smj] 질문 리스트 조회
    @ApiOperation(value="나만의 질문 리스트 조회", notes = "userId 필수 입력", response = List.class)
    @GetMapping("/{userId}")
    public ResponseEntity<List<UserQlistRes>> selectAll(@PathVariable int userId) {
        logger.debug("[ 나만의 질문 리스트 조회 프로세스 호출 ]");

        List<UserQlistRes> result = userQlistService.selectAllUserQuestion(userId);
        
        return new ResponseEntity<List<UserQlistRes>>(result, HttpStatus.OK);
    }
    
    // [smj] 질문 수정
    @ApiOperation(value="나만의 질문 수정", notes = "5개 데이터 모두 필수 입력", response = UserQlistRes.class)
    @PostMapping("/update")
    public ResponseEntity<UserQlistRes> update(@RequestBody UserQlistReq userQuestion) {
        logger.debug("[ 나만의 질문 수정 프로세스 호출 ]");

        UserQlistRes result = userQlistService.updateUserQuestion(userQuestion);
        
        if(result != null) {
        	return new ResponseEntity<UserQlistRes>(result, HttpStatus.OK);
        } else {
        	return new ResponseEntity<UserQlistRes>(result, HttpStatus.BAD_REQUEST);
        }
    }

	// [smj] 질문 삭제
    // ☆★☆  질문 리스트를 반환해줘야하나
    @ApiOperation(value="나만의 질문 삭제", notes = "id, userId 필수 입력", response = String.class)
    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody UserQlistReq userQuestion) {
        logger.debug("[ 나만의 질문 삭제 프로세스 호출 ]");

       String result = userQlistService.deleteUserQuestion(userQuestion.getId());
        
        if(result.equals(SUCCESS)) {
        	return new ResponseEntity<String>(result, HttpStatus.OK);
        } else {
        	return new ResponseEntity<String>(result, HttpStatus.BAD_REQUEST);
        }
    }
    
	
}
