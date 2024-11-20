package com.kosa.emerjeonsiBack.service.serviceImpl;

import com.kosa.emerjeonsiBack.dto.Reservation;
import com.kosa.emerjeonsiBack.dto.ReservationHistory;
import com.kosa.emerjeonsiBack.mapper.ReservationHistoryMapper;
import com.kosa.emerjeonsiBack.mapper.ReservationMapper;
import com.kosa.emerjeonsiBack.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationMapper reservationMapper;
    @Autowired
    private ReservationHistoryMapper reservationHistoryMapper;

    /**
     * 예매 등록
     * @param reservation
     */
    @Override
    @Transactional
    public void createReservation(Reservation reservation) {
         reservationMapper.insertReservation(reservation);
         ReservationHistory reservationHistory = new ReservationHistory();
         reservationHistory.setReservationNo(reservation.getReservationNo());
         reservationHistory.setReservationStatus("결제대기");
         reservationHistory.setReservationEventTimeStamp(LocalDateTime.now());
         reservationHistoryMapper.insertReservationHistory(reservationHistory);
    }


    /**
     * 서비스 - 결제 상세
     * @param reservationNo
     * @return
     */
    @Override
    public Reservation getReservationDetail(int reservationNo) {
        return reservationMapper.getReservationDetail(reservationNo);
    }

    /**
     * 예매 대기 후 결제 시 예매 상태 변경
     * @param reservationNo
     * @param reservationStatus
     * @return
     */
    @Override
    public int updateReservationStatus(int reservationNo, String reservationStatus) {
        return reservationMapper.updateReservationStatus(reservationNo, reservationStatus);
    }

    /**
     * 예매 목록 조회
     * @param userNo
     * @return
     */
    @Override
    public List<Reservation> getReservationsByUserNo(int userNo, int offset, int size) {
        log.info("Fetching reservations for userNo: {}, offset: {}, size: {}", userNo, offset, size);
        return reservationMapper.getReservationsByUserNo(userNo, offset, size);
    }

    //사용자 번호에 따른 예약 목록 총 개수 조회
    @Override
    public int countReservationsByUserNo(int userNo) {
        return reservationMapper.countReservationsByUserNo(userNo);
    }

    @Override
    public int calculateTotalPages(int totalCount, int pageSize) {
        if (pageSize <= 0) {
            throw new IllegalArgumentException("Page size must be greater than 0");
        }
        return (int) Math.ceil((double) totalCount / pageSize);
    }


    /**
     * 나의 예매 목록 - 상세 내역
     * @param reservationId
     * @return
     */
    @Override
    public Reservation getMyReservationDetail(int reservationId) {
        return reservationMapper.getMyReservationDetail(reservationId);
    }

    /**
     * 나의 예매 목록 - 결제 내역
     * @param PaymentNo
     * @return
     */
    @Override
    public Reservation getMyReservationPaymentDetail(int PaymentNo) {
        return reservationMapper.getMyReservationPaymentDetail(PaymentNo);
    }
}
