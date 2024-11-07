package com.kosa.emerjeonsiBack.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class PageController {
    @GetMapping("/payment")
    public String paymentPage() {
        log.info("결제 페이지 호출");
        return "payment";
    }

    @GetMapping("/paymentDetail")
    public String paymentDetailPage() {
        return "payment";
    }

    @GetMapping("/mypage/myReservation")
    public String myReservationPage() {
        return "myReservation";
    }



    @GetMapping("/exhibitionDetail")
    public String exhibitionDetailPage() {
        return "exhibitionDetail";
    }

    @GetMapping("/main")
    public String exhibitionDetailPage2() {
        return "main";
    }




}
