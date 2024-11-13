package com.kosa.emerjeonsiBack.controller.myPage;

import com.kosa.emerjeonsiBack.dto.Reservation;
import com.kosa.emerjeonsiBack.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@Slf4j
public class MyPageReservationRefundViewController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservationRefund")
    public String myPageReservationRefundView(@RequestParam("paymentNo") int paymentNo , Model model) {
        Reservation reservation = reservationService.getMyReservationPaymentDetail(paymentNo);
        int totalPrice = reservation.getReservationPrice() * reservation.getReservationQuantity();
        model.addAttribute("reservation", reservation);
        LocalDateTime paymentDateTime = reservation.getPayment().getPaymentDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = paymentDateTime.format(formatter);
        model.addAttribute("reservation", reservation);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentDateTime", formattedDate);
        return "myReservations_refund";
    }
}
