package com.kosa.emerjeonsiBack.controller.myPage;
import com.kosa.emerjeonsiBack.dto.Reservation;
import com.kosa.emerjeonsiBack.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Controller
@Slf4j
public class MyPageReservationPaymentDetailViewController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservationPaymentDetails")
    public String reservationDetails(@RequestParam("paymentNo") int paymentNo , Model model) throws ParseException {
        Reservation reservation = new Reservation();
        reservation = reservationService.getMyReservationPaymentDetail(paymentNo);
        //총 금액 = 티켓 금액 * 티켓 수량
        int totalPrice = reservation.getReservationPrice() * reservation.getReservationQuantity();
        log.info("결제금액: " + reservation.getPayment().getPaymentPrice());
        log.info("결제수단: " + reservation.getPayment().getPaymentMethod());
        log.info("결제일시: " + reservation.getPayment().getFormattedPaymentDate());
        log.info("총 금액 : " + totalPrice);
        log.info("결제 번호 : " + paymentNo);
        //String localDateTimeFormat2 = localDateTime

        LocalDateTime paymentDateTime = reservation.getPayment().getPaymentDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = paymentDateTime.format(formatter);

        log.info("시간 : " + formattedDate);

        model.addAttribute("reservation", reservation);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentDateTime", formattedDate);
        model.addAttribute("paymentNo", paymentNo);
        return "myReservations_PaymentDetails";
    }
}
