package com.kosa.emerjeonsiBack.controller.main;

import com.kosa.emerjeonsiBack.dto.User;
import com.kosa.emerjeonsiBack.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class MainRestController {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MainRestController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/home")
    public ResponseEntity<String> main() {
        return ResponseEntity.ok("환영합니다. 메인 페이지입니다.");
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