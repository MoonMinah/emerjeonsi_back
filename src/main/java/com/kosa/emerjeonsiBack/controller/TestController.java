package com.kosa.emerjeonsiBack.controller;

import com.kosa.emerjeonsiBack.dto.Exhibition;
import com.kosa.emerjeonsiBack.dto.Payments;
import com.kosa.emerjeonsiBack.dto.Reservation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
public class TestController {
    @GetMapping({"", "/"})
    public ModelAndView main() {
        System.out.println("하하히");
        System.out.println("히히히");
        System.out.println("다시다시");
        return new ModelAndView("mainPage");
    }
}
