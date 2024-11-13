package com.kosa.emerjeonsiBack.controller.admin;

import com.kosa.emerjeonsiBack.dto.Exhibition;
import com.kosa.emerjeonsiBack.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:9400")
@Slf4j
public class PublicDataRestController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RestTemplate restTemplate;

//    @GetMapping("/admin")
//    public void fetchOpenApiData() {
//        String apiUrl = "http://api.kcisa.kr/openapi/API_CCA_145/request";
//        String serviceKey = "5013479c-edf8-46cd-8bf4-5ad7d7416898"; // 서비스키
//        String numOfRows = "10"; // 세션당 요청레코드수
//        String pageNo = "1"; // 페이지수
//
//        String url = apiUrl + "?" + "serviceKey=" + serviceKey + "&numOfRows=" + numOfRows + "&pageNo=" + pageNo;
//
//        try {
//            // RestTemplate으로 API 호출
//            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//            log.info("API Response: {}", response.getBody());
//
//            // JSON 문자열을 Map으로 변환
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, Object> result = objectMapper.readValue(response.getBody(), Map.class);
//            log.info("result: {}", result);
//
//            Map<String, Object> result2 = (Map<String, Object>)result.get("response");
//            log.info("\n\nresult2(response): {}", result2 );
//
//            Map<String, Object> result3 = (Map<String, Object>)result2.get("body");
//            log.info("\n\nresult3(body): {}", result3);
//
//            log.info("\n\n=======================================================\n\n" );
//
//            Map<String, Object> result4 = (Map<String, Object>)result3.get("items");
//            log.info("\n\nresult4(items): {}", result4.get("item"));
//
//            log.info("\n\n=======================================================\n\n" );
//            log.info("result4.get(\"item\") type : {}", result4.get("item").getClass().getName() );
//
//
//
//            log.info("\n\n=======================================================\n\n" );
//
//            ArrayList<Object> result5 = (ArrayList<Object>)result4.get("item");
//            log.info("\n\nresult5(item): {}", result5);
//
//            log.info("\n\n/////////////////////////////////////////////////////////////\n\n" );
//            for(int i =0; i < result5.size(); i++){
//                log.info("\n\n" + result5.get(i).toString());
//                log.info("\n\n---------------------------------------------------------------\n\n" );
//                String[] array = result5.get(i).toString().split(", ");
//                for(int j = 0; j < array.length; j++){
//                    log.info("\n" + array[j]);
//                }
//
//                log.info("\n\n=======================================================\n\n" );
//            }
//
////            return objectMapper.readValue(response.getBody(), Map.class);
//            return result;
//        } catch (IOException e) {
//            log.error("Error parsing JSON data", e);
//            throw new RuntimeException("Failed to parse JSON data: " + e.getMessage());
//        } catch (Exception e) {
//            log.error("Error fetching data from API", e);
//            throw new RuntimeException("Failed to fetch data from API: " + e.getMessage());
//        }
//    }

    @GetMapping("/admin/data")
    public ResponseEntity<String> save() {
        String serviceKey = "5013479c-edf8-46cd-8bf4-5ad7d7416898";  // 서비스 키는 빈 문자열로 남겨둡니다.
        String url = "http://api.kcisa.kr/openapi/API_CCA_145/request";

        // URL 생성
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("serviceKey", serviceKey)
                .queryParam("resultType", "json")
                .queryParam("numOfRows", "100")
                .queryParam("pageNo", "1")
                .build(true)
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        String responseString;

        try {
            // API 요청
            responseString = restTemplate.getForObject(uri, String.class);

            // 응답 확인: JSON 형태인지 HTML로 시작하는지 확인
            if (responseString != null && responseString.trim().startsWith("<")) {
                throw new IOException("Received HTML response instead of JSON. Check API URL or parameters.");
            }

            // JSON 파싱 시도
            JSONObject jsonObject;
            try {
                jsonObject = (JSONObject) new org.json.simple.parser.JSONParser().parse(responseString);
            } catch (Exception e) {
                throw new IOException("Failed to parse JSON response. The response might not be in JSON format.");
            }

            // JSON에서 원하는 데이터 추출
            JSONObject response = (JSONObject) jsonObject.get("response");
            JSONObject body = (JSONObject) response.get("body");
            JSONObject items = (JSONObject) body.get("items");  // 'items'는 JSONObject일 수 있음
            JSONArray itemArray = (JSONArray) items.get("item");  // 'item'은 JSONArray여야 함

            for (Object obj : itemArray) {
                JSONObject item = (JSONObject) obj;  // 각 객체에 접근
                /*System.out.println("전시명: " + item.get("TITLE"));
                System.out.println("연계기관명: " + item.get("CNTC_INSTT_NM"));
                System.out.println("수집일: " + item.get("COLLECTED_DATE"));
                System.out.println("자료생성일자: " + item.get("ISSUED_DATE"));
                System.out.println("소개(설명): " + item.get("DESCRIPTION"));
                System.out.println("이미지주소: " + item.get("IMAGE_OBJECT"));
                System.out.println("전시ID: " + item.get("LOCAL_ID"));
                System.out.println("홈페이지주소: " + item.get("URL"));
                System.out.println("조회수: " + item.get("VIEW_COUNT"));
                System.out.println("좌석정보: " + item.get("SUB_DESCRIPTION"));
                System.out.println("예매안내: " + item.get("SPATIAL_COVERAGE"));
                System.out.println("장소: " + item.get("EVENT_SITE"));
                System.out.println("장르: " + item.get("GENRE"));
                System.out.println("관람시간: " + item.get("DURATION"));
                System.out.println("전시품(수)정보: " + item.get("NUMBER_PAGES"));
                System.out.println("안내 및 유의사항: " + item.get("TABLE_OF_CONTENTS"));
                System.out.println("작가: " + item.get("AUTHOR"));
                System.out.println("문의: " + item.get("CONTACT_POINT"));
                System.out.println("출연진및제작진: " + item.get("ACTOR"));
                System.out.println("주최/후원: " + item.get("CONTRIBUTOR"));
                System.out.println("연령: " + item.get("AUDIENCE"));
                System.out.println("관람료 할인정보: " + item.get("CHARGE"));
                System.out.println("기간: " + item.get("PERIOD"));
                System.out.println("시간: " + item.get("EVENT_PERIOD"));*/

            }
            return ResponseEntity.ok(responseString);

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            return ResponseEntity.status(500).body("API 응답 형식 오류: JSON 대신 HTML이 수신되었습니다.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return ResponseEntity.status(500).body("예기치 않은 오류 발생: " + e.getMessage());
        }
    }

    @PostMapping("/admin/saveExhibitions")
    public String saveExhibitions(@RequestBody Map<String, Object> data) {
        List<Exhibition> exhibitions = (List<Exhibition>) data.get("exhibitions"); // "exhibitions" 키에 맞게 조정
        if (exhibitions != null && !exhibitions.isEmpty()) {
            adminService.saveExhibitions(exhibitions);
            return "데이터 저장 완료";
        } else {
            return "저장할 데이터가 없습니다.";
        }
    }
}