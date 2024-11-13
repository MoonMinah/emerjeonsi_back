package com.kosa.emerjeonsiBack.service.serviceImpl;

import com.kosa.emerjeonsiBack.dto.Reservation;
import com.kosa.emerjeonsiBack.mapper.ReservationMapper;
import com.kosa.emerjeonsiBack.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationMapper reservationMapper;

    @Override
    public void createReservation(Reservation reservation) {

         reservationMapper.insertReservation(reservation);
    }

    /**
     * 예매 목록 조회
     * @param userNo
     * @return
     */
    @Override
    public List<Reservation> getReservationsByUserNo(int userNo) {
        return reservationMapper.getReservationsByUserNo(userNo);
    }

    /**
     * 나의 예매 목록 - 상세 내역
     * @param reservationId
     * @return
     */
    @Override
    public Reservation getMyReservationDetail(int reservationId) {
        return reservationMapper.getMyReservationDetail(reservationId);
    }

    /**
     * 나의 예매 목록 - 결제 내역
     * @param PaymentNo
     * @return
     */
    @Override
    public Reservation getMyReservationPaymentDetail(int PaymentNo) {
        return reservationMapper.getMyReservationPaymentDetail(PaymentNo);
    }
}
