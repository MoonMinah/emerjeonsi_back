document.addEventListener("DOMContentLoaded", function() {
    const path = window.location.pathname;
    const pathParts = path.split('/');
    const exhibitionNo = pathParts[2];
    axios.get("/api/home/data")
        .then(response => {
            const data = response.data;
            // console.log("main.jsp::데이터 확인:", data); // 데이터 전체 확인
            console.log("main.jsp::data=", data); // 데이터 전체 확인
            const exhibitionList = document.getElementById("exhibitionList");

            /*                exhibitionList.innerHTML = '';*/

            data.forEach(exhibition => {
                // console.log("전시 항목:", exhibition); // 각 전시 데이터 확인
                /*console.log("exhibition=", exhibition); // 각 전시 데이터 확인
                console.log("exhibition.exhibitionNo=", exhibition.exhibitionNo);
                console.log("exhibition.title=", exhibition.title);
                console.log("exhibition.startPeriod=", exhibition.startPeriod);
                console.log("exhibition.endPeriod=", exhibition.endPeriod);*/
                const title = exhibition.title;
                console.log("title=", title);
                imageUrl = exhibition.imageUrl; // 이미지 URL 설정

                let card = document.createElement("div");
                card.className = "card";
                card.innerHTML = `
                        <a href="/home/${exhibition.exhibitionNo || '제목없음'}">
                        <img src="${imageUrl}" alt="${title}">
                        <div class="card-content">
                            <h2>${title|| '제목없음'}</h2>
                            <div class="info-row">
                                <div class="info">${exhibition.cntcInsttNm}<br>${exhibition.startPeriod || ''} - ${exhibition.endPeriod || ''}</div>
                                <div class="price">성인 : ${exhibition.adultPrice} (원)
                                                <br>유아 : ${exhibition.infantPrice} (원)
                                                <br>노인 : ${exhibition.seniorPrice} (원)</div>
                            </div>
                        </div>
                        </a>
                `;

                exhibitionList.appendChild(card);
            });
        })
        .catch(error => {
            console.error("Error fetching exhibitions:", error);
        });
});

/*
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
*/
