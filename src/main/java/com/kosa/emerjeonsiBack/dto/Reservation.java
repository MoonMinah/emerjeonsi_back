package com.kosa.emerjeonsiBack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reservation {
    private int reservationNo; //예매 번호
    private int userNo; // 회원 번호
    private int exhibitionNo; //전시 번호
    private int reservationPrice; // 예매 가격
    private int reservationQuantity; //예매 수량
}
