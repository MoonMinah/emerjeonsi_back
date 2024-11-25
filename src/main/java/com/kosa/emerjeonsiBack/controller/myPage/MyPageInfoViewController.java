package com.kosa.emerjeonsiBack.controller.myPage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user/mypage")
public class MyPageInfoViewController {
    @GetMapping("/myinfo")
    public String myInfo() {
        return "myInfo";
    }
}
