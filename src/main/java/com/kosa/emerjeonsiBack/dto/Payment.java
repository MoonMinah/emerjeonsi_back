package com.kosa.emerjeonsiBack.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@ToString
public class Payment {
    private int paymentNo; //결제 번호
    private int reservationNo; // 예약 번호
    private String paymentUid; //결제 고유 번호
    private String paymentMethod; //결제 수단
    private String paymentStatus; //결제 상태
    private BigDecimal paymentPrice; //결제 금액


    @JsonIgnore // JSON에 포함되지 않도록 설정
    private LocalDateTime paymentDate; //결제 일시

    // JSON 응답에 포함될 필드
    @JsonProperty("formattedPaymentDate")
    public String getFormattedPaymentDate() {
        if (this.paymentDate == null) {
            return null;
        }
        return this.paymentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
