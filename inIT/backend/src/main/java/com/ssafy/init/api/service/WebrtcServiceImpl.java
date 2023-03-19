package com.ssafy.init.api.service;

import com.ssafy.init.api.request.WebrtcReq;
import com.ssafy.init.api.response.WebrtcRes;
import com.ssafy.init.db.entity.Report;
import com.ssafy.init.db.entity.User;
import com.ssafy.init.db.entity.Webrtc;
import com.ssafy.init.db.repository.WebrtcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebrtcServiceImpl implements WebrtcService {
    private static final String baseUrl = "https://interview.ml:4443/openvidu/recordings/";
    private static final String endUrl = ".mp4";

    @Autowired
    WebrtcRepository webrtcRepository;

    @Override
    public boolean upload(WebrtcReq webrtcReq) {
        System.out.println(">>> upload : " + webrtcReq);

        try {
            Webrtc webrtc = new Webrtc();
            User user = new User();
            Report report = new Report();

            user.setId(webrtcReq.getUserId());
            report.setId(webrtcReq.getReportId());

            webrtc.setCode(webrtcReq.getCode());
            webrtc.setUser(user);
            webrtc.setReport(report);

            webrtcRepository.save(webrtc);

            return (true);
        } catch (Exception e) {
            System.out.println(">>> upload fail");
            return (false);
        }
    }

    @Override
    public List<WebrtcRes> getUrlList(int reportId, int userId) {
        List<Webrtc> webrtcList = webrtcRepository.findAllByReportIdOrderByIdAsc(reportId);
        List<WebrtcRes> webrtcResList = new ArrayList<>();

        if (webrtcList == null) return (null);
        if (webrtcList.get(0).getUser().getId() != userId)  return (null);

        for (int i = 0; i < webrtcList.size(); i++) {
            WebrtcRes webrtcRes = new WebrtcRes();
            String code = webrtcList.get(i).getCode();

            webrtcRes.setUrl(baseUrl + code + "/" + code + endUrl);
            webrtcRes.setCreateTime(webrtcList.get(i).getCreatedAt());

            webrtcResList.add(webrtcRes);
        }
        return (webrtcResList);
    }

    @Override
    public String getUrl(int reportId, int sequence) {
        List<Webrtc> webrtcList = webrtcRepository.findAllByReportIdOrderByIdAsc(reportId);

        if (webrtcList == null) return (null);

        System.out.println(">>> getUrl Method");
        webrtcList.forEach(System.out::println);

        if (sequence <= webrtcList.size()) {
            String code = webrtcList.get(sequence - 1).getCode();
            return (baseUrl + code + "/" + code + endUrl);
        }
        return (null);
    }
}