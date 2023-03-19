package com.ssafy.init.api.service;

import com.ssafy.init.api.request.BoardReq;
import com.ssafy.init.db.entity.Board;
import com.ssafy.init.db.entity.Boardlikes;
import com.ssafy.init.db.entity.User;
import com.ssafy.init.db.repository.BoardRepository;
import com.ssafy.init.db.repository.BoardlikesRepository;
import com.ssafy.init.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class BoardlikesServiceImpl implements BoardlikesService {
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardlikesRepository boardlikesRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    @Transactional
    public int insert(int boardId, int userId) {
        if (boardId < 0 || boardId > Integer.MAX_VALUE || userId < 0 || userId > Integer.MAX_VALUE) return (-1);

        Optional<Board> board = boardRepository.findById(boardId);
        Optional<User> user = userRepository.findById(userId);
        if (board.isPresent() && user.isPresent()) {
            Boardlikes boardlikes = new Boardlikes();

            boardlikes.setUser(user.get());
            boardlikes.setBoard(board.get());
            try {
                final int INFO_CATEGORY = 3;
                int cnt = board.get().getLikes_cnt() + 1;
                int boardType = board.get().getType();

                //실패 시 트렌젝션 처리
                boardlikesRepository.save(boardlikes);
                if (boardType == INFO_CATEGORY && cnt >= STD_LIKES)    boardService.turnOnInfo(boardId);
                boardService.changeLikesCnt(boardId, cnt);

                return (cnt);
            } catch (IllegalArgumentException iae) {
                System.out.println(">>> 추천 등록 중 에러");
                return (-1);
            } catch (Exception e) {
                System.out.println(">>> 추천 등록 중 전역 에러");
                return (-1);
            }
        }
        return (-1);
    }

    @Override
    @Transactional
    public int delete(int boardId, int userId) {
        if (boardId < 0 || boardId > Integer.MAX_VALUE || userId < 0 || userId > Integer.MAX_VALUE) return (-1);

        Optional<Board> board = boardRepository.findById(boardId);
        Optional<User> user = userRepository.findById(userId);
        if (board.isPresent() && user.isPresent()) {
            Boardlikes boardlikes = boardlikesRepository.findByBoardIdAndUserId(boardId, userId);

            System.out.println(">>> : service : " + boardlikes);

            if (boardlikes == null) return (-1);
            try {
                final int INFO_CATEGORY = 3;
                int cnt = board.get().getLikes_cnt() - 1;
                int boardType = board.get().getType();

                //실패 시 트렌젝션 처리
                boardService.changeLikesCnt(boardId, cnt);
                if (boardType == INFO_CATEGORY && cnt < STD_LIKES)    boardService.turnOffInfo(boardId);
                boardlikesRepository.delete(boardlikes);

                return (cnt);
            } catch (IllegalArgumentException iae) {
                System.out.println(">>> 추천 삭제 중 에러");
                return (-1);
            } catch (Exception e) {
                System.out.println(">>> 추천 삭제 중 전역 에러");
                return (-1);
            }
        }
        return (-1);
    }
}
