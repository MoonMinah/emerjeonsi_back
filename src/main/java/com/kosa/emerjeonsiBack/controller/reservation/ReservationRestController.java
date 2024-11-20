package com.kosa.emerjeonsiBack.controller.reservation;
// ReservationRestController.java


import com.kosa.emerjeonsiBack.dto.Payment;
import com.kosa.emerjeonsiBack.dto.Reservation;
import com.kosa.emerjeonsiBack.dto.ReservationPayment;
import com.kosa.emerjeonsiBack.dto.User;
import com.kosa.emerjeonsiBack.service.PaymentService;
import com.kosa.emerjeonsiBack.service.ReservationHistoryService;
import com.kosa.emerjeonsiBack.service.ReservationService;
import com.kosa.emerjeonsiBack.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class ReservationRestController {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationHistoryService reservationHistoryService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserService userService;

    @PostMapping("/reservation")
    public ResponseEntity<Integer> createReservation(@RequestBody Reservation reservation) {
         reservationService.createReservation(reservation);
         //reservationHistoryService.insertReservationHistory(reservation.getReservationNo(), reservation.getReservationStatus())
        int reservationNo = reservation.getReservationNo(); // 생성된 reservationNo 가져오기
        log.info("reservationNo : " + reservationNo);
        try {
            log.info("reservationNo1 : " + reservationNo);
            return ResponseEntity.ok(reservationNo);
        }catch (Exception e) {
            log.error("Error creating reservation: ", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 서비스 - 결제 상세
     * @param reservationNo
     * @return
     */
    @GetMapping("/reservation/{reservationNo}")
    public ResponseEntity<Reservation> getReservationDetail(@PathVariable int reservationNo) {
        Reservation reservationDetail = reservationService.getReservationDetail(reservationNo);
        log.info("reservation.getReservationNo : " + reservationDetail.getReservationNo());
        log.info("getReservationQuantity : " + reservationDetail.getReservationQuantity());
        log.info("getReservationPrice : " + reservationDetail.getReservationPrice());
        log.info("getInfantPrice : " + reservationDetail.getExhibition().getInfantPrice());
        log.info("getAdultPrice : " + reservationDetail.getExhibition().getAdultPrice());
        log.info("getSeniorPrice : " + reservationDetail.getExhibition().getSeniorPrice());
        return ResponseEntity.ok(reservationDetail);
    }





}
