package com.ssafy.init.db.repository;

import com.ssafy.init.db.entity.Board;
import com.ssafy.init.db.entity.Boardlikes;
import com.ssafy.init.db.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardlikesRepositoryTest {
    @Autowired
    BoardlikesRepository boardlikesRepository;

    @Test
    public void test() {
        Boardlikes boardlikes = new Boardlikes();

        User user = new User();
        user.setId(26);

        Board board = new Board();
        board.setId(3);

        boardlikes.setUser(user);
        boardlikes.setBoard(board);

        boardlikesRepository.save(boardlikes);
    }
}