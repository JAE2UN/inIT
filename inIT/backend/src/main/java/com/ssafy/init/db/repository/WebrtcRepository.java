package com.ssafy.init.db.repository;

import com.ssafy.init.db.entity.Webrtc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebrtcRepository extends JpaRepository<Webrtc, Integer> {
    List<Webrtc> findAllByReportIdOrderByIdAsc(int reportId);
}
