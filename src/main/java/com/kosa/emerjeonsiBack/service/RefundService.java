package com.kosa.emerjeonsiBack.service;

public interface RefundService {

    public boolean processRefund(String impUid, int amount, String reason); //환불
}
