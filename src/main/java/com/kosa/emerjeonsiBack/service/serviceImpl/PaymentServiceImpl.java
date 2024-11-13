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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
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
    public int insertPayment(Payment payment) {
        return paymentMapper.insertPayment(payment);
    }

    @Transactional
    @Override
    public void reserveAndPay(Reservation reservation, Payment payment) {
        // 예매 정보 저장 및 상태 이력 기록
        reservationMapper.insertReservation(reservation);  // reservationNo가 생성됨
        System.out.println("결제 서비스(예매번호) : " + reservation.getReservationNo());

        // reservationNo가 정상적으로 생성되었는지 확인
        if (reservation.getReservationNo() == 0) {
            throw new RuntimeException("Reservation insertion failed, reservationNo not generated.");
        }

        ReservationHistory reservationHistory = new ReservationHistory();
        reservationHistory.setReservationNo(reservation.getReservationNo());
        reservationHistory.setReservationStatus("결제대기");
        reservationHistoryMapper.insertReservationHistory(reservationHistory);

        // 결제 정보 저장 및 상태 이력 기록
        payment.setReservationNo(reservation.getReservationNo()); // 예약 번호를 결제 정보에 설정
        paymentMapper.insertPayment(payment);
        System.out.println("결제 서비스(결제번호) : " + payment.getPaymentNo());

        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.setPaymentNo(payment.getPaymentNo());
        paymentHistory.setPaymentStatus(payment.getPaymentStatus());
        paymentHistoryMapper.insertPaymentHistory(paymentHistory);

        // 결제가 성공적으로 완료된 경우 예매 상태 업데이트
        reservation.setReservationStatus("예매완료");
        reservationMapper.updateReservationStatus(reservation);
        reservationHistory.setReservationStatus("예매완료");
        reservationHistoryMapper.insertReservationHistory(reservationHistory);
    }


}
