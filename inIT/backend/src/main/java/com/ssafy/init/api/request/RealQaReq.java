package com.ssafy.init.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "RealQaReq: 실전 면접 질문과 답변 작성 API 요청에 필요한 답변 모음")
public class RealQaReq {
	
	// 식별자 (작성할 때말고 수정할 때 필요하려나... 수정은 리스트가 아니라 수정된 것만 .. 하나씩 처리 )
	@ApiModelProperty(value="실전 면접 질문과 답 식별자", example = "1")
	private int id;
	
	@ApiModelProperty(value="구분(10:인적성, 20:CS(21:네트워크 등), 30:프로젝트)", example = "10")
	private int type;
	
	@ApiModelProperty(value = "질문", example = "1분 자기소개")
    private String question;
	
	@ApiModelProperty(value = "대답", example = "안녕하십니까! ~같은 지원자 _입니다. 저는 ~~.")
    private String answer;

}
