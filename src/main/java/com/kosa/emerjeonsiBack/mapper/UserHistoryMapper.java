package com.kosa.emerjeonsiBack.mapper;

import com.kosa.emerjeonsiBack.dto.UserHistory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserHistoryMapper {
    public int insertUserHistory(UserHistory userHistory);
}
