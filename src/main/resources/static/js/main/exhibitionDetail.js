function goBack() {
    window.history.back();
}

document.addEventListener("DOMContentLoaded", function() {
    const path = window.location.pathname;
    const pathParts = path.split('/');
    const exhibitionNo = pathParts[2];

    if (exhibitionNo) {
        axios.get(`/api/home/${exhibitionNo}/data`)
            .then(response => {
                const exhibition = response.data;

                /*imageUrl = exhibition.imageUrl; // 이미지 URL 설정*/
                let banner = document.querySelector('.banner');
                banner.innerHTML = `
                <img src="${exhibition.imageUrl}" alt="${exhibition.title}">
             <!--   <div class="banner">
                    <p>\${exhibition.eventSite}<br>\${exhibition.startPeriod || ''} - \${exhibition.endPeriod || ''}</p>
                </div> -->
                <div class="details">
                    <h2 id="exhibitionTitle">${exhibition.title || '제목없음'}</h2>
                    <div class="description">
                        ${exhibition.description || '전시 설명 없음'}
                    </div>
                    <div class="info">${exhibition.cntcInsttNm}<br><br>
                                    ${exhibition.startPeriod || ''} - ${exhibition.endPeriod || ''}</div>
                    <div class="row">
                        <div class="price">
                            성인 : 5,000 원<br>
                            유아 : 1,000 원<br>
                            노인 : 3,000 원
                        </div>    
                        <div>
                            <button class="reserve-btn" type="button" onclick="openModal()">예매하기</button>
                        </div>
                      </div>
                </div>`;
            })
            .catch(error => {
                console.error("Error fetching exhibition data:", error);
            });
    } else {
        console.error("Exhibition number (exhibitionNo) not found in URL");
    }
});

function openModal() {
    document.getElementById("reservationModal").classList.add("active");
}

function closeModal() {
    document.getElementById("reservationModal").classList.remove("active");
}

function cleanAndTrimTitle(title) {
    if (!title) return "제목 없음";
    const cleanedTitle = title.replace(/[^\w\sㄱ-힣]/g, ""); // 특수문자 제거
    const maxLength = 20; // 최대 길이
    const trimmedTitle = cleanedTitle.length > maxLength ? cleanedTitle.substring(0, maxLength) + "..." : cleanedTitle;
    return trimmedTitle.trim(); // 공백 정리
}

function submitReservation() {
    // 로그인 상태 확인
    axios.get('/api/check-login') // 로그인 상태 확인 엔드포인트
        .then(response => {
            console.log("로그인 상태 확인 성공:", response.data);

            // 로그인된 상태면 예매 진행
            if (response.status === 200 && response.data) {
                proceedWithReservation(response.data); // 로그인된 사용자 정보 전달
            } else {
                // 로그인되지 않은 상태
                alert("로그인이 필요합니다. 로그인 페이지로 이동합니다.");
                window.location.href = '/login'; // 로그인 페이지로 리다이렉트
            }
        })
        .catch(error => {
            console.error("로그인 상태 확인 실패:", error);

            // 인증 실패로 인한 401 응답 처리
            if (error.response && error.response.status === 401) {
                alert("로그인이 필요합니다. 로그인 페이지로 이동합니다.");
                window.location.href = '/login'; // 로그인 페이지로 리다이렉트
            } else {
                alert("로그인 상태 확인 중 오류가 발생했습니다.");
            }
        });
}

function proceedWithReservation(user) {
    // 예약 데이터 수집
    const quantity1 = parseInt(document.getElementById("quantity1").innerText);
    const quantity2 = parseInt(document.getElementById("quantity2").innerText);
    const quantity3 = parseInt(document.getElementById("quantity3").innerText);

    const reservationPrice = (5000 * quantity1) + (1000 * quantity2) + (3000 * quantity3);
    const reservationQuantity = quantity1 + quantity2 + quantity3;
    const rawExhibitionTitle = document.querySelector("#exhibitionTitle").innerText;
    const exhibitionTitle = cleanAndTrimTitle(rawExhibitionTitle);

    const exhibitionNo = window.location.pathname.split('/')[2];
    const imageUrl = document.querySelector(".banner img").src;

    const reservationData = {
        userNo: user.userNo, // 서버에서 가져온 로그인된 사용자 정보
        exhibitionNo: exhibitionNo,
        reservationPrice: reservationPrice,
        reservationQuantity: reservationQuantity,
        exhibitionTitle: exhibitionTitle,
    };

    // 예매 생성 API 호출
    axios.post('/api/user/reservation', reservationData)
        .then(response => {
            const reservationNo = response.data;
            console.log("예매 성공:", reservationNo);

            // 예매 상세 페이지로 이동
            window.location.href = `/user/reservationDetail?reservationNo=${reservationNo}&exhibitionNo=${exhibitionNo}&adult=${quantity1}&infant=${quantity2}&senior=${quantity3}&price=${reservationPrice}&quantity=${reservationQuantity}&title=${exhibitionTitle}&imageUrl=${encodeURIComponent(imageUrl)}`;
        })
        .catch(error => {
            console.error("예매 처리 중 오류 발생:", error);
            alert("예매 중 문제가 발생했습니다. 다시 시도해주세요.");
        });
}



function increase1() {
    let quantityElement = document.getElementById("quantity1");
    let quantity = parseInt(quantityElement.innerText);
    quantityElement.innerText = quantity + 1;
    updateTotalAmount();
}

function increase2() {
    let quantityElement = document.getElementById("quantity2");
    let quantity = parseInt(quantityElement.innerText);
    quantityElement.innerText = quantity + 1;
    updateTotalAmount();
}

function increase3() {
    let quantityElement = document.getElementById("quantity3");
    let quantity = parseInt(quantityElement.innerText);
    quantityElement.innerText = quantity + 1;
    updateTotalAmount();
}

function decrease1() {
    let quantityElement = document.getElementById("quantity1");
    let quantity = parseInt(quantityElement.innerText);
    if (quantity > 0) {
        quantityElement.innerText = quantity - 1;
        updateTotalAmount();
    }
}

function decrease2() {
    let quantityElement = document.getElementById("quantity2");
    let quantity = parseInt(quantityElement.innerText);
    if (quantity > 0) {
        quantityElement.innerText = quantity - 1;
        updateTotalAmount();
    }
}

function decrease3() {
    let quantityElement = document.getElementById("quantity3");
    let quantity = parseInt(quantityElement.innerText);
    if (quantity > 0) {
        quantityElement.innerText = quantity - 1;
        updateTotalAmount();
    }
}

function updateTotalAmount1(quantity1) {
    let totalAmountElement = document.getElementById("totalAmount1");
    totalAmountElement.innerText = (5000 * quantity1).toLocaleString() + "원";
}
function updateTotalAmount2(quantity2) {
    let totalAmountElement = document.getElementById("totalAmount2");
    totalAmountElement.innerText = (1000 * quantity2).toLocaleString() + "원";
}
function updateTotalAmount3(quantity3) {
    let totalAmountElement = document.getElementById("totalAmount3");
    totalAmountElement.innerText = (3000 * quantity3).toLocaleString() + "원";
}

function updateTotalAmount() {
    let quantity1 = parseInt(document.getElementById("quantity1").innerText) || 0;
    let quantity2 = parseInt(document.getElementById("quantity2").innerText) || 0;
    let quantity3 = parseInt(document.getElementById("quantity3").innerText) || 0;

    let totalAmount = (5000 * quantity1) + (1000 * quantity2) + (3000 * quantity3);
    document.getElementById("totalAmount").innerText = totalAmount.toLocaleString() + "원";
}