package com.ssafy.init.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "ScrapRes : 오답 노트 조회 API 요청에 대한 응답값 정의")
public class ScrapRes {
	@ApiModelProperty(name="오답노트 식별자", value = "수정할 때만 사용", example="2")
    private int id;

    @ApiModelProperty(name="유저 식별자", value = "작성 / 수정 시 모두 사용", example = "2")
    private int userId;
    
    @ApiModelProperty(name="질문 식별자", value = "작성 / 수정 시 모두 사용", example = "99")
    private int questionId;
    
    @ApiModelProperty(name="질문 유형", value = "작성 / 수정 시 모두 사용 (10: 일적성,  21~29: 기술,  30: 프로젝트)", example = "10")
    private int type;
    
    @ApiModelProperty(name="질문", value = "작성 / 수정 시 모두 사용", example = "1분 자기소개 해주세요.")
    private String quest;
    
    @ApiModelProperty(name="답변", value = "수정 시 사용", example = "안녕하십니까? 저는 ~한 지원자 ~입니다.")
    private String ans;

    // to do list처럼 완료된 것도 보여지게 되면 사용하게 될 항목
//    @ApiModelProperty(name="완료 유무", value = "수정 할 때만 사용", example = "false")
//    private boolean finish;    
    

}
