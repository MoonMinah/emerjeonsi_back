<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <title>환불</title>
    <link rel="stylesheet" href="/css/refund.css">
</head>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<body>
<div class="header">
    <button class="back-btn" onclick="goBack()">←</button>
    <span>환불</span>
</div>
<div class="refund-container">
    <div class="refund-header">
        <img id="refund-image" src="img/default.png" alt="전시 이미지" class="refund-image">
        <div class="refund-info">
            <h3 id="exhibition-title">제목 없음</h3>
            <p id="exhibition-institution">기관 정보 없음</p>
            <p id="exhibition-period">기간 없음</p>
            <p id="reservation-price">예매 금액 없음</p>
        </div>
    </div>

    <div class="section">
        <h4>환불 금액</h4>
        <p><strong>결제 금액:</strong> <span id="payment-price">-</span></p>
        <p><strong>결제 수단:</strong> <span id="payment-method">-</span></p>
        <p><strong>결제 일시:</strong> <span id="payment-datetime">-</span></p>
        <p><strong>최종 환불 금액:</strong> <span id="refund-amount">-</span></p>
    </div>
    <p class="notice">티켓 유효기간 이후에는 취소 및 환불이 되지 않습니다.</p>
    <button class="refund-btn" id="refund-btn">환불</button>
</div>
<script src="/js/refund.js"></script>
</body>
</html>
