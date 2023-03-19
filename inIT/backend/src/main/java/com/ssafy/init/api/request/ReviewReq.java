package com.ssafy.init.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "ReviewReq : 면접 후기 게시글 작성, 수정 API 요청에 필요한 RequestBody 정의")
public class ReviewReq {
    @ApiModelProperty(name="게시글 id", value = "수정 일때만 사용", example="2")
    private int id;

    @ApiModelProperty(name="게시글 제목", value = "작성 / 수정 시 모두 사용", example = "제목제목")
    private String title;

    @ApiModelProperty(name="게시글 내용", value = "작성 / 수정 시 모두 사용", example = "내용내용")
    private String content;

    @ApiModelProperty(name="글 작성자 식별값", value = "작성 / 수정 시 모두 사용", example = "2")
    private int userId;
    
    @ApiModelProperty(name="실전 면접 일지 식별값", value = "작성할 때만 사용", example = "2")
    private int realreportId;
}
