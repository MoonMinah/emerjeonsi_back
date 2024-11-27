<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <title>나의 환불 목록</title>
    <link rel="stylesheet" href="/css/myPageRefunds.css" type="text/css">
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>

<!-- 헤더 -->
<header class="header">
    <img src="/img/logo.png" alt="Logo" class="logo" onclick="goToMain()">
    <span class="title">환불 목록</span>
    <div class="user-menu-container">
        <span class="user-icon" onclick="toggleMenu()">👤</span>
        <ul class="user-menu" id="userMenu">
        </ul>
    </div>
</header>

<!-- 환불 목록 -->
<div class="container" id="refunds-container"></div>

<!-- 페이징 버튼 -->
<div id="pagination"></div>
<script>
function goToMain(){
    window.location.href="/home";
}
</script>
<script src="/js/common/userIconToggle.js"></script>
<script type="module" src="/js/myPage/myRefunds.js"></script>
</body>
</html>
