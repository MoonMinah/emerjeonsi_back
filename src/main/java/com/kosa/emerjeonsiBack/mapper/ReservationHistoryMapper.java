package com.kosa.emerjeonsiBack.mapper;

import com.kosa.emerjeonsiBack.dto.ReservationHistory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReservationHistoryMapper {
     public int insertReservationHistory(ReservationHistory reservationHistory);
     public ReservationHistory updateReservationHistoryStatus(int reservationHistoryNo, String status);


}
