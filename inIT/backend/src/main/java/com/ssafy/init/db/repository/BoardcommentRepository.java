package com.ssafy.init.db.repository;

import com.ssafy.init.db.entity.Boardcomment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardcommentRepository extends JpaRepository<Boardcomment, Integer> {
    List<Boardcomment> findAllByBoardId(int boardId);
}
