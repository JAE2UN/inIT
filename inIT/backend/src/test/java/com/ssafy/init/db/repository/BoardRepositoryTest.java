package com.ssafy.init.db.repository;

import com.ssafy.init.db.entity.Board;
import com.ssafy.init.db.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void boardTest(){
        Board board = new Board();

        board.setInfo(false);
        board.setContent("content");
        board.setTitle("title");
        board.setComment_cnt(10);
        board.setLikes_cnt(10);
        board.setType(0);

        User user = userRepository.findById(26).orElseThrow(RuntimeException::new);

        board.setUser(user);

        System.out.println(board.getUser());
        boardRepository.save(board);


        System.out.println(boardRepository.findAll());
    }


}