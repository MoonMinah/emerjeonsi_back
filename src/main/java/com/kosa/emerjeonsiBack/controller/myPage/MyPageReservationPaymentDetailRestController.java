package com.kosa.emerjeonsiBack.controller.myPage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@Slf4j
@RequestMapping("/api/myPage")
public class MyPageReservationPaymentDetailRestController {
    @GetMapping("/reservationPaymentDetail")
    public ResponseEntity<String> getMyPageReservationPaymentDetail(@RequestParam("paymentNo") int paymentNo) {
        log.info("getMyPageReservationPaymentDetail");
        log.info("paymentNo: {}", paymentNo);
        return ResponseEntity.ok("myPageReservationPaymentDetail");
    }
}
