package com.ssafy.init.db.repository;

import com.ssafy.init.db.entity.Question;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionRepository extends JpaRepository<Question, Integer> {
	
	List<Question> findAllByType(int type); // 문제유형 전체검색
	
	List<Question> findAllByPriority(int priority); // 문제 우선순위 전체검색
	
	List<Question> findAllByTypeAndPriority(int type, int priority); // 문제유형&우선순위 전체검색
		
	List<Question> findAllByIsAdminAndTypeOrderByPriorityDesc(boolean isAdmin, int type); // 서버문제&문제유형 우선순위높은순으로 전체검색
	
	List<Question> findAllByUserIdAndTypeOrderByPriorityDesc(int userId, int type); // 사용자문제&문제유형 우선순위 높은순 전체검색

	List<Question> findAllByIsAdminAndTypeAndPriority(boolean isAdmin, int type, int priority); // 서버문제&문제유형&우선순위 전체검색

	List<Question> findAllByUserIdAndTypeAndPriority(int userId, int type, int priority); // 사용자문제&문제유형&우선순위 전체검색
	
	List<Question> findAllByUserIdAndPriority(int userId, int priority); // 사용자문제&우선순위 전체검색

	// [smj] 나의 질문리스트에서 필요
    List<Question> findAllByUserId(int userId);
}
