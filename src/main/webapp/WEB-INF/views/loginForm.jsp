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
            <img src="https://upload.wikimedia.org/wikipedia/commons/5/53/Google_%22G%22_Logo.svg" alt="Google logo" class="icon">
            구글 아이디로 로그인
        </button>
        <button class="social-button kakao-login" onclick="window.location.href='/oauth2/authorization/kakao'">
            <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/8/82/Kakao_icon_%28square%29.svg/1024px-Kakao_icon_%28square%29.svg.png" alt="Kakao logo" class="icon">
            카카오 아이디로 로그인
        </button>
    </div>

    <script src="/js/login.js"></script>
</body>
</html>
