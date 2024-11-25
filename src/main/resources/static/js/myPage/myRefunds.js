import { getDefaultImage, formatPrice } from './myReservationUtils.js';

// DOM 컨테이너
const refundsContainer = document.getElementById('refunds-container');
const paginationContainer = document.getElementById('pagination');

// 현재 페이지 및 페이지 크기
let currentPage = 1;
const pageSize = 4;

// 환불 목록 가져오기 및 렌더링
async function fetchAndRenderRefunds(page = 1, size = pageSize) {
    try {
        // 데이터 요청
        const response = await axios.get('/api/user/myPage/refunds', {
            params: { page: page, size: size },
        });

        console.log("API 응답 데이터 : " + response.data);

        if (response.status === 200) {
            const { reservations, totalPages, currentPage } = response.data;

            // 환불 목록 렌더링
            refundsContainer.textContent = ""; // 초기화
            if (reservations.length === 0) {
                const noDataMessage = document.createElement("p");
                noDataMessage.textContent = "환불된 항목이 없습니다.";
                noDataMessage.style.textAlign = "center"; // 텍스트 중앙 정렬
                noDataMessage.style.marginTop = "20px";
                noDataMessage.style.fontSize = "18px"; // 텍스트 크기
                noDataMessage.style.color = "#666"; // 색상 설정
                refundsContainer.appendChild(noDataMessage);
                paginationContainer.textContent = ""; // 페이지네이션 초기화

            } else {
                reservations.forEach((refund) => {
                    const refundItem = document.createElement("div");
                    refundItem.className = "refund-item";

                    // 전시 이미지
                    const image = document.createElement("img");
                    image.src = getDefaultImage(refund.exhibition.imageUrl);
                    image.alt = "전시 이미지";
                    image.className = "refund-image";
                    refundItem.appendChild(image);

                    // 정보 컨테이너
                    const infoContainer = document.createElement("div");
                    infoContainer.className = "refund-info";

                    // 전시 제목
                    const title = document.createElement("h3");
                    title.textContent = refund.exhibition.title;
                    infoContainer.appendChild(title);

                    // 전시 기관명
                    const institution = document.createElement("p");
                    institution.textContent = refund.exhibition.cntcInsttNm;
                    infoContainer.appendChild(institution);

                    // 환불 금액
                    const refundPrice = document.createElement("p");
                    refundPrice.textContent = `환불 금액: ${formatPrice(refund.paymentHistory.refundAmount)}`;
                    infoContainer.appendChild(refundPrice);

                    // 환불 일자
                    const refundDate = document.createElement("p");
                    refundDate.textContent = `환불 일자: ${new Date(refund.paymentHistory.paymentEventTimestamp).toLocaleDateString()}`;
                    infoContainer.appendChild(refundDate);

                    // 버튼 컨테이너
                    const buttonContainer = document.createElement("div");
                    buttonContainer.className = "refund-buttons";

                    // 상세내역 버튼
                    const detailButton = document.createElement("button");
                    detailButton.className = "refund-detail-btn";
                    detailButton.textContent = "상세내역";
                    detailButton.addEventListener("click", () => {
                        console.log('상세내역 클릭, PaymentNo:', refund.payment.paymentNo);
                        window.location.href = `/user/myPage/refundsDetail?paymentNo=${refund.payment.paymentNo}`;
                    });
                    buttonContainer.appendChild(detailButton);

                    // 컨테이너에 버튼 추가
                    infoContainer.appendChild(buttonContainer);

                    // 항목 구성
                    refundItem.appendChild(infoContainer);
                    refundsContainer.appendChild(refundItem);
                });
            }

            // 페이지네이션 렌더링
            paginationContainer.textContent = ""; // 초기화

            renderPagination(totalPages, currentPage);
        }
    } catch (error) {
        console.error('환불 목록 가져오기 오류:', error);
        refundsContainer.innerHTML = `<p>환불 목록을 가져오는 데 실패했습니다.</p>`;
    }
}

// 페이지네이션 처리
function renderPagination(totalPages, currentPage) {
    const maxVisiblePages = 3; // 한 번에 보이는 최대 페이지 수
    let startPage = Math.max(1, currentPage - Math.floor(maxVisiblePages / 2));
    let endPage = Math.min(totalPages, startPage + maxVisiblePages - 1);

    // 현재 페이지가 끝에 가까울 경우, startPage 조정
    if (endPage - startPage + 1 < maxVisiblePages) {
        startPage = Math.max(1, endPage - maxVisiblePages + 1);
    }

    // 이전 버튼
    if (currentPage > 1) {
        const prevButton = document.createElement("button");
        prevButton.textContent = "<";
        prevButton.className = "prev-page";
        prevButton.onclick = () => {
            fetchAndRenderRefunds(currentPage - 1, pageSize);
        };
        paginationContainer.appendChild(prevButton);
    }

    // 페이지 번호
    for (let i = startPage; i <= endPage; i++) {
        const pageButton = document.createElement("button");
        pageButton.textContent = i;
        pageButton.disabled = i === currentPage;
        pageButton.className = i === currentPage ? "active-page" : "";
        pageButton.onclick = () => {
            fetchAndRenderRefunds(i, pageSize);
        };
        paginationContainer.appendChild(pageButton);
    }

    // 다음 버튼
    if (currentPage < totalPages) {
        const nextButton = document.createElement("button");
        nextButton.textContent = ">";
        nextButton.className = "next-page";
        nextButton.onclick = () => {
            fetchAndRenderRefunds(currentPage + 1, pageSize);
        };
        paginationContainer.appendChild(nextButton);
    }
}

// 초기 데이터 로드
fetchAndRenderRefunds();
