package com.ssafy.init.api.response;


import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "ChecklistRes: 체크리스트 조회 API 요청에 필요한 응답 모음")
public class ChecklistRes {
	// 체크리스트 조회
	// 체크리스트 id, reportId, 총평, 면접유형(구분)
	// answerRes: 문제유형(구분), 문제번호&문제(평가)&답변(일반평가/CS평가)&문제유형(구분, 일반)
	
	
	@ApiModelProperty(value = "면접일지 id(=면접연습 고유번호)", example = "3")
	private int reportId;
	
	@ApiModelProperty(value="총평(메모)", example = "인성질문은 대답 잘함, CS 네트워크 조짐, 플젝은 꼬리질문 추가연습하자")
	private String allCmt;
	
	@ApiModelProperty(value="면접구분(1:일반,2:기술)", example = "1")
	private int type;
	
	@ApiModelProperty(value = "질문,평가,답변리스트", example = "['조회해보세요']")
	private List<AnswerRes> answerRes;
	
	

}
