package com.kosa.emerjeonsiBack.controller.myPage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class MyPageReservationDetailViewController {

    @GetMapping("/myReservationDetail")
    public String reservationDetail() {
        return "myReservationsDetails";
    }

}
