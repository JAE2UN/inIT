package com.ssafy.init.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ApiModel(value = "ReviewRes : 면접 후기 게시글 정보 조회 API 요청에 대한 응답값 정의")
public class ReviewRes {
    @ApiModelProperty(value = "게시글 id", example = "2")
    private int id;

//    @ApiModelProperty(value = "게시글 생성 시간", example = "2022-08-08T18:22:53")
//    private LocalDateTime createTime;

    @ApiModelProperty(value = "게시글 생성 시간", example = "22-08-08")
    private String createTime;
    
    @ApiModelProperty(value = "게시글 업데이트 시간", example = "2022-08-08T18:22:53")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "게시글 제목", example = "제목제목")
    private String title;

    @ApiModelProperty(value = "게시글 내용", example = "내용내용")
    private String content;

    @ApiModelProperty(value = "게시글 추천 수", example = "21")
    private int likesCnt;

    @ApiModelProperty(value = "게시글 댓글 수", example = "8")
    private int commentCnt;

	@ApiModelProperty(value = "게시글 작성자 식별자", example = "1")
	private int userId;
	  
	@ApiModelProperty(value = "회사명", example = "삼성")
	private String company;
	
	@ApiModelProperty(value = "면접 본 날짜", example = "2022-08-011")
	private LocalDate realDate;
	
	@ApiModelProperty(value = "면접 정보", example = "최종 면접")
	private String info;
	
    @ApiModelProperty(value = "게시글 추천 여부(재입장 시 사용)", example = "false")
    private boolean userAlreadyPush;
}
