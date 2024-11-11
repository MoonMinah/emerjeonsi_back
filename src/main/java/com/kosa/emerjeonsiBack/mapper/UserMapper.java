package com.kosa.emerjeonsiBack.mapper;

import com.kosa.emerjeonsiBack.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    public User selectUserByUserId(String userId);

    public Boolean existsByUserId(String userId);

    public int userInsert(User user);
}
