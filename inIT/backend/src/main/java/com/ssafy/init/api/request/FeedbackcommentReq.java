package com.ssafy.init.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "FeedbackcommentReq : 게시글에 대한 댓글 작성 API 요청에 필요한 RequestBody 정의")
public class FeedbackcommentReq {
    @ApiModelProperty(name="작성자 식별자", value = "로그인 된 회원의 식별자", example="2")
    private int userId;

    @ApiModelProperty(name="댓글 내용", value = "댓글 내용", example="나는 피드백 댓글~")
    private String content;

    @ApiModelProperty(name="피드백 게시글 식별자", value = "작성할 피드백 게시글의 식별자", example="7")
    private int feedbackId;
}
