package com.kosa.emerjeonsiBack.controller.myPage;

import com.kosa.emerjeonsiBack.dto.Reservation;
import com.kosa.emerjeonsiBack.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
public class MyPageReservationViewController {

    @Autowired
    private ReservationService reservationService;

    /**
     * 결제 완료 후 나의 예매 목록으로 페이지 이동
     * @param model
     * @return
     */
    @GetMapping("/myReservations")
    public String viewMyReservations(Model model) {
        int userNo = 1; // 예시 사용자 번호 (로그인된 사용자 ID로 변경)
        int count = 1;
        List<Reservation> myReservations = reservationService.getReservationsByUserNo(userNo);
        // LocalDateTime을 String 형식으로 변환
        /**
         *  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
         *         for (ReservationDTO reservation : myReservations) {
         *             reservation.getExhibition().setStartPeriod(LocalDateTime.parse(reservation.getExhibition().getStartPeriod().format(formatter)));
         *             reservation.getExhibition().setEndPeriod(LocalDateTime.parse(reservation.getExhibition().getEndPeriod().format(formatter)));
         *         }
         */
        try {
            for(int i = 0; i < myReservations.size(); i++) {
                System.out.println(i + " 번째 목록-예매번호 : " + myReservations.get(i).getReservationNo());
                System.out.println(i + " 번째 목록-유저번호 : " + myReservations.get(i).getUserNo());
                System.out.println(i + " 번째 목록-전시번호 : " + myReservations.get(i).getExhibitionNo());
                System.out.println(i + " 번째 목록-예매가격 : " + myReservations.get(i).getReservationPrice());
                System.out.println(i + " 번째 목록-예매수량 : " + myReservations.get(i).getReservationQuantity());
                System.out.println(i + " 번째 목록-예매상태 : " + myReservations.get(i).getReservationStatus());
                System.out.println(i + " 번째 목록-결제번호 : " + myReservations.get(i).getPayment().getPaymentNo());
                System.out.println(i + " 번째 목록-결제고유번호 : " + myReservations.get(i).getPayment().getPaymentUid());
            }
        }catch (Exception e) {
            log.error("에러메시지" + e.getMessage());
            log.error("에러" +e);
        }

        //log.info("나의 예매 목록 : " + myReservations.size());
        model.addAttribute("myReservations", myReservations);
        return "myReservations";
    }

    @GetMapping("/reservationDetails")
    public String reservationDetails(@RequestParam("reservationNo") int reservationNo, Model model) {
        Reservation reservation = new Reservation();
        reservation = reservationService.getMyReservationDetail(reservationNo);
        log.info(reservation.toString());
        log.info("전시제목 : " + reservation.getExhibition().getTitle());
        log.info("전시기관 : " + reservation.getExhibition().getCntcInsttNm());
        log.info("전시시작 : " + reservation.getExhibition().getStartPeriod());
        log.info("전시시작 : " + reservation.getExhibition().getEndPeriod());
        log.info("전시가격 : " + reservation.getReservationPrice());
        log.info("성인가격 : " + reservation.getExhibition().getAdultPrice());
        log.info("노인가격 : " + reservation.getExhibition().getSeniorPrice());
        log.info("아동가격 : " + reservation.getExhibition().getInfantPrice());

        //총 금액 반환
        int totalPrice = reservation.getReservationPrice() * reservation.getReservationQuantity();
        model.addAttribute("reservation", reservation);
        model.addAttribute("totalPrice", totalPrice);
        return "myReservations_Details";
    }
}
