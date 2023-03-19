package com.ssafy.init.api.response;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "RealReportRes: 실전 면접 일지 상세 조회 API 요청에 필요한 응답 모음")
public class RealReportRes {

	@ApiModelProperty(value = "실전 면접 일지 식별자(=면접일지 식별자)", example = "2")
	private int id;
	
	@ApiModelProperty(value = "구분(1:일반, 2:기술, 3:실전)", example = "3")
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
	
	@ApiModelProperty(value="회사명", example = "토스")
	private String company;
	
	@ApiModelProperty(value="면접 본 날짜", example = "2022-08-08")
	private LocalDate realDate;
	
	@ApiModelProperty(value="면접 정보", example = "최종 면접")
	private String info;
	
	@ApiModelProperty(value="면접 내용", 
			example = "면접관이 3명이었는데 가운데 분이 공격적으로 많이 물어봄.. 전체적으로 분위기 나쁘지 않았는데 프로젝트 관련해서 대답을 잘 못했다  제발 붙으면 좋겠지만 또 모르니 프로젝트 관련해서 다시 정리해봐야겠다ㅠㅠ")
	private String allCmt;
	
	@ApiModelProperty(value = "면접 질문과 대답(식별자, 구분, 질문, 대답)", 
			example = "[{'id': 1, 'type': 10, 'question': '1분 자기소개', 'answer': '안녕하십니까'},"
					+ "{'id': 2, 'type': 30, 'question': '기억에 남는 프로젝트', 'answer': '싸피에서 ~프로젝트 진행 ~해서 ~를 배움 이를 통해 ~로 발전하게 됨'}]")
	private List<RealQaRes> realQaRes;

}
