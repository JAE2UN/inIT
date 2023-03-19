package com.ssafy.init.db.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.init.db.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Integer> {
	
	Report findTop1ByUserIdAndTypeOrderByCreatedAtDesc(int userId, int type);
	
	Report findByIdAndUserId(int id, int userId);
	
	// 전체 조회
	List<Report> findAllByUserIdOrderByCreatedAtDesc(int userId);
	
	// 타입별 조회 (일반 연습, 기술 연습, 실전)
	List<Report> findAllByUserIdAndTypeOrderByCreatedAtDesc(int userId, int type);
	
	// 태그별 조회
	List<Report> findAllByUserIdAndTagsContainingOrderByCreatedAtDesc(int userId, String tags);
	
	// 대시보드
	List<Report> findTop5ByUserIdAndTypeOrderByCreatedAtDesc(int userId, int type);

}
