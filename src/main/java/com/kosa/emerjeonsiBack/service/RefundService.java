package com.kosa.emerjeonsiBack.service;

import java.math.BigDecimal;

public interface RefundService {

    public boolean processRefund(String imp_uid, BigDecimal amount, int reservationNo, int paymentNo); //환불
}
