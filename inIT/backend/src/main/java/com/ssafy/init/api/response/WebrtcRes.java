package com.ssafy.init.api.response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ApiModel(value = "WebrtcRes : Openvidu 영상 처리에 대한 API 요청에 대한 응답값 정의")
public class WebrtcRes {
    @ApiModelProperty(value = "영상 url", example = "https://interview.ml:4443/openvidu/recordings/ses_ZSsLc0NwU6~1/ses_ZSsLc0NwU6~1.mp4")
    private String url;

    @ApiModelProperty(value = "영상 생성 시간", example = "2022-08-08T18:22:53")
    private LocalDateTime createTime;

}
