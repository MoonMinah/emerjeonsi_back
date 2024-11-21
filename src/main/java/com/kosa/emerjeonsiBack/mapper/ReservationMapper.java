package com.kosa.emerjeonsiBack.mapper;

import com.kosa.emerjeonsiBack.dto.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReservationMapper {
    public void insertReservation(Reservation reservation); //예약 등록.
    //int getLastInsertedReservationNo(); // 가장 최근의 reservationNo 가져오기
    public Reservation getReservationDetail(int reservationNo); //예매 상세 피이지 조회
    public int updateReservationStatus(int reservationNo, String reservationStatus);
    //public void updateReservationStatus(Reservation reservation); //예매 상태 수정
    public List<Reservation> getReservationsByUserNo(int userNo, @Param("offset")int offset, @Param("size")int size); //나의 예매 목록 조회.
    public int countReservationsByUserNo(int userNo); //사용자 번호에 따른 예약 목록 총 개수 조회
    public Reservation getMyReservationDetail(int reservationNo); //나의 예매 목록 - 상세 내역 조회
    public Reservation getMyReservationPaymentDetail(int paymentNo);//나의 예매 목록 - 결제 내역 조회
}
