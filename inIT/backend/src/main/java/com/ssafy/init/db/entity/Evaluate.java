package com.ssafy.init.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel(value = "Evaluate : 일반면접 평가질문", description="평가 질문과 관련된 DB")
@Data
@Entity
@Table(name="evaluate")
@EqualsAndHashCode(callSuper = false)
@DynamicInsert
public class Evaluate extends BaseEntity {
	
	@ApiModelProperty(value="구분, Null X, 1:태도,2:내용,3:기타(default: 0)")
	@Column(nullable = false)
	@ColumnDefault("0")
	private int type;
	
	@ApiModelProperty(value = "평가내용, Null X")
	@Column(nullable = false, columnDefinition = "TEXT")
	private String eval;
	

}
