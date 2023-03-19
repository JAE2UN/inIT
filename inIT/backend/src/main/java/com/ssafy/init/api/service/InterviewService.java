package com.ssafy.init.api.service;

import com.ssafy.init.api.request.InterviewReq;
import com.ssafy.init.api.request.ReadyReq;
import com.ssafy.init.api.response.ChecklistRes;
import com.ssafy.init.api.response.InterviewRes;

public interface InterviewService {

	// 면접 연습에 사용할 질문리스트(답변에 저장) 생성
	int createQuestion(int makeR, ReadyReq readyReq);

	// 평가항목 setting
	ChecklistRes settingEvaluate(int rid);

	// 면접 연습에서 다음에 실행할 질문정보(현재 번호, 내용) 보냄
	InterviewRes getNextQuest(InterviewReq interviewReq);

}
