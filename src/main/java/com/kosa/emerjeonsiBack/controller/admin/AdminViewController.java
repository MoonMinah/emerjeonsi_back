package com.kosa.emerjeonsiBack.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminViewController {
    @GetMapping("")
    public String adminForm() {
        return "adminForm";
    }
}
