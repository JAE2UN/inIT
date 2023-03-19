package com.ssafy.init.db.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.init.db.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
	List<Answer> findAllByReportId(int rid);
	
	List<Answer> findAllByFlagAndReportId(int flag, int rid);
	
	@Transactional
	List<Answer> deleteAllByReportId(int rid);

}
