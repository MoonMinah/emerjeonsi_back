package com.kosa.emerjeonsiBack.service;

import com.kosa.emerjeonsiBack.dto.User;

public interface UserService {
    public User selectUserByUserId(String userId);

    public Boolean existsByUserId(String userId);

    public int userInsert(User user);
}
