// 환불 상세 내역 페이지 렌더링 함수
function renderRefundDetail(container, refundDetail) {
    container.textContent = ""; // 초기화

    // 데이터 매핑
    const exhibition = refundDetail.exhibition || {};
    const payment = refundDetail.payment || {};
    const paymentHistory = refundDetail.paymentHistory || {};

    // 상세 헤더 섹션
    const detailHeader = document.createElement("div");
    detailHeader.className = "detail-header";

    const image = document.createElement("img");
    image.src = exhibition.imageUrl || "img/default.png";
    image.alt = "전시 이미지";
    image.className = "detail-image";
    detailHeader.appendChild(image);

    const detailInfo = document.createElement("div");
    detailInfo.className = "detail-info";

    const title = document.createElement("h3");
    title.textContent = exhibition.title || "전시 제목 없음";
    detailInfo.appendChild(title);

    const institution = document.createElement("p");
    institution.textContent = exhibition.cntcInsttNm || "기관 정보 없음";
    detailInfo.appendChild(institution);

    const period = document.createElement("p");
    period.textContent = `${exhibition.startPeriod || "시작 날짜 없음"} ~ ${exhibition.endPeriod || "종료 날짜 없음"}`;
    detailInfo.appendChild(period);

    detailHeader.appendChild(detailInfo);
    container.appendChild(detailHeader);

    // 상세 주문 내역 섹션
    const section = document.createElement("div");
    section.className = "section";

    const sectionTitle = document.createElement("h4");
    sectionTitle.textContent = "상세 주문 내역";
    section.appendChild(sectionTitle);

    const refundAmount = document.createElement("p");
    refundAmount.textContent = `환불 금액: ${paymentHistory.refundAmount != null ? paymentHistory.refundAmount.toLocaleString() : "정보 없음"}원`;
    section.appendChild(refundAmount);

    const paymentMethod = document.createElement("p");
    paymentMethod.textContent = `결제 수단: ${payment.paymentMethod || "결제 수단 정보 없음"}`;
    section.appendChild(paymentMethod);

    const refundStatus = document.createElement("p");
    refundStatus.textContent = `환불 상태: ${paymentHistory.paymentStatus || "상태 정보 없음"}완료`;
    section.appendChild(refundStatus);

    const refundPolicy = document.createElement("p");
    refundPolicy.textContent="환불 규정 : 전액환불";
    section.appendChild(refundPolicy);

    const refundDate = document.createElement("p");
    refundDate.textContent = `환불 일자: ${paymentHistory.formattedPaymentEventTimestamp || "환불 날짜 정보 없음"}`;
    section.appendChild(refundDate);




    container.appendChild(section);

    // 안내 문구
    const notice = document.createElement("p");
    notice.className = "notice";
    notice.textContent = "환불금액에 대한 카카오페이 결제취소가 완료되었습니다.";
    container.appendChild(notice);
}

// Axios를 사용해 환불 상세 데이터 가져오기
async function fetchAndRenderRefundDetail(container, paymentNo) {
    try {
        const response = await axios.get('/api/user/myPage/refundsDetail', {
            params: { paymentNo }
        });

        console.log("응답 데이터:", response.data); // 확인용 로그 추가

        if (response.status === 200) {
            const refundDetail = response.data;
            renderRefundDetail(container, refundDetail);
        } else {
            container.innerHTML = '<p>환불 상세 정보를 가져오는 데 실패했습니다.</p>';
        }
    } catch (error) {
        console.error("환불 상세 내역 가져오기 실패:", error);
        container.innerHTML = '<p>환불 상세 정보를 가져오는 데 실패했습니다.</p>';
    }
}

// URL에서 paymentNo 파라미터 가져오기
const urlParams = new URLSearchParams(window.location.search);
const paymentNo = urlParams.get('paymentNo');
console.log("paymentNo : " + paymentNo);

// 렌더링 대상 컨테이너
const detailContainer = document.getElementById("detail-container");

// 데이터 가져오기 및 렌더링
if (paymentNo) {
    fetchAndRenderRefundDetail(detailContainer, paymentNo);
} else {
    detailContainer.innerHTML = '<p>잘못된 요청입니다.</p>';
}
