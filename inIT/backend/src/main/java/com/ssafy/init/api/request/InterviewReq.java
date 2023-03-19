package com.ssafy.init.api.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "InterviewReq: 면접 중 API요청에 필요한 RequestBody 모음")
public class InterviewReq {
	
	// 질문을 가져오기 위해 현재 번호 & 면접번호
	
	@ApiModelProperty(value = "면접일지 id(=면접연습 고유번호)", example = "3")
	private int reportId;
	
	@ApiModelProperty(value = "필요한 질문번호", example = "1")
    private int nextNo;
	
	

}
