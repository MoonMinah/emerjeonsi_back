<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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
        <span class="filter-icon" onclick="settingModal()">⚙️</span>
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
        <br>
        <label>
            <input type="radio" name="option" value="title" onclick="applyFilter('title')">
            전시제목
        </label>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <label>
            <input type="radio" name="option" value="museum" onclick="applyFilter('museum')">
            박물관명
        </label>
        <!-- 필터 옵션 추가 -->
    </div>
</div>

<!-- axios 라이브러리 추가 -->
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<%--<script src="/js/main/main.js"></script>--%>

<script>
    let data = []; // 서버에서 받은 데이터를 저장할 변수
    let selectedFilter = ''; // 기본 필터
    let searchInput = ''; // 검색어 초기화

    document.addEventListener("DOMContentLoaded", function () {
        // 초기 데이터 로드
        loadData('latest');
    });

    // 이름순 or 최신순
    document.querySelector(".search-bar input").addEventListener("input", (e) => {
        searchInput = e.target.value;
    });

    /*
        // 모달에서 필터 선택 시 동작
        function applyFilter(option) {
            selectedFilter = option; // 선택된 필터 업데이트
            closeModal(); // 모달 닫기
            if (searchInput.trim() !== '') {
                searchByKeyword(); // 검색 수행
            }
        }

        // 검색 수행 함수
        function searchByKeyword() {
            const apiUrl = `/api/home/search?filter=\${selectedFilter}&keyword=\${searchInput}`;
            axios.get(apiUrl)
                .then(response => {
                    const data = response.data;
                    renderExhibitions(data); // 검색 결과 렌더링
                })
                .catch(error => {
                    console.error("검색 중 오류 발생:", error);
                    alert("검색 결과를 가져오는 중 오류가 발생했습니다.");
                });
        }*/

    // 정렬 기준에 따라 데이터 로드 및 정렬
    function sortBy(criteria) {
        loadData(criteria);

        // 버튼 활성화 상태 갱신
        document.querySelectorAll(".sort-options button").forEach(button => {
            button.classList.remove("active");
        });
        if (criteria === 'latest') {
            document.querySelector(".sort-options button:nth-child(1)").classList.add("active");
        } else {
            document.querySelector(".sort-options button:nth-child(2)").classList.add("active");
        }
    }

    function loadData(criteria) {
        let apiUrl = criteria === 'latest' ? '/api/home/data' : '/api/home/data/sort?criteria=name';

        axios.get(apiUrl)
            .then(response => {
                data = response.data;
                renderExhibitions(data); // 데이터 렌더링
            })
            .catch(error => {
                console.error("Error fetching exhibitions:", error);
            });
    }

    // 전시 목록 렌더링 함수
    function renderExhibitions(data) {
        const exhibitionList = document.getElementById("exhibitionList");
        exhibitionList.innerHTML = ''; // 기존 목록 제거

        if (data.length === 0) {
            exhibitionList.innerHTML = '<p class="no-result">검색 결과가 없습니다.</p>';
            return;
        }

        data.forEach(exhibition => {
            const title = exhibition.title || '제목없음';
            const imageUrl = exhibition.imageUrl || '/img/default-image.png';

            let card = document.createElement("div");
            card.className = "card";
            card.innerHTML = `
                <a href="/home/\${exhibition.exhibitionNo}">
                    <img src="\${imageUrl}" alt="\${title}">
                    <div class="card-content">
                        <h2>\${title}</h2>
                        <div class="info-row">
                            <div class="info">\${exhibition.cntcInsttNm || ''}<br>\${exhibition.startPeriod || ''} - \${exhibition.endPeriod || ''}</div>
                            <div class="price">
                                성인 : \${exhibition.adultPrice || "무료"}원<br>
                                유아 : \${exhibition.infantPrice || "무료"}원<br>
                                노인 : \${exhibition.seniorPrice || "무료"}원
                            </div>
                        </div>
                    </div>
                </a>
            `;
            exhibitionList.appendChild(card);
        });
    }

    // 모달 설정 함수
    function settingModal() {
        document.getElementById('filterModal').style.display = 'flex';
    }

    // 모달 닫기 함수
    function closeModal() {
        document.getElementById('filterModal').style.display = 'none';
    }
</script>

</body>
</html>