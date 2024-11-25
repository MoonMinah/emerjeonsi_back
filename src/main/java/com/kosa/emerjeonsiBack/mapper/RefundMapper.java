package com.kosa.emerjeonsiBack.mapper;

import com.kosa.emerjeonsiBack.dto.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface RefundMapper {
    public int updateReservationStatus(int reservationNo, String reservationStatus); //예매 상태 변경
    public void insertReservationHistory(int reservationNo, String reservationStatus);
    public int updatePaymentStatus(int paymentNo, String paymentStatus); //결제 상태 변경
    public void insertPaymentHistory(int paymentNo, String paymentStatus, BigDecimal refundAmount);

    public List<Reservation> getRefundsByUserNo(@Param("userNo")int userNo, @Param("offset")int offset, @Param("size")int size); // 환불 목록 조회.
    public int countRefundsByUserNo(int userNo); //사용자 번호에 따른 환불 목록 총 개수 조회
    public Reservation getMyRefundsDetail(int paymentNo); //환불 목록의 상세내역


}
