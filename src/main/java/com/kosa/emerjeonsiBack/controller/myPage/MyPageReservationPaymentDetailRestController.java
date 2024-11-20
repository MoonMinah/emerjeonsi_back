package com.kosa.emerjeonsiBack.controller.myPage;

import com.kosa.emerjeonsiBack.dto.Payment;
import com.kosa.emerjeonsiBack.dto.Reservation;
import com.kosa.emerjeonsiBack.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Slf4j
@RequestMapping("/api/user/myPage")
public class MyPageReservationPaymentDetailRestController {

    @Autowired
    ReservationService reservationService;


    @GetMapping("/reservationPaymentDetail")
    public ResponseEntity<Reservation> getMyPageReservationPaymentDetail(@RequestParam("paymentNo") int paymentNo) {
        Reservation reservation = reservationService.getMyReservationPaymentDetail(paymentNo);
        log.info("getMyPageReservationPaymentDetail");
        log.info("paymentNo: {}", paymentNo);
        log.info("reservation(결제내역): {}", reservation);
        log.info("reservation(결제일시): {}", reservation.getPayment().getPaymentDate());

        log.info("reservation(일시): {}", reservation.getPayment().getFormattedPaymentDate());

        if (reservation.getPayment().getPaymentDate() == null) {
            reservation.getPayment().setPaymentDate(LocalDateTime.now()); // 기본 시간 설정
        }

        return ResponseEntity.ok(reservation);
    }
}
