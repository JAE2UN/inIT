package com.ssafy.init.api.request;


import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "ChecklistReq: 체크리스트 작성 API 요청에 필요한 RequestBody 모음")
public class ChecklistReq {
	// 체크리스트 작성
	// 체크리스트 id, reportId, answerID, 총평, 면접유형(구분)
	// 문제유형(구분), 문제번호 & 문제답변(일반평가/CS평가)
	
	@ApiModelProperty(value = "면접일지 id(=면접연습 고유번호)", example = "3")
	private int reportId;
	
	@ApiModelProperty(value="총평(메모)", example = "인성질문은 대답 잘함, CS 네트워크 조짐, 플젝은 꼬리질문 추가연습하자")
	private String allCmt;
	
	@ApiModelProperty(value="면접구분(1:일반,2:기술)", example = "1")
	private int type;
	
	@ApiModelProperty(value="문제구분(1:통합(question),2:평가(eval))", example = "2")
	private int flag;
	
	@ApiModelProperty(value = "평가점수리스트(문제번호, 점수)", example = "[{'id':1, 'evalScore':4, 'csScore':0}, {'id':2, 'evalScore':5, 'csScore':0}]")
	private List<AnswerReq> answerReq;
	
	

}
