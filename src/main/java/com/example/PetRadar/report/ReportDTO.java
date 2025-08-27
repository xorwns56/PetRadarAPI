package com.example.PetRadar.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReportDTO {
    private Long id;
    private Long petMissingId;
    private Long userId;
    private String petReportPlace;
    private PetReportPoint petReportPoint;
    private String petImage;
    private String title;
    private String content;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PetReportPoint{
        private Double lat;
        private Double lng;
    }

    public ReportDTO(Report report) {
        this.id = report.getId();
        this.petMissingId = report.getMissing().getId();
        this.userId = report.getUser().getId();
        this.title = report.getTitle();
        this.content = report.getContent();
        this.petImage = report.getPetImage();
        this.petReportPlace = report.getPetReportPlace();
        this.petReportPoint = new PetReportPoint(report.getLatitude(), report.getLongitude());
    }
}
