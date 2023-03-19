package com.ssafy.init.db.repository;

import com.ssafy.init.api.response.BoardRes;
import com.ssafy.init.db.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<Board> findAllByOrderByIdDesc();

    List<Board> findAllByIsInfoOrderByIdDesc(boolean isInfo);

    List<Board> findAllByTypeOrderByIdDesc(int type);

    Page<Board> findAllByIsInfo(boolean isInfo, Pageable pageable);

    Page<Board> findAllByType(int type, Pageable pageable);
}
