package com.ssafy.init.api.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.init.api.request.InterviewReq;
import com.ssafy.init.api.request.ReadyReq;
import com.ssafy.init.api.response.AnswerRes;
import com.ssafy.init.api.response.ChecklistRes;
import com.ssafy.init.api.response.InterviewRes;
import com.ssafy.init.db.entity.Answer;
import com.ssafy.init.db.entity.Evaluate;
import com.ssafy.init.db.entity.Question;
import com.ssafy.init.db.entity.Report;
import com.ssafy.init.db.repository.AnswerRepository;
import com.ssafy.init.db.repository.EvaluateRepository;
import com.ssafy.init.db.repository.QuestionRepository;
import com.ssafy.init.db.repository.ReportRepository;

@Service
public class InterviewServiceImpl implements InterviewService {
	private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    
    // 면접관련 repository: question, checklist, answer, record, report
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    EvaluateRepository evaluateRepository;
    @Autowired
    ChecklistService checklistService;
    

    // [sje] 면접에서 연습할 문제 답변리스트에 넣기 -> return -2(에러), -1(나질리), serverQno+myQno(전체문제개수: 성공)
	@Override
	public int createQuestion(int rid, ReadyReq readyReq) {
		try {
			int serverQno = readyReq.getChoiceNum().get(0); // 서버 문제개수
			int myQno = readyReq.getChoiceNum().get(1); // 사용자 문제개수
			int userId = readyReq.getUserId(); // 사용자id
			int success = serverQno + myQno; // 성공시 return 값(전체 입력 문제개수)
			List<Integer> cquest = readyReq.getChoiceQuest();
			System.out.println("서버문제: "+serverQno+", 사용자문제: "+myQno);

			if(serverQno+myQno > 15) return -2; // 합쳐서 15개 이상 넘으면 안됨(기술은 어차피 항목별 개수라 넘을 수 없음)
			if(serverQno==0 && myQno==0) return -2; // 둘 다 개수 없으면 연습할 문제 X므로 에러
			else if(serverQno == 0) {
				// 사용자질문리스트만 사용하는 경우
				if(readyReq.getType()==1) {
					// 일반연습 -> 인성, CS, 프로젝트 유형별로 사용자 질문 리스트에서 불러옴
					int save = 0; // DB에 저장된 사용자 문제 개수
					for(int i=1; i<=3; i++) {
						save += questionRepository.findAllByUserIdAndTypeOrderByPriorityDesc(userId, i*10).size();
						if(i==2) { // CS(21~29)
							for(int j=1; j<10; j++) {
								save += questionRepository.findAllByUserIdAndTypeOrderByPriorityDesc(userId, i*10+j).size();
							}
						}
					}
					// 사용자 요청 문제 개수가 저장된 문제수보다 많으면 문제 불러오기 불가능 -> 나질리 에러
					if(myQno > save) return -1;

					System.out.println("사용자: 일반");
					boolean result = myQTA(1, userId, rid, myQno);
					
					if(result) return success;
					else return -2;
					
				} else {
					// 기술연습 -> 세부유형 상관없이 사용자 질문 리스트의 기술에서만 불러옴
					int save = 0; // DB에 저장된 사용자 문제 개수
					for(int i=1; i<10; i++) {
						save += questionRepository.findAllByUserIdAndTypeOrderByPriorityDesc(userId, 20+i).size();
					}
					// 사용자 요청 문제 개수가 저장된 문제수보다 많으면 문제 불러오기 불가능 -> 나질리 에러
					if(myQno > save) return -1;

					System.out.println("사용자: 기술");
					boolean result = myQTA(2, userId, rid, myQno);
					
					if(result) return success;
					else return -1;
				}
			} else { // 서버 질문 항상 사용&(사용자질문리스트 OX) -> cquest 고려
				boolean admin = false;
				boolean user = true;
				if(readyReq.getType()==1) {
					// 일반연습 - 사용자질문(존재할 때), 서버질문 각각 뽑아서 답변에 저장
					System.out.println("user: "+user+", admin: "+admin);
					if(serverQno < 3) return -2; // 일반유형 서버질문 받을때는 최소가 3
					if(myQno != 0) {
						int save = 0; // DB에 저장된 사용자 문제 개수
						for(int i=1; i<=3; i++) {
							save += questionRepository.findAllByUserIdAndTypeOrderByPriorityDesc(userId, i*10).size();
							if(i==2) { // CS(21~29)
								for(int j=1; j<10; j++) {
									save += questionRepository.findAllByUserIdAndTypeOrderByPriorityDesc(userId, i*10+j).size();
								}
							}
						}
						// 사용자 요청 문제 개수가 저장된 문제수보다 많으면 문제 불러오기 불가능 -> 나질리 에러
						if(myQno > save) return -1;
						user = myQTA(1, userId, rid, myQno);
					}
					admin = generalAdminQTA(rid, serverQno);
					System.out.println("admin 일반 완료: "+admin);
					if(user && admin) return success;
					
				} else if(readyReq.getType()==2) {
					// 기술연습 - 사용자질문(존재할 때), 서버질문 각각 뽑아서 답변에 저장
					if(myQno != 0) {
						int save = 0; // DB에 저장된 사용자 문제 개수
						for(int i=1; i<10; i++) {
							save += questionRepository.findAllByUserIdAndTypeOrderByPriorityDesc(userId, 20+i).size();
						}
						// 사용자 요청 문제 개수가 저장된 문제수보다 많으면 문제 불러오기 불가능 -> 나질리 에러
						if(myQno > save) return -1;
						user = myQTA(2, userId, rid, myQno);
					}
					admin = csAdminQTA(rid, serverQno, cquest);
					System.out.println("admin CS 완료: "+admin);
					
					if(user && admin) return success;
				} else return -2;
				
			}
			return success;
		} catch (Exception e) {
			return -2;
		}
		
	}
	
