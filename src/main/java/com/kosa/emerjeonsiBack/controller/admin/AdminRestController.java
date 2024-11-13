package com.kosa.emerjeonsiBack.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
    @GetMapping("")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("Admin 권한을 가진 사용자만 접근 가능한 페이지입니다.");
    }
}
