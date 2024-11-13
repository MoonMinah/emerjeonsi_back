<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <title>상세 내역</title>
    <link rel="stylesheet" href="css/myReservations_PaymentDetails.css">
</head>
<body>
<div class="header">
    <button class="back-btn" onclick="goBack()">←</button>
    <span>상세 내역</span>
</div>
<div class="detail-container">
    <div class="detail-header">
        <img src="img/kumho.png" alt="전시 이미지" class="detail-image">
        <div class="detail-info">
            <h3>${reservation.getExhibition().getTitle()}</h3>
            <p>${reservation.getExhibition().getCntcInsttNm()}</p>
            <p>${reservation.getExhibition().getStartPeriod()} ~ ${reservation.getExhibition().getEndPeriod()}</p>
            <p><fmt:formatNumber value="${reservation.getReservationPrice()}" pattern="#,###"/>(원)</p>

        </div>
    </div>
    <div class="section">
        <h4>상세주문내역</h4>
        <p><strong>날짜</strong><br>${reservation.getExhibition().getStartPeriod()} ~ ${reservation.getExhibition().getEndPeriod()}</p>
        <p><strong>장소</strong><br>${reservation.getExhibition().getEventSite()}</p>
        <p><strong>옵션선택</strong><br><span class="option">[유효기간:2024.10.30~2024.11.12]</span></p>
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

    <p class="notice">전시 시작일로부터는 취소 및 환불이 되지 않습니다.</p>
</div>
<script src="js/myReservations_PaymentDetail.js"></script>
</body>
</html>
