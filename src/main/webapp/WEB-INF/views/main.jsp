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
            overflow: hidden;
        }
        /* 헤더 스타일 */
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
            padding: 5px;
            background-color: #f2f2f2;
            border-radius: 20px;
            margin-right: 10px;
        }
        .search-bar input {
            border: none;
            background: none;
            outline: none;
            width: 100%;
            font-size: 14px;
        }
        .filter-icon {
            font-size: 16px;
            color: #888;
            margin-left: 8px;
            cursor: pointer;
        }
        .user-icon {
            font-size: 20px;
            color: #888;
            cursor: pointer;
        }

        /* 전시목록 배너 */
        .banner {
            text-align: left;
            padding: 15px 20px;
            font-size: 20px;
            font-weight: bold;
            color: #333;
            border-bottom: 1px solid #e0e0e0;
            margin-top: 5px;
            margin-bottom: 10px;
            background-color: #fff;
        }

        /* 콘텐츠 영역 */
        .content-container {
            position: relative;
            height: calc(100vh - 180px);
            overflow-y: auto;
            padding: 10px;
        }
        .sort-options {
            display: flex;
            gap: 10px;
            justify-content: flex-end;
            margin-bottom: 10px;
            margin-right: 15px;
        }
        .sort-options button {
            background: none;
            border: none;
            font-size: 14px;
            color: #555;
            cursor: pointer;
        }
        .sort-options .active {
            color: #ff5e5e;
            font-weight: bold;
        }

        .card {
            background-color: #fff;
            border-radius: 10px;
            margin-bottom: 20px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }
        .card img {
            width: 100%;
            height: auto;
        }
        .card-content {
            padding: 15px;
            display: flex;
            flex-direction: column;
            gap: 5px;
        }
        .card-content h2 {
            font-size: 20px;
            margin: 0;
            color: #333;
            font-weight: bold;
        }
        .card-content .info-row {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .card-content .info {
            font-size: 13px;
            color: #777;
        }
        .card-content .price {
            font-size: 13px;
            font-weight: bold;
            color: #333;
        }
        .card-content .price::before {
            font-weight: normal;
            color: #555;
        }
        /*.card-content .rating {
            font-size: 14px;
            color: #ff9900;
            margin-top: 5px;
        }*/

        /* 모달 스타일 */
        .modal {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5);
            justify-content: center;
            align-items: center;
        }
        .modal-content {
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            width: 80%;
            max-width: 400px;
            text-align: center;
        }
        .close-btn {
            cursor: pointer;
            font-size: 18px;
            color: #888;
            float: right;
        }
    </style>
</head>
<body>

<header class="header">
    <img src="img/logo.png" alt="로고" />
    <div class="search-bar">
        <input type="text" placeholder="검색" />
        <span class="filter-icon" onclick="openModal()">⚙️</span>
    </div>
    <span class="user-icon">👤</span>
</header>

<!-- 전시목록 배너 -->
<div class="banner">전시목록</div>

<!-- 콘텐츠 영역 -->
<div class="content-container">
    <!-- 최신순과 이름순 버튼 -->
    <div class="sort-options">
        <button class="active" onclick="sortBy('latest')">최신순</button>
        <button onclick="sortBy('name')">이름순</button>
    </div>

    <!-- 전시 카드 -->
    <div class="card">
        <a href="/exhibitionDetail">
            <img src="img/kumho.png" alt="Exhibition Image">
            <div class="card-content">
                <h2>전시회 제목</h2>
                <div class="info-row">
                    <div class="info">전시회 장소<br>YYYY-MM-DD ~ YYYY-MM-DD</div>
                    <div class="price">1,000 ~ 5,000 원</div>
                </div>
            </div>
        </a>
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

<script>
    function openModal() {
        document.getElementById('filterModal').style.display = 'flex';
    }

    function closeModal() {
        document.getElementById('filterModal').style.display = 'none';
    }

    function sortBy(criteria) {
        // 정렬 기준에 따른 정렬 함수 구현
        console.log(criteria + "으로 정렬합니다.");
    }
</script>

</body>
</html>





