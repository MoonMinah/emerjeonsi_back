<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>금호강과 길</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
        }
        .container {
            width: 100%;
            max-width: 375px; /* 아이폰 미니 13 해상도에 맞춤 */
            margin: 0 auto;
            background-color: #fff;
            overflow: hidden;
        }
        .header {
            background-color: #0066cc;
            color: #fff;
            padding: 15px;
            text-align: center;
            font-size: 1.2em;
        }
        .banner {
            background-color: #3ea6ff;
            color: #fff;
            text-align: center;
            padding: 20px;
        }
        .banner h1 {
            font-size: 1.8em;
            margin: 0;
        }
        .banner h2 {
            font-size: 1.1em;
            margin: 5px 0;
        }
        .banner p {
            font-size: 0.9em;
            margin: 10px 0;
        }
        .details {
            padding: 20px;
        }
        .details h2 {
            font-size: 1.4em;
            margin: 0 0 10px;
        }
        .info {
            font-size: 0.9em;
            color: #666;
            margin-bottom: 15px;
        }
        .rating {
            font-size: 0.9em;
            color: #ffcc00;
            margin-bottom: 15px;
        }
        .description {
            font-size: 0.9em;
            color: #333;
            margin-bottom: 20px;
        }
        .price {
            font-size: 1em;
            font-weight: bold;
            color: #333;
            margin-bottom: 10px;
        }
        .button-container {
            text-align: center;
            margin: 20px 0;
        }
        .button {
            padding: 10px 20px;
            background-color: #ff5252;
            color: #fff;
            border: none;
            border-radius: 5px;
            font-size: 1em;
            cursor: pointer;
        }
        .button:hover {
            background-color: #ff3333;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="header">전시회 정보</div>

    <div class="banner">
        <h1>금호강과 길</h1>
        <h2>ROADS OF GEUMHOGANG: THE ANCIENT ARTIFACTS</h2>
        <p>06.19 TUE - 09.30 SUN<br>국립대구박물관 기획전시실 II</p>
    </div>

    <div class="details">
        <h2>금호강과 길</h2>
        <div class="info">국립대구박물관<br>2018.6.19 - 2018.9.30</div>
        <div class="rating">★ 5.0</div>
        <div class="description">
            (사)한국매장문화재협회와 공동주최로 최근 금호강 유역에서 확인된 선사~고대의 유적과 유물을 소개하고
            대구-경산-영천을 잇는 물길로서의 금호강과 그 주변에 형성된 고대문화의 특징을 종합적으로 조명하는 전시
        </div>
        <div class="price">1,000 - 5,000 (원)</div>
        <div class="info">2018.6.19 - 2018.9.30</div>
    </div>

    <div class="button-container">
        <button class="button">예약</button>
    </div>
</div>

</body>
</html>
\