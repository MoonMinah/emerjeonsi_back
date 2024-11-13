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
            log.info("reservation : " + reservation);
            return ResponseEntity.ok("성공");
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/api/reservation-payment")
    public ResponseEntity<String> processReservationPayment(@RequestBody Reservation reservation,
                                                            @RequestBody Payment payment) {
        try {
            paymentService.reserveAndPay(reservation, payment);
            return ResponseEntity.ok("결제가 성공적으로 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결제 처리 중 오류가 발생했습니다.");
        }
    }
}
