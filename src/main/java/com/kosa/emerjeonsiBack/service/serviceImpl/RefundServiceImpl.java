package com.kosa.emerjeonsiBack.service.serviceImpl;

import com.kosa.emerjeonsiBack.service.RefundService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class RefundServiceImpl implements RefundService {
    private static final String API_KEY = "0000";  // PortOne에서 발급받은 API Key
    private static final String API_SECRET = "0000";  // PortOne에서 발급받은 API Secret
    private static final String BASE_URL = "https://api.iamport.kr";

//    private final RestTemplate restTemplate;

//    public RefundServiceImpl(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }


    @Override
    public boolean processRefund(String impUid, int amount, String reason) {
        try {
            // 1. Access Token 발급
            String accessToken = getAccessToken();

            // 2. Access Token이 발급되지 않으면 실패
            if (accessToken == null) {
                throw new RuntimeException("Access Token 발급 실패");
            }

            // 3. 환불 요청 데이터 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);

            Map<String, Object> body = new HashMap<>();
            body.put("imp_uid", impUid); // 결제 고유 ID
            body.put("amount", amount); // 환불할 금액
            body.put("reason", reason); // 환불 사유

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            // 4. 환불 요청 보내기
//            ResponseEntity<Map> response = restTemplate.exchange(
//                    BASE_URL + "/payments/cancel",
//                    HttpMethod.POST,
//                    entity,
//                    Map.class
//            );

            // 5. 환불 요청 성공 여부 확인
//            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
//                Map responseBody = response.getBody();
//                boolean success = (boolean) responseBody.get("success");
//                return success;  // 성공 여부 반환
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; // 실패 시 false 반환
    }

    private String getAccessToken() {
        try {
            // Access Token 요청 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> body = new HashMap<>();
            body.put("imp_key", API_KEY);
            body.put("imp_secret", API_SECRET);

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

            // Access Token 요청 보내기
//            ResponseEntity<Map> response = restTemplate.exchange(
//                    BASE_URL + "/users/getToken",
//                    HttpMethod.POST,
//                    entity,
//                    Map.class
//            );

            // Access Token 추출
//            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
//                Map responseBody = response.getBody();
//                Map<String, Object> responseData = (Map<String, Object>) responseBody.get("response");
//                return (String) responseData.get("access_token");
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // 실패 시 null 반환
    }
}
