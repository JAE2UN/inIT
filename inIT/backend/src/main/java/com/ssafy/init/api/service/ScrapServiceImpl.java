package com.ssafy.init.api.service;

import com.ssafy.init.api.request.ScrapReq;
import com.ssafy.init.api.response.ScrapRes;
import com.ssafy.init.db.entity.Question;
import com.ssafy.init.db.entity.Scrap;
import com.ssafy.init.db.entity.User;
import com.ssafy.init.db.repository.QuestionRepository;
import com.ssafy.init.db.repository.ScrapRepository;
import com.ssafy.init.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScrapServiceImpl implements ScrapService {

    @Autowired
    private ScrapRepository scrapRepository;
    @Autowired
    private UserRepository userRepository; 
    @Autowired
    private QuestionRepository questionRepository;    
    

	// [smj] ScrapRes에 Scrap 넣기
	@Override
	public ScrapRes fillScrapRes(Scrap scrap) {
		ScrapRes scrapRes =  new ScrapRes();
		scrapRes.setId(scrap.getId());
		scrapRes.setQuestionId(scrap.getQuestion().getId());
		scrapRes.setType(scrap.getQuestion().getType());
		scrapRes.setQuest(scrap.getQuestion().getQuest());
		scrapRes.setAns(scrap.getAns());
		scrapRes.setUserId(scrap.getUser().getId());
		
		return scrapRes;
	}
    
    
	// [smj] 오답 노트 항목 추가 
	@Override
	public int insert(int questionId, int userId) {
		// 면접 일지에서만 추가할 수 있으므로 리스트를 반환하지 않고 int를 반환
		// questionId가 questNo일 듯
		
		Question question = questionRepository.findById(questionId).get();
		User user = userRepository.findById(userId).get();
		
		Scrap scrap = new Scrap();
		scrap.setUser(user);
		scrap.setQuestion(question);
		
		try {
			scrapRepository.save(scrap);
			return 1;
		} catch (Exception e) {
			System.out.println(">>> Exception: "+ e);
			return 0;
		}
	}
	
    // [smj] 오답 노트 조회 (리스트)
	@Override
	public List<ScrapRes> selectAll(int userId) {
		
		List<Scrap> scrapList = scrapRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
		
		List<ScrapRes> scrapResList = new ArrayList<ScrapRes>();
		
		for(int i=0; i<scrapList.size(); i++) {
			scrapResList.add(fillScrapRes(scrapList.get(i)));			
		}
		
		return scrapResList;
	}
	
	
    // [smj] 오답 노트 항목 수정 (답변만 수정 가능한 듯)
	@Override
	public ScrapRes update(ScrapReq scrapReq) {
		Scrap scrap = scrapRepository.findById(scrapReq.getId()).get();
		
		if(scrapReq.getUserId() != scrap.getUser().getId()) {
			return null;
		}
		scrap.setAns(scrapReq.getAns());
		
		Scrap updateScrap = scrapRepository.save(scrap);
		
		return fillScrapRes(updateScrap);
	}
	
    // [smj] 오답 노트 항목 삭제
	@Transactional
	@Override
	public int delete(int userId, int scrapId) {
		
		try {
			Scrap scrap = scrapRepository.findById(scrapId).get();
			
			if(userId != scrap.getUser().getId()) {
				// 잘못된 접근. 자기 게시글도 아닌데 지우려 함
				return -1;
			}
			
			scrapRepository.delete(scrap);
			return 1;
		} catch (Exception e) {
			System.out.println(">> Exception: "+e);
			return 0;
		}
	}


}
