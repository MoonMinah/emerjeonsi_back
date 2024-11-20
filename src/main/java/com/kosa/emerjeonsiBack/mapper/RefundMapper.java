package com.kosa.emerjeonsiBack.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;

@Mapper
public interface RefundMapper {
    public int updateReservationStatus(int reservationNo, String reservationStatus); //예매 상태 변경
    public void insertReservationHistory(int reservationNo, String reservationStatus);
    public int updatePaymentStatus(int paymentNo, String paymentStatus); //결제 상태 변경
    public void insertPaymentHistory(int paymentNo, String paymentStatus, BigDecimal refundAmount);


}
