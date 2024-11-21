<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인</title>
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="stylesheet" href="/css/login.css">
</head>
<body>
    <div id="login-container">
        <h1 class="title">로그인</h1>
        <form id="loginForm" action="/login" method="post">
            <input type="text" placeholder="아이디" name="userId" class="input-field" required>
            <input type="password" placeholder="비밀번호" name="password" class="input-field" required>
            <input type="submit" class="login-button" value="로그인">
        </form>

        <a href="/signup" class="signup-link">회원 가입</a>
        <p class="separator">or</p>

        <button class="social-button google-login" onclick="window.location.href='/oauth2/authorization/google'">
            <img src="/img/social/google_login.png" alt="Google logo" class="icon">
            구글 아이디로 로그인
        </button>
<%--        <img src="/img/social/kakao_login_medium_wide.png" alt="Kakao logo" onclick="window.location.href='/oauth2/authorization/kakao'">--%>
        <button class="social-button kakao-login" onclick="window.location.href='/oauth2/authorization/kakao'">
            <img src="/img/social/kakao_login.png" alt="Kakao logo" class="icon">
            카카오 아이디로 로그인
        </button>
    </div>

    <script src="/js/login.js"></script>
</body>
</html>