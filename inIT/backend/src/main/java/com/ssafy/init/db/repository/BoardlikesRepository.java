package com.ssafy.init.db.repository;

import com.ssafy.init.db.entity.Board;
import com.ssafy.init.db.entity.Boardlikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardlikesRepository extends JpaRepository<Boardlikes, Integer> {
    Boardlikes findByBoardIdAndUserId(int boardId, int userId);
}
