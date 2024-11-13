package com.kosa.emerjeonsiBack.controller;

import com.kosa.emerjeonsiBack.dto.Reservation;
import com.kosa.emerjeonsiBack.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
public class PageController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/exhibitionDetail")
    public String exhibitionDetailPage() {
        return "exhibitionDetail";
    }

   /* @GetMapping("/main")
    public String updateMainPage() {
        return "main";
    }*/
}
