package com.kosa.emerjeonsiBack.dto;

import lombok.Data;

@Data
public class Payments {
    private int paymentId; //결제 분류 번호
    private String ticketName; // 옵션
    private int price; //금액
    private int quantity; //티켓 수량

    public Payments(int paymentId, String ticketName, int price, int quantity) {
        this.paymentId = paymentId;
        this.ticketName = ticketName;
        this.price = price;
        this.quantity = quantity;
    }
}
