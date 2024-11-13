package com.kosa.emerjeonsiBack.dto;

import lombok.Data;



@Data
public class ReservationPayment {

    private Reservation reservation;
    private Payment payment;
}
