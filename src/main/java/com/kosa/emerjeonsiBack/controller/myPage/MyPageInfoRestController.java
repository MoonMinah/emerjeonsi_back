package com.kosa.emerjeonsiBack.controller.myPage;

import com.kosa.emerjeonsiBack.dto.User;
import com.kosa.emerjeonsiBack.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/user/mypage")
public class MyPageInfoRestController {
    private final UserService userService;

    public MyPageInfoRestController(UserService userService) {
        this.userService = userService;
    }

    // 회원 정보 가져오기
    @GetMapping("/myinfo")
    public ResponseEntity<User> getUserInfo(Authentication auth) {
        String userId = auth.getName();
        User resultUser = userService.selectUserByUserId(userId);
        log.info("getUserInfo() : resultUser = {}", resultUser);

        if(resultUser != null) {
            return ResponseEntity.ok(resultUser);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 회원 정보 수정
    @PutMapping("/myinfo")
    public ResponseEntity<?> updateUserInfo(Authentication auth, @RequestBody @Valid User user, BindingResult result) {
        String userId = auth.getName();

        User existingUser = userService.selectUserByUserId(userId);

        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // 수정되지 않은 필드는 기존 값 유지
        if (user.getUserId() == null || user.getUserId().equals(existingUser.getUserId())) {
            user.setUserId(existingUser.getUserId());
        }

        if (user.getPassword() == null || user.getPassword().equals(existingUser.getPassword())) {
            user.setPassword(existingUser.getPassword());
        }

        if(result.hasErrors()) {
            Map<String, String> errorMessages = new HashMap<>();

            for(FieldError error : result.getFieldErrors()) {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();
                System.out.println("fieldName = " +  fieldName);
                System.out.println("errorMessage = " + errorMessage);

                errorMessages.put(fieldName, errorMessage);
            }

            return ResponseEntity.badRequest().body(errorMessages);
        }

        try {
            boolean resultUser = userService.updateUserInfoAndHistory(user, existingUser);
            log.info("updateUserInfo() : resultUser = {}", resultUser);

            if(resultUser) {
                return ResponseEntity.ok().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/withdrawal")
    public ResponseEntity<String> withdrawUser(Authentication auth) {
        String userId = auth.getName();

        userService.withdrawUser(userId);

        return ResponseEntity.ok("탈퇴 처리가 완료되었습니다.");
    }
}
