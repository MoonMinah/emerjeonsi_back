package com.kosa.emerjeonsiBack.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@ToString
/**
 * 굘제 이력 테이블
 */
public class PaymentHistory {
    private int paymentHistoryNo; //결제 이력 번호
    private int paymentNo; //결제 번호
    private String paymentStatus; //결제 이벤트 유형


     // JSON에 포함되지 않도록 설정
    private LocalDateTime paymentEventTimestamp; //결제 이벤트 발생 일시.

    // JSON 응답에 포함될 필드
    @JsonProperty("formattedPaymentEventTimestamp")
    public String getFormattedPaymentDate() {
        if (this.paymentEventTimestamp == null) {
            return null;
        }
        return this.paymentEventTimestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    private BigDecimal refundAmount; //환불 금액.
}
