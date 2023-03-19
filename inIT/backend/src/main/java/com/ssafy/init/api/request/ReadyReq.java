package com.ssafy.init.api.request;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "ReadyReq: 면접시작을 위한 준비 과정에서 API요청에 필요한 답변 모음")
public class ReadyReq {

	// 면접 시작 하기 전에 면접일지 -> 연습할질문(answer), 체크리스트 생성
	// 브라우저에서 받을 값
	// userid, 문제유형(구분), 선택한질문
	
	@ApiModelProperty(value = "사용자 id", example = "1")
	private int userId;
	
	@ApiModelProperty(value="면접구분(1:일반,2:기술)", example = "1")
	private int type;
	
	@ApiModelProperty(value="선택질문수([서버,사용자질문리스트])", example = "[2, 5]")
	private List<Integer> choiceNum;
	
	@ApiModelProperty(value="선택질문(일반(1:서버,2:사용자질문리스트), 기술(21~26,29:서버,20:사용자질문리스트)", example = "[21, 26, 20]")
	private List<Integer> choiceQuest;


}
