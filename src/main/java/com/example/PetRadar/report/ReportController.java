package com.example.PetRadar.report;

import com.example.PetRadar.missing.MissingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/{reportId}")
    public ResponseEntity<ReportDTO> getReportDetail(@PathVariable Long reportId) {
        ReportDTO reportDTO = reportService.findReportById(reportId);
        return ResponseEntity.ok(reportDTO);
    }

    @PostMapping("/missing/{missingId}")
    public ResponseEntity<Void> createReport(@PathVariable Long missingId, @RequestBody ReportDTO reportDTO, @AuthenticationPrincipal UserDetails userDetails) {
        reportService.createReport(userDetails != null ? Long.parseLong(userDetails.getUsername()) : null, missingId, reportDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/missing/{missingId}")
    public ResponseEntity<List<ReportDTO>> getReportsByMissingId(@PathVariable Long missingId) {
        List<ReportDTO> reports = reportService.getReportsByMissingId(missingId);
        return ResponseEntity.ok(reports);
    }

}
