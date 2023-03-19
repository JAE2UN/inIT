package com.ssafy.init.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "WebrtcReq : Openvidu 영상 처리에 대한 API 요청에 필요한 RequestBody 정의")
public class WebrtcReq {
    @ApiModelProperty(name="작성자 식별자", value = "로그인 된 회원의 식별자", example="2")
    private int userId;

    @ApiModelProperty(name="면접일지 식별자", value = "면접일지 식별자", example="42")
    private int reportId;

    @ApiModelProperty(name="동영상 id", value = "동영상 id, Openvidu STOP시 Http responses의 id값", example = "ses_WD0D9bh3js~1")
    private String code;
}
