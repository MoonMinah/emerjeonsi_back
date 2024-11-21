package com.kosa.emerjeonsiBack.controller.payment;
import com.kosa.emerjeonsiBack.dto.ReservationPayment;
import com.kosa.emerjeonsiBack.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
@Slf4j
public class PaymentRestController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/api/user/reservation-payment")
    public ResponseEntity<String> reservationAndPayment(@RequestBody ReservationPayment reservationPayment) {
      try {
          log.info("Reservation(예매): " + reservationPayment.getReservation());
          log.info("Payment(결제): " + reservationPayment.getPayment());
          log.info("payment(결제번호" + reservationPayment.getPayment().getPaymentNo());
          paymentService.reserveAndPay(reservationPayment.getReservation(), reservationPayment.getPayment());
          return ResponseEntity.ok("결제가 성공적으로 완료되었습니다!");
      }catch (Exception e) {
          log.error("에러 메시지 : " +e.getMessage());
          log.error("error : " + e);
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결제 처리 중 오류가 발생했습니다.");
      }
    }
}
