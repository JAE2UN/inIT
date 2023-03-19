package com.ssafy.init.db.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.init.db.entity.Scrap;

public interface ScrapRepository extends JpaRepository<Scrap, Integer> {
	
	// 전체 조회
	List<Scrap> findAllByUserIdOrderByCreatedAtDesc(int userId);
		
}
