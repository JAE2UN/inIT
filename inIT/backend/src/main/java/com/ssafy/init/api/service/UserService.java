package com.ssafy.init.api.service;

import com.ssafy.init.db.entity.User;

public interface UserService {
    // 회원가입
    String registerUser(User userInfo);
    // 로그인
    int checkInfo(String email, String pw);
    // 사용자 정보 조회, email 중복 확인
    User getUser(String email);
        
    // nick 중복 확인
    int checkNick(String nick);
    
    // 사용자 정보 변경
    User updateUser(User userInfo);
    
    // 사용자 정보 전송
    User sendUser(User userInfo);

//    s3에서 사용 imgUrl Update
    boolean updateUserImgUrl(String url, int userId);
}
