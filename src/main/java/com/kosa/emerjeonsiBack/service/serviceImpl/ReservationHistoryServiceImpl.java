package com.kosa.emerjeonsiBack.service.serviceImpl;

import com.kosa.emerjeonsiBack.dto.ReservationHistory;
import com.kosa.emerjeonsiBack.mapper.ReservationHistoryMapper;
import com.kosa.emerjeonsiBack.service.ReservationHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReservationHistoryServiceImpl  implements ReservationHistoryService {

    @Autowired
    private ReservationHistoryMapper reservationHistoryMapper;

    @Override
    public int insertReservationHistory(ReservationHistory reservationHistory) {
        return reservationHistoryMapper.insertReservationHistory(reservationHistory);
    }
}
