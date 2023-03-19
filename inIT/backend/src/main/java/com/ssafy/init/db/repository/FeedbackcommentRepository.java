package com.ssafy.init.db.repository;

import com.ssafy.init.db.entity.Feedbackcomment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackcommentRepository extends JpaRepository<Feedbackcomment, Integer> {
    List<Feedbackcomment> findAllByFeedbackId(int feedbackId);
}
