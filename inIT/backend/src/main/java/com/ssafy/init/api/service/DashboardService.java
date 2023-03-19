package com.ssafy.init.api.service;

import java.util.List;
import java.util.Map;

import com.ssafy.init.api.response.AnswerRes;

public interface DashboardService {

	// 일반 면접 연습 기록들 최근 5개에 해당하는 answer 모든 데이터 가져오기
	// flag가 2인 애들만! (1인  애들은 연습 시 사용된 질문임)
	Map<Integer, Map<String, List<AnswerRes>>> collectCommonAns(int userId);
	
	// 기술 면접 연습 기록들 최근 5개에 해당하는 answer 모든 데이터 가져오기
	List<AnswerRes> collectCsAns(int userId);
	
	// 면접 일지 상세화면에 보이는 평가 요약 데이터
	Map<Integer, Double> summaryEval(int reportId);
}
