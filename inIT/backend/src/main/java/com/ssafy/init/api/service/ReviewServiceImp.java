package com.ssafy.init.api.service;

import com.ssafy.init.api.request.ReviewReq;
import com.ssafy.init.api.response.ReviewRes;
import com.ssafy.init.db.entity.RealReport;
import com.ssafy.init.db.entity.Review;
import com.ssafy.init.db.entity.Reviewlikes;
import com.ssafy.init.db.entity.User;
import com.ssafy.init.db.repository.RealReportRepository;
import com.ssafy.init.db.repository.ReviewRepository;
import com.ssafy.init.db.repository.ReviewcommentRepository;
import com.ssafy.init.db.repository.ReviewlikesRepository;
import com.ssafy.init.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImp implements ReviewService {
    @Autowired
    private ReviewRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RealReportRepository realReportRepository;
    @Autowired
    private  ReviewcommentRepository boardcommentRepository;
    @Autowired
    private  ReviewlikesRepository boardlikesRepository;
    
    @Override
    @Transactional
    public int insert(ReviewReq boardReq) {
        int userId = boardReq.getUserId();

        if (userId < 0 || userId > Integer.MAX_VALUE) return (-1);
        try {
        	Review board = new Review();
            User user = new User();
            RealReport realReport = new RealReport();
            
            int resultId = - 1;

            board.setTitle(boardReq.getTitle());
            board.setContent(boardReq.getContent());

            user.setId(boardReq.getUserId());
            board.setUser(user);
            
            realReport.setId(boardReq.getRealreportId());
            board.setRealReport(realReport);

            resultId = boardRepository.save(board).getId();

            return (resultId);
        } catch (IllegalArgumentException iae) {
            System.out.println(">>> 글 작성 중 저장 에러");
            return (-1);
        } catch (Exception e) {
            System.out.println(">>> 글 작성 중 전역 에러");
            return (-1);
        }
    }

    //수정 - Update : 글 수정 시 권한 없는 요청(-1), 수정 실패(0) / 성공(1) 여부를 반환
    @Override
    public int modify(ReviewReq newBoard) {
        int boardId = newBoard.getId();

        if (boardId < 0 || boardId > Integer.MAX_VALUE) return (-1);

        Optional<Review> board = boardRepository.findById(boardId);
        if (board.isPresent()) {
            if (newBoard.getUserId() != board.get().getUser().getId()) return (-1);

            if (newBoard.getTitle() != null)   board.get().setTitle(newBoard.getTitle());
            if (newBoard.getContent() != null)  board.get().setContent(newBoard.getContent());
            try {
                boardRepository.save(board.get());
                return (1);
            } catch (IllegalArgumentException iae) {
                System.out.println(">>> 글 수정 중 저장 에러");
                return (0);
            } catch (Exception e) {
                System.out.println(">>> 글 수정 중 전역 에러");
                return (0);
            }
        }
        return (-1);
    }

    //삭제 - Delete : 글 삭제 시 권한 없는 요청(-1), 삭제 실패(0) / 성공(1) 여부를 반환
    @org.springframework.transaction.annotation.Transactional
    @Override
    public int delete(int boardId, int userId) {
        if (boardId < 0 || boardId > Integer.MAX_VALUE || userId < 0 || userId > Integer.MAX_VALUE) return (-1);

        Optional<Review> board = boardRepository.findById(boardId);
        if(board.isPresent()) {
            if (board.get().getUser().getId() != userId) return (-1);

            try {
            	//
            	boardcommentRepository.deleteAllByReviewId(boardId);
            	boardlikesRepository.deleteAllByReviewId(boardId);
            	
            	boardRepository.delete(board.get());
                return (1);
            } catch (IllegalArgumentException iae) {
                System.out.println(">>> 글 삭제 중 처리 에러");
                return (0);
            } catch (Exception e) {
                System.out.println(">>> 글 삭제 중 전역 에러" + e);
                return (0);
            }
        }
        return (-1);
    }

    private ReviewRes convertReviewRes(Review board) {
        Optional<User> user = userRepository.findById(board.getUser().getId());
        Optional<RealReport> realReport = realReportRepository.findById(board.getRealReport().getId());
        ReviewRes boardRes = new ReviewRes();

        if (user.isPresent()) {
        	boardRes.setUserId(user.get().getId());
        	
        	boardRes.setCompany(realReport.get().getCompany());
        	boardRes.setRealDate(realReport.get().getRealDate());
        	boardRes.setInfo(realReport.get().getInfo());
        	
            boardRes.setId(board.getId());
            // [smj] dateTime -> date (String)
            String onlyDate = board.getCreatedAt().toString().substring(2, 10);
            boardRes.setCreateTime(onlyDate);
            boardRes.setUpdateTime(board.getUpdatedAt());
            boardRes.setTitle(board.getTitle());
            boardRes.setContent(board.getContent());
            boardRes.setLikesCnt(board.getLikes_cnt());
            boardRes.setCommentCnt(board.getComment_cnt());

            return (boardRes);
        }
        return (null);
    }

    @Override
    public ReviewRes getInfo(int boardId, int userId) {
        if (boardId < 0 || boardId > Integer.MAX_VALUE || userId < 0 || userId > Integer.MAX_VALUE) return (null);

        Optional<Review> board = boardRepository.findById(boardId);
          
        
        if(board.isPresent()) {
        	ReviewRes boardRes = convertReviewRes(board.get());
//            // [smj] datetime -> date
//        	String onlyDate = boardRes.getCreateTime().substring(2, 10);
//            boardRes.setCreateTime(onlyDate);
        	
        	Reviewlikes boardlikes = boardlikesRepository.findByReviewIdAndUserId(boardId, userId);

            if (boardlikes == null) boardRes.setUserAlreadyPush(false);
            else boardRes.setUserAlreadyPush(true);

            return (boardRes);
        }
        return (null);
    }

    @Override
    public List<ReviewRes> getAllInfo() {
        List<Review> boardList = boardRepository.findAllByOrderByIdDesc();
        List<ReviewRes> boardResList = new ArrayList<>();

        for (int i = 0; i < boardList.size(); i++) {
        	ReviewRes boardRes = convertReviewRes(boardList.get(i));
            if (boardRes != null) boardResList.add(boardRes);
//            else return (null);
        }
        return (boardResList);
    }

    @Override
    public boolean changeLikesCnt(int boardId, int likesCnt) {
        if (boardId < 0 || boardId > Integer.MAX_VALUE) return (false);

        Optional<Review> board = boardRepository.findById(boardId);
        if (board.isPresent()) {
            board.get().setLikes_cnt(likesCnt);

            try {
                boardRepository.save(board.get());
            } catch (Exception e) {
                System.out.println(">>> 게시판 추천 개수 수정 중 에러");
                return (false);
            }
        }
        return (false);
    }

    @Override
    public boolean changeCommentCnt(int boardId, int commentCnt) {
        if (boardId < 0 || boardId > Integer.MAX_VALUE) return (false);

        Optional<Review> board = boardRepository.findById(boardId);
        if (board.isPresent()) {
            board.get().setComment_cnt(commentCnt);

            try {
                boardRepository.save(board.get());
            } catch (Exception e) {
                System.out.println(">>> 게시판 댓글 개수 수정 중 에러");
                return (false);
            }
        }
        return (false);
    }

    @Override
    public Page<ReviewRes> pageList(Pageable pageable) {
        return (boardRepository.findAll(pageable).map(review -> { return (convertReviewRes(review)); }));
    }

}
