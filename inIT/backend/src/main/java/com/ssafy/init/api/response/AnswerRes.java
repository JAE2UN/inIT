package com.ssafy.init.api.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "AnswerRes: 체크리스트 조회, 작성시 필요한 답변 응답값 모음")
public class AnswerRes {
	
	@ApiModelProperty(value="문제유형 구분, (1:통합(question),2:평가(eval))", example = "2")
	private int flag;

	@ApiModelProperty(value = "문제번호", example = "1")
    private int questNo;
	
	@ApiModelProperty(value = "면접 질문", example = "자기소개 해보세요.")
	private String quest;
	
	@ApiModelProperty(value="질문 구분(10:인성,20~29:기술,30:프로젝트)", example = "10")
	private int type;
	
	@ApiModelProperty(value = "일반 평가 점수")
    private int evalScore;
	
	@ApiModelProperty(value = "CS 평가 점수")
    private int csScore;
	

}
