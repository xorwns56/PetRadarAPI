package com.example.PetRadar.report;

import com.example.PetRadar.missing.MissingRepository;
import com.example.PetRadar.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final MissingRepository missingRepository;
    private final UserRepository userRepository;

    public ReportDTO findReportById(Long reportId) {
        Optional<Report> reportOptional = reportRepository.findById(reportId);
        return reportOptional.map(ReportDTO::new).orElse(null);
    }

    public Page<ReportDTO> getReportsByMissingId(Long missingId, Pageable pageable) {
        Page<Report> reportPage = reportRepository.findByMissingId(missingId, pageable);
        return reportPage.map(ReportDTO::new);
    }

    public void createReport(Long userId, Long missingId, ReportDTO reportDTO) {
        Report report = new Report();
        if(userId != null) report.setUser(userRepository.getReferenceById(userId));
        report.setMissing(missingRepository.getReferenceById(missingId));
        report.setTitle(reportDTO.getTitle());
        report.setContent(reportDTO.getContent());
        report.setPetImage(reportDTO.getPetImage());
        report.setPetReportPlace(reportDTO.getPetReportPlace());
        report.setLatitude(reportDTO.getPetReportPoint().getLat());
        report.setLongitude(reportDTO.getPetReportPoint().getLng());
        reportRepository.save(report);
    }
}