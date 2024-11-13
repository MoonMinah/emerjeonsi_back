package com.kosa.emerjeonsiBack.controller.main;

import com.kosa.emerjeonsiBack.dto.Exhibition;
import com.kosa.emerjeonsiBack.service.MainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
public class SearchRestController {

    @Autowired
    private MainService mainService;

    // JSON 데이터 반환 메서드 (필요 시 사용)
    @GetMapping("/home/data")
    public List<Exhibition> getExhibitions() {
        log.info("Exhibition list API called");
//        log.info("RestApi : " + mainService.getExhibitionList());
//        return mainService.getExhibitionList();

        List<Exhibition> result = mainService.getExhibitionList();
        log.info("RestApi : " + result);
        return result;
    }

    // 전시 상세 정보 반환
    @GetMapping("/home/{exhibitionNo}/data")
    public Exhibition getExhibitionDetail(@PathVariable("exhibitionNo") Long exhibitionNo) {
        log.info("Exhibition detail API called for exhibitionNo: " + exhibitionNo);
        Exhibition exhibition = mainService.getExhibitionById(exhibitionNo);
        log.info("Exhibition detail: " + exhibition);
        return exhibition;
    }

}
