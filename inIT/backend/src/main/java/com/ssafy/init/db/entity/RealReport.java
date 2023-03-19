package com.ssafy.init.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="realreport")
@EqualsAndHashCode(callSuper = false)
//public class RealReport extends BaseEntity {
public class RealReport {
	
    @ApiModelProperty(value = "면접일지(report)테이블의 식별자와 동일한 값")
    @Id
    private int id;
	
    @Column(length = 100)
    private String company;

    @Column(nullable = false)
    private LocalDate realDate;
	
    @Column(length = 250)
    private String info;
	
    @Column(columnDefinition = "TEXT")
    private String allCmt;
    

    // 면접 일지와 같은 식별자를 갖기 때문에 굳이 연결을 안해줘도 될 거 같음 
    // 연결을 안해주니 계속 일일이 넣어줘야 하는 듯.... 
//	// report 연결 (OTO)
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="report_id")
//	private Report report;
}
