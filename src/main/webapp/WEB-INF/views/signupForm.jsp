<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <link rel="stylesheet" href="/css/signup.css">
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
</head>
<body>
    <div class="signup-container">
        <h2 class="signup-title">회원 가입</h2>
        <form id="signUpForm" action="/api/signup" method="post">
            <input type="hidden" name="role" value="ROLE_USER">
            <input type="text" placeholder="아이디" name="userId" class="input-field" required>
            <input type="password" placeholder="비밀번호" name="password" class="input-field" required>
            <input type="email" placeholder="이메일" name="email" class="input-field" required>
            <input type="text" placeholder="이름" name="userName" class="input-field" required>

            <div class="gender-container">
                <div class="gender-option">
                    <input type="radio" id="male" name="gender" value="남" required>
                    <label for="male">남자</label>
                </div>
                <div class="gender-option">
                    <input type="radio" id="female" name="gender" value="여" required>
                    <label for="female">여자</label>
                </div>
            </div>

            <input type="text" placeholder="생년월일" name="birthday" class="input-field" required>
            <input type="text" placeholder="전화번호" name="phone" class="input-field" required>

            <input type="submit" class="signup-button" value="회원가입">
            <input type="reset" class="cancel-button" value="취소">
        </form>
    </div>

    <script src="/js/signup.js"></script>
</body>
</html>