	// [sje] 면접 평가를 위해  해당 면접의 checklist 찾아오고 checklistRes return
	@Override
	public ChecklistRes settingEvaluate(int rid) {
		Optional<Report> r = reportRepository.findById(rid);
		if(!r.isPresent()) return null;
		ChecklistRes clRes = checklistService.getClist(rid); // 해당 번호의 체크리스트 조회
		if(clRes != null) {
			if(clRes.getType() == 1) { // 일반 -> 평가항목이 조회할 때 들어가있어야함
				List<AnswerRes> anslist = clRes.getAnswerRes();
				System.out.println("체크리스트 조회, ansRes: "+anslist);
				if(anslist.size()<12 || anslist.get(anslist.size()-1).getFlag()==1) {
					// 평가항목 안 들어감(평가항목 12개 or 마지막 항목 flag가 1(질문)) -> 평가항목 넣어줘야함
					List<Evaluate> eval = evaluateRepository.findAll();
					for(int i=0; i<eval.size(); i++) {
						Answer ans = new Answer();
						ans.setReport(r.get());
						ans.setFlag(2);
						ans.setQuestNo(eval.get(i).getId());
						answerRepository.save(ans);
					}
				}
				clRes = checklistService.getClist(rid); // 값 업데이트	
			}
			if(clRes != null) return clRes;
			else return null;
		} else return null;
	}
	
	// [sje] 면접 연습을 위해 다음 문제를 담은 InterviewRes 만들고 return
	@Override
	public InterviewRes getNextQuest(InterviewReq interviewReq) {
		int next = interviewReq.getNextNo(); // 처음이 1
		InterviewRes interviewRes = new InterviewRes();
		
		// 답변에서 현재 면접의 질문만 불러옴(flag: 1)
		List<Answer> list = answerRepository.findAllByFlagAndReportId(1, interviewReq.getReportId());
		List<Question> qlist = new ArrayList<>();
		for(int i=0; i<list.size(); i++) {
			Optional<Question> quest = questionRepository.findById(list.get(i).getQuestNo());
			if(!quest.isPresent()) interviewRes.setNowNo(-2); // 질문 못 찾아오면 에러
			else qlist.add(quest.get());
			
		}
		// 숫자 시작이 0(필요한 번호 요청)이면 now를, 1이면 now-1을 가져오면 됨
		if(qlist.size() == 0) { // 에러
			interviewRes.setNowNo(-2);
		} else if(qlist.size() < next) { // 요청 들어온 번호가 문제개수보다 많아짐 -> 종료해야함
			interviewRes.setNowNo(-1);
			interviewRes.setQuest("연습 완료!");
		} else { // 정상 실행 -> 문제번호와 문제 저장
			interviewRes.setNowNo(next);
			interviewRes.setQuest(qlist.get(next-1).getQuest());
		}
		return interviewRes;
	}
	
	
	/* 사용하는 method */

