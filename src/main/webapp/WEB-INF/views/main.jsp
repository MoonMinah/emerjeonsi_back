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
        <input type="text" placeholder="검색" onclick="checkFilter()"/>
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
<%--<script src="/js/main/main.js"></script>--%>

<script>
    let data = []; // 서버에서 받은 데이터를 저장할 변수
    let selectedFilter = ''; // 기본 필터
    let searchInput = ''; // 검색어 초기화

    document.addEventListener("DOMContentLoaded", function () {
        // 초기 데이터 로드
        loadData('latest');
    });

    // 검색 조건이 설정되지 않았을 때 경고 메시지 표시
    function checkFilter() {
        if (!selectedFilter) {
            alert("설정 아이콘을 클릭해 검색 조건을 설정해주세요.");
        }
    }

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

    // 필터 적용 함수
    function applyFilter() {
        // 라디오 버튼에서 선택된 값 가져오기
        const selectedFilter = document.querySelector('input[name="option"]:checked')?.value;
        const searchInput = document.querySelector('.modal input[type="text"]').value.trim();

        if (!selectedFilter || !searchInput) {
            alert("필터 조건과 검색어를 입력해주세요.");
            return;
        }

        // 필터 검색 API 호출
        axios.get('/api/home/data/search', {
            params: { selectedFilter, searchInput }
        })
            .then(response => {
                data = response.data;
                renderExhibitions(data); // 필터 결과 렌더링
                closeModal(); // 모달 닫기
            })
            .catch(error => {
                console.error("Error searching exhibitions:", error);
            });
    }
</script>

</body>
</html>