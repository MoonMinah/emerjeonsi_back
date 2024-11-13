package com.kosa.emerjeonsiBack.mapper;


import com.kosa.emerjeonsiBack.dto.Payment;
import com.kosa.emerjeonsiBack.dto.Reservation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {

    public  int insertPayment(Payment payment); //결제 등록.
    public void insertReservationAndPayment(Reservation reservation, Payment payment);
}
