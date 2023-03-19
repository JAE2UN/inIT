package com.ssafy.init.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.init.api.response.AnswerRes;
import com.ssafy.init.api.response.ReportRes;
import com.ssafy.init.db.entity.Report;
import com.ssafy.init.db.repository.ReportRepository;


@Service
public class DashboardServiceImpl implements DashboardService {
//	private static final String SUCCESS = "success";
//    private static final String FAIL = "fail";
    
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    ChecklistService checklistService;
    @Autowired
    ReportService reportService;
    
	// [smj] 일반 면접 연습 기록들 최근 5개에 해당하는 answer 모든 데이터 가져오기    
	@Override
	public Map<Integer, Map<String, List<AnswerRes>>> collectCommonAns(int userId) {
//	public List<List<AnswerRes>> collectCommonAns(int userId) {
		// List<Report> findAllByUserIdAndTypeOrderByCreatedAtDesc(int userId, int type);
		
//		List<Report> reportList = reportRepository.findAllByUserIdAndTypeOrderByCreatedAtDesc(userId, 1);
//		int loopCnt = reportList.size()>5 ? 5 : reportList.size();
//		for(int i=0; i<loopCnt; i++) {
//			List<AnswerRes> answerResList = checklistService.getAnsList(reportList.get(i).getId());
//			
//			returnAnswerResList.addAll(answerResList);
//		}
		
		Map<Integer, Map<String, List<AnswerRes>>> returnResult = new HashMap<Integer, Map<String, List<AnswerRes>>>();
		
		
		List<Report> reportList = reportRepository.findTop5ByUserIdAndTypeOrderByCreatedAtDesc(userId, 1);
//		System.out.println(">>> reportList: "+reportList);
		
		for(int i=0; i<reportList.size(); i++) {
			Report report = reportList.get(i);
			int reportId = report.getId();
			String reportDate = String.valueOf(report.getCreatedAt());
//			System.out.println(">>> reportDate: "+reportDate);
			
			List<AnswerRes> answerResList = checklistService.getAnsList(reportId);
//			System.out.println(">>> answerResList: "+answerResList);

			List<AnswerRes> chooseAnswerResList = new ArrayList<AnswerRes>();
			for(int idx=0; idx<answerResList.size(); idx++) {
				if(answerResList.get(idx).getFlag() == 2) {
					chooseAnswerResList.add(answerResList.get(idx));
				}
			}
//			System.out.println(">>> chooseAnswerResList: "+chooseAnswerResList);
			
			Map<String, List<AnswerRes>> evalResult = new HashMap<String, List<AnswerRes>>();
			
//			evalResult.put(reportDate, answerResList);
			evalResult.put(reportDate, chooseAnswerResList);
//			System.out.println(">>> evalResult: "+evalResult);
			
			returnResult.put(reportId, evalResult);
			
//			System.out.println(">>> returnResult: "+returnResult);
			if(i == reportList.size()-1) {
				return returnResult;
			}
			
			// 다음 데이터 담기 전 clear
//			answerResList.clear();
//			chooseAnswerResList.clear();
//			evalResult.clear();
		}
//		System.out.println(">>> return: "+returnResult);
		// 왜 여기서 returnResult를 return하면 값이 다 비어서 나오지..? ☆★☆
		return returnResult;
	}
	
	// [smj] 기술 면접 연습 기록들 최근 5개에 해당하는 answer 모든 데이터 가져오기
	@Override
	public List<AnswerRes> collectCsAns(int userId) {

		List<AnswerRes> result = new ArrayList<AnswerRes>();
		
		List<Report> reportList = reportRepository.findTop5ByUserIdAndTypeOrderByCreatedAtDesc(userId, 2);
		for(int i=0; i<reportList.size(); i++) {
			int reportId = reportList.get(i).getId();
			List<AnswerRes> answerResList = checklistService.getAnsList(reportId);
			
			result.addAll(answerResList);
//			System.out.println(">> result: "+result);
//			result.put(reportId, answerResList);
		}
		
		return result;
	}

	// [smj] 면접 일지 상세화면에 보이는 평가 요약 데이터
	@Override
	public Map<Integer, Double> summaryEval(int reportId) {
		
		ReportRes report = reportService.getReport(reportId);
		int type = report.getType(); // 1: 일반, 2: 기술
		
		Map<Integer, Double> result = new HashMap<Integer, Double>();
		
		// 일반/기술 면접에서 사용
		double evalData = 0;
		
		// 기술 면접에서 사용
		Integer[] category = {21, 22, 23, 24, 25, 26, 29};
		// 0번째 열에 해당 카테고리(type)의 문제 개수, 1번째 열에 해당 카테고리의 문제 중 맞은(cs_score == 3) 개수
		double[][] evalDataArray =  new double[category.length][2];
		
		// 기술에서만 사용
		List<AnswerRes> evalList = report.getChecklistRes().getAnswerRes();
//		System.out.println(">>> evalList: "+evalList);
		
		// 일반에서 사용
		List<AnswerRes> commonEvalList = new ArrayList<AnswerRes>();
		int unit = 4;
		
		if(type == 1) { // 일반일 경우
			for(int i=0; i<evalList.size(); i++) {
				AnswerRes eval = evalList.get(i);
				if(eval.getFlag() == 2) { // 일반 면접 연습일 경우 
					commonEvalList.add(eval);
				}	
			}
			
			for(int i=0; i<commonEvalList.size(); i++) {
				if(i != 0 && i%unit == 0) {
					// map에 값 넣기
					result.put(i/unit, evalData/unit);
					// 초기화
					evalData = 0;
				}
				evalData += commonEvalList.get(i).getEvalScore();
			}
			result.put(commonEvalList.size()/unit, evalData/unit);
			
		} else if(type == 2) { // 기술일 경우
			for(int i=0; i<evalList.size(); i++) {
				AnswerRes eval = evalList.get(i);
//				System.out.println(">> eval: "+eval);
				// 카테고리들 중 나온 문제 개수, 맞은 개수 체크
				for(int c=0; c<category.length; c++) {
					if(eval.getType() == category[c]) { // 문제 개수 체크
						evalDataArray[c][0]++;
//						System.out.println(">>> evalDataArray[c][0]: "+evalDataArray[c][0]+" c: "+c);
						
						if(eval.getCsScore() == 3) { // 맞은 개수 체크
							evalDataArray[c][1]++;
//							System.out.println(">>> evalDataArray[c][1]: "+evalDataArray[c][1]+" c: "+c);
						}
					}
				}
			}
			
			for(int a=0; a<evalDataArray.length; a++) {
//				System.out.println("evalDataArray[a][1] / evalDataArray[a][0] :"+evalDataArray[a][1]+" / "+evalDataArray[a][0]);
				evalData = (evalDataArray[a][1]/evalDataArray[a][0])*100;
//				System.out.println(evalData);
				result.put(category[a], evalData > 100 ? -1 : evalData);
				result.put(category[a], evalDataArray[a][0] == 0 ? -1 : evalData);
			}
		}

//		System.out.println(">> result: "+result);
		return result;
	}
	
}
