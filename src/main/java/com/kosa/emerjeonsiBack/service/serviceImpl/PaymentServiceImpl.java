package com.kosa.emerjeonsiBack.service.serviceImpl;

import com.kosa.emerjeonsiBack.dto.Payment;
import com.kosa.emerjeonsiBack.dto.PaymentHistory;
import com.kosa.emerjeonsiBack.dto.Reservation;
import com.kosa.emerjeonsiBack.dto.ReservationHistory;
import com.kosa.emerjeonsiBack.mapper.PaymentHistoryMapper;
import com.kosa.emerjeonsiBack.mapper.PaymentMapper;
import com.kosa.emerjeonsiBack.mapper.ReservationHistoryMapper;
import com.kosa.emerjeonsiBack.mapper.ReservationMapper;
import com.kosa.emerjeonsiBack.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private ReservationMapper reservationMapper;
    @Autowired
    private ReservationHistoryMapper reservationHistoryMapper;
    @Autowired
    private PaymentHistoryMapper paymentHistoryMapper;

    /**
     * 결제 등록.
     * @param payment
     * @return
     */
    @Override
    public void insertPayment(Payment payment) {

         paymentMapper.insertPayment(payment);
    }

    @Transactional
    @Override
    public void reserveAndPay(Reservation reservation, Payment payment) {

        // 예약 테이블 상태 업데이트
        log.info("Updating reservation status for reservationNo: {}", reservation.getReservationNo());
        String newReservationStatus = payment.getPaymentStatus().equals("결제성공") ? "예매완료" : "취소";
        reservationMapper.updateReservationStatus(reservation.getReservationNo(), newReservationStatus);

        // 예약 이력 테이블에 새로운 상태 기록 삽입
        log.info("Inserting reservation history for reservationNo: {}", reservation.getReservationNo());
        ReservationHistory reservationHistory = new ReservationHistory();
        reservationHistory.setReservationNo(reservation.getReservationNo());
        reservationHistory.setReservationStatus(newReservationStatus);
        reservationHistory.setReservationEventTimeStamp(LocalDateTime.now());
        reservationHistoryMapper.insertReservationHistory(reservationHistory);

        // 결제 테이블에 결제 정보 삽입
        log.info("Inserting payment record for reservationNo: {}", reservation.getReservationNo());
        payment.setReservationNo(reservation.getReservationNo());
        paymentMapper.insertPayment(payment);
        log.info("Generated paymentNo after insertion: {}", payment.getPaymentNo()); // 확인용 로그
        // 로그 확인
        log.info("paymentNo-ServiceImpl", payment.getPaymentNo());
        System.out.println("Generated paymentNo: " + payment.getPaymentNo());

        // 결제 이력 테이블에 결제 상태 기록 삽입
        log.info("Inserting payment history for paymentNo: {}", payment.getPaymentNo());
        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.setPaymentNo(payment.getPaymentNo());
        paymentHistory.setPaymentStatus(payment.getPaymentStatus());
        paymentHistory.setPaymentEventTimestamp(LocalDateTime.now());
        paymentHistoryMapper.insertPaymentHistory(paymentHistory);
    }



}
