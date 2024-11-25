package com.kosa.emerjeonsiBack.mapper;

import com.kosa.emerjeonsiBack.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    public User selectUserByUserId(String userId);

    public Boolean existsByUserId(String userId);

    public int userInsert(User user);

    public int updateUserInfo(User user);

    public void updateUserStatus(@Param("userNo") int userNo, @Param("userStatus") String userStatus);
}
