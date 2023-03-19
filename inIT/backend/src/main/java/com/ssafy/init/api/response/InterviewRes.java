package com.ssafy.init.api.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "InterviewRes : 면접중 API 요청에 대한 응답값 정의")
public class InterviewRes {
	
	// 다음 문제 내용 & 번호
	@ApiModelProperty(value = "면접 질문", example = "자기소개 해보세요.")
	private String quest;
	
	@ApiModelProperty(value = "현재 질문번호(순서)", example = "3")
    private int nowNo;

}
