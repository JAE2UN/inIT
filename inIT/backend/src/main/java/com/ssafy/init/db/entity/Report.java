package com.ssafy.init.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel(value = "Report : 면접일지", description="면접일지와 관련된 DB")
@Data
@Entity
@Table(name="report")
@EqualsAndHashCode(callSuper = false)
@DynamicInsert
public class Report extends BaseEntity {
	
	// !!! 면접 영상(녹화영상)도 저장 !!!
	// 논의: 태그->어떻게 설정?, 연결->체크리스트 단방향?
	// 실전면접 어떻게 연결?
	
	@ApiModelProperty(value="구분, Null X, 1:일반,2:기술,3:실전(default: 0)")
	@Column(nullable = false)
	@ColumnDefault("0")
	private int type;
	
	@ApiModelProperty(value = "일지제목, Null X")
	@Column(nullable = false, length = 100)
    private String title;
	
	@ApiModelProperty(value = "별점, default: 0")
    @ColumnDefault("0")
    private float star;
	
	@ApiModelProperty(value="한줄평, Null O")
	@Column(length = 100)
	private String oneCmt;
	
	@ApiModelProperty(value="태그, Null O")
	@Column(length = 300) // 길이 논의
	private String tags;
	
	// user 연결(MTO)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;

	
	// +) 논의해보자: checklist 연결(OTO)/eval, question, answer 연결(OTM)
//	// realreport 연결 (OTO)
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="realreport_id")
//	private RealReport realReport;
}
