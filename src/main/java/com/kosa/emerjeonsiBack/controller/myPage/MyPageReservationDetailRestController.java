package com.kosa.emerjeonsiBack.controller.myPage;

import com.kosa.emerjeonsiBack.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/myPage")
@Slf4j
public class MyPageReservationDetailRestController {

    @Autowired
    private ReservationService reservationService;

    /**
     * 나의 예약목록 - 상세내역
     * @param reservationNo
     * @return
     */
    @GetMapping("/reservationDetail")
    public ResponseEntity<String> reservationDetail(@RequestParam("reservationNo") int reservationNo) {
        log.info("예매 번호 : " + reservationNo);
        try {
            log.info("예매 번호1 : " + reservationNo);
            reservationService.getMyReservationDetail(reservationNo);
            log.info("reservationDetail : " +reservationService.getMyReservationDetail(reservationNo));
            return ResponseEntity.ok("reservationDetail");
        }catch (Exception e) {
            log.error("reservationDetail : " +e.getMessage());
            log.error("reservationDetail : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("reservationDetail");
        }
    }
}
