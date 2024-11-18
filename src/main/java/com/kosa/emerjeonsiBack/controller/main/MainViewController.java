package com.kosa.emerjeonsiBack.controller.main;

import com.kosa.emerjeonsiBack.service.MainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainViewController {

    @Autowired
    private MainService mainService;

    @GetMapping("/home")
    public String main() {
        log.info("메인 페이지 호출");

        return "main";
    }


    @GetMapping("/home/{exhibitionNo}")
    public String showExhibitionDetail() {

        return "exhibitionDetail"; // exhibitionDetail.jsp
    }

    @GetMapping("/login")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "signupForm";
    }
}
