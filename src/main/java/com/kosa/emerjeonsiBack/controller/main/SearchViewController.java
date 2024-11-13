package com.kosa.emerjeonsiBack.controller.main;

import com.kosa.emerjeonsiBack.dto.Exhibition;
import com.kosa.emerjeonsiBack.service.MainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@Slf4j
public class SearchViewController {

    @Autowired
    private MainService mainService;

    // 화면 반환 메서드
    @GetMapping("/homeView")
    public String mainPage(Model model) {
        List<Exhibition> exhibitionList = mainService.getExhibitionList();
        for(int i = 0; i < exhibitionList.size(); i++) {
//            log.info("mainPage" + exhibitionList.get(i));
            log.info("mainPage()::" + exhibitionList.get(i));
        }
        model.addAttribute("exhibitionList", exhibitionList);

        log.info("메인 페이지 호출");
        return "testMain"; // testMain.jsp 페이지 반환
    }

    @GetMapping("/home/{exhibitionNo}")
    public String showExhibitionDetail(@PathVariable("exhibitionNo") Long exhibitionNo, Model model) {
        Exhibition exhibition = mainService.getExhibitionById(exhibitionNo);
        model.addAttribute("exhibition", exhibition);

        log.info("전시 상세 페이지 호출");
        return "exhibitionDetail"; // exhibitionDetail.jsp
    }
}