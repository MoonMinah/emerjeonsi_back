package com.kosa.emerjeonsiBack.service.serviceImpl;

import com.kosa.emerjeonsiBack.dto.PaymentHistory;
import com.kosa.emerjeonsiBack.mapper.RefundMapper;
import com.kosa.emerjeonsiBack.service.RefundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class RefundServiceImpl implements RefundService {

    @Value("${portone.api.key}")
    private String apiKey;

    @Value("${portone.api.secret}")
    private String apiSecret;

    private static final String BASE_URL = "https://api.iamport.kr";

    private final RestTemplate restTemplate;
    private final RefundMapper refundMapper;

    // Access Token과 만료 시간을 캐싱하기 위한 필드
    private String cachedAccessToken;
    private long tokenExpiryTime;

    @Autowired
    public RefundServiceImpl(RestTemplate restTemplate, RefundMapper refundMapper) {
        this.restTemplate = restTemplate;
        this.refundMapper = refundMapper;
    }

    @Override
    @Transactional
    public boolean processRefund(String imp_uid, BigDecimal amount, int reservationNo, int paymentNo) {
        try {
            log.info("API Key: " + apiKey);
            log.info("API Secret: " + apiSecret);
            log.info("imp_uid(고유번호): " + imp_uid);
            log.info("amount(환불금액):" + amount);

            // 1. Access Token 발급 (캐싱된 토큰 확인)
            String accessToken = getCachedAccessToken();
            log.info("access token: " + accessToken);
            if (accessToken == null) {
                throw new RuntimeException("Access Token 발급 실패");
            }

            // 2. 환불 요청 데이터 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);

            Map<String, Object> body = new HashMap<>();
            body.put("imp_uid", imp_uid);
            body.put("amount", amount);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            // 3. 환불 요청 보내기
            ResponseEntity<Map> response = restTemplate.exchange(
                    BASE_URL + "/payments/cancel",
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            // 4. 환불 성공 여부 확인
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();

                // 응답 코드 확인
                Integer code = (Integer) responseBody.get("code");
                if (code != null && code == 0) {
                    // 응답의 상태가 성공인지 확인
                    Map<String, Object> responseData = (Map<String, Object>) responseBody.get("response");
                    String status = (String) responseData.get("status");

                    log.info("환불 상태: " + status);

                    if ("cancelled".equals(status)) {
                        // 환불 성공 시 DB 상태 업데이트
                        refundMapper.updateReservationStatus(reservationNo, "취소");
                        refundMapper.insertReservationHistory(reservationNo, "취소");
                        refundMapper.updatePaymentStatus(paymentNo, "환불");
                        refundMapper.insertPaymentHistory(paymentNo, "환불", amount);
                        return true;
                    } else {
                        log.error("환불 요청은 성공했으나 상태가 '취소됨'이 아닙니다: " + status);
                        throw new RuntimeException("환불 요청 실패: 상태가 '취소됨'이 아님");
                    }
                } else {
                    log.error("API 요청 실패, 코드: " + code + ", 메시지: " + responseBody.get("message"));
                    throw new RuntimeException("환불 처리 실패: API 코드 오류");
                }
            } else {
                log.error("환불 API 요청 실패: 상태 코드 {}, 응답 내용: {}", response.getStatusCode(), response.getBody());
                throw new RuntimeException("환불 API 요청 실패");
            }

        } catch (Exception e) {
            log.error("환불 처리 중 오류 발생: ", e);
            return false;
        }
    }

    // Access Token을 캐싱하여 사용하고, 만료 시 새로운 토큰 발급
    private String getCachedAccessToken() {
        if (cachedAccessToken == null || System.currentTimeMillis() > tokenExpiryTime) {
            cachedAccessToken = getAccessToken();
        }
        return cachedAccessToken;
    }

    private String getAccessToken() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> body = new HashMap<>();
            body.put("imp_key", apiKey);
            log.info("imp_key(apiKey): " + apiKey);
            body.put("imp_secret", apiSecret);
            log.info("imp_secret(apiSecret): " + apiSecret);

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    BASE_URL + "/users/getToken",
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map responseBody = response.getBody();
                Map<String, Object> responseData = (Map<String, Object>) responseBody.get("response");

                if (responseData != null) {
                    cachedAccessToken = (String) responseData.get("access_token");

                    // expires_in 값이 존재하는지 확인하고, 없을 경우 기본값 설정 또는 예외 처리
                    Integer expiresIn = (Integer) responseData.get("expires_in");
                    if (expiresIn != null) {
                        tokenExpiryTime = System.currentTimeMillis() + (expiresIn * 1000L);
                    } else {
                        log.error("expires_in 값이 응답에 없습니다. 기본 만료 시간 설정이 필요합니다.");
                        // 필요시 기본 만료 시간 설정
                        tokenExpiryTime = System.currentTimeMillis() + (60 * 30 * 1000L); // 30분
                    }
                    return cachedAccessToken;
                } else {
                    log.error("Access Token 응답에 'response' 필드가 없습니다.");
                }
            }
        } catch (Exception e) {
            log.error("Access Token 발급 중 오류 발생: ", e);
        }

        return null;
    }
}

