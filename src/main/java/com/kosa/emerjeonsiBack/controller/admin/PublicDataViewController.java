package com.kosa.emerjeonsiBack.controller.admin;

import com.kosa.emerjeonsiBack.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@Slf4j
public class PublicDataViewController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RestTemplate restTemplate;

    // 화면 반환 메서드
    @GetMapping("/adminView")
    public String admin(Model model) {

        return "testAdmin"; // testAdmin.jsp 페이지 반환
    }
}