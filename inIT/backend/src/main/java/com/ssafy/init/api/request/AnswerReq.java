package com.ssafy.init.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "AnswerReq: 체크리스트 작성 API 요청에 필요한 답변 모음")
public class AnswerReq {
	
	@ApiModelProperty(value = "질문번호", example = "10")
    private int questNo;
	
	@ApiModelProperty(value = "일반 평가 점수", example = "5")
    private int evalScore;
	
	@ApiModelProperty(value = "CS 평가 점수", example = "3")
    private int csScore;

}
