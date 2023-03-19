package com.ssafy.init.api.service;

import java.util.List;

import com.ssafy.init.api.request.ChecklistReq;
import com.ssafy.init.api.response.AnswerRes;
import com.ssafy.init.api.response.ChecklistRes;

public interface RealQaService {

	// 체크리스트 생성
	String createList(int rid);
	
	// 체크리스트 작성(수정, update)
	boolean writeList(ChecklistReq clReq) throws Exception;
	
	// 체크리스트 조회1 - 체크리스트 항목
	ChecklistRes getClist(int rid);

	// 체크리스트 조회2 - 문제(&평가) 항목
	List<AnswerRes> getAnsList(int rid);

}
