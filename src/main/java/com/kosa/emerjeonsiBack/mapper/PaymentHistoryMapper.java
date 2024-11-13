package com.kosa.emerjeonsiBack.mapper;

import com.kosa.emerjeonsiBack.dto.PaymentHistory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentHistoryMapper {


public void insertPaymentHistory(PaymentHistory paymentHistoryDTO); //결제 이력 등록.


}
