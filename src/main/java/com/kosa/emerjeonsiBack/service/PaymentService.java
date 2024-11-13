package com.kosa.emerjeonsiBack.service;


import com.kosa.emerjeonsiBack.dto.Payment;
import com.kosa.emerjeonsiBack.dto.Reservation;

public interface PaymentService {
    public int insertPayment(Payment payment);
    public void reserveAndPay(Reservation reservation, Payment payment); //예매, 결제 등록.ㄴ
}
