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

        String role = "ROLE_USER";

        CustomOAuth2User customOAuth2User = new CustomOAuth2User(oAuth2Response, role);

        String userId = customOAuth2User.getName();
        User existData = userMapper.selectUserByUserId(userId);

        if(existData == null) {
            User user = new User();
            user.setUserId(userId);
            user.setPassword(bCryptPasswordEncoder.encode("Qwer1234!"));
            user.setEmail(oAuth2Response.getEmail());
            user.setUserName(oAuth2Response.getName());
            user.setGender(oAuth2Response.getGender());
            user.setBirthday(oAuth2Response.getBirthday());
            user.setPhone(oAuth2Response.getPhone());
            user.setProvider(oAuth2Response.getProvider());
            user.setRole(role);

            userMapper.userInsert(user);
        } else {
            role = existData.getRole();
        }

        return customOAuth2User;
    }
}
