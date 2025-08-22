package com.example.PetRadar.missing;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class MissingDTO {
    private String petName;
    private String petType;
    private String petGender;
    private String petBreed;
    private String petAge;
    private String petMissingDate;
    private String petMissingPlace;
    private PetMissingPoint petMissingPoint;
    private String petImage;
    private String title;
    private String content;

    @Getter
    @NoArgsConstructor
    public static class PetMissingPoint{
        private Double lat;
        private Double lng;
    }
}

