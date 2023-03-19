package com.ssafy.init.api.service;

import com.ssafy.init.api.request.BoardcommentReq;
import com.ssafy.init.api.response.BoardcommentRes;
import com.ssafy.init.db.entity.Board;
import com.ssafy.init.db.entity.Boardcomment;
import com.ssafy.init.db.entity.User;
import com.ssafy.init.db.repository.BoardRepository;
import com.ssafy.init.db.repository.BoardcommentRepository;
import com.ssafy.init.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardcommentServiceImpl implements BoardcommentService {
    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardcommentRepository boardcommentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Override
    @Transactional
    public boolean insert(BoardcommentReq boardCommentReq) {
        int userId = boardCommentReq.getUserId();
        int boardId = boardCommentReq.getBoardId();

        if (boardId < 0 || boardId > Integer.MAX_VALUE || userId < 0 || userId > Integer.MAX_VALUE) return (false);

        Optional<User> user = userRepository.findById(userId);
        Optional<Board> board = boardRepository.findById(boardId);
        if (user.isPresent() && board.isPresent()) {
            try {
                int cnt = board.get().getComment_cnt() + 1;

                Boardcomment boardcomment = new Boardcomment();

                boardcomment.setContent(boardCommentReq.getContent());
                boardcomment.setUser(user.get());
                boardcomment.setBoard(board.get());

                boardcommentRepository.save(boardcomment);
                boardService.changeCommentCnt(boardId, cnt);
                return (true);
            } catch (IllegalArgumentException iae) {
                System.out.println(">>> 댓글 저장 에러");
                return (false);
            } catch (Exception e) {
                System.out.println(">>> 댓글 저장 전역 에러");
                return (false);
            }
        }
        return (false);
    }

    @Override
    @Transactional
    public boolean delete(int commentId, int userId) {
        if (commentId < 0 || commentId > Integer.MAX_VALUE || userId < 0 || userId > Integer.MAX_VALUE) return (false);

        Optional<Boardcomment> boardcomment = boardcommentRepository.findById(commentId);
        if (boardcomment.isPresent()) {
            if (boardcomment.get().getUser().getId() != userId) return (false);

            try {
                int boardId = boardcomment.get().getBoard().getId();
                int cnt = boardcomment.get().getBoard().getComment_cnt() - 1;

                boardcommentRepository.delete(boardcomment.get());
                boardService.changeCommentCnt(boardId, cnt);
                return (true);
            } catch (IllegalArgumentException iae) {
                System.out.println(">>> 댓글 삭제 중 처리 에러");
                return (false);
            } catch (Exception e) {
                System.out.println(">>> 댓글 수정 중 전역 에러");
                return (false);
            }
        }
        return (false);
    }

    @Override
    public List<BoardcommentRes> getFilteringInfo(int boardId) {
        if (boardId < 0 || boardId > Integer.MAX_VALUE) return (null);

        List<Boardcomment> boardcommentList = boardcommentRepository.findAllByBoardId(boardId);
        List<BoardcommentRes> boardcommentResList = new ArrayList<>();

        for (int i = 0; i < boardcommentList.size(); i++) {
            BoardcommentRes boardcommentRes = new BoardcommentRes();

            boardcommentRes.setUserNick(boardcommentList.get(i).getUser().getNick());
            boardcommentRes.setUserGrade(boardcommentList.get(i).getUser().getGrade());
            boardcommentRes.setUserTemperature(boardcommentList.get(i).getUser().getTemperature());

            boardcommentRes.setId(boardcommentList.get(i).getId());
            boardcommentRes.setContent(boardcommentList.get(i).getContent());
            
            // [smj] dateTime -> date (String)
            String onlyDate = boardcommentList.get(i).getCreatedAt().toString().substring(2, 10);
            boardcommentRes.setCreateTime(onlyDate);
//            boardcommentRes.setCreateTime(boardcommentList.get(i).getCreatedAt());

            boardcommentResList.add(boardcommentRes);
        }
        return (boardcommentResList);
    }
}
