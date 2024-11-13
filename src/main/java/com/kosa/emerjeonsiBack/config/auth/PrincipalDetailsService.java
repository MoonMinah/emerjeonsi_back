package com.kosa.emerjeonsiBack.config.auth;

import com.kosa.emerjeonsiBack.dto.User;
import com.kosa.emerjeonsiBack.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PrincipalDetailsService implements UserDetailsService {
    private final UserMapper userMapper;

    public PrincipalDetailsService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info("loadUserByUsername() : userId = {}", userId);

        User resultUser = userMapper.selectUserByUserId(userId);
        log.info("loadUserByUsername() : resultUser = {}", resultUser);

        if(resultUser != null) {
            return new PrincipalDetails(resultUser);
        }

        return null;
    }
}
