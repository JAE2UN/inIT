package com.ssafy.init.db.repository;

import com.ssafy.init.db.entity.RealQa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RealQaRepository extends JpaRepository<RealQa, Integer> {
	List<RealQa> findAllByRealReportId(int realReportId);
	
	void deleteByRealReportId(int realReportId);
	
}
