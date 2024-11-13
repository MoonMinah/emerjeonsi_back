package com.kosa.emerjeonsiBack.dto.social;

public interface OAuth2Response {
    // 제공자(kakao, google, ...)
    String getProvider();

    // 제공자에서 발급해주는 아이디(번호)
    String getProviderId();

    // 이메일
    String getEmail();

    // 사용자 실명(설정된 이름)
    String getName();

    // 사용자 성별
    String getGender();

    // 사용자 생년월일
    String getBirthday();

    // 사용자 핸드폰 번호
    String getPhone();
}
