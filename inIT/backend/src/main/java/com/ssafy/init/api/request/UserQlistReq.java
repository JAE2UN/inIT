package com.ssafy.init.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "UserQlistReq : 나만의 질문 리스트 관련 API 요청에 필요한 RequestBody 정의")
public class UserQlistReq {
    @ApiModelProperty(name="질문 식별자", value = "질문 식별자", example="1")
    private int id;

    @ApiModelProperty(name="질문 중요도 우선순위", value = "질문 중요도 우선순위(1:하 2:중 3:상)", example = "3")
    private int priority;
    
    @ApiModelProperty(name="질문 내용", value = "질문 내용", example = "~~했습니까?")
    private String quest;
    
    @ApiModelProperty(name="질문 구분자", value = "질문 구분자(10:일반, 21~29:CS, 30:프로젝트)", example = "10")
    private int type;

    @ApiModelProperty(name="질문 작성자 식별자 값", value = "질문 작성자 식별자 값", example = "1")
    private int userId;
}
