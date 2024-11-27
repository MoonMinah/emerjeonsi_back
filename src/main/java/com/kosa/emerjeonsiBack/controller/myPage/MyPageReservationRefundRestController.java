package com.kosa.emerjeonsiBack.controller.myPage;

import com.kosa.emerjeonsiBack.dto.Reservation;
import com.kosa.emerjeonsiBack.dto.User;
import com.kosa.emerjeonsiBack.dto.social.CustomOAuth2User;
import com.kosa.emerjeonsiBack.service.RefundService;
import com.kosa.emerjeonsiBack.service.ReservationService;
import com.kosa.emerjeonsiBack.service.UserService;
import com.kosa.emerjeonsiBack.service.serviceImpl.RefundServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/user/myPage")
public class MyPageReservationRefundRestController {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private RefundService refundService;

    @Autowired
    private UserService userService;

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

//    private User getLoggedInUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            // userId를 사용하여 데이터베이스에서 유저 정보 가져오기
//            return userService.selectUserByUserId(userDetails.getUsername());
//        }
//        return null;
//    }
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

    @GetMapping("/refunds")
    public ResponseEntity<?>getRefunds( @RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "size", defaultValue = "4") int size) {
        if (page < 1) {
            page = 1; // page가 1 미만일 경우 기본값 설정
        }

        User user = getLoggedInUser();
        if (user == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        int userNo = user.getUserNo();
        int total = refundService.countRefundsByUserNo(userNo);
        int totalPages = refundService.calculateTotalPages(total, size);

        // **수정: total이 0일 경우 빈 데이터 반환**
        if (total == 0) {
            Map<String, Object> emptyResponse = new HashMap<>();
            emptyResponse.put("reservations", Collections.emptyList());
            emptyResponse.put("total", 0);
            emptyResponse.put("pageSize", size);
            emptyResponse.put("currentPage", page);
            emptyResponse.put("totalPages", 0);
            return ResponseEntity.ok(emptyResponse); // 정상 응답으로 반환
        }

        if (page > totalPages) {
            return ResponseEntity.badRequest().body("잘못된 페이지 요청입니다.");
        }

        int offset = (page - 1) * size;
        List<Reservation> reservations = refundService.getRefundsByUserNo(userNo, offset, size);
        log.info("Total reservations: {}, Page size: {}, Total pages: {}", total, size, totalPages);
        log.info("Fetched reservations: {}", reservations);
        System.out.println("페이지 번호: " + page);
        System.out.println("오프셋: " + offset);
        System.out.println("요청 크기: " + size);
        System.out.println("반환된 데이터: " + reservations);

        Map<String, Object> response = new HashMap<>();
        response.put("reservations", reservations);
        response.put("total", total);
        response.put("pageSize", size);
        response.put("currentPage", page);
        response.put("totalPages", totalPages);
        log.info("Response(toString): {}", response.toString());
        log.info("Response(reservations): {}", response.get("reservations").toString());
        log.info("Response Data: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/refundsDetail")
    public ResponseEntity<?> getRefundDetail(@RequestParam int paymentNo) {
        try {
            Reservation refundDetail = refundService.getMyRefundsDetail(paymentNo);
            log.info("환불 상세내역 : " + refundDetail);
            return ResponseEntity.ok(refundDetail);
        } catch (Exception e) {
            log.error("환불 상세내역 가져오기 실패:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("환불 상세 내역을 가져오는 데 실패했습니다.");
        }
    }
}
