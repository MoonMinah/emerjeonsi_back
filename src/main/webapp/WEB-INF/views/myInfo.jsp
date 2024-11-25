<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MyInfo</title>
    <link rel="stylesheet" href="/css/myInfo.css">
</head>
<body>
    <header class="header">
        <img src="/img/logo.png" alt="로고" />
        <div class="search-bar">
            <input type="text" placeholder="검색" onclick="checkFilter()"/>
            <span class="filter-icon" onclick="settingModal()">⚙️</span>
        </div>
        <div class="user-menu-container">
            <span class="user-icon" onclick="toggleMenu()">👤</span>
            <ul class="user-menu" id="userMenu">

            </ul>
        </div>
    </header>

    <div class="content-container">
        <div class="myInfo-container">
            <h2 class="myInfo-title">내 정보 수정</h2>
            <form id="myInfoForm" novalidate>
                <input type="hidden" name="role" value="ROLE_USER">
                <input type="text" placeholder="아이디" name="userId" id="userId" class="input-field" readonly disabled required>
                <input type="password" placeholder="비밀번호" name="password" id="password" class="input-field" readonly disabled required>
                <input type="email" placeholder="이메일" name="email" id="email" class="input-field" required>
                <div class="error-message" id="emailError"></div>

                <input type="text" placeholder="이름" name="userName" id="userName" class="input-field" required>
                <div class="error-message" id="userNameError"></div>

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
                <div class="error-message" id="genderError"></div>

                <input type="text" placeholder="생년월일" name="birthday" id="birthday" class="input-field" required>
                <div class="error-message" id="birthdayError"></div>

                <input type="text" placeholder="전화번호" name="phone" id="phone" class="input-field" required>
                <div class="error-message" id="phoneError"></div>

                <input type="submit" class="myInfoUpdate-button" value="수정하기">
                <input type="button" class="delete-account-button" va  lue="탈퇴하기" onclick="confirmDelete()">
                <input type="reset" class="cancel-button" value="취소" onclick="history.back()">
            </form>
        </div>
    </div>

    <!-- 모달 -->
    <div class="modal" id="filterModal">
        <div class="modal-content">
            <span class="close-btn" onclick="closeModal()">✖</span>
            <h2>상세 검색</h2>
            <br>
            <label>
                <input type="radio" name="option" value="title">
                전시제목
            </label>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <label>
                <input type="radio" name="option" value="museum">
                박물관명
            </label>
            <br><br>
            <input type="text" placeholder="검색어" />
            <button onclick="applyFilter()">확인</button>
        </div>
    </div>

    <!-- axios 라이브러리 추가 -->

    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="/js/common/userIconToggle.js"></script>
    <script src="/js/myPage/myInfo.js"></script>
</body>
</html>
