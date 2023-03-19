package com.ssafy.init.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.init.api.request.AnswerReq;
import com.ssafy.init.api.request.ChecklistReq;
import com.ssafy.init.api.response.AnswerRes;
import com.ssafy.init.api.response.ChecklistRes;
import com.ssafy.init.db.entity.Answer;
import com.ssafy.init.db.entity.Checklist;
import com.ssafy.init.db.entity.Evaluate;
import com.ssafy.init.db.entity.Question;
import com.ssafy.init.db.entity.Report;
import com.ssafy.init.db.repository.AnswerRepository;
import com.ssafy.init.db.repository.ChecklistRepository;
import com.ssafy.init.db.repository.EvaluateRepository;
import com.ssafy.init.db.repository.QuestionRepository;
import com.ssafy.init.db.repository.ReportRepository;

@Service
public class RealQaServiceImpl implements RealQaService {
	private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    
    // 사용하는 DB: 체크리스트, 평가, 질문, 답변
    @Autowired
    ChecklistRepository checklistRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    EvaluateRepository evaluateRepository;
    @Autowired
    QuestionRepository questionRepository;

    // [sje] 체크리스트 생성
	@Override
	@Transactional
	public String createList(int rid) {
		// 면접일지 번호(rid)를 외래키로, rid == cid인 체크리스트 생성
		try {
			Checklist cl = new Checklist();
			Optional<Report> r = reportRepository.findById(rid);
			if(!r.isPresent()) return FAIL;
			// id, type, report 연결
			cl.setId(rid);
			if(r.get().getType() == 3) return FAIL; // 실전면접은 체크리스트 X
			cl.setType(r.get().getType());
			cl.setReport(r.get());
			Checklist c = checklistRepository.save(cl);
			System.out.println("체크리스트 생성 : "+c.getId());
			return SUCCESS;
		} catch (Exception e) {
			return FAIL;
		}
		
	}


	// [sje] 체크리스트 작성(수정)
	@Override
	public boolean writeList(ChecklistReq clReq) throws Exception {
		// 작성(수정)할 체크리스트를 불러옴
		Optional<Checklist> list = checklistRepository.findById(clReq.getReportId());
		int rid = clReq.getReportId(); // 면접번호
		// 연습한 질문리스트 -> 면접 연습 시작할 때 생성
		List<Answer> ansList = answerRepository.findAllByReportId(rid);
		// 답변한 질문리스트(answerId, 평가점수)
		List<AnswerReq> ansReq = clReq.getAnswerReq();
		/* !!! 면접연습 질문리스트 저장에 활용 !!!
		for(int i=0; i<ansList.size(); i++) {
			Answer ans = new Answer();
			Optional<Question> quest = questionRepository.findById(clReq.getQuestNo());
			if(quest.isPresent()) { 
				Question q = quest.get();
				Optional<Report> r = reportRepository.findById(rid);
				if(r.isPresent()) {
					ans.setReport(r.get());
					ans.setFlag(1); // 유형: 질문
					ans.setQuestNo(ansList.get(i).getQuestNo());
				}
			}
		}
		*/
		
		// 체크리스트가 없으면 throw exception(현재는 return null)
		if(!list.isPresent()) return false;
		
		// 체크리스트 작성(수정) - 총평, 평가
		Checklist cl = list.get();
		cl.setAllCmt(clReq.getAllCmt());
		
		// 평가 작성
		if(clReq.getType() == 1) { // 일반 체크리스트 -> 평가 문제와 답변을 저장
			// eval의 평가항목을 answer에 cl의 reportid로 할당
			Optional<Report> r = reportRepository.findById(rid);
			if(!r.isPresent()) return false;
			for(int i=0; i<ansList.size(); i++) {
				// 기본설정(면접일지와 연결, 유형:평가)
				Answer ans = ansList.get(i);
				ans.setReport(r.get());
				ans.setFlag(2);
				// 문제번호, 답변 저장 - ansReq에 있는 문제번호로 eval에서 문제 찾아와서 번호와 점수 저장
				Optional<Evaluate> eval = evaluateRepository.findById(ansReq.get(i).getQuestNo());
				if(eval.isPresent()) {
					ans.setQuestNo(ansReq.get(i).getQuestNo());
					ans.setEvalScore(ansReq.get(i).getEvalScore());
				}
				answerRepository.save(ans);
			}
		} else if(cl.getType() == 2) { // 기술 -> 저장된 질문리스트에 평가 저장
			for(int i=0; i<ansList.size(); i++) {
				Answer ans = ansList.get(i);
				// 연습한 질문리스트의 질문이 question 유형이면 번호 찾아서 문제별 평가 넣어줌
				if(ans.getFlag() == 1) {
					if(ans.getQuestNo() == ansReq.get(i).getQuestNo()) {
						ans.setCsScore(ansReq.get(i).getCsScore());
					}
				}
				answerRepository.save(ans);
			}
		} else {
			throw new Exception("체크리스트 구분 에러");
		}
		
		checklistRepository.save(cl);
		return true;
	}

	// [sje] 체크리스트 조회1 - 체크리스트
	@Override
	public ChecklistRes getClist(int rid) {
		// 보낼 체크리스트Res
		ChecklistRes clRes = new ChecklistRes();
		// 조회할 체크리스트를 report id로 불러옴
		Optional<Checklist> list = checklistRepository.findById(rid);
		if(!list.isPresent()) return null;
		
		// 체크리스트, ansRes의 값을 clRes에 넣어 보내줌
		Checklist cl = list.get();
		clRes.setAllCmt(cl.getAllCmt());
		clRes.setReportId(rid);
		clRes.setType(cl.getType());
		clRes.setAnswerRes(getAnsList(rid));
		return clRes;
	}

	// [sje] 체크리스트 조회2 - 평가(질문)
	@Override
	public List<AnswerRes> getAnsList(int rid) {
		List<AnswerRes> ansRes = new ArrayList<AnswerRes>();
		List<Answer> list = answerRepository.findAllByReportId(rid);
		
		for(int i=0; i<list.size(); i++) {
			AnswerRes ans = new AnswerRes();
			Answer ai = list.get(i);
			if(ai.getFlag() == 1) { // 연습한 질문
				Optional<Report> r = reportRepository.findById(rid);
				Optional<Question> q = questionRepository.findById(ai.getQuestNo());
				if(r.isPresent() && q.isPresent()) {
					// 질문구분, 문제 저장
					ans.setQuest(q.get().getQuest());
					ans.setType(q.get().getType());
					// 일반연습 -> 개별 답변 X, 기술연습 -> 개별 답변 O
					if(r.get().getType() == 2) {
						// 평가점수(기술)
						ans.setCsScore(ai.getCsScore());
					}
				} else return null;
				
			} else if(ai.getFlag() == 2) { // 평가항목
				// 문제, 평가점수
				Optional<Evaluate> eval = evaluateRepository.findById(ai.getQuestNo());
				if(eval.isPresent()) {
					ans.setQuest(eval.get().getEval());
					ans.setEvalScore(ai.getEvalScore());
				}
			}
			
			ansRes.add(ans);
		}
		
		return ansRes;
	}


}