	// 기술연습: 서버 질문에서 선정해서 답변에 저장
	private boolean csAdminQTA(int rid, int qno, List<Integer> cquest) {
		// 기술연습: CS(21~26, 29) 7항목중 넘어온 항목수별로 문제 뽑아서 answer에 저장
		for(int i=0; i<cquest.size(); i++) {
			List<Question> qlist = questionRepository.findAllByIsAdminAndTypeOrderByPriorityDesc(true, cquest.get(i));
			// 우선순위 3인 문제는 필수적으로 뽑아야함
			int must = questionRepository.findAllByIsAdminAndTypeAndPriority(true, cquest.get(i), 3).size();
			List<Integer> qnolist = questRandom(qno, qlist, must);
			boolean anslist = makeAnswer(rid, qnolist);
			
			if(!anslist) return false;
		}
		return true;
	}

	// 일반연습: 서버 질문에서 선정해서 답변에 저장
	private boolean generalAdminQTA(int rid, int qno) {
		
		// 일반연습: 인성, CS, 프로젝트 3항목(10, 20, 30), 각 항목에서 문제 뽑아서 answer에 저장
		List<Integer> list = divideNo(qno);
		System.out.println("항목별 문제개수: "+list);
		int must = 0;
		for(int i=1; i<=3; i++) {
			List<Question> qlist = questionRepository.findAllByIsAdminAndTypeOrderByPriorityDesc(true, i*10);
			must = questionRepository.findAllByIsAdminAndTypeAndPriority(true, i*10, 3).size();
			if(i==2) { // 기술 -> 21~29 범위 다 가져와서 addAll
				for(int t=3; t>=1; t--) { // 우선순위 높은 순으로 담아줌
					for(int j=1; j<10; j++) {
						qlist.addAll(questionRepository.findAllByIsAdminAndTypeAndPriority(true, i*10+j, t));
						must += questionRepository.findAllByIsAdminAndTypeAndPriority(true, i*10+j, 3).size();
					}	
				}
			}
			System.out.println("qlist: "+qlist);
			// 우선순위 3인 문제는 필수적으로 뽑아야함
			List<Integer> qnolist = questRandom(list.get(i-1), qlist, must);
			System.out.println("qnolist: "+qnolist);
			boolean anslist = makeAnswer(rid, qnolist);
			System.out.println("anslist: "+anslist);
			must = 0; // reset
			if(!anslist) return false;
		}
		return true;
	}

	// 사용자리스트 질문에서 선정해서 답변에 저장
	private boolean myQTA(int type, int userId, int rid, int qno) {
		if(type == 1) {
			// 일반연습: 인성, CS, 프로젝트 3항목(10, 20, 30), 각 항목에서 문제 뽑아서 answer에 저장
			List<Question> qlist = new ArrayList<>();
			int must = questionRepository.findAllByUserIdAndPriority(userId, 3).size();
			for(int t=3; t>=1; t--) { // 우선순위 높은 순으로 담아줌
				for(int i=10; i<=30; i++) {
					qlist.addAll(questionRepository.findAllByUserIdAndTypeAndPriority(userId, i, t));
				}	
			}
			System.out.println("myQTA general: "+qlist);
			List<Integer> qnolist = questRandom(qno, qlist, must);
			System.out.println(qnolist);
			boolean anslist = makeAnswer(rid, qnolist);
			
			if(!anslist) return false;
			return true;
		} else if(type == 2) {
			// 기술연습: CS(21~29) 항목에서 내질문 불러오고 거기서 random으로 qno만큼 뽑아서 answer에 저장
			List<Question> qlist = new ArrayList<>(); 
			int must = questionRepository.findAllByUserIdAndPriority(userId, 3).size();
			
			for(int t=3; t>=1; t--) { // 우선순위 높은 순으로 담아줌
				for(int i=1; i<10; i++) {
					qlist.addAll(questionRepository.findAllByUserIdAndTypeAndPriority(userId, 20+i, t));
				}	
			}
			System.out.println("myQTA CS: "+qlist);
			List<Integer> qnolist = questRandom(qno, qlist, must);
			System.out.println(qnolist);
			boolean anslist = makeAnswer(rid, qnolist);
			
			if(!anslist) return false;
			return true;
		} else return false; // 에러
	}

