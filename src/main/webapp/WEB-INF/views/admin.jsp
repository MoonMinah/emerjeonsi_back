<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>관리자</title>
</head>
<body>
<header class="header">
    <img src="img/logo.png" alt="로고" />
    <div class="search-bar">
        <input type="text" placeholder="검색" />
    </div>
</header>

<div class="content">
    <h2>전시회 목록</h2>
    <!-- 전시회 목록이 여기에 표시됩니다 -->
    <table id="exhibitionTable" border="1">
        <thead>
        <tr>
            <th>제목</th>
            <th>장르</th>
            <th>장소</th>
            <th>연락처</th>
            <th>설명</th>
        </tr>
        </thead>
        <tbody>
        <!-- 데이터를 여기에 추가합니다 -->
        </tbody>
    </table>
</div>

<!-- axios 라이브러리 추가 -->
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

<button id="fetchDataButton">전시회 데이터 가져오기</button>
<button id="saveDataButton">전시회 데이터 저장하기</button>

<script>
    let exhibitionDatas = new Map();  // 전시회 데이터를 저장할 배열

    document.getElementById("fetchDataButton").addEventListener("click", function() {
        axios.get('/api/admin')
            .then(function(response) {
                // response.data.exhibitions가 배열이라면, 이를 exhibitionDatas에 할당
                // 예: response.data.exhibitions 구조가 아니라, response.data 자체가 전시회 목록이라면 수정
                exhibitionDatas = response.data;  // 수정된 부분

                const exhibitionTableBody = document.getElementById("exhibitionTable").getElementsByTagName("tbody")[0];
                exhibitionTableBody.innerHTML = ''; // 테이블 초기화

                exhibitionDatas.forEach(exhibition => {
                    const row = exhibitionTableBody.insertRow();
                    row.innerHTML = `
                        <td>${exhibition.title}</td>
                        <td>${exhibition.genre}</td>
                        <td>${exhibition.eventSite}</td>
                        <td>${exhibition.contactPoint}</td>
                        <td>${exhibition.description}</td>
                    `;
                });
            })
            .catch(function(error) {
                console.error("데이터 가져오기 실패:", error);
            });
    });

    document.getElementById("saveDataButton").addEventListener("click", function() {
        if (exhibitionDatas.length === 0) {  // exhibitions -> exhibitionDatas로 수정
            alert("저장할 데이터가 없습니다.");
            return;
        }

        axios.post('/api/admin/saveExhibitions', { exhibitions: exhibitionDatas })  // 데이터 전송 시 'exhibitions' 키로 감싸서 전송
            .then(function(response) {
                alert(response.data); // 데이터 저장 성공 메시지
            })
            .catch(function(error) {
                console.error("데이터 저장 실패:", error);
            });
    });
</script>

</body>
</html>