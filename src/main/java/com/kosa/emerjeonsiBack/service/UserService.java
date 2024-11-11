package com.kosa.emerjeonsiBack.service;

import com.kosa.emerjeonsiBack.dto.User;

public interface UserService {
    public Boolean existsByUserId(String userId);

    public int userInsert(User user);
}
