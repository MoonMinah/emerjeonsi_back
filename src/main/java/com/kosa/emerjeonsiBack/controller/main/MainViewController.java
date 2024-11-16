package com.kosa.emerjeonsiBack.controller.main;

import com.kosa.emerjeonsiBack.dto.Exhibition;
import com.kosa.emerjeonsiBack.service.MainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
public class MainViewController {

    @Autowired
    private MainService mainService;

    @GetMapping("/home")
    public String main() {
        /*List<Exhibition> exhibitionList = mainService.getExhibitionList();
        for(int i = 0; i < exhibitionList.size(); i++) {
//            log.info("mainPage" + exhibitionList.get(i));
            log.info("mainPage()::" + exhibitionList.get(i));
        }
        model.addAttribute("exhibitionList", exhibitionList);*/

        log.info("메인 페이지 호출");

        return "main";
    }
    @GetMapping("/home/{exhibitionNo}")
    public String showExhibitionDetail(@PathVariable("exhibitionNo") Long exhibitionNo, Model model) {
        Exhibition exhibition = mainService.getExhibitionById(exhibitionNo);
        model.addAttribute("exhibition", exhibition);

        log.info("전시 상세 페이지 호출");
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
