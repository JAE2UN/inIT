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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Question : 통합 질문리스트", description="질문리스트와 관련된 DB")
@Data
@Entity
@Table(name="question")
@EqualsAndHashCode(callSuper = false)
@DynamicInsert
public class Question extends BaseEntity {
	
	@ApiModelProperty(value = "관리자여부, Null X, 관리자면 true, 아니면 false(default: false)")
	@Column(nullable = false)
	@ColumnDefault("false")
	private boolean isAdmin;
	
	@ApiModelProperty(value = "우선순위, Null X, 123(하중상)(default: 3)")
	@Column(nullable = false)
	@ColumnDefault("3")
	private int priority;
	
	@ApiModelProperty(value = "질문, Null X")
	@Column(nullable = false, length = 350)
	private String quest;
	
	@ApiModelProperty(value="구분, Null X, 10:일반,20:기술(21~29는 세부),30:프로젝트(default: 0)")
	@Column(nullable = false)
	@ColumnDefault("0")
	private int type;
	
	// user 연결(MTO)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
	
}
