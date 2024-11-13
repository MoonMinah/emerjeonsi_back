package com.kosa.emerjeonsiBack.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Reservation {
    private int reservationNo; //예약 번호
    private int userNo; //회원 번호
    private int exhibitionNo; //전시 번호
    private int reservationPrice; //예매 가격
    private int reservationQuantity; //예매 수량
    private String reservationStatus; //예매 상태

    private Exhibition exhibition; //전시 정보를 저장할 필드.

    private Payment payment; //나의 예매 목록 - 결제 내역을 위한 필드.
}
