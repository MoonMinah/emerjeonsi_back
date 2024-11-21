package com.kosa.emerjeonsiBack.controller.myPage;

import com.kosa.emerjeonsiBack.dto.Reservation;
import com.kosa.emerjeonsiBack.service.RefundService;
import com.kosa.emerjeonsiBack.service.ReservationService;
import com.kosa.emerjeonsiBack.service.serviceImpl.RefundServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/user/myPage")
public class MyPageReservationRefundRestController {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private RefundService refundService;


    /**
     * 환불페이지 데이터 렌더링
     * @param paymentNo
     * @return
     */
    @GetMapping("/refund")
    public ResponseEntity<Reservation> refund(@RequestParam("paymentNo") int paymentNo) {
        Reservation reservation = reservationService.getMyReservationPaymentDetail(paymentNo);
        log.info("reservation: {}", reservation);
        log.info("reservation.getPayment: {}", reservation.getPayment());

        return ResponseEntity.ok(reservation);
    }

    @PostMapping("/processRefund")
    public ResponseEntity<String> processRefund(@RequestBody Map<String, Object> request) {
        // imp_uid 처리
        String imp_uid = (String) request.get("imp_uid");
        log.info("processRefund impUid {}", imp_uid);

        // amount 처리
        Object amountObj = request.get("amount");
        BigDecimal amount;
        try {
            if (amountObj instanceof String) {
                amount = new BigDecimal((String) amountObj);
            } else if (amountObj instanceof Number) {
                amount = BigDecimal.valueOf(((Number) amountObj).doubleValue());
            } else {
                log.error("amount 값이 올바르지 않습니다: {}", amountObj);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("환불 금액이 올바르지 않습니다.");
            }
        } catch (Exception e) {
            log.error("amount 변환 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("환불 금액 변환 오류");
        }

        log.info("Converted amount: {}", amount);

        // reservationNo와 paymentNo 처리
        int reservationNo;
        int paymentNo;
        try {
            reservationNo = Integer.parseInt(request.get("reservationNo").toString());
            paymentNo = Integer.parseInt(request.get("paymentNo").toString());
        } catch (Exception e) {
            log.error("reservationNo 또는 paymentNo 변환 오류", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("예약 번호 또는 결제 번호가 올바르지 않습니다.");
        }

        boolean isRefunded = refundService.processRefund(imp_uid, amount, reservationNo, paymentNo);

        if (isRefunded) {
            return ResponseEntity.ok("환불이 성공적으로 처리되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("환불 처리 중 오류가 발생했습니다.");
        }
    }
}
