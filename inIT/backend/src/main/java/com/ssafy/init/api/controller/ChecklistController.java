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

import com.ssafy.init.api.request.ChecklistReq;
import com.ssafy.init.api.response.ChecklistRes;
import com.ssafy.init.api.service.ChecklistService;
import com.ssafy.init.db.entity.Checklist;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("체크리스트 API")
@RestController
@RequestMapping("/check")
public class ChecklistController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    // [sje]
    @Autowired
    private ChecklistService checklistService;
    
    // 1. 체크리스트 작성(수정, 저장) -> 총평(메모), 평가항목 체크(answer insert)
    @ApiOperation(value = "체크리스트 작성(수정)", notes = "체크리스트를 작성하고 결과(수정할 체크리스트)를 반환", response = Checklist.class)
    @PostMapping("/write")
    public ResponseEntity<Integer> write(@RequestBody ChecklistReq clReq) {
    	logger.debug("[ 체크리스트 작성(수정) ]");
    	// 입력된 값을 넣어줌(체크리스트, 답변)
		try {
			int list = checklistService.writeList(clReq);
			if(list > 0) { // 성공시 rid return
	    		return new ResponseEntity<Integer>(list, HttpStatus.OK);
	    	} else { // 실패시 -1 return
	    		return new ResponseEntity<Integer>(-1, HttpStatus.BAD_REQUEST);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    	
    }
    
    // 2. 체크리스트 조회 -> 면접일지, 작성 전 화면에서 보임(질문, 평가, 총평(메모))
    @ApiOperation(value = "체크리스트 조회", notes = "해당 체크리스트가 존재하면 내용을, 없으면 400을 반환", response = Checklist.class)
    @GetMapping("/detail/{id}")
    public ResponseEntity<ChecklistRes> detail(@PathVariable int id) {
    	logger.debug("[ 체크리스트 조회 ]");
    	// 체크리스트를 id로 조회해서 불러온 후 전달
    	ChecklistRes clRes = checklistService.getClist(id);
    	//List<AnswerRes> ans = checklistService.getAnsList(id);
    	//HashMap<Checklist, List<AnswerRes>> result = new HashMap<Checklist, List<AnswerRes>>();
    	//result.put(cl, ans);
    	
    	if(clRes!=null) { // 해당 id의 체크리스트가 존재하면 체크리스트 반환
    		return new ResponseEntity<ChecklistRes>(clRes, HttpStatus.OK);
    	} else {
    		return new ResponseEntity<ChecklistRes>(clRes, HttpStatus.BAD_REQUEST);
    	}
    	
    }

}
