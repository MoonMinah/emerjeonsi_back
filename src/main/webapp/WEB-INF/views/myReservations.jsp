<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <title>나의 예매 목록</title>
    <link rel="stylesheet" href="/css/myReservations.css" type="text/css">
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
</head>
<body>

<!-- 헤더 -->
<header class="header">
    <img src="/img/logo.png" alt="Logo" class="logo" onclick="goToMain()">
    <span class="title">예매 목록</span>
    <div class="user-menu-container">
        <span class="user-icon" onclick="toggleMenu()">👤</span>
        <ul class="user-menu" id="userMenu">
            <li onclick="goToUpdateInfo()">정보 수정</li>
            <li onclick="goToReservations()">예매 내역</li>
            <li onclick="goToRefunds()">환불 내역</li>
            <li onclick="logout()">로그아웃</li>
        </ul>
    </div>
</header>


<!-- 배너 -->
<%--<div id="banner">--%>
<%--    <button id="btnMyReservations" class="active" onclick="navigateTo('myReservations')">나의 예매 목록</button>--%>
<%--    <button id="btnRefundHistory" onclick="navigateTo('refundHistory')">나의 환불 내역</button>--%>
<%--    <button id="btnEditInfo" onclick="navigateTo('editInfo')">정보 수정</button>--%>
<%--</div>--%>

<!-- 예매 목록 -->
<div class="container" id="reservation-container"></div>

<!-- 페이징 버튼 -->
<div id="pagination"></div>
<script>
    function goToMain() {
        window.location.href = '/home'; // 메인 페이지의 URL로 수정하세요.
    }
    // 유저 메뉴 보이기/숨기기 토글
    function toggleMenu() {
        const menu = document.getElementById('userMenu');
        menu.style.display = menu.style.display === 'block' ? 'none' : 'block';
    }

    // 예시 함수들
    function goToReservations() {
        alert('예매 목록 페이지로 이동합니다.');
        window.location.href = '/user/myPage/myReservations';
    }

    function goToRefunds() {
        alert('환불 내역페이지로 이동합니다.');
    }

    function goToUpdateInfo() {
        alert('정보 수정페이지로 이동합니다.');
    }

    function logout() {
        alert('로그아웃되었습니다.');
        window.location.href = '/logout';
    }
</script>
<script type="module" src="/js/myPage/myReservations.js"></script>
</body>

<%--<script src="/js/myPage/myReservationDetails.js"></script>--%>

</html>
