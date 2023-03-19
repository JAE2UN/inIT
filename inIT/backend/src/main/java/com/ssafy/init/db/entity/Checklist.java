package com.ssafy.init.db.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel(value = "Checklist : 체크리스트", description="체크리스트와 관련된 DB")
@Data
@Entity
@Table(name="checklist")
@EqualsAndHashCode(callSuper = false)
@DynamicInsert
public class Checklist {
	
	@ApiModelProperty(value = "체크리스트 id(=면접일지 id), Null X, PK")
    @Id
    private int id;

    @ApiModelProperty(value = "생성시간")
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "수정시간")
    @LastModifiedDate
    private LocalDateTime updatedAt;
	
	@ApiModelProperty(value="총평(메모), Null O")
	@Column(columnDefinition = "TEXT")
	private String allCmt;
	
	@ApiModelProperty(value="구분, Null X, 1:일반,2:기술(default: 0)")
	@Column(nullable = false)
	@ColumnDefault("0")
	private int type;

	// report 연결(OTO)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="report_id")
	private Report report;
	
	
}
