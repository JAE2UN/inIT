package com.ssafy.init.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ApiModel(value = "ReportRes : 면접 일지 조회 API 요청에 대한 응답값 정의")
public class ReportRes {
	
	@ApiModelProperty(value = "면접 일지 식별자", example = "2")
	private int id;
	
	@ApiModelProperty(value = "구분(1:일반, 2:기술, 3:실전)", example = "1")
	private int type;

	@ApiModelProperty(value="일지 제목", example = "토스 최종면접 후기")
	private String title;
	
	@ApiModelProperty(value="일지 작성 날짜", example = "22-08-08")
	private String createdAt;
	
	@ApiModelProperty(value="별점", example = "3.5")
	private float star;
	
	@ApiModelProperty(value="한줄평", example = "제발 붙기를.. 프로젝트 관련 내용들 재정리하자..!")
	private String oneCmt;
	
	@ApiModelProperty(value="태그들", example = "#토스#최종 면접#플젝관련질문")
	private String tags;
	
	@ApiModelProperty(value="유저 식별자", example = "1")
	private int userId;
	
	@ApiModelProperty(value="체크리스트 관련 데이터 모음", example = "...")
	private ChecklistRes checklistRes;
	
	
}
