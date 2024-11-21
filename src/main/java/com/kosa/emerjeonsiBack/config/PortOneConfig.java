package com.kosa.emerjeonsiBack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class PortOneConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // 인터셉터 추가하여 인증 헤더 자동 설정
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>(restTemplate.getInterceptors());
        interceptors.add((request, body, execution) -> {
            // 필요 시 토큰을 요청 헤더에 추가
            String accessToken = ""; // 액세스 토큰을 여기에 설정 또는 가져오는 메서드를 호출
            if (accessToken != null && !accessToken.isEmpty()) {
                request.getHeaders().setBearerAuth(accessToken);
            }
            return execution.execute(request, body);
        });

        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}
