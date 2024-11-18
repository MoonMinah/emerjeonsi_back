package com.kosa.emerjeonsiBack.controller.main;

import com.kosa.emerjeonsiBack.dto.Exhibition;
import com.kosa.emerjeonsiBack.dto.User;
import com.kosa.emerjeonsiBack.service.MainService;
import com.kosa.emerjeonsiBack.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class MainRestController {

    @Autowired
    private MainService mainService;

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MainRestController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/home/data")
    public List<Exhibition> getExhibitions() {
        log.info("Exhibition list API called");
//        log.info("RestApi : " + mainService.getExhibitionList());
//        return mainService.getExhibitionList();

        List<Exhibition> result = mainService.getExhibitionList();
        log.info("RestApi : " + result);
        return result;
    }
/*    @GetMapping("/home/search?filter=${selectedFilter}&keyword=${searchInput}")
    public ResponseEntity<?> searchExhibitions(
            @PathVariable String selectedFilter,
            @PathVariable String searchInput){
        return (ResponseEntity<?>) mainService.searchByFilter(selectedFilter, searchInput);
    }*/


    // 전시 상세 정보 반환
    @GetMapping("/home/{exhibitionNo}/data")
    public Exhibition getExhibitionDetail(@PathVariable("exhibitionNo") Long exhibitionNo) {
        log.info("Exhibition detail API called for exhibitionNo: " + exhibitionNo);
        Exhibition exhibition = mainService.getExhibitionById(exhibitionNo);
        log.info("Exhibition detail: " + exhibition);
        return exhibition;
    }

    // 전시회 최신순, 이름순 정렬
    @GetMapping("/home/data/sort")
    public List<Exhibition> getExhibitionsSorted(@RequestParam("criteria") String criteria) {
        if ("name".equalsIgnoreCase(criteria)) {
            return mainService.getExhibitionsByName();
        }
        return mainService.getExhibitionList();
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody User user, BindingResult result) {
        log.info("User : {}", user);

        // 유효성 검사 실패 시, 오류 메시지 던짐.
        if(result.hasErrors()) {
            // 가장 먼저 발생한 오류 메시지를 가져옴.
            String errorMessage = result.getFieldError().getDefaultMessage();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        try {
            // 아이디 중복 체크
            if(userService.existsByUserId(user.getUserId())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 아이디입니다.");
            }

            // 비밀번호 암호화 후 저장
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setProvider("origin");

            int resultUser = userService.userInsert(user);
            log.info("resultUser = {}", resultUser);

            if(resultUser > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 실패");
            }
        } catch (Exception e) {
            log.error("회원가입 중 오류 발생 : ", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러로 인해 오류가 발생하였습니다.");
        }
    }
}
