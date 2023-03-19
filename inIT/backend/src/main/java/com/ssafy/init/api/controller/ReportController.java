package com.ssafy.init.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.init.api.request.ReportReq;
import com.ssafy.init.api.response.ReportRes;
import com.ssafy.init.api.service.ReportService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("면접일지 API")
@RestController
@RequestMapping("/report")
public class ReportController {
		private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
//    private static final String SUCCESS = "success";
//    private static final String FAIL = "fail";
    
    @Autowired
    private ReportService reportService;


 	// [smj] 면접일지 작성 및 수정(한줄평, 별점, 태그, 제목 작성)
  	@ApiOperation(value="면접일지 작성 및 수정", notes = "checklistReq 제외 모든 데이터 필수 입력 (존재하지 않는 면접 일지 식별자가 들어오거나, 사용자 식별자가 db에 저장된 것과 다른 값이 들어올 경우 400  코드 반환)", response = ReportRes.class)
  	@PostMapping("/update")
  	public ResponseEntity<ReportRes> update(@RequestBody ReportReq reportReq) {
  	    logger.debug("[ 면접일지 작성 및 수정 프로세스 호출 ]");
  	    
  	    ReportRes returnReportRes = reportService.updateReport(reportReq);
  	    
  	    if(returnReportRes != null) {
  	    	return new ResponseEntity<ReportRes>(returnReportRes, HttpStatus.OK);
          } else {
          	return new ResponseEntity<ReportRes>(returnReportRes, HttpStatus.BAD_REQUEST);
          }
  	}
    
    // [smj] 면접일지 조회
 	@ApiOperation(value="연습 면접 일지 상세조회", notes = "연습 면접 일지 식별자(id) 필수 입력", response = ReportRes.class)
 	@GetMapping("/detail/{id}")
 	public ResponseEntity<ReportRes> detail(@PathVariable int id) {
 	    logger.debug("[ 면접 일지 조회 프로세스 호출 ]");
 	    
 	    ReportRes returnReportRes = reportService.getReport(id);
 	    
 	    if(returnReportRes != null) {	    	
 	    	return new ResponseEntity<ReportRes>(returnReportRes, HttpStatus.OK);
         } else {
         	return new ResponseEntity<ReportRes>(returnReportRes, HttpStatus.BAD_REQUEST);
         }
 	}

	// [smj] 면접일지 리스트 조회
 	@ApiOperation(value="면접일지 전체 조회", notes = "사용자 식별자 필요", response = List.class)
 	@GetMapping("/All/{userId}")
 	public ResponseEntity<List<ReportRes>> selectAll(@PathVariable int userId) {
 	    logger.debug("[ 면접일지 전체 조회 프로세스 호출 ]");
 	  
 	    List<ReportRes> returnReportResList = reportService.getAllReport(userId);;
 	    
 	    if(returnReportResList != null) {	    	
 	    	return new ResponseEntity<List<ReportRes>>(returnReportResList, HttpStatus.OK);
         } else {
         	return new ResponseEntity<List<ReportRes>>(returnReportResList, HttpStatus.BAD_REQUEST);
         }
 	}
 	
	// [smj] 면접일지 구분별 리스트 조회
 	@ApiOperation(value="면접일지 타입별 리스트 조회", notes = "사용자 식별자와 면접 일지 구분자(1: 일반, 2: 기술, 3: 실전) 필수 입력", response = List.class)
 	@GetMapping("/All/{userId}/{type}")
 	public ResponseEntity<List<ReportRes>> selectAllByType(@PathVariable int userId, @PathVariable int type) {
 		List<ReportRes> returnReportResList = reportService.getAllReportByType(userId, type);
 		
 	    if(returnReportResList != null) {	    	
 	    	return new ResponseEntity<List<ReportRes>>(returnReportResList, HttpStatus.OK);
         } else {
         	return new ResponseEntity<List<ReportRes>>(returnReportResList, HttpStatus.BAD_REQUEST);
         }
 	}
 	
	// [smj] 면접일지 태그별 리스트 조회
 	@ApiOperation(value="면접일지 태그별 리스트 조회", notes = "사용자 식별자와 검색 키워드 필수 입력", response = List.class)
 	@GetMapping("/search/{userId}/{searchTag}")
 	public ResponseEntity<List<ReportRes>> selectAllByTag(@PathVariable int userId, @PathVariable String searchTag) {
 		List<ReportRes> returnReportResList = reportService.getAllReportByTag(userId, searchTag);
 		
 	    if(returnReportResList != null) {	    	
 	    	return new ResponseEntity<List<ReportRes>>(returnReportResList, HttpStatus.OK);
         } else {
         	return new ResponseEntity<List<ReportRes>>(returnReportResList, HttpStatus.BAD_REQUEST);
         }
 	}
 	
 	// [smj] 면접일지 삭제 (후순위)  (구현하기!)
 	
 	
}
