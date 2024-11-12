package com.kosa.emerjeonsiBack.service;

import com.kosa.emerjeonsiBack.dto.User;
import com.kosa.emerjeonsiBack.dto.social.CustomOAuth2User;
import com.kosa.emerjeonsiBack.dto.social.GoogleResponse;
import com.kosa.emerjeonsiBack.dto.social.KakaoResponse;
import com.kosa.emerjeonsiBack.dto.social.OAuth2Response;
import com.kosa.emerjeonsiBack.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public CustomOAuth2UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("OAuth2User = {}", oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("registrationId = {}", registrationId);

        OAuth2Response oAuth2Response = null;

        if(registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else if(registrationId.equals("kakao")) {
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        String userId = oAuth2Response.getProvider() + "_" + oAuth2Response.getProviderId();
        User existData = userMapper.selectUserByUserId(userId);

        String role = "ROLE_USER";

        if(existData == null) {
            User user = new User();
            user.setUserId(userId);
            user.setPassword(bCryptPasswordEncoder.encode("Qwer1234!"));
            user.setEmail(oAuth2Response.getEmail());
            user.setUserName(oAuth2Response.getName());
            user.setProvider(oAuth2Response.getProvider());
            user.setRole(role);

            if(registrationId.equals("kakao")) {
                // OAuth2Response를 KakaoResponse로 캐스팅
                KakaoResponse kakaoResponse = (KakaoResponse) oAuth2Response;
                user.setGender(kakaoResponse.getGender());
                user.setBirthday(kakaoResponse.getBirthday());
                user.setPhone(kakaoResponse.getPhone());
            }

            userMapper.userInsert(user);
        } else {
            role = existData.getRole();
        }

        return new CustomOAuth2User(oAuth2Response, role);
    }
}
