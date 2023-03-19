package com.ssafy.init.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "ReportReq : 면접 일지 작성, 수정 API 요청에 필요한 RequestBody 정의")
public class ReportReq {
	
	@ApiModelProperty(value = "면접일지 식별자, 수정 시에만 사용", example = "2")
	private int id;

	@ApiModelProperty(value="구분(1: 일반, 2: 기술)", example = "1")
	private int type;	
	
	@ApiModelProperty(value="일지 제목", example = "토스 대비 연습")
	private String title;
	
	@ApiModelProperty(value="별점", example = "3.5")
	private float star;
	
	@ApiModelProperty(value="한줄평", example = "프로젝트 관련 내용들 재정리하자..!")
	private String oneCmt;
	
	@ApiModelProperty(value="태그들", example = "#토스#일반 면접 연습#플젝관련질문")
	private String tags;
	
	@ApiModelProperty(value="유저 식별자", example = "1")
	private int userId;
	
	@ApiModelProperty(value="체크리스트 관련 데이터 모음", example = "...")
	private ChecklistReq checklistReq;
	
}
