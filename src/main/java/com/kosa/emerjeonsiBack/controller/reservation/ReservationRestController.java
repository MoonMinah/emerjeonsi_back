package com.kosa.emerjeonsiBack.controller.reservation;
// ReservationRestController.java


import com.kosa.emerjeonsiBack.dto.Payment;
import com.kosa.emerjeonsiBack.dto.Reservation;
import com.kosa.emerjeonsiBack.service.PaymentService;
import com.kosa.emerjeonsiBack.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/reservation")
public class ReservationRestController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> createReservation(@RequestBody Reservation reservation) {
         //reservationService.createReservation(reservation);
        try {
            log.info("예약 데이터: " + reservation);
            // 예약 처리 서비스 호출
            //reservationService.createReservation(reservation);
            return ResponseEntity.ok(String.valueOf(reservation.getReservationNo()));
        } catch (Exception e) {
            log.error("예약 처리 중 오류 발생", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
//    @PostMapping("/api/reservation-payment")
//    public ResponseEntity<String> processReservationPayment(@RequestBody Reservation reservation,
//                                                            @RequestBody Payment payment) {
//        try {
//            paymentService.reserveAndPay(reservation, payment);
//            return ResponseEntity.ok("결제가 성공적으로 완료되었습니다.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결제 처리 중 오류가 발생했습니다.");
//        }
//    }
}
