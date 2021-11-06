package com.imdev.webchat.repository;

import com.imdev.webchat.domain.Report;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ReportRepository {
    private Map<String, Report> ReportMap;

    @PostConstruct
    private void init(){
        ReportMap = new LinkedHashMap<>();
    }

    public List<Report> findAllReport(){
        List reports = new ArrayList(ReportMap.values());
        Collections.reverse(reports);
        return reports;
    }

    public Report findReportById(String id){
        return ReportMap.get(id);
    }

    public Report createReport(String memberId){
        Report report = Report.create(memberId);
        ReportMap.put(report.getReportId(), report);
        return report;
    }
}
