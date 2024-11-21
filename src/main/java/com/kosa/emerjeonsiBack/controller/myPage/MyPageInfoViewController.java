package com.kosa.emerjeonsiBack.controller.myPage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user/mypage")
public class MyPageInfoViewController {
    @GetMapping("/myInfo")
    public String myInfo() {
        String sessionId = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("sessionId = {}", sessionId);

        return "myInfo";
    }
}
