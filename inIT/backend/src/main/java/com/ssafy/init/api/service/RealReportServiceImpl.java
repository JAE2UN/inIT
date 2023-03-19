package com.ssafy.init.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.init.api.request.RealQaReq;
import com.ssafy.init.api.request.RealReportReq;
import com.ssafy.init.api.request.ReportReq;
import com.ssafy.init.api.response.RealQaRes;
import com.ssafy.init.api.response.RealReportRes;
import com.ssafy.init.db.entity.RealQa;
import com.ssafy.init.db.entity.RealReport;
import com.ssafy.init.db.entity.Report;
import com.ssafy.init.db.entity.User;
import com.ssafy.init.db.repository.RealQaRepository;
import com.ssafy.init.db.repository.RealReportRepository;
import com.ssafy.init.db.repository.ReportRepository;
import com.ssafy.init.db.repository.ReviewRepository;

@Service
public class RealReportServiceImpl implements RealReportService {
	private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    
	@Autowired
	ReportRepository reportRepository;
	@Autowired
	RealReportRepository realReportRepository;
	@Autowired
	RealQaRepository realQaRepository;
	
	@Autowired
	ReviewRepository reviewRepository;
	
	
	// [smj] Report에 RealReportReq 넣기
	@Override
	public Report fillReport(Report report, RealReportReq realReportReq) {
//		Report report = new Report();
		// 실전 면접 일지 작성 시에만 사용되는 메서드이므로 type은 항상 3
		report.setType(3); 
		report.setTitle(realReportReq.getTitle());
		report.setStar(realReportReq.getStar());
		report.setOneCmt(realReportReq.getOneCmt());
		report.setTags(realReportReq.getTags());
		
		User user = new User();
		user.setId(realReportReq.getUserId());
		report.setUser(user);
		
		return report;
	}
	
	// [smj] RealReport에 RealReportReq 넣기
	@Override
	public RealReport fillRealReport(RealReport realReport, RealReportReq realReportReq) {
//		RealReport realReport = new RealReport();
		realReport.setCompany(realReportReq.getCompany());
		realReport.setRealDate(realReportReq.getRealDate());
		realReport.setInfo(realReportReq.getInfo());
		realReport.setAllCmt(realReportReq.getAllCmt());
		
		return realReport;
	}

	// [smj] RealQa에 RealReportReq 넣기
	@Override
	public RealQa fillRealQa(RealQa realQa, RealReportReq realReportReq, int idx) {
//		RealQa realQa = new RealQa();
		realQa.setType(realReportReq.getRealQaReq().get(idx).getType());
		realQa.setQuestion(realReportReq.getRealQaReq().get(idx).getQuestion());
		realQa.setAnswer(realReportReq.getRealQaReq().get(idx).getAnswer());
		
		return realQa;
	}

	// [smj] RealReportRes에 Report, RealReport, RealQa 넣기
	@Override
	public RealReportRes fillRealReportRes(Report report, RealReport realReport, List<RealQa> realQaList) {
		RealReportRes realReportRes = new RealReportRes();
		
		realReportRes.setId(report.getId());
		realReportRes.setType(report.getType());
		realReportRes.setTitle(report.getTitle());
		
        // [smj] dateTime -> date (String)
        String onlyDate = report.getCreatedAt().toString().substring(2, 10);
        realReportRes.setCreatedAt(onlyDate);
//		realReportRes.setCreatedAt(report.getCreatedAt());
		
		realReportRes.setStar(report.getStar());
		realReportRes.setOneCmt(report.getOneCmt());
		realReportRes.setTags(report.getTags());
		realReportRes.setUserId(report.getUser().getId());
		
		realReportRes.setCompany(realReport.getCompany());
		realReportRes.setRealDate(realReport.getRealDate());
		realReportRes.setInfo(realReport.getInfo());
		realReportRes.setAllCmt(realReport.getAllCmt());
		
		List<RealQaRes> realQaResList = new ArrayList<RealQaRes>();
		for(int i=0; i<realQaList.size(); i++) {
			RealQaRes realQaRes = new RealQaRes();
			realQaRes.setId(realQaList.get(i).getId());
			realQaRes.setType(realQaList.get(i).getType());
			realQaRes.setQuestion(realQaList.get(i).getQuestion());
			realQaRes.setAnswer(realQaList.get(i).getAnswer());

			realQaResList.add(realQaRes);
		}
		realReportRes.setRealQaRes(realQaResList);
		
		return realReportRes;
	}
	
