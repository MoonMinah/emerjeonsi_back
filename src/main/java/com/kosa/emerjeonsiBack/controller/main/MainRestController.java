package com.kosa.emerjeonsiBack.controller.main;

import com.kosa.emerjeonsiBack.dto.Exhibition;
import com.kosa.emerjeonsiBack.dto.User;
import com.kosa.emerjeonsiBack.dto.social.CustomOAuth2User;
import com.kosa.emerjeonsiBack.mapper.UserMapper;
import com.kosa.emerjeonsiBack.service.MainService;
import com.kosa.emerjeonsiBack.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Autowired
    private UserMapper userMapper;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MainRestController(UserService userService) {
        this.userService = userService;
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

    @GetMapping("/home/data/search")
    public List<Exhibition> searchExhibitions(
            @RequestParam("selectedFilter") String selectedFilter,
            @RequestParam("searchInput") String searchInput) {
        log.info("Filter Search API called with filter: {}, input: {}", selectedFilter, searchInput);
        return mainService.searchByFilter(selectedFilter, searchInput);
    }

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
//            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//            user.setProvider("origin");
//
//            int resultUser = userService.userInsert(user);
//            log.info("resultUser = {}", resultUser);
//
//            if(resultUser > 0) {
//                return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
//            } else {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 실패");
//            }
            userService.signup(user);

            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
        } catch (Exception e) {
            log.error("회원가입 중 오류 발생 : ", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러로 인해 오류가 발생하였습니다.");
        }
    }
//    @GetMapping("/check-login")
//    public ResponseEntity<User> checkLoginStatus() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        log.info("Security Context 인증 정보: {}", authentication);
//        log.info("getLoggedInUserInfo Authentication: {}", authentication);
//
//        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//
//        Object principal = authentication.getPrincipal();
//
//        if(principal instanceof CustomOAuth2User) {
//            // OAuth2 사용자 처리
//            CustomOAuth2User oAuth2User = (CustomOAuth2User) principal;
//            String userId = oAuth2User.getUsername();
////            User user = userService.selectUserByUserId(oAuth2User.getName());
//            User user = userService.selectUserByUserId(userId);
//
//            if(user == null) {
//                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//            }
//
//            return ResponseEntity.ok(user);
//        } else if(principal instanceof UserDetails) {
//            UserDetails userDetails = (UserDetails) principal;
//            User user = userService.selectUserByUserId(userDetails.getUsername());
//            log.info("로그인된 사용자 정보: {}", user);
//
//            if(user == null) {
//                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//            }
//
//            return ResponseEntity.ok(user);
//        }
//
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    }

    @GetMapping("/check-login")
    public ResponseEntity<?> checkLoginStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomOAuth2User) {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) principal;
            String userId = oAuth2User.getUsername();
            User user = userMapper.selectUserByUserId(oAuth2User.getName());

            log.info("user : " + user);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            return ResponseEntity.ok(user);
        } else if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            User user = userMapper.selectUserByUserId(userDetails.getUsername());
            return ResponseEntity.ok(user);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
