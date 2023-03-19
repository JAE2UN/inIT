package com.ssafy.init.db.repository;

import com.ssafy.init.db.entity.Review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findAllByOrderByIdDesc();
    
    void deleteByRealReportId(int realreportId);

}
