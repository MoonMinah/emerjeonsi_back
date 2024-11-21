package com.kosa.emerjeonsiBack.controller.reservation;
import com.kosa.emerjeonsiBack.dto.Reservation;
import com.kosa.emerjeonsiBack.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class ReservationViewController {

    @Autowired
    ReservationService reservationService;



    @GetMapping("/reservationDetail")
    public String viewReservationDetail(Model model) {

        Reservation reservation = new Reservation();
        return "reservationDetail";
    }
}
