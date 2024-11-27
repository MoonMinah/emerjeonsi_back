package com.kosa.emerjeonsiBack.controller.myPage;

import com.kosa.emerjeonsiBack.dto.Reservation;
import com.kosa.emerjeonsiBack.dto.User;
import com.kosa.emerjeonsiBack.dto.social.CustomOAuth2User;
import com.kosa.emerjeonsiBack.service.ReservationService;
import com.kosa.emerjeonsiBack.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/user/myPage")
public class MyPageReservaitonRestController {

    @Autowired
    private ReservationService reservationService;

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


    @GetMapping("/reservations")
    public ResponseEntity<?> getReservations(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "4") int size) {

        if (page < 1) {
            page = 1; // page가 1 미만일 경우 기본값 설정
        }

        User user = getLoggedInUser();
        if (user == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        int userNo = user.getUserNo();
        int total = reservationService.countReservationsByUserNo(userNo);
        int totalPages = reservationService.calculateTotalPages(total, size);

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
        List<Reservation> reservations = reservationService.getReservationsByUserNo(userNo, offset, size);
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

        return ResponseEntity.ok(response);
    }




}
