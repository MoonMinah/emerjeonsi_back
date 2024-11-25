package com.kosa.emerjeonsiBack.service;

import com.kosa.emerjeonsiBack.dto.Reservation;

import java.math.BigDecimal;
import java.util.List;

public interface RefundService {

    public boolean processRefund(String imp_uid, BigDecimal amount, int reservationNo, int paymentNo); //환불

    public List<Reservation> getRefundsByUserNo(int userNo, int offset, int size); //환불 목록

    public int countRefundsByUserNo(int userNo); //사용자 번호에 따른 환불 목록 총 개수 조회

    int calculateTotalPages(int totalCount, int pageSize);

    public Reservation getMyRefundsDetail(int paymentNo); //환불 목록의 상세내역

}
