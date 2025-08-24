package com.example.PetRadar.missing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MissingDetailDTO {
    private Long id;
    private Long userId;
    private String petName;
    private String petType;
    private String petGender;
    private String petBreed;
    private String petAge;
    private String petMissingDate;
    private String petMissingPlace;
    private MissingDTO.PetMissingPoint petMissingPoint;
    private String petImage;
    private String title;
    private String content;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PetMissingPoint{
        private Double lat;
        private Double lng;
    }
}
