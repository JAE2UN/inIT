package com.ssafy.init.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel(value = "Answer : 답변", description="면접 답변과 관련된 DB")
@Data
@Entity
@Table(name="answer")
@EqualsAndHashCode(callSuper = false)
@DynamicInsert
public class Answer extends BaseEntity {
	
	@ApiModelProperty(value="구분, Null X, 1:통합(question),2:평가(eval)(default: 0)")
	@Column(nullable = false)
	@ColumnDefault("0")
	private int flag;
	
	@ApiModelProperty(value = "문제번호, Null X")
	@Column(nullable = false)
    private int questNo;
	
	@ApiModelProperty(value = "일반 평가 점수")
    private int evalScore;
	
	@ApiModelProperty(value = "CS 평가 점수")
    private int csScore;
	
	// 면접일지로 연결(MTO)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "report_id")
	private Report report;

}
