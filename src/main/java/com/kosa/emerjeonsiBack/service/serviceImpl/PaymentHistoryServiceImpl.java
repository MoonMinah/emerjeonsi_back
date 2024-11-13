package com.kosa.emerjeonsiBack.service.serviceImpl;

import com.kosa.emerjeonsiBack.dto.PaymentHistory;
import com.kosa.emerjeonsiBack.mapper.PaymentHistoryMapper;
import com.kosa.emerjeonsiBack.service.PaymentHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentHistoryServiceImpl implements PaymentHistoryService {

    @Autowired
    private PaymentHistoryMapper paymentHistoryMapper;

    @Override
    public void insertPaymentHistory(PaymentHistory PaymentHistory) {
        paymentHistoryMapper.insertPaymentHistory(PaymentHistory);
    }
}
