package com.ssafy.init.api.service;

import com.ssafy.init.api.request.UserQlistReq;
import com.ssafy.init.api.response.UserQlistRes;
import com.ssafy.init.db.entity.Question;
import com.ssafy.init.db.entity.User;
import com.ssafy.init.db.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserQlistServiceImpl implements UserQlistService {
	private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
	
    @Autowired
    QuestionRepository questionRepository;


	// [smj] Question에 UserQlistReq 넣기
	@Override
	public Question fillQuestionUser(UserQlistReq userQlistReq) {
		Question quesiontUser = new Question();
		quesiontUser.setId(userQlistReq.getId());
		quesiontUser.setPriority(userQlistReq.getPriority());
		quesiontUser.setQuest(userQlistReq.getQuest());
		quesiontUser.setType(userQlistReq.getType());
		
		quesiontUser.setAdmin(false);
		User user = new User();
		user.setId(userQlistReq.getUserId());
		quesiontUser.setUser(user);
		
		return quesiontUser;
	}

	// [smj] UserQlistRes에 Question 넣기
	@Override
	public UserQlistRes fillUserQlistRes(Question question) {
		UserQlistRes userQlistRes = new UserQlistRes();
		userQlistRes.setId(question.getId());
		userQlistRes.setPriority(question.getPriority());
		userQlistRes.setQuest(question.getQuest());
		userQlistRes.setType(question.getType());
		return userQlistRes;
	}
	
    // [smj] 질문 추가
	@Override
	public UserQlistRes insertUserQuestion(UserQlistReq userQuestion) {
		try {
			Question question = questionRepository.save(fillQuestionUser(userQuestion));
			return fillUserQlistRes(question);
		}catch (Exception e) {
            System.out.println("Exception : "+e);
            return null;
        }
	}

	// [smj] 질문 리스트 조회
	@Override
	public List<UserQlistRes> selectAllUserQuestion(int userId) {
		
		List<Question> list = questionRepository.findAllByUserId(userId);
		
		List<UserQlistRes> returnList = new ArrayList<UserQlistRes>();
		for(int i=0; i<list.size(); i++) {
			returnList.add(fillUserQlistRes(list.get(i)));
		}
		
		return returnList;
	}

	// [smj] 질문 수정
	@Override
	public UserQlistRes updateUserQuestion(UserQlistReq userQuestion) {

		try {
			Optional<Question> option = questionRepository.findById(userQuestion.getId());
			// 없는 id가 들어와서 반환할 데이터가 없는 경우도 에러 발생하는지 체크
			Question question = option.get();
			
			question.setPriority(userQuestion.getPriority());
			question.setQuest(userQuestion.getQuest());
			question.setType(userQuestion.getType());
			
			questionRepository.save(question);
			
			return fillUserQlistRes(question);
			
		} catch (Exception e) { 
			return null;
		}
	}

	// [smj] 질문 삭제
	@Override
	public String deleteUserQuestion(int id) {
		try {
			questionRepository.deleteById(id);
			return SUCCESS;
		} catch (Exception e) {
			return FAIL;
		}
	}
	
}
