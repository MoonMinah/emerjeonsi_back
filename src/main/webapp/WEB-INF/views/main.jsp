<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>모바일 전시 목록</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
            overflow: hidden; /* 전체 화면에서 스크롤 바 제거 */
        }
        .header {
            display: flex;
            align-items: center;
            padding: 10px;
            background-color: #fff;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        .header img {
            width: 30px;
            margin-right: 10px;
        }
        .search-bar {
            flex: 1;
            display: flex;
            align-items: center;
            padding: 5px 10px;
            background-color: #f2f2f2;
            border-radius: 20px;
        }
        .search-bar input {
            border: none;
            background: none;
            outline: none;
            width: 100%;
            font-size: 14px;
        }
        .menu {
            display: flex;
            justify-content: space-around;
            padding: 10px;
            background-color: #fff;
            border-top: 1px solid #e0e0e0;
        }
        .menu div {
            text-align: center;
            font-size: 12px;
            color: #555;
        }
        .menu .active {
            color: #ff5e5e;
        }
        .content {
            height: calc(100vh - 130px); /* 전체 화면 높이에서 헤더와 메뉴 높이 뺌 */
            overflow-y: scroll; /* 세로 스크롤 활성화 */
            padding: 10px;
            box-sizing: border-box;
        }
        .card {
            background-color: #fff;
            border-radius: 10px;
            margin-bottom: 10px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }
        .card img {
            width: 100%;
            height: auto;
        }
        .card-content {
            padding: 10px;
        }
        .card-content h2 {
            font-size: 18px;
            margin: 0;
            color: #333;
        }
        .card-content .info {
            font-size: 14px;
            color: #777;
            margin: 5px 0;
        }
        .card-content .price {
            font-size: 16px;
            font-weight: bold;
            color: #333;
        }
        .card-content .rating {
            display: flex;
            align-items: center;
            color: #ff9900;
        }
    </style>
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
