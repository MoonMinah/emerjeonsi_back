package com.kosa.emerjeonsiBack.dto.social;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class KakaoResponse implements OAuth2Response {
    private final Map<String, Object> attribute;
    private final Map<String, Object> attributeAccount;
    private final Map<String, Object> attributeProfile;

    public KakaoResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
        this.attributeAccount = (Map<String, Object>) attribute.get("kakao_account");
        this.attributeProfile = (Map<String, Object>) attributeAccount.get("profile");
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return attributeAccount.get("email").toString();
    }

    @Override
    public String getName() {
        return attributeProfile.get("nickname").toString();
    }

    public String getBirthday() {
//        String birthday = attributeAccount.get("birthyear").toString() + attributeAccount.get("birthday").toString();
//        log.info("getBirthday() : birthday = {}", birthday);
//
//        return birthday;

        // 사용자가 생년월일에 대해 정보 제공을 하지 않을 경우, NULL값 처리를 위한 Default 처리
        String birthyear = attributeAccount.getOrDefault("birthyear", "2000").toString();
        String birthday = attributeAccount.getOrDefault("birthday", "0101").toString();
        String fullBirthday = birthyear + birthday;
        log.info("getBirthday() : fullBirthday = {}", fullBirthday);

        return fullBirthday;
    }

    public String getGender() {
//        String gender = "";
//
//        if(attributeAccount.get("gender").toString().equals("male")) {
//            gender = "남";
//        } else if(attributeAccount.get("gender").toString().equals("female")) {
//            gender = "여";
//        }
//
//        return gender;

        // 사용자가 성별에 대해 정보 제공을 하지 않을 경우, NULL값 처리를 위한 Default 처리
        String gender = attributeAccount.getOrDefault("gender", "unknown").toString();

        if("male".equals(gender)) {
            return "남";
        } else if("female".equals(gender)) {
            return "여";
        }

        return "N/A";
    }

    public String getPhone() {
//        String phone = attributeAccount.get("phone_number").toString().replace("+82 ", "0");
//
//        return phone;

        // 사용자가 핸드폰 번호에 대해 정보 제공을 하지 않을 경우, NULL값 처리를 위한 Default 처리
        return attributeAccount.getOrDefault("phone_number", "000-0000-0000").toString().replace("+82 ", "0");
    }
}
