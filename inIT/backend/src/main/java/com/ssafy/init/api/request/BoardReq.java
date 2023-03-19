package com.ssafy.init.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "BoardReq : 자유 게시글 작성, 수정 API 요청에 필요한 RequestBody 정의")
public class BoardReq {
    @ApiModelProperty(name="게시글 id", value = "수정 일때만 사용", example="13")
    private int id;

    @ApiModelProperty(name="게시글 구분자", value = "작성 / 수정 시 모두 사용 (1:일반 2:질문 3:정보)", example = "3")
    private int type;

    @ApiModelProperty(name="게시글 제목", value = "작성 / 수정 시 모두 사용", example = "나는 제목~")
    private String title;

    @ApiModelProperty(name="게시글 내용", value = "작성 / 수정 시 모두 사용", example = "나는 내용 문어~")
    private String content;

    @ApiModelProperty(name="글 작성자 구분 값", value = "작성 / 수정 시 모두 사용", example = "2")
    private int userId;
}
