package com.kosa.emerjeonsiBack.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ToString
/**
 * 굘제 이력 테이블
 */
public class PaymentHistory {
    private int paymentHistoryNo; //결제 이력 번호
    private int paymentNo; //결제 번호
    private String paymentStatus; //결제 이벤트 유형
    private LocalDateTime paymentEventTimestamp; //결제 이벤트 발생 일시.
    private BigDecimal refundAmount; //환불 금액.
}
