<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <title>결제 내역</title>
    <link rel="stylesheet" href="css/myReservations_PaymentDetails.css">
</head>
<body>
<div class="header">
    <button class="back-btn" onclick="goBack()">←</button>
    <span>결제 내역</span>
</div>
<div class="payment-container">
    <div class="payment-header">
        <img src="img/kumho.png" alt="전시 이미지" class="payment-image">
        <div class="payment-info">
            <h3>${reservation.getExhibition().getTitle()}</h3>
            <p>${reservation.getExhibition().getCntcInsttNm()}</p>
            <p>${reservation.getExhibition().getStartPeriod()} ~ ${reservation.getExhibition().getEndPeriod()}</p>
            <p><fmt:formatNumber value="${totalPrice}" pattern="#,###"/>(원)</p>
        </div>
    </div>
    <div class="section">
        <h4>결제 내역</h4>
        <p><strong>결제 금액</strong><br><fmt:formatNumber value="${reservation.getPayment().getPaymentPrice()}" pattern="#,###"/>(원)</p>
        <p><strong>결제 수단</strong><br>${reservation.getPayment().getPaymentMethod()}</p>
        <p><strong>결제 일시</strong><br>${paymentDateTime}</p>
        <p><strong>환불 기한</strong><br>티켓 유효기간 전까지 신청 가능</p>
    </div>
    <div class="total-amount">
        <h4>총 금액</h4>
        <c:choose>
            <c:when test="${reservation.getReservationPrice() == 5000}">
                <p>티켓(성인): <span><fmt:formatNumber value="${reservation.getReservationPrice()}" pattern="#,###"/>(원)</span></p>
            </c:when>
            <c:when test="${reservation.getReservationPrice() == 3000}">
                <p>티켓(노인): <span><fmt:formatNumber value="${reservation.getReservationPrice()}" pattern="#,###"/>(원)</span></p>
            </c:when>
            <c:when test="${reservation.getReservationPrice() == 1000}">
                <p>티켓(아동): <span><fmt:formatNumber value="${reservation.getReservationPrice()}" pattern="#,###"/>(원)</span></p>
            </c:when>
        </c:choose>
        <p><strong>티켓 수량: ${reservation.getReservationQuantity()}</strong></p>
        <p><strong>총 금액: <fmt:formatNumber value="${totalPrice}" pattern="#,###"/>(원)</strong></p>
    </div>
    <p class="notice">티켓 유효기간 이후에는 취소 및 환불이 되지 않습니다.</p>
    <button class="refund-btn" onclick="refund()">환불 진행</button>
</div>
<script src="js/myReservations_PaymentDetails.js"></script>
<script>
    const paymentId = "${paymentNo}";
   function refund(){
       window.location.href="/reservationRefund?paymentNo=" + paymentId;
   }
</script>
</body>
</html>
