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