	// 문제번호 리스트(qnolist)를 rid에 맞게 Answer 테이블에 넣어줌
	private boolean makeAnswer(int rid, List<Integer> qnolist) {
		Optional<Report> report = reportRepository.findById(rid);
		for(int i=0; i<qnolist.size(); i++) {
			Answer ans = new Answer();
			if(!report.isPresent()) return false;
			ans.setFlag(1); // 질문
			ans.setReport(report.get());
			ans.setQuestNo(qnolist.get(i));
			answerRepository.save(ans);
			System.out.println(i+". answer 저장 완료");
		}
		return true;
	}

	// 선정된 리스트(qlist)에서 필요한 문제 개수(qno)만큼 랜덤으로 뽑아서 저장한 문제번호를 return
	private List<Integer> questRandom(int qno, List<Question> qlist, int must) {
		List<Integer> list = new ArrayList<>();
		boolean use[] = new boolean[qlist.size()+1];
		Arrays.fill(use, false); // true면 사용했다는 의미
		
		// 우선순위: 절반은 3으로, 없으면 1+2로 넘겨줌 -> type, priority별 개수 알아야함(repository 확인)
		// qno가 1이면 무조건 3인거 pick, 2이상이면 절반은 3(high(qno/2)), 나머지는 그 외에서 뽑기
		// 0~high만큼 뽑고, high~질문총개수 크기만큼 뽑아서 안 겹치면 list.add()해서 보냄
		System.out.println("중요도 3 필수 개수(must): "+must);
		int high = qno / 2;
		if(qno == 1) high = qno;
		if(high > must) high = must;
		System.out.println("중요도 3 필수 개수(high): "+high);
		
		for(int i=0; i<qno; i++) {
			// 0~(리스트크기) 중 랜덤으로 숫자 뽑아서(qno만큼 반복) 해당 숫자의 questno list에 추가
			int random = 0;
			if(i<high) {
				random = (int) (Math.random()*high); // 0~(high-1)
			} else {
				random = (int) (Math.random()*(qlist.size()-high+1))+high; // high~qlist.size
			}
			System.out.println(i+"번째 random: "+random+", use 여부: "+use[random]);
			int pick = qlist.get(random).getId();
			if(!use[random]) {
				use[random] = true;
				list.add(qlist.get(random).getId()); // 뽑힌적 없는 수는 넣어줌
				System.out.println("use 체크완: "+pick);
			}
			else {
				System.out.println(random);
				i--; // 뽑힌적 있는 수면 다시 random 돌려서 실행
			}
		}
		return list;
	}

	// 일반연습에서 문제 구분별로 몇 문제 낼지 나눠줌
	private List<Integer> divideNo(int qno) {
		// 항목별 문제개수[0: 인성, 1: CS, 2: 프로젝트]
		List<Integer> list = new ArrayList<>();
		for(int i=0; i<3; i++) {
			list.add(qno/3);
		}
		int remain = qno % 3; // 남은 개수
		// 1개 남으면 프로젝트 문제를, 2개면 인적성과 프로젝트 문제개수를 추가
		if(remain == 1) list.set(2, list.get(2)+1);
		else if(remain == 2) {
			list.set(0, list.get(0)+1);
			list.set(2, list.get(2)+1);
		}
		System.out.println("개수: "+list);
		return list;
	}



	

    

}
