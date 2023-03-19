package com.ssafy.init.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "FeedbackReq : 피드백 게시글 작성, 수정 API 요청에 필요한 RequestBody 정의")
public class FeedbackReq {
    @ApiModelProperty(name="게시글 id", value = "수정 일때만 사용", example="26")
    private int id;

    @ApiModelProperty(name="게시글 제목", value = "작성 / 수정 시 모두 사용", example = "나는 피드백 제목~")
    private String title;

    @ApiModelProperty(name="게시글 내용", value = "작성 / 수정 시 모두 사용", example = "나는 피드백 내용 문어~")
    private String content;

    @ApiModelProperty(name="글 작성자 구분 값", value = "작성 / 수정 시 모두 사용", example = "3")
    private int userId;

//    @ApiModelProperty(name="동영상 url", value = "작성 / 수정 시 모두 사용", example = "https://youtube.com/shorts/18OYMT2qUSY?feature=share")
//    private String video_url;

    @ApiModelProperty(name = "면접일지 구분 값", value = "면접 일지 구분 값", example = "7")
    private int reportId;

    @ApiModelProperty(name = "문제(동영상) 위치", value = "문제(동영상) 위치 몇 번째 영상을 가져올 것인지 값", example = "3")
    private int sequence;
}
