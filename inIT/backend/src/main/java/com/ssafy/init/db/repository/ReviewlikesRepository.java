package com.ssafy.init.db.repository;

import com.ssafy.init.db.entity.Reviewlikes;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewlikesRepository extends JpaRepository<Reviewlikes, Integer> {
	Reviewlikes findByReviewIdAndUserId(int reviewId, int userId);
	
	// 게시글 삭제 시 해당 추천 수 모두 삭제
	void deleteAllByReviewId(int boardId);
}
