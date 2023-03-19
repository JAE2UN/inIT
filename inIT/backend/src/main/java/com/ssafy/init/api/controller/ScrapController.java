package com.ssafy.init.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.init.api.request.ScrapReq;
import com.ssafy.init.api.response.ScrapRes;
import com.ssafy.init.api.service.ScrapService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("오답노트 API")
@RestController
@RequestMapping("/scrap")
public class ScrapController {
	private static final Logger logger = LoggerFactory.getLogger(ScrapController.class);
//    private static final String SUCCESS = "success";
//    private static final String FAIL = "fail";
    
    @Autowired
    private ScrapService scrapService;


 	// [smj] 오답 노트 작성(항목 추가)
  	@ApiOperation(value="오답 노트 작성(항목 추가)", notes = "문제 식별자(questionId(=questNo)), 유저 식별자(userId) 데이터 필수 입력. 1: 정상 작동, 0: db 처리하다 에러", response = Integer.class)
  	@PostMapping("/insert")
  	public ResponseEntity<Integer> insert(@RequestBody ScrapReq scrapReq) {
  	    logger.debug("[ 오답 노트 작성(항목 추가) 프로세스 호출 ]");
  	    
  	    
  	    int result = scrapService.insert(scrapReq.getQuestionId(), scrapReq.getUserId());
  	   
  	    if(result == 1) {
  	    	return new ResponseEntity<Integer>(result, HttpStatus.OK);
          } else {
          	return new ResponseEntity<Integer>(result, HttpStatus.BAD_REQUEST);
          }
  	}
    
  
    // [smj] 오답 노트 조회 (리스트)
 	@ApiOperation(value="오답 노트 조회 (리스트)", notes = "사용자 식별자(userId) 필수 입력", response = List.class)
 	@GetMapping("/{userId}")
 	public ResponseEntity<List<ScrapRes>> selectAll(@PathVariable int userId) {
 	    logger.debug("[ 오답 노트 조회 (리스트) 프로세스 호출 ]");
 	    
 	    List<ScrapRes> scrapResList = scrapService.selectAll(userId);
 	    
 	    if(scrapResList != null) {	    	
 	    	return new ResponseEntity<List<ScrapRes>>(scrapResList, HttpStatus.OK);
         } else {
         	return new ResponseEntity<List<ScrapRes>>(scrapResList, HttpStatus.BAD_REQUEST);
         }
 	}

    // [smj] 오답 노트 항목 수정 (답변만 수정 가능)
 	@ApiOperation(value="오답 노트 항목 수정 (답변만 수정 가능)", notes = "오답노트 식별자(id), 유저 식별자(userId), 대답(ans) 데이터 필수 입력", response = ScrapRes.class)
  	@PostMapping("/update")
  	public ResponseEntity<ScrapRes> update(@RequestBody ScrapReq scrapReq) {
  	    logger.debug("[ 오답 노트 항목 수정 프로세스 호출 ]");
  	    
  	    
  	    ScrapRes scrapRes = scrapService.update(scrapReq);
  	   
  	    if(scrapRes != null) {
  	    	return new ResponseEntity<ScrapRes>(scrapRes, HttpStatus.OK);
          } else {
          	return new ResponseEntity<ScrapRes>(scrapRes, HttpStatus.BAD_REQUEST);
          }
  	}
 	
 	// [smj] 오답 노트 항목 삭제
 	@ApiOperation(value="오답 노트 항목 삭제", notes = "오답노트 식별자 (scrapI), 유저 식별자(userId) 데이터 필수 입력. -1: 오답노트 작성자 != 삭제하려는 사용자, 1: 정상 작동, 0: db 처리하다 에러", response = Integer.class)
 	@DeleteMapping("/{userId}/{scrapId}")
 	public ResponseEntity<Integer> delete(@PathVariable int userId, @PathVariable int scrapId) {
 	    logger.debug("[ 오답 노트 항목 삭제 프로세스 호출 ]");
 	    
 	    int result = scrapService.delete(userId, scrapId);
 	    
 	    if(result == 1) {	    	
 	    	return new ResponseEntity<Integer>(result, HttpStatus.OK);
         } else if(result == -1){
         	return new ResponseEntity<Integer>(result, HttpStatus.BAD_REQUEST);
         } else {
        	 return new ResponseEntity<Integer>(result, HttpStatus.INTERNAL_SERVER_ERROR);
         }
 	}
 	
}
