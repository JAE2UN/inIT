package com.ssafy.init.api.service;

import com.ssafy.init.db.entity.Review;
import com.ssafy.init.db.entity.Reviewlikes;
import com.ssafy.init.db.entity.User;
import com.ssafy.init.db.repository.ReviewRepository;
import com.ssafy.init.db.repository.ReviewlikesRepository;
import com.ssafy.init.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ReviewlikesServiceImpl implements ReviewlikesService {
    @Autowired
    private ReviewService boardService;
    @Autowired
    private ReviewlikesRepository boardlikesRepository;
    @Autowired
    private ReviewRepository boardRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    @Transactional
    public int insert(int boardId, int userId) {
        if (boardId < 0 || boardId > Integer.MAX_VALUE || userId < 0 || userId > Integer.MAX_VALUE) return (-1);

        Optional<Review> board = boardRepository.findById(boardId);
        Optional<User> user = userRepository.findById(userId);
        if (board.isPresent() && user.isPresent()) {
        	Reviewlikes boardlikes = new Reviewlikes();

            boardlikes.setUser(user.get());
            boardlikes.setReview(board.get());
            try {
                int cnt = board.get().getLikes_cnt() + 1;

                //실패 시 트렌젝션 처리
                boardlikesRepository.save(boardlikes);
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

        Optional<Review> board = boardRepository.findById(boardId);
        Optional<User> user = userRepository.findById(userId);
        if (board.isPresent() && user.isPresent()) {
        	Reviewlikes boardlikes = boardlikesRepository.findByReviewIdAndUserId(boardId, userId);

//            System.out.println(">>> : service : " + boardlikes);

            if (boardlikes == null) return (-1);
            try {
                int cnt = board.get().getLikes_cnt() - 1;

                //실패 시 트렌젝션 처리
                boardService.changeLikesCnt(boardId, cnt);
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
