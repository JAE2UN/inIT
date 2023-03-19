package com.ssafy.init.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ApiModel(value = "ReviewcommentRes : 게시글에 대한 댓글 정보 조회 API 요청에 대한 응답값 정의")
public class ReviewcommentRes {
    @ApiModelProperty(name="댓글 식별자", value = "댓글 식별자", example = "5")
    private int id;

    @ApiModelProperty(name="댓글 작성자 식별자", value = "댓글 작성자 식별자", example = "2")
    private int userId;

    @ApiModelProperty(name="댓글 내용", value = "댓글 내용", example = "댓글 댓글")
    private String content;

    @ApiModelProperty(name="댓글 작성 시간", value = "댓글 작성 시간", example = "22-08-08")
    private String createTime;
}
