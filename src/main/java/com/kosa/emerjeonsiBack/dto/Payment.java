package com.kosa.emerjeonsiBack.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ToString
public class Payment {
    private int paymentNo; //결제 번호
    private int reservationNo; // 예약 번호
    private String paymentUid; //결제 고유 번호
    private String paymentMethod; //결제 수단
    private String paymentStatus; //결제 상태
    private BigDecimal paymentPrice; //결제 금액
    private LocalDateTime paymentDate; //결제 일시

    // 날짜 형식 지정
    public String getFormattedPaymentDate() {
        if (this.paymentDate == null) {
            return null;
        }
        return this.paymentDate.toString(); // LocalDate는 기본적으로 "yyyy-MM-dd" 형식입니다.
    }
}