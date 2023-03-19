package com.ssafy.init.db.repository;

import com.ssafy.init.db.entity.Feedback;
import com.ssafy.init.db.entity.Feedbackcomment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findAllByOrderByIdDesc();
}
