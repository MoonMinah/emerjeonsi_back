package com.kosa.emerjeonsiBack.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
public class TestController {
    @GetMapping({"", "/"})
    public ModelAndView main() {
        System.out.println("하하하");
        return new ModelAndView("mainPage");
    }
}
