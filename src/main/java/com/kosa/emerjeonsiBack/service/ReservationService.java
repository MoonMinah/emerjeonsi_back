package com.kosa.emerjeonsiBack.service;

import com.kosa.emerjeonsiBack.dto.Reservation;

import java.util.List;

public interface ReservationService {
    public void createReservation(Reservation reservation); //예매 등록

    public Reservation getReservationDetail(int reservationNo); //예매 상세 페이지 조회

    public int updateReservationStatus(int reservationNo, String reservationStatus); //예매 완료 후 결제 시 상태 변경

    public int calculateTotalPages(int totalCount, int pageSize);

    public List<Reservation> getReservationsByUserNo(int userNo, int offset, int size); //나의 예매 목록

    public int countReservationsByUserNo(int userNo); //사용자 번호에 따른 예약 목록 총 개수 조회

    public Reservation getMyReservationDetail(int reservationId); //나의 예매 목록 - 상세 내역

    public Reservation getMyReservationPaymentDetail(int PaymentNo); //나의 예매 목록 - 결제 내역

}
