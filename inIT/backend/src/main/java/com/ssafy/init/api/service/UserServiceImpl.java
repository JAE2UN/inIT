package com.ssafy.init.api.service;

import com.ssafy.init.db.entity.User;
import com.ssafy.init.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
	private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
	
    @Autowired
    UserRepository userRepository;


    // [psh]
    @Autowired
    private PasswordEncoder passwordEncoder;

    // [smj] 회원가입
    @Override
    public String registerUser(User userInfo) {
        System.out.println(">>> service user : " + userInfo);

        try {
        	userInfo.setGrade(1);
        	userInfo.setTemperature(36);

            //[psh]
            userInfo.setPw(passwordEncoder.encode(userInfo.getPw()));

            userRepository.save(userInfo);
            return SUCCESS;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("test log -> Fail");
            return FAIL;
        }
    }

    // [smj] 로그인
    @Override
    public int checkInfo(String email, String pw) {

        if(getUser(email) != null) { // 입력된 email과 일치하는 데이터 있는 경우
            // 비밀번호 체크
            String realPw = getUser(email).getPw();

            // [psh]
            if (passwordEncoder.matches(pw, realPw)) return (2);
            else return (1);

//            if(!realPw.equals(pw)) { // 비밀번호 일치하지 않으면
//                return 1;
//            } else { // 비밀번호 일치하면
//                return 2;
//            }
        }
        // 입력된 email과 일치하는 데이터가 없는 경우 0 리턴
        return 0;
    }

    // [smj] 사용자 정보 조회, email 중복 확인
    @Override
    public User getUser(String email) {
        Optional<User> option = userRepository.findByEmail(email);
        if(!option.isPresent())
            return null;
        User userInfo = option.get();

        return userInfo;
    }


	// [smj] nick 중복 확인
	@Override
	public int checkNick(String nick) {
		User userInfo = userRepository.findByNick(nick);
		if(userInfo == null) { // 입력된 nick과 일치하는 데이터 없는 경우
			return 0;
		}
		return 1;
	}
	
	// [smj] 사용자 정보 변경
	// id email pw nick tel grade temperature status_msg created_at updated_at
	@Override
	public User updateUser(User userInfo) {
		Optional<User> originUserInfo = userRepository.findByEmail(userInfo.getEmail());
		
		if(!originUserInfo.isPresent())
            return null;
		
		User updateUserInfo = originUserInfo.get();
		// nick 중복 여부 확인 후 update해야함 
		// 브라우저에서 중복 확인을 했다고 해도 내가 수정버튼 누르기 전 그 사이에 누군가가 해당 nick으로 변경하면 쿼리문이 정상작동되지 않고 에러날듯
		updateUserInfo.setNick(userInfo.getNick());
		updateUserInfo.setImgUrl(userInfo.getImgUrl());
		updateUserInfo.setStatusMsg(userInfo.getStatusMsg());
		
		userRepository.save(updateUserInfo);
		
		return updateUserInfo;
	}

	// [smj] 사용자 정보 전송
	@Override
	public User sendUser(User userInfo) {
		if(userInfo != null) {
			// 유저 정보 보낼 때 pw, createdAt, UpdatedAt, id 보내지 않기
			userInfo.setPw(null);
			userInfo.setCreatedAt(null);
			userInfo.setUpdatedAt(null);
		}
		return userInfo;
	}

    // [psh] s3에서 사용 imgUrl Update
    @Override
    public boolean updateUserImgUrl(String url, int userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            if (url != "") user.get().setImgUrl(url);
            else user.get().setImgUrl(null);

            userRepository.save(user.get());
            return (true);
        }
        return (false);
    }

}
