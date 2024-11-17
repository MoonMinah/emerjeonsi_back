let data = []; // 서버에서 받은 데이터를 저장할 변수

document.addEventListener("DOMContentLoaded", function () {
    // 초기 데이터 로드
    loadData('latest');
});

// 검색어 입력 시 값 업데이트
document.querySelector(".search-bar input").addEventListener("input", (e) => {
    searchInput = e.target.value;
});

// 모달에서 필터 선택 시 동작
function applyFilter(option) {
    selectedFilter = option; // 선택된 필터 업데이트
    closeModal(); // 모달 닫기
    if (searchInput.trim() !== '') {
        searchByKeyword(); // 검색 수행
    } else {
        alert("검색어를 입력해주세요!");
    }
}

// 검색 수행 함수
function searchByKeyword() {
    const apiUrl = `/api/home/search?filter=${selectedFilter}&keyword=${encodeURIComponent(searchInput)}`;
    axios.get(apiUrl)
        .then(response => {
            const data = response.data;
            renderExhibitions(data); // 검색 결과 렌더링
        })
        .catch(error => {
            console.error("검색 중 오류 발생:", error);
            alert("검색 결과를 가져오는 중 오류가 발생했습니다.");
        });
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

    data.forEach(exhibition => {
        const title = exhibition.title || '제목없음';
        const imageUrl = exhibition.imageUrl || '/img/default-image.png'; // 기본 이미지 설정

        let card = document.createElement("div");
        card.className = "card";
        card.innerHTML = `
            <a href="/home/${exhibition.exhibitionNo}">
                <img src="${imageUrl}" alt="${title}">
                <div class="card-content">
                    <h2>${title}</h2>
                    <div class="info-row">
                        <div class="info">${exhibition.cntcInsttNm || ''}<br>${exhibition.startPeriod || ''} - ${exhibition.endPeriod || ''}</div>
                        <div class="price">
                            성인 : ${exhibition.adultPrice || ""}원<br>
                            유아 : ${exhibition.infantPrice || ""}원<br>
                            노인 : ${exhibition.seniorPrice || ""}원
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