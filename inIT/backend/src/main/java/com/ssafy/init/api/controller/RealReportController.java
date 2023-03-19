package com.ssafy.init.api.controller;

import com.ssafy.init.api.request.RealReportReq;
import com.ssafy.init.api.response.RealReportRes;
import com.ssafy.init.api.service.RealReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api("실전 면접 일지 API")
@RestController
@RequestMapping("/realreport")
public class RealReportController {
    private static final Logger logger = LoggerFactory.getLogger(RealReportController.class);
    private static final String SUCCESS = "success";
//    private static final String FAIL = "fail";

    @Autowired
    private RealReportService realReportService;

    
    // [smj] 실전 면접 일지 생성
    @ApiOperation(value="실전 면접 일지 작성", notes = "id 제외 나머지 데이터 필수 입력", response = RealReportRes.class)
    @PostMapping("/insert")
    public ResponseEntity<RealReportRes> insert(@RequestBody RealReportReq realReportReq) {
        logger.debug("[ 실전 면접 일지 작성 프로세스 호출 ]");
        
        RealReportRes result = realReportService.create(realReportReq);
        
        if(result != null) {
        	return new ResponseEntity<RealReportRes>(result, HttpStatus.CREATED);
        	
        } else {
        	return new ResponseEntity<RealReportRes>(result, HttpStatus.BAD_REQUEST);
        }
    }

	// [smj] 실전 면접 일지 상세 조회 
    @ApiOperation(value="실전 면접 일지 조회", notes = "실전 면접 일지 식별자(id) 필수 입력", response = RealReportRes.class)
    @GetMapping("/{realReportId}")
    public ResponseEntity<RealReportRes> selectAll(@PathVariable int realReportId) {
        logger.debug("[ 실전 면접 일지 조회 프로세스 호출 ]");

        RealReportRes result = realReportService.detail(realReportId);
        
        return new ResponseEntity<RealReportRes>(result, HttpStatus.OK);
    }
    
	// [smj] 실전 면접 일지 수정
    @ApiOperation(value="실전 면접 일지 수정", notes = "모든 데이터 필수 입력", response = RealReportRes.class)
    @PostMapping("/update")
    public ResponseEntity<RealReportRes> update(@RequestBody RealReportRes realReportRes) {
        logger.debug("[ 나만의 질문 수정 프로세스 호출 ]");

        RealReportRes result = realReportService.update(realReportRes);
        
        if(result != null) {
        	return new ResponseEntity<RealReportRes>(result, HttpStatus.OK);
        } else {
        	return new ResponseEntity<RealReportRes>(result, HttpStatus.BAD_REQUEST);
        }
    }
    /*
    @ApiOperation(value="실전 면접 일지 수정", notes = "모든 데이터 필수 입력", response = RealReportRes.class)
    @PostMapping("/update")
    public ResponseEntity<RealReportRes> update(@RequestBody RealReportReq realReportReq) {
        logger.debug("[ 나만의 질문 수정 프로세스 호출 ]");

        RealReportRes result = realReportService.update(realReportReq);
        
        if(result != null) {
        	return new ResponseEntity<RealReportRes>(result, HttpStatus.OK);
        } else {
        	return new ResponseEntity<RealReportRes>(result, HttpStatus.BAD_REQUEST);
        }
    }
    */

	// [smj] 실전 면접 일지 삭제
    @ApiOperation(value="실전 면접 일지 삭제", notes = "id, userId만 필수 입력", response = String.class)
    @DeleteMapping("/delete/{id}/{userId}")
    public ResponseEntity<String> delete(@PathVariable int id, @PathVariable int userId) {
        logger.debug("[ 실전 면접 일지 삭제 프로세스 호출 ]");

//       String result = realReportService.delete(realReportReq.getId(), realReportReq.getUserId());
       String result = realReportService.delete(id, userId);
        
        if(result.equals(SUCCESS)) {
        	return new ResponseEntity<String>(result, HttpStatus.OK);
        } else {
        	return new ResponseEntity<String>(result, HttpStatus.BAD_REQUEST);
        }
    }
    
}