	// [smj] RealReportReq에 ReportRes 넣기
		@Override
		public RealReportReq fillRealReportReq(RealReportRes realReportRes) {
			RealReportReq realReportReq = new RealReportReq();
			
			realReportReq.setId(realReportRes.getId());
			realReportReq.setTitle(realReportRes.getTitle());
			realReportReq.setStar(realReportRes.getStar());
			realReportReq.setOneCmt(realReportRes.getOneCmt());
			realReportReq.setTags(realReportRes.getTags());
			realReportReq.setUserId(realReportRes.getUserId());
		
			realReportReq.setCompany(realReportRes.getCompany());
			realReportReq.setRealDate(realReportRes.getRealDate());
			realReportReq.setInfo(realReportRes.getInfo());
			realReportReq.setAllCmt(realReportRes.getAllCmt());
			
			List<RealQaReq> realQaReqList = new ArrayList<RealQaReq>();
			for(int i=0; i<realReportRes.getRealQaRes().size(); i++) {
				RealQaReq realQaReq = new RealQaReq();
//				realQaReq.setId(realReportRes.getRealQaRes().get(i).getId());
				realQaReq.setType(realReportRes.getRealQaRes().get(i).getType());
				realQaReq.setQuestion(realReportRes.getRealQaRes().get(i).getQuestion());
				realQaReq.setAnswer(realReportRes.getRealQaRes().get(i).getAnswer());

				realQaReqList.add(realQaReq);
			}
			/*
			List<RealQaRes> realQaResList = new ArrayList<RealQaRes>();
			for(int i=0; i<realQaList.size(); i++) {
				RealQaRes realQaRes = new RealQaRes();
				realQaRes.setId(realQaList.get(i).getId());
				realQaRes.setType(realQaList.get(i).getType());
				realQaRes.setQuestion(realQaList.get(i).getQuestion());
				realQaRes.setAnswer(realQaList.get(i).getAnswer());

				realQaResList.add(realQaRes);
			}
			*/
			realReportReq.setRealQaReq(realQaReqList);
			
			return realReportReq;
		}

    // [smj] 실전 면접 일지 생성
	@Override
	@Transactional
	public RealReportRes create(RealReportReq realReportReq) {
		try {
			Report report = new Report();
			report = reportRepository.save(fillReport(report, realReportReq));
			// 해당 userid의 type3인 데이터들 중 최근에 작성한 거 하나
			Report insertReport = reportRepository.findTop1ByUserIdAndTypeOrderByCreatedAtDesc(report.getUser().getId(), report.getType());
						
			// report 데이터 식별자를 realreport의 id(식별자)에 넣어주기
			int realReportId = insertReport.getId();
			
			RealReport realReport = new RealReport();
			realReport = fillRealReport(realReport, realReportReq);
			realReport.setId(realReportId);
			RealReport inputRealReport = realReportRepository.save(realReport);
			
			// realreport 데이터 식별자를 realqa의 realreportId에 넣어주기
			List<RealQaReq> realQaReqList = realReportReq.getRealQaReq();
			List<RealQa> realQaList = new ArrayList<RealQa>();
			
			for(int i=0; i<realQaReqList.size(); i++) {
				RealQa realQa = new RealQa();
				realQa.setRealReport(inputRealReport);
				realQa = fillRealQa(realQa, realReportReq, i);
				
				realQaList.add(realQa);
			}		
			realQaRepository.saveAll(realQaList);
				
			return detail(realReportId);
			
		}catch (Exception e) {
            // TODO: handle exception
            System.out.println("실전 면접 일지 생성 Exception : "+e);
            return null;
        }
	}
	
	// [smj] 실전 면접 일지 상세 조회  (면접 일지 리스트는 reportService 에서 구현하자☆★☆)
	@Override
	public RealReportRes detail(int realReportId) {
		// TODO Auto-generated method stub
		Report report = reportRepository.findById(realReportId).get();

		RealReport realReport = realReportRepository.findById(realReportId).get();

		List<RealQa> realQaList = realQaRepository.findAllByRealReportId(realReportId);

		
		return fillRealReportRes(report, realReport, realQaList);
	}
	
	// [smj] 실전 면접 일지 수정
	@Override
	@Transactional
	public RealReportRes update(RealReportRes realReportRes) {
		
		RealReportReq realReportReq = fillRealReportReq(realReportRes);
		
		int realReportId = realReportReq.getId();
		int userId = realReportReq.getUserId();

		int dbUserId = reportRepository.findById(realReportId).get().getUser().getId();
		
		if(userId != dbUserId) {
			return null;
		}
		
		try {
			
			Report report = reportRepository.findByIdAndUserId(realReportId, userId);
			fillReport(report, realReportReq);
			
			RealReport realReport = realReportRepository.findById(realReportId).get();
			fillRealReport(realReport, realReportReq);
			
			// 이건 qa에서 따로 처리?
			List<RealQa> realQaList = realQaRepository.findAllByRealReportId(realReportId);
			
			for(int i=0; i<realQaList.size(); i++) {
				
				realQaRepository.save(realQaList.set(i, fillRealQa(realQaList.get(i), realReportReq, i)));
			}
			
			return detail(realReportId);
			
		}catch (Exception e) {
            // TODO: handle exception
            System.out.println("실전 면접 일지 수정 Exception : "+e);
            return null;
        }		
		
	}
	
	// [smj] 실전 면접 일지 삭제
	@Override
	@Transactional
	public String delete(int realReportId, int userId) {
		int dbUserId = reportRepository.findById(realReportId).get().getUser().getId();
		
		if(userId != dbUserId) {
			return "mismatch";
		}
		
		try {
			
			// RealQa 삭제
			realQaRepository.deleteByRealReportId(realReportId);
			
			// 게시판 삭제...?
			reviewRepository.deleteByRealReportId(realReportId);
			
			// RealReport 삭제
			realReportRepository.deleteById(realReportId);
			
			// Report 삭제
			reportRepository.deleteById(realReportId);
			
			return SUCCESS;
			
		} catch (Exception e) {
			System.out.println("실전 면접 일지 삭제 Exception: " + e);
			
			return FAIL;
		}
	}


}
