<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>모바일 전시 목록</title>
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>
    <header class="header">
        <img src="img/logo.png" alt="" />
        <div class="search-bar">
            <input type="text" placeholder="검색" />
        </div>
    </header>

    <div class="menu">
        <div class="active">전시</div>
        <div>마이페이지</div>
        <div>Playgrounds</div>
        <div>별점순</div>
        <div>이름순</div>
    </div>

    <div class="content">
        <div class="card">
            <a href="/exhibitionDetail1">
            <img src="img/kumho.png" alt="금호강과 길">
            <div class="card-content">
                <h2>금호강과 길</h2>
                <div class="info">국립대구박물관<br>2018.6.19 - 2018.9.30</div>
                <div class="price">1,000 - 5,000 (원)</div>
                <div class="rating">★ 5.0</div>
            </div>
            </a>
        </div>

        <div class="card">
            <img src="exhibit2.png" alt="행복한 해방">
            <div class="card-content">
                <h2>행복한 해방</h2>
                <div class="info">국립대구박물관<br>2018.10.1 - 2018.12.31</div>
                <div class="price">무료</div>
                <div class="rating">★ 4.8</div>
            </div>
        </div>

        <div class="card">
            <img src="exhibit2.png" alt="행복한 해방">
            <div class="card-content">
                <h2>행복한 해방</h2>
                <div class="info">국립대구박물관<br>2018.10.1 - 2018.12.31</div>
                <div class="price">무료</div>
                <div class="rating">★ 4.8</div>
            </div>
        </div>

        <div class="card">
            <img src="exhibit2.png" alt="행복한 해방">
            <div class="card-content">
                <h2>행복한 해방</h2>
                <div class="info">국립대구박물관<br>2018.10.1 - 2018.12.31</div>
                <div class="price">무료</div>
                <div class="rating">★ 4.8</div>
            </div>
        </div>

        <div class="card">
            <img src="exhibit2.png" alt="행복한 해방">
            <div class="card-content">
                <h2>행복한 해방</h2>
                <div class="info">국립대구박물관<br>2018.10.1 - 2018.12.31</div>
                <div class="price">무료</div>
                <div class="rating">★ 4.8</div>
            </div>
        </div>

        <div class="card">
            <img src="exhibit2.png" alt="행복한 해방">
            <div class="card-content">
                <h2>행복한 해방</h2>
                <div class="info">국립대구박물관<br>2018.10.1 - 2018.12.31</div>
                <div class="price">무료</div>
                <div class="rating">★ 4.8</div>
            </div>
        </div>

        <!-- 추가 카드들 -->
    </div>
</body>
</html>
