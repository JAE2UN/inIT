package com.ssafy.init.api.service;

import com.ssafy.init.api.request.ReviewReq;
import com.ssafy.init.api.response.FeedbackRes;
import com.ssafy.init.api.response.ReviewRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {

    //작성 - Create : 글 작성 성공 시 글 번호, 실패 시 -1 반환
    int insert(ReviewReq reviewreq);

    //수정 - Update : 글 수정 시 권한 없는 요청(-1), 수정 실패(0) / 성공(1) 여부를 반환
    int modify(ReviewReq reviewReq);

    //삭제 - Delete : 글 삭제 시 권한 없는 요청, 삭제 실패 / 성공 여부를 반환
    int delete(int reviewId, int userId);

    //읽기 - Read : 실패 시 null 반환
    ReviewRes getInfo(int reviewId, int userId);

    //읽기 - Read : 글 정보 전체 반환 실패 시 null 반환
    List<ReviewRes> getAllInfo();

    
    boolean changeLikesCnt(int reviewId, int likesCnt);

    boolean changeCommentCnt(int reviewId, int commentCnt);

    Page<ReviewRes> pageList(Pageable pageable);
}
