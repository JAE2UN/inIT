package com.ssafy.init.api.service;

import com.ssafy.init.api.request.ReviewcommentReq;
import com.ssafy.init.api.response.ReviewcommentRes;
import com.ssafy.init.db.entity.Review;
import com.ssafy.init.db.entity.Reviewcomment;
import com.ssafy.init.db.entity.User;
import com.ssafy.init.db.repository.ReviewRepository;
import com.ssafy.init.db.repository.ReviewcommentRepository;
import com.ssafy.init.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewcommentServiceImpl implements ReviewcommentService {
    @Autowired
    private ReviewService boardService;

    @Autowired
    private ReviewcommentRepository boardcommentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository boardRepository;

    @Override
    @Transactional
    public boolean insert(ReviewcommentReq boardCommentReq) {
        int userId = boardCommentReq.getUserId();
        int boardId = boardCommentReq.getReviewId();

        if (boardId < 0 || boardId > Integer.MAX_VALUE || userId < 0 || userId > Integer.MAX_VALUE) return (false);

        Optional<User> user = userRepository.findById(userId);
        Optional<Review> board = boardRepository.findById(boardId);
        if (user.isPresent() && board.isPresent()) {
            try {
                int cnt = board.get().getComment_cnt() + 1;

                Reviewcomment boardcomment = new Reviewcomment();

                boardcomment.setContent(boardCommentReq.getContent());
                boardcomment.setUser(user.get());
                boardcomment.setReview(board.get());

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

        Optional<Reviewcomment> boardcomment = boardcommentRepository.findById(commentId);
        if (boardcomment.isPresent()) {
            if (boardcomment.get().getUser().getId() != userId) return (false);

            try {
                int boardId = boardcomment.get().getReview().getId();
                int cnt = boardcomment.get().getReview().getComment_cnt() - 1;

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
    public List<ReviewcommentRes> getFilteringInfo(int boardId) {
        if (boardId < 0 || boardId > Integer.MAX_VALUE) return (null);

        List<Reviewcomment> boardcommentList = boardcommentRepository.findAllByReviewId(boardId);
        List<ReviewcommentRes> boardcommentResList = new ArrayList<>();

        for (int i = 0; i < boardcommentList.size(); i++) {
        	ReviewcommentRes boardcommentRes = new ReviewcommentRes();
        	
            boardcommentRes.setId(boardcommentList.get(i).getId());
            boardcommentRes.setContent(boardcommentList.get(i).getContent());
            
            // [smj] dateTime -> date (String)
            String onlyDate = boardcommentList.get(i).getCreatedAt().toString().substring(2, 10);
            boardcommentRes.setCreateTime(onlyDate);
//            boardcommentRes.setCreateTime(boardcommentList.get(i).getCreatedAt());
            
            boardcommentRes.setUserId(boardcommentList.get(i).getUser().getId());

            boardcommentResList.add(boardcommentRes);
        }
        return (boardcommentResList);
    }
}
