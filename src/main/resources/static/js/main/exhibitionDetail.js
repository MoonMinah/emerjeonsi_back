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
                    <div class="header">전시회 정보</div>
                    <img src="${exhibition.imageUrl}" alt="${exhibition.title}">
                 <!--   <div class="banner">
                        <p>\${exhibition.eventSite}<br>\${exhibition.startPeriod || ''} - \${exhibition.endPeriod || ''}</p>
                    </div> -->
                    <div class="details">
                        <h2>${exhibition.title || '제목없음'}</h2>
                        <div class="info">${exhibition.cntcInsttNm}<br>${exhibition.startPeriod || ''} - ${exhibition.endPeriod || ''}</div>
                        <div class="description">
                            ${exhibition.description || '전시 설명 없음'}
                        </div>
                        <div class="price">성인 : ${exhibition.adultPrice} (원)
                                        <br>유아 : ${exhibition.infantPrice} (원)
                                        <br>노인 : ${exhibition.seniorPrice} (원)</div>

                            <button type="button" onclick="openModal()">예매하기</button>

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

function submitReservation() {
    const quantity1 = parseInt(document.getElementById("quantity1").innerText);
    const quantity2 = parseInt(document.getElementById("quantity2").innerText);
    const quantity3 = parseInt(document.getElementById("quantity3").innerText);

    const reservationPrice = (5000 * quantity1) + (1000 * quantity2) + (3000 * quantity3);

    const reservationQuantity = quantity1 + quantity2 + quantity3;


    const exhibitionNo = window.location.pathname.split('/')[2];


    const reservationData = {
        userNo: 1, // 예시로 1을 넣었으나 실제 유저 번호를 넣어야 함
        exhibitionNo: exhibitionNo, // exhibitionNo는 URL에서 추출한 값을 사용
        reservationPrice: reservationPrice,
        reservationQuantity: reservationQuantity

    };


    // 예약 생성 API 호출
    axios.post('/api/reservation', reservationData)
        .then(response => {
            const reservationNo = response.data;
            console.log("예약 성공:", response.data);

            // 예약이 성공적으로 완료되면 상세 페이지로 이동
            window.location.href = `/reservationDetail?exhibitionNo=` + exhibitionNo +
                `&reservationPrice=` + reservationPrice+
                `&reservationQuantity=` + reservationQuantity;

        })
        .catch(error => {
            console.error("예약 처리 중 오류 발생:", error);
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