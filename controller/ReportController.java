package com.imdev.webchat.controller;

import com.imdev.webchat.domain.Report;
import com.imdev.webchat.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReportController {

    private final ReportRepository reportRepository;

    // 신고 리스트 출력하기(어드민)
    @GetMapping("/report")
    public String reports(Model model){
        List<Report> findReports = reportRepository.findAllReport();
        model.addAttribute("reports", findReports);
        return "admin/reports";
    }

    // 신고방 들어가기(어드민)
    @GetMapping("/report/{id}")
    public String report(@PathVariable String id, Model model){
        Report report = reportRepository.findReportById(id);
        model.addAttribute("report",report);
        return "admin/report";
    }

    // 신고방 만들기(유저)
    @GetMapping("/report/new")
    public String make(HttpSession session){
        String memberId = (String) session.getAttribute("memberId");
        reportRepository.createReport(memberId);
        return "chat/chat";
    }
}
