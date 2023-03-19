package com.ssafy.init.api.controller;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.init.api.response.AnswerRes;
import com.ssafy.init.api.service.DashboardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("대시보드 API")
@RestController
@RequestMapping("/dashboard")
public class DashboardController {
	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    
    @Autowired
    private DashboardService dashboardService;

    
	// [smj] 일반 면접 연습 기록 최근 5개 조회
 	@ApiOperation(value="일반 면접 연습 기록 최근 5개 조회", notes = "유저 식별자 필수 입력", response = Map.class)
 	@GetMapping("/commonTop5/{userId}")
 	public ResponseEntity<Map<Integer, Map<String, List<AnswerRes>>>> selectCommonByRecent5(@PathVariable int userId) {
 	    logger.debug("[ 일반 면접 연습 기록 최근 5개 조회 프로세스 호출 ]");
 	    
 	    Map<Integer, Map<String, List<AnswerRes>>> answerResList = dashboardService.collectCommonAns(userId);
 	    
 	    if(answerResList != null) {	    	
 	    	return new ResponseEntity<Map<Integer, Map<String, List<AnswerRes>>>>(answerResList, HttpStatus.OK);
         } else {
         	return new ResponseEntity<Map<Integer, Map<String, List<AnswerRes>>>>(answerResList, HttpStatus.BAD_REQUEST);
         }
 	    
 	}
    
    
	// [smj] 기술 면접 연습 기록 최근 5개 조회
 	@ApiOperation(value="기술 면접 연습 기록 최근 5개 조회", notes = "유저 식별자 필수 입력", response = Map.class)
 	@GetMapping("/csTop5/{userId}")
 	public ResponseEntity<List<AnswerRes>> selectCsByRecent5(@PathVariable int userId) {
 	    logger.debug("[ 기술 면접 연습 기록 최근 5개 조회 프로세스 호출 ]");
 	    
 	    List<AnswerRes> answerResList = dashboardService.collectCsAns(userId);
 	    
 	    if(answerResList != null) {	    	
 	    	return new ResponseEntity<List<AnswerRes>>(answerResList, HttpStatus.OK);
         } else {
         	return new ResponseEntity<List<AnswerRes>>(answerResList, HttpStatus.BAD_REQUEST);
         }
 	}
 	
 	
 	
 	// [smj] 면접 일지 상세화면에 보이는 평가 요약 데이터
 	@ApiOperation(value="면접 일지 상세화면에 보이는 평가 요약 데이터 조회", notes = "면접 일지 식별자 필수 입력", response = List.class)
 	@GetMapping("/{reportId}")
 	public ResponseEntity< Map<Integer, Double>> summaryEval(@PathVariable int reportId) {
 	   logger.debug("[ 면접 일지 상세화면에 보이는 평가 요약 데이터 조회 프로세스 호출 ]");
 	    
 	   Map<Integer, Double> summaryData = dashboardService.summaryEval(reportId);
 	   
 	   
 	  return new ResponseEntity< Map<Integer, Double>>(summaryData, HttpStatus.OK);
 	}
 
}
