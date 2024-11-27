package com.kosa.emerjeonsiBack.controller.reservation;
// ReservationRestController.java


import com.kosa.emerjeonsiBack.dto.Payment;
import com.kosa.emerjeonsiBack.dto.Reservation;
import com.kosa.emerjeonsiBack.dto.ReservationPayment;
import com.kosa.emerjeonsiBack.dto.User;
import com.kosa.emerjeonsiBack.dto.social.CustomOAuth2User;
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

import java.util.Map;

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

    private User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomOAuth2User) {
                CustomOAuth2User customOAuth2User = (CustomOAuth2User) principal;
                return userService.selectUserByUserId(customOAuth2User.getName());
            }
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                return userService.selectUserByUserId(userDetails.getUsername());
            }
        }
        return null;
    }

    @PostMapping("/reservation")
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation, Authentication authentication) {

        // Spring Security에서 인증된 사용자 정보 가져오기
        User user = getLoggedInUser();
        if (user == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        int userNo = user.getUserNo();

        log.info("userNo: " + userNo);
        // 중복 예매 확인
        Integer  existingReservationNo = reservationService.checkDuplicateReservation(userNo, reservation.getExhibitionNo());
        log.info("existingReservationNo: " + existingReservationNo);
        if (existingReservationNo != null) {
            // 중복된 예매가 있을 경우, 클라이언트로 예매 번호 반환
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "isDuplicate", true,
                            "message", "이미 해당 전시에 대한 예매가 존재합니다.",
                            "reservationNo", existingReservationNo
                    ));
        }

        try {
            reservation.setUserNo(userNo); // 예약에 사용자 정보 추가
            reservationService.createReservation(reservation);
            log.info("reservation created");
            int reservationNo = reservation.getReservationNo();
            return ResponseEntity.ok(reservationNo);
        } catch (Exception e) {
            log.error("예매 생성 중 오류 발생:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예매 생성에 실패했습니다.");
        }
    }

    /**
     * 예매 삭제
     * @param reservationNo
     * @return
     */
    @DeleteMapping("/{reservationNo}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable int reservationNo) {
        reservationService.deleteReservation(reservationNo);
        return ResponseEntity.ok().build();
    }

    /**
     * 서비스 - 결제 상세
     * @param reservationNo
     * @return
     */
    @GetMapping("/reservation/{reservationNo}")
    public ResponseEntity<?> getReservationDetail(@PathVariable int reservationNo) {
        Reservation reservationDetail = reservationService.getReservationDetail(reservationNo);
        if (reservationDetail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("예약 정보를 찾을 수 없습니다.");
        }
        log.info("reservation.getReservationNo : " + reservationDetail.getReservationNo());
        log.info("getReservationQuantity : " + reservationDetail.getReservationQuantity());
        log.info("getReservationPrice : " + reservationDetail.getReservationPrice());
        log.info("getInfantPrice : " + reservationDetail.getExhibition().getInfantPrice());
        log.info("getAdultPrice : " + reservationDetail.getExhibition().getAdultPrice());
        log.info("getSeniorPrice : " + reservationDetail.getExhibition().getSeniorPrice());
        return ResponseEntity.ok(reservationDetail);
    }





}
