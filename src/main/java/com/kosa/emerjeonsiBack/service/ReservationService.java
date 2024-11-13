package com.kosa.emerjeonsiBack.service;

import com.kosa.emerjeonsiBack.dto.Reservation;

import java.util.List;

public interface ReservationService {
    public void createReservation(Reservation reservation); //예매 등록

    public List<Reservation> getReservationsByUserNo(int userNo); //나의 예매 목록

    public Reservation getMyReservationDetail(int reservationId); //나의 예매 목록 - 상세 내역

    public Reservation getMyReservationPaymentDetail(int PaymentNo); //나의 예매 목록 - 결제 내역

}
