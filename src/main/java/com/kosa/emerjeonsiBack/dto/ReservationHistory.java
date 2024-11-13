package com.kosa.emerjeonsiBack.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class ReservationHistory {
    private int reservationHistoryNo; //예매 이력 번호
    private int reservationNo; //예매 번호
    private String reservationStatus; //예매 상태
    private LocalDateTime reservationEventTimeStamp; //예매 일시
}

