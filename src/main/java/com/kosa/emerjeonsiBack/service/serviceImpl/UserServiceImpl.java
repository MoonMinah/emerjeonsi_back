package com.kosa.emerjeonsiBack.service.serviceImpl;

import com.kosa.emerjeonsiBack.dto.User;
import com.kosa.emerjeonsiBack.mapper.UserMapper;
import com.kosa.emerjeonsiBack.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User selectUserByUserId(String userId) {
        return userMapper.selectUserByUserId(userId);
    }

    @Override
    public Boolean existsByUserId(String userId) {
        return userMapper.existsByUserId(userId);
    }

    @Override
    public int userInsert(User user) {
        return userMapper.userInsert(user);
    }
}
