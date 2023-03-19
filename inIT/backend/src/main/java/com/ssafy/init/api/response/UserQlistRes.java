package com.ssafy.init.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ApiModel(value = "UserQlistRes : 나만의 질문 리스트 관련 API 요청에 대한 응답값 정의")
public class UserQlistRes {
    @ApiModelProperty(value = "질문 식별자", example="1")
    private int id;

    @ApiModelProperty(value = "질문 중요도 우선순위(1:하 2:중 3:상)", example = "3")
    private int priority;
    
    @ApiModelProperty(value = "질문 내용", example = "~~했습니까?")
    private String quest;
    
    @ApiModelProperty(value = "질문 구분자(10:일반, 21~29:CS, 30:프로젝트)", example = "10")
    private int type;

}
