package com.ssafy.init.db.repository;

import com.ssafy.init.db.entity.Reviewcomment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewcommentRepository extends JpaRepository<Reviewcomment, Integer> {
    List<Reviewcomment> findAllByReviewId(int reviewId);
    
    // 게시글 삭제 시 해당 댓글 모두 삭제
    void deleteAllByReviewId(int boardId);
}
