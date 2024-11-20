package com.kosa.emerjeonsiBack.controller.myPage;
import com.kosa.emerjeonsiBack.dto.Reservation;
import com.kosa.emerjeonsiBack.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Controller
@Slf4j
@RequestMapping("/user/myPage")
public class MyPageReservationPaymentDetailViewController {

    @GetMapping("/reservationPaymentDetails")
    public String reservationDetails() {

        return "myReservationsPaymentDetails";
    }
}
