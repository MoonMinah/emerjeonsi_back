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
        String birthday = attributeAccount.get("birthyear").toString() + attributeAccount.get("birthday").toString();
        log.info("getBirthday() : birthday = {}", birthday);

        return birthday;
    }

    public String getGender() {
        String gender = "";

        if(attributeAccount.get("gender").toString().equals("male")) {
            gender = "남";
        } else if(attributeAccount.get("gender").toString().equals("female")) {
            gender = "여";
        }

        return gender;
    }

    public String getPhone() {
        String phone = attributeAccount.get("phone_number").toString().replace("+82 ", "0");

        return phone;
    }
}
