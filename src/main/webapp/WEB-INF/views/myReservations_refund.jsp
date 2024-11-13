<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <title>환불</title>
    <link rel="stylesheet" href="css/refund.css">
</head>
<body>
<div class="header">
    <button class="back-btn" onclick="goBack()">←</button>
    <span>환불</span>
</div>
<div class="refund-container">
    <div class="refund-header">
        <img src="img/kumho.png" alt="전시 이미지" class="refund-image">
        <div class="refund-info">
            <h3>${reservation.getExhibition().getTitle()}</h3>
            <p>${reservation.getExhibition().getCntcInsttNm()}</p>
            <p>${reservation.getExhibition().getStartPeriod()} ~ ${reservation.getExhibition().getEndPeriod()}</p>
            <p><fmt:formatNumber value="${reservation.getReservationPrice()}" pattern="#,###"/>(원)</p>
        </div>
    </div>

    <div class="section">
        <h4>환불 금액</h4>
        <p><strong>결제 금액</strong><span><fmt:formatNumber value="${reservation.getPayment().getPaymentPrice()}" pattern="#,###"/>(원)</span></p>
        <p><strong>결제 수단</strong><span>${reservation.getPayment().getPaymentMethod()}</span></p>
        <p><strong>결제 일시</strong><span>${paymentDateTime}</span></p>
        <p><strong>최종 환불 금액</strong><span><fmt:formatNumber value="${totalPrice}" pattern="#,###"/>(원)</span></p>
    </div>

    <p class="notice">티켓 유효기간 이후에는 취소 및 환불이 되지 않습니다.</p>
    <button class="refund-btn">환불</button>
</div>
<script src="js/refund.js"></script>

</body>
</html>
