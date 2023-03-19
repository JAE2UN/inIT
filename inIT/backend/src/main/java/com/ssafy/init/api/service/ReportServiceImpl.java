package com.ssafy.init.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.init.api.request.ReadyReq;
import com.ssafy.init.api.request.ReportReq;
import com.ssafy.init.api.response.AnswerRes;
import com.ssafy.init.api.response.ChecklistRes;
import com.ssafy.init.api.response.ReportRes;
import com.ssafy.init.db.entity.Report;
import com.ssafy.init.db.entity.User;
import com.ssafy.init.db.repository.ReportRepository;
import com.ssafy.init.db.repository.UserRepository;

@Service
public class ReportServiceImpl implements ReportService {
//	private static final String SUCCESS = "success";
//    private static final String FAIL = "fail";
    
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ChecklistService checklistService;
    
	// [smj] Report에 ReportReq 넣기
	@Override
	public Report fillReport(Report report, ReportReq reportReq) {
		report.setOneCmt(reportReq.getOneCmt());
		report.setStar(reportReq.getStar());
		report.setTags(reportReq.getTags());
		report.setTitle(reportReq.getTitle());
		report.setType(reportReq.getType());
		
		User user = userRepository.findById(reportReq.getUserId()).get();
//		user.setId(reportReq.getUserId());
		
		report.setUser(user);
		return report;
	}
	
	// [smj] ReportRes에 Report 넣기
	@Override
	public ReportRes fillReportRes(Report report) {
		ReportRes reportRes = new ReportRes();
		
        // [smj] dateTime -> date (String)
        String onlyDate = report.getCreatedAt().toString().substring(2, 10);
        reportRes.setCreatedAt(onlyDate);
//		reportRes.setCreatedAt(report.getCreatedAt());
        
		reportRes.setId(report.getId());
		reportRes.setOneCmt(report.getOneCmt());
		reportRes.setStar(report.getStar());
		reportRes.setTags(report.getTags());
		reportRes.setTitle(report.getTitle());
		reportRes.setType(report.getType());
		reportRes.setUserId(report.getUser().getId() )
		;
		
		return reportRes;
	}
	
 	// [smj] 면접일지 생성
	@Override
	public int createReport(ReadyReq readyReq) {
	    try {
	        Report report = new Report();
	        Optional<User> user = userRepository.findById(readyReq.getUserId());
	        if(!user.isPresent()) return -1;
	        report.setUser(user.get());
	        report.setType(readyReq.getType());
	        String type = readyReq.getType()==1 ? "일반" : "기술";
	        report.setTitle("inIT " + type + " 면접 연습");
	        Report option = reportRepository.save(report);
	        return option.getId();
	    } catch (Exception e) {
	    	System.out.println(">> 면접 일지 생성 Exception: "+e);
	        return -1;
	    }   
	}
	
	
 	// [smj] 면접일지 수정
	@Override
	public ReportRes updateReport(ReportReq reportReq) {
		int reportId = reportReq.getId();
		int userId = reportReq.getUserId();
		Optional<Report> originReport = reportRepository.findById(reportId);
		
		if(!originReport.isPresent() || originReport.get().getUser().getId() != userId)
            return null;
		
		Report updateReport = originReport.get();
		updateReport = fillReport(updateReport, reportReq);
		reportRepository.save(updateReport);
		
		return getReport(reportId);
	}
	
 	// [smj] 면접일지 조회
	@Override
	public ReportRes getReport(int id) {
		Optional<Report> option = reportRepository.findById(id);
        if(!option.isPresent())
            return null;
        Report report = option.get();
        ReportRes reportRes = fillReportRes(report);
        
		ChecklistRes checklistRes = checklistService.getClist(id);
		List<AnswerRes> answerRes = checklistService.getAnsList(id);
		
		checklistRes.setAnswerRes(answerRes);
		reportRes.setChecklistRes(checklistRes);

        return reportRes;
	}
	
	// [smj] 면접일지 리스트 조회
	@Override
	public List<ReportRes> getAllReport(int userId) {
		List<Report> reportList = reportRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
		
		List<ReportRes> reportResList = new ArrayList<ReportRes>();
		
		for(int i=0; i<reportList.size(); i++) {
			reportResList.add(i, fillReportRes(reportList.get(i)));
		}
		
		return reportResList;
	}
	
	// [smj] 면접일지 구분별 리스트 조회
	@Override
	public List<ReportRes> getAllReportByType(int userId, int type) {
		
		List<Report> reportList = reportRepository.findAllByUserIdAndTypeOrderByCreatedAtDesc(userId, type);
//		System.out.println(reportList);
		
		List<ReportRes> reportResList = new ArrayList<ReportRes>();
		
		for(int i=0; i<reportList.size(); i++) {
			reportResList.add(i, fillReportRes(reportList.get(i)));
		}
		
		return reportResList;
	}
	
	// [smj] 면접일지 태그별 리스트 조회
	@Override
	public List<ReportRes> getAllReportByTag(int userId, String tag) {
		List<Report> reportList = reportRepository.findAllByUserIdAndTagsContainingOrderByCreatedAtDesc(userId, tag);
//		System.out.println(reportList);

		List<ReportRes> reportResList = new ArrayList<ReportRes>();
		
		for(int i=0; i<reportList.size(); i++) {
			reportResList.add(i, fillReportRes(reportList.get(i)));
		}
		
		return reportResList;
	}
	
	
	// [smj] 면접일지 삭제
	@Override
	public String deleteReport(int id, int userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
