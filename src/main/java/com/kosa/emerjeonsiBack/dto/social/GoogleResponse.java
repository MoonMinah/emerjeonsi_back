package com.kosa.emerjeonsiBack.dto.social;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class GoogleResponse implements OAuth2Response {
    private final Map<String, Object> attribute;

    public GoogleResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attribute.get("sub").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }

    @Override
    public String getGender() {
        // 사용자가 성별에 대해 정보 제공을 하지 않을 경우, NULL값 처리를 위한 Default 처리
        String gender = attribute.getOrDefault("gender", "unknown").toString();

        if("male".equals(gender)) {
            return "남";
        } else if("female".equals(gender)) {
            return "여";
        }

        return "N/A";
    }

    @Override
    public String getBirthday() {
        // 사용자가 생년월일에 대해 정보 제공을 하지 않을 경우, NULL값 처리를 위한 Default 처리
        String birthyear = attribute.getOrDefault("birthyear", "2000").toString();
        String birthday = attribute.getOrDefault("birthday", "0101").toString();
        String fullBirthday = birthyear + birthday;
        log.info("getBirthday() : fullBirthday = {}", fullBirthday);

        return fullBirthday;
    }

    @Override
    public String getPhone() {
        // 사용자가 핸드폰 번호에 대해 정보 제공을 하지 않을 경우, NULL값 처리를 위한 Default 처리
        return attribute.getOrDefault("phone_number", "000-0000-0000").toString().replace("+82 ", "0");
    }
}
