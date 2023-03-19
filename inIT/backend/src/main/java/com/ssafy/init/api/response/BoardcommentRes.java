package com.ssafy.init.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ApiModel(value = "BoardcommentRes : 게시글에 대한 댓글 정보 조회 API 요청에 대한 응답값 정의")
public class BoardcommentRes {
    @ApiModelProperty(name="댓글 식별자", value = "댓글 식별자", example = "5")
    private int id;

    @ApiModelProperty(name="작성자 닉네임", value = "작성자 닉네임", example = "타코야끼")
    private String userNick;

    @ApiModelProperty(name="댓글 내용", value = "댓글 내용", example = "나는 댓글 문어~~")
    private String content;

    @ApiModelProperty(name="댓글 작성 시간", value = "댓글 작성 시간", example = "22-08-08")
    private String createTime;

    @ApiModelProperty(value = "댓글 작성자 등급", example = "1")
    private int userGrade;

    @ApiModelProperty(value = "댓글 작성자 온도", example = "36")
    private int userTemperature;
}
