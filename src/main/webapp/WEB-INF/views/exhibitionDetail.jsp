<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>전시 상세 페이지</title>

    <link rel="stylesheet" href="/css/exhibitionDetail.css">

</head>
<body>

<header class="header">
    <img src="../img/logo.png" alt="로고" />
    <div class="search-bar">
        <input type="text" placeholder="검색" />
        <span class="filter-icon" onclick="openModal()">⚙️</span>
    </div>
    <a href="/api/myPage">👤</a>
</header>

<%--

<!-- 전시목록 배너 -->
<div class="banner">전시상세</div>

<!-- 최신순과 이름순 버튼 -->
<div class="sort-options">
    <button class="active" onclick="sortBy('latest')">최신순</button>
    <button onclick="sortBy('name')">이름순</button>
</div>
--%>


    <div class="container">
    </div>
        <div class="banner">
        </div>
        <div class="details">
        </div>

<!-- Modal Structure -->
<div class="modal" id="reservationModal">
    <div class="modal-close" onclick="closeModal()">×</div>
    <div class="modal-header">옵션 선택</div>
    <div class="modal-content">
        <!-- 선택사항 UI 내용 -->
        <p>[유효기간: 2024.10.30~2024.11.12]</p>
        <div>
            <p>성인 (5,000원)
                <span class="quantity-selector">

                <button onclick="decrease1()">-</button> <span id="quantity1">0</span> <button onclick="increase1()">+</button>
            </span>
            </p>
            <p>아이 (1,000원)
                <span class="quantity-selector">

                <button onclick="decrease2()">-</button> <span id="quantity2">0</span> <button onclick="increase2()">+</button>
            </span>
            </p>
            <p>노인 (3,000원)
                <span class="quantity-selector">

                <button onclick="decrease3()">-</button> <span id="quantity3">0</span> <button onclick="increase3()">+</button>
            </span>
            </p>
        </div>

        <p>총 결제 금액: <span id="totalAmount">(원)</span></p>

        <button class="button" onclick="submitReservation()">예매하기</button>
    </div>
</div>


</body>

    <!-- axios 라이브러리 추가 -->
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <script src="/js/main/exhibitionDetail.js"></script>


</html>