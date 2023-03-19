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
@Table(name="scrap")
@EqualsAndHashCode(callSuper = false)
public class Scrap extends  BaseEntity {

    // to do list처럼 완료된 것도 보여지게 되면 사용하게 될 항목
//    @Column(nullable = false)
//    private boolean finish;
	
	// 답변 적는 칸
	@Column(length = 500)
	private String ans;
	
	
	// user 연결(MTO)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;
	
	// question 연결 (MTO)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id")
	@JsonIgnore
	private Question question;
	
}
