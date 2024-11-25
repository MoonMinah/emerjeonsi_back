<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>전시 상세 페이지</title>

    <link rel="stylesheet" href="/css/exhibitionDetail.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

</head>
<body>

<header class="header">
    <a href="/home"><img src="../img/logo.png" alt="로고" /></a>
<%--    <a href="/user/myPage/myReservations">👤</a>--%>
    <div class="user-menu-container">
        <span class="user-icon" onclick="toggleMenu()">👤</span>
        <ul class="user-menu" id="userMenu">

        </ul>
    </div>
</header>

<!-- 전시상세 배너 -->
<div class="bannerText">전시상세</div>

    <div class="container">
        <div class="banner">
        </div>
    </div>

<!-- Modal Structure -->
<div class="modal" id="reservationModal">
    <div class="modal-close" onclick="closeModal()">✖</div>
    <div class="modal-header">옵션 선택</div>
    <div class="modal-content">
        <!-- 선택사항 UI 내용 -->
        <div>
            <div class="option-row">
                <p>성인 : 5,000 (원)</p>
                <div class="quantity-selector">
                    <button onclick="decrease1()">-</button>
                    <span id="quantity1">0</span>
                    <button onclick="increase1()">+</button>
                </div>
            </div>
            <div class="option-row">
                <p>아이 : 1,000 (원)</p>
                <div class="quantity-selector">
                    <button onclick="decrease2()">-</button>
                    <span id="quantity2">0</span>
                    <button onclick="increase2()">+</button>
                </div>
            </div>
            <div class="option-row">
                <p>노인 : 3,000 (원)</p>
                <div class="quantity-selector">
                    <button onclick="decrease3()">-</button>
                    <span id="quantity3">0</span>
                    <button onclick="increase3()">+</button>
                </div>
            </div>

        <br>
        <p>총 결제 금액: <span id="totalAmount">(원)</span></p>
        <br>
        <button class="button" onclick="submitReservation()">예매하기</button>

        </div>
    </div>
</div>

    <!-- axios 라이브러리 추가 -->
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script src="/js/common/userIconToggle.js"></script>
    <script src="/js/main/exhibitionDetail.js"></script>

</body>
</html>