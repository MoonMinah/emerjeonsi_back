package com.kosa.emerjeonsiBack.mapper;

import com.kosa.emerjeonsiBack.dto.Reservation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReservationMapper {
    public void insertReservation(Reservation reservation); //예약 등록.
    public void updateReservationStatus(Reservation reservation); //예매 상태 수정
    public List<Reservation> getReservationsByUserNo(int userNo); //나의 예매 목록 조회.
    public Reservation getMyReservationDetail(int reservationNo); //나의 예매 목록 - 상세 내역 조회
    public Reservation getMyReservationPaymentDetail(int paymentNo);//나의 예매 목록 - 결제 내역 조회
}
