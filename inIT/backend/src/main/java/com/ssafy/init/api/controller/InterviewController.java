package com.ssafy.init.api.controller;

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

import com.ssafy.init.api.request.InterviewReq;
import com.ssafy.init.api.request.ReadyReq;
import com.ssafy.init.api.response.ChecklistRes;
import com.ssafy.init.api.response.InterviewRes;
import com.ssafy.init.api.service.ChecklistService;
import com.ssafy.init.api.service.InterviewService;
import com.ssafy.init.api.service.ReportService;
import com.ssafy.init.db.entity.Checklist;
import com.ssafy.init.db.entity.User;
import com.ssafy.init.db.repository.AnswerRepository;
import com.ssafy.init.db.repository.ChecklistRepository;
import com.ssafy.init.db.repository.ReportRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("면접 연습 API(OpenVidu 제외)")
@RestController
@RequestMapping("/interview")
public class InterviewController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    
    @Autowired
    private InterviewService interviewService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private ChecklistService checklistService;
    @Autowired
    private ReportRepository reportRepository;    
    @Autowired
    private ChecklistRepository checklistRepository;
    @Autowired
    private AnswerRepository answerRepository;
    
    // 1. 면접 연습 시작 - [면접종류, 질문리스트](Request) 선택한거랑 시작요청(POST) -> 바로 시작 or 대기화면?
    //	    면접일지 생성, 해당면접 질문리스트 생성(answer insert), 체크리스트 생성
    // 2. 면접 연습 - 요청마다 질문 순서대로 보냄, 종료시 알려줌
    // 3. 면접 평가 - 종료 후 체크리스트Res(문제, 평가) 보냄

    // [smj, sje] 면접 연습 설정(준비)
    @ApiOperation(value = "면접연습 설정(준비)", notes="면접연습을 위한 준비를 하고 완료되면 결과(success: 준비완료, fail: 다시시도)를 반환", response = String.class)
    @PostMapping("/ready")
    public ResponseEntity<Integer> ready(@RequestBody ReadyReq readyReq) {

    	logger.debug("[ 면접연습 준비 ]");
    	
    	// InterviewReq: userId, 면접종류(type), 선택한 질문종류
    	
    	// 생성 완료시 SUCCESS 반환
    	int makeR = reportService.createReport(readyReq); // 면접일지 생성 -> report id를 반환
    	int makeA = interviewService.createQuestion(makeR, readyReq); // 면접 질문리스트 생성(답변칸)
    	String makeC = checklistService.createList(makeR); // 체크리스트 생성
    	System.out.println("rid: "+makeR+", makeA: "+makeA+", makeC: "+makeC);
    	int allQno = readyReq.getChoiceNum().get(0) + readyReq.getChoiceNum().get(1);
    	// 전부 생성 -> 준비 완료
    	if(makeR>=0 && makeA==allQno && makeC.equals(SUCCESS)) {
    		return new ResponseEntity<Integer>(makeR, HttpStatus.CREATED);
    	} else {
    		// 생성한 값이 존재하면(체크리스트, 면접일지, 질문) 전부 삭제
    		if(makeR > 0) {
    			if(answerRepository.findAllByReportId(makeR) != null) {
    				answerRepository.deleteAllByReportId(makeR);
    				System.out.println("answer delete");
    			}
    			if(checklistRepository.findById(makeR) != null) {
    				checklistRepository.deleteById(makeR);
    				System.out.println("checklist delete");
    			}
    			reportRepository.deleteById(makeR);
    			System.out.println("report delete");
    		}
    		
    		if(makeA == -1) return new ResponseEntity<Integer>(-1, HttpStatus.BAD_REQUEST);
    		else if(makeA == -2) return new ResponseEntity<Integer>(-2, HttpStatus.BAD_REQUEST);
    		else return new ResponseEntity<Integer>(-3, HttpStatus.BAD_REQUEST);
    	}
    }
    
    // 면접 연습 openvidu 설정 -> 파일 분리
    
    // 요청마다 질문 보내기
    @ApiOperation(value = "면접 연습", notes = "면접연습을 하며 연습할 문제와 번호를 보내고(res: questNo, quest) 화상을 녹화해서 받음(req: 영상)", response = String.class)
    @PostMapping("/quest")
    public ResponseEntity<InterviewRes> quest(@RequestBody InterviewReq interviewReq) {
    	
    	InterviewRes interviewRes = interviewService.getNextQuest(interviewReq);
    	if(interviewRes.getNowNo() == -1) { // 질문 다 가져옴 -> 종료
    		return new ResponseEntity<InterviewRes>(interviewRes, HttpStatus.OK);
    	} else if(interviewRes.getNowNo() == -2) { // 질문 없음 등으로 못 가져옴 -> 에러
    		return new ResponseEntity<InterviewRes>(interviewRes, HttpStatus.BAD_REQUEST);
    	} else { // 질문 제대로 가져옴 -> 문제&번호 담긴 res 보내줌(0부터인지는 얘기 후 수정)
    		return new ResponseEntity<InterviewRes>(interviewRes, HttpStatus.OK);
    	}
    	
    }
    
    // 면접 종료 후 평가 -> 영상 보며 체크리스트 작성
    @ApiOperation(value = "면접 평가(체크리스트 조회)", notes = "면접 연습 후 녹화된 영상을 보며 체크리스트를 작성할 수 있게 영상주소와 체크리스트를 보냄", response = Checklist.class)
    @GetMapping("/evaluate/{rid}")
    public ResponseEntity<ChecklistRes> evaulate(@PathVariable int rid) {
    	
    	ChecklistRes clRes = interviewService.settingEvaluate(rid);
		if(clRes.getReportId() == rid) {
    		return new ResponseEntity<ChecklistRes>(clRes, HttpStatus.OK);
    	} else { // return null 등
    		return new ResponseEntity<ChecklistRes>(clRes, HttpStatus.BAD_REQUEST);
    	}

    }
    
    
    
    
}

