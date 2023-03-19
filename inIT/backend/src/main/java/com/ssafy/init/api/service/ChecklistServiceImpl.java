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
public class ChecklistServiceImpl implements ChecklistService {
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
	public int writeList(ChecklistReq clReq) throws Exception {
		// 작성(수정)할 체크리스트를 불러옴
		Optional<Checklist> list = checklistRepository.findById(clReq.getReportId());
		int rid = clReq.getReportId(); // 면접번호
		// 연습한 질문리스트 -> ans에 저장(질문, 평가 둘 다 저장되어 있음)
		List<Answer> ansList = answerRepository.findAllByReportId(rid);
		// 답변한 질문리스트(answerId, 평가점수)
		List<AnswerReq> ansReq = clReq.getAnswerReq();
		
		// 체크리스트가 없으면 throw exception(현재는 return null)
		if(!list.isPresent()) return -1;
		
		// 체크리스트 작성(수정) - 총평, 평가
		Checklist cl = list.get();
		cl.setAllCmt(clReq.getAllCmt());
		System.out.println("총평 작성");
		// 평가 작성
		if(clReq.getType() == 1) { // 일반 체크리스트 -> 평가 문제와 답변을 저장
			// eval의 평가항목을 answer에 cl의 reportid로 할당
			Optional<Report> r = reportRepository.findById(rid);
			if(!r.isPresent()) return -1;
			System.out.println("report 존재: "+rid);
			// interview/evaluate에서 평가 문제 저장해놓음-> 답변을 해당 문제에 넣어주면 됨
			List<Answer> evalList = answerRepository.findAllByFlagAndReportId(2, rid);
			System.out.println("저장된 평가항목: "+evalList);
			for(int i=0; i<evalList.size(); i++) {
				// Answer 불러와서 번호 같은지 확인 후 같으면 score 저장, 아니면 -1
				Answer eval = evalList.get(i);
				System.out.println("eval: "+eval.getQuestNo()+", req: "+ansReq.get(i).getQuestNo());
				if(eval.getQuestNo() != ansReq.get(i).getQuestNo()) return -1;
				
				eval.setEvalScore(ansReq.get(i).getEvalScore());
				System.out.println(i+". qno: "+eval.getQuestNo()+", score: "+eval.getEvalScore());
			}
			
			
		} else if(cl.getType() == 2) { // 기술 -> 저장된 질문리스트에 평가 저장
			System.out.println(ansReq);
			for(int i=0; i<ansList.size(); i++) {
				Answer ans = ansList.get(i);
				// 연습한 질문리스트의 질문이 question 유형이면 번호 찾아서 문제별 평가 넣어줌
				if(ans.getFlag() == 1) {
					//System.out.println("ansqno: "+ans.getQuestNo()+", ansReqqno: "+ansReq.get(i).getQuestNo());
					ans.setCsScore(ansReq.get(i).getCsScore());
					System.out.println(i+". ansreq score: "+ansReq.get(i).getCsScore());
					// id가 아니라 questno로 swagger에서 입력해야 이거 체크 가능
					//if(ans.getQuestNo() == ansReq.get(i).getQuestNo()) {}
				}
				answerRepository.save(ans);
				System.out.println(i+". score: "+ans.getCsScore());
			}
		} else {
			throw new Exception("체크리스트 구분 에러");
		}
		
		checklistRepository.save(cl);
		return rid;
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
					ans.setFlag(1);
					ans.setQuestNo(q.get().getId());
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
					ans.setFlag(2);
					ans.setQuestNo(eval.get().getId());
					ans.setQuest(eval.get().getEval());
					ans.setEvalScore(ai.getEvalScore());
				}
			}
			
			ansRes.add(ans);
		}
		
		return ansRes;
	}


}
