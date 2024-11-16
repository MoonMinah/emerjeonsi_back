package com.kosa.emerjeonsiBack.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class Exhibition {

    private int exhibitionNo;      // 전시회 번호
    private String title;           // 전시회 제목
    private String cntcInsttNm;     // 연계 기관명
    private LocalDate issuedDate;        // 자료 생성 일자
    private String description;     // 전시회 소개
    private String imageUrl;        // 이미지 주소
    private int viewCount;          // 조회수
    private String eventSite;       // 장소
    private String contactPoint;    // 문의
    private LocalDate startPeriod;  // 전시회 시작일자
    private LocalDate endPeriod;    // 전시회 종료일자
    private int adultPrice;         // 성인 가격
    private int infantPrice;        // 유아 가격
    private int seniorPrice;        // 노인 가격
    private String exhibitionStatus; // 전시회 상태 (등록, 삭제, 취소 등)

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
