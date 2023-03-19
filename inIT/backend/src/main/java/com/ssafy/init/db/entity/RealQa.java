package com.ssafy.init.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="realqa")
@EqualsAndHashCode(callSuper = false)
public class RealQa extends BaseEntity {
	
	@Column(nullable = false)
	private int type;
	
    @Column(nullable = false, length = 350)
    private String question;
	
    @Column(length = 350)
    private String answer;
    
	// realreport 연결(MTO)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "realreport_id")
	@JsonIgnore
	private RealReport realReport;
}
