<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>모바일 전시 목록</title>

    <link rel="stylesheet" href="/css/main.css">
</head>
<body>

<header class="header">
    <img src="img/logo.png" alt="로고" />
    <div class="search-bar">
        <input type="text" placeholder="검색" />
        <span class="filter-icon" onclick="openModal()">⚙️</span>
    </div>
    <a href="/api/myPage">👤</a>
</header>

<!-- 전시목록 배너 -->
<div class="banner">전시목록</div>

<!-- 최신순과 이름순 버튼 -->
<div class="sort-options">
    <button class="active" onclick="sortBy('latest')">최신순</button>
    <button onclick="sortBy('name')">이름순</button>
</div>

<!-- 콘텐츠 영역 -->
<div id="exhibitionList" class="content-container">
    <!-- 최신순과 이름순 버튼 -->
    <%--<div class="sort-options">
        <button class="active" onclick="sortBy('latest')">최신순</button>
        <button onclick="sortBy('name')">이름순</button>
    </div>--%>

    <!-- 전시 카드 -->
    <div class="card">

    </div>
    <!-- 추가 전시 카드들 -->
</div>

<!-- 모달 -->
<div class="modal" id="filterModal">
    <div class="modal-content">
        <span class="close-btn" onclick="closeModal()">✖</span>
        <h2>상세 검색</h2>
        <p>필터 옵션을 선택하세요.</p>
        <!-- 필터 옵션 추가 -->
    </div>
</div>

<!-- axios 라이브러리 추가 -->
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script src="/js/main/main.js"></script>

</body>
</html>