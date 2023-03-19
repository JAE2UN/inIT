package com.ssafy.init.api.service;

import java.util.List;

import com.ssafy.init.api.request.UserQlistReq;
import com.ssafy.init.api.response.UserQlistRes;
import com.ssafy.init.db.entity.Question;

public interface UserQlistService {
	// QuestionUser에 UserQlistReq 넣기
	Question fillQuestionUser(UserQlistReq userQuestion);
	
	// UserQlistRes에 QuestionUser 넣기
	UserQlistRes fillUserQlistRes(Question question);
	
    // 질문 추가
    UserQlistRes insertUserQuestion(UserQlistReq userQuestion);
    
    // 질문 리스트 조회
    List<UserQlistRes> selectAllUserQuestion(int userId);
    
    // 질문 수정
    UserQlistRes updateUserQuestion(UserQlistReq userQuestion);
    
    // 질문 삭제
    String deleteUserQuestion(int id);
}
