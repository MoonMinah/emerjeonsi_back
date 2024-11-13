package com.kosa.emerjeonsiBack.dto;


import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@ToString
public class Exhibition {
    private int exhibitionNo;
    private int userNo;
    private String title;
    private String cntcInsttNm;
    private LocalDateTime issuedDate;
    private String description;
    private String imageUrl;
    private int viewCount;
    private String eventSite;
    private String contactPoint;
    private LocalDate startPeriod;
    private LocalDate endPeriod;
    private LocalDateTime updatedEx;
    private int adultPrice;
    private int infantPrice;
    private int seniorPrice;
    private String exhibitionStatus;

    // 날짜 형식 지정
    public String getFormattedStartPeriod() {
        if (this.startPeriod == null) {
            return null;
        }
        return this.startPeriod.toString(); // LocalDate는 기본적으로 "yyyy-MM-dd" 형식입니다.
    }

    public String getFormattedEndPeriod() {
        if (this.endPeriod == null) {
            return null;
        }
        return this.endPeriod.toString();
    }

}