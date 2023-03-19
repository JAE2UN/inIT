package com.ssafy.init.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ApiModel(value = "FeedbackRes : 피드백 게시글 정보 조회 API 요청에 대한 응답값 정의")
public class FeedbackRes {
    @ApiModelProperty(value = "게시글 id", example = "1")
    private int id;

    @ApiModelProperty(value = "게시글 생성 시간", example = "22-08-08")
    private String createTime;

    @ApiModelProperty(value = "게시글 업데이트 시간", example = "2022-08-08T18:22:53")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "게시글 제목", example = "나는 제목~")
    private String title;

    @ApiModelProperty(value = "게시글 내용", example = "나는 내용 문어~")
    private String content;

    @ApiModelProperty(value = "게시글 댓글 수", example = "8")
    private int commentCnt;

    @ApiModelProperty(value = "동영상 url", example = "https://www.youtube.com/watch?v=LBmB3DGxnNk")
    private String videoUrl;

    @ApiModelProperty(value = "게시글 작성자 닉네임", example = "타코야끼")
    private String userNick;

    @ApiModelProperty(value = "게시글 작성자 등급", example = "1")
    private int userGrade;

    @ApiModelProperty(value = "게시글 작성자 온도", example = "36")
    private int userTemperature;
}
