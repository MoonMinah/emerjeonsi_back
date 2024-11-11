package com.kosa.emerjeonsiBack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Exhibition {
    private int exhibitionNo; //전시 구분 번호
    private String exhibitionTitle; //전시 제목
    private String exhibitionDescription; //전시 설명.
    private String exhibitionStartPeriod; //전시 시작일.
    private String exhibitionEndPeriod; //전시 종료일
    private int adultPrice; //성인 요금.
    private int seniorPrice; //노인 요금.
    private int infantPrice; //아이 요금.

    public Exhibition(int exhibitionNo, String exhibitionTitle, String exhibitionDescription, String exhibitionStartPeriod, String exhibitionEndPeriod, int infantPrice){
        this.exhibitionNo = exhibitionNo;
        this.exhibitionTitle = exhibitionTitle;
        this.exhibitionDescription = exhibitionDescription;
        this.exhibitionStartPeriod = exhibitionStartPeriod;
        this.exhibitionEndPeriod = exhibitionEndPeriod;
        this.infantPrice = infantPrice;
    }
}
