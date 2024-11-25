<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>예매 상세</title>
    <link rel="stylesheet" href="/css/reservationDatail.css">
    <script src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>

<header class="header">
    <img src="/img/logo.png" alt="로고" class="logo">
    <div class="search-bar">
        <input type="text" placeholder="검색">
        <span class="filter-icon" onclick="openModal()">⚙️</span>
    </div>
    <div class="user-menu-container">
        <span class="user-icon" onclick="toggleMenu()">👤</span>
        <ul class="user-menu" id="userMenu">

        </ul>
    </div>
</header>

<div class="banner">예매 상세</div>

<div class="content-container">
    <div class="card">
        <img id="exhibitionImage" src="/img/kumho.png" alt="전시 이미지">
        <div class="card-content">
            <h2 id = "exhibitionTitle">금호강과 길</h2>
            <div class="info-row">
                <div class="info">국립대구박물관<br>2018.6.19 - 2018.9.30</div>
                <div class="price">1,000 - 5,000 (원)</div>
            </div>
        </div>
    </div>

    <!-- 주문 내역 섹션 -->
    <div class="order-summary">
        <h3>상세 주문 내역</h3>
<%--        <p><strong>전시 번호:</strong> <span id="exhibitionNo"></span></p>--%>
        <p><strong>예매 금액:</strong> <span id="reservationPrice"></span></p>
        <p><strong>총 티켓 수량:</strong> <span id="reservationQuantity"></span></p>
<%--        <p><strong>총 금액:</strong> <span id="totalPrice"></span></p>--%>
        <p><strong>선택된 옵션:</strong> <span id="selectedDetails"></span></p>
    </div>

    <h3>결제 수단</h3>
    <div class="custom-select-box">
        <img id="currentPaymentIcon" class="payment-icon" src="/img/payment_icon_yellow_medium.png" alt="">
        <select id="paymentMethodSelect" class="form-control">
            <option value="kakaopay" selected data-icon="/img/payment_icon_yellow_medium.png">카카오페이</option>
<%--            <option value="inicis" disabled data-icon="/img/ci_KG_JPG.jpg">KG이니시스</option>--%>
        </select>
    </div>
    <br>
<%--    <div class="payment-button-container">--%>
<%--        <button id="kakaoPayButton" class="payment-button">--%>
<%--            <img id="kakaoPayIcon" class="payment-icon" src="/img/payment_icon_yellow_medium.png" alt="카카오페이">--%>
<%--            <span class="payment-text">카카오페이</span>--%>
<%--        </button>--%>
<%--    </div>--%>
    <button id="payment" class="btn btn-primary">결제하기</button>

    <p class="notice">전시 시작일로부터는 취소 및 환불이 되지 않습니다.</p>
</div>
<script src="/js/common/userIconToggle.js"></script>
<script src="/js/reservation/reservationDetail.js"></script>
</body>
</html>
