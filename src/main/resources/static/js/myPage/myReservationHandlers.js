// reservationHandlers.js

import { getDefaultImage, formatPrice } from './myReservationUtils.js';

// 예매 목록 렌더링
export function renderReservations(container, reservations, onDetailClick, onPaymentClick) {
    container.textContent = ""; // 초기화

    if (reservations.length === 0) {
        const noDataMessage = document.createElement("p");
        noDataMessage.textContent = "예약된 항목이 없습니다.";
        container.appendChild(noDataMessage);
        return;
    }

    reservations.forEach(reservation => {
        const reservationItem = document.createElement("div");
        reservationItem.className = "reservation-item";

        const image = document.createElement("img");
        image.src = getDefaultImage(reservation.exhibition.imageUrl);
        image.alt = "전시 이미지";
        image.className = "reservation-image";
        reservationItem.appendChild(image);

        const infoContainer = document.createElement("div");
        infoContainer.className = "reservation-info";

        // 결제 일시 (yyyy-MM-dd 형식 변환)
        const paymentDate = reservation.payment.formattedPaymentDate;
        const reservationDate = paymentDate.substring(0, 10);
        console.log("reservationDate : " + reservationDate);
        console.log("paymentDate : " + paymentDate);

        const dateElement = document.createElement("p");
        dateElement.textContent = `${reservationDate}`;
        dateElement.className = "reservation-info";
        infoContainer.appendChild(dateElement);

        const title = document.createElement("h3");
        title.textContent = reservation.exhibition.title;
        infoContainer.appendChild(title);



        const institution = document.createElement("p");
        institution.textContent = reservation.exhibition.cntcInsttNm;
        infoContainer.appendChild(institution);



        const price = document.createElement("p");
        price.textContent = `가격: ${formatPrice(reservation.reservationPrice)}`;
        infoContainer.appendChild(price);

        const buttonContainer = document.createElement("div");
        buttonContainer.className = "buttons";

        const detailButton = document.createElement("button");
        detailButton.className = "detail-btn";
        detailButton.textContent = "상세내역";
        detailButton.addEventListener("click", () => onDetailClick(reservation.reservationNo));
        buttonContainer.appendChild(detailButton);

        const paymentButton = document.createElement("button");
        paymentButton.className = "payment-btn";
        paymentButton.textContent = "결제내역";
        paymentButton.addEventListener("click", () => onPaymentClick(reservation.payment.paymentNo));
        buttonContainer.appendChild(paymentButton);

        infoContainer.appendChild(buttonContainer);
        reservationItem.appendChild(infoContainer);
        container.appendChild(reservationItem);
    });
}

// 상세내역 렌더링
export function renderDetail(container, reservation) {
    // 컨테이너 확인
    if (!container) {
        console.error("렌더링할 컨테이너가 존재하지 않습니다.");
        return;
    }

    // 데이터 검증
    if (!reservation || !reservation.exhibition) {
        console.error("렌더링할 데이터가 올바르지 않습니다.", reservation);
        return;
    }
    // 날짜 포맷 함수
    function formatDate(timestamp) {
        if (!timestamp) return "날짜 없음"; // 값이 없는 경우 처리
        try {
            const date = new Date(timestamp);
            if (isNaN(date.getTime())) throw new Error("Invalid time value");
            return date.toISOString().split('T')[0]; // yyyy-MM-dd 형식 반환
        } catch (error) {
            console.error("날짜 변환 중 오류:", error);
            return "날짜 오류";
        }
    }


    container.textContent = ""; // 초기화

    // 상세 헤더 섹션
    const detailHeader = document.createElement("div");
    detailHeader.className = "detail-header";

    const image = document.createElement("img");
    image.src = reservation.exhibition.imageUrl || "img/default.png";
    image.alt = "전시 이미지";
    image.className = "detail-image";
    detailHeader.appendChild(image);

    const detailInfo = document.createElement("div");
    detailInfo.className = "detail-info";

    const title = document.createElement("h3");
    title.textContent = reservation.exhibition.title || "제목 없음";
    detailInfo.appendChild(title);

    const institution = document.createElement("p");
    institution.textContent = reservation.exhibition.cntcInsttNm || "기관 정보 없음";
    detailInfo.appendChild(institution);

    const period = document.createElement("p");
    period.textContent = `${reservation.exhibition.startPeriod || "기간 없음"} ~ ${
        reservation.exhibition.endPeriod || "기간 없음"
    }`;
    detailInfo.appendChild(period);

    const price = document.createElement("p");
    price.textContent = `${reservation.reservationPrice.toLocaleString()}원`;
    detailInfo.appendChild(price);

    detailHeader.appendChild(detailInfo);
    container.appendChild(detailHeader);

    // 상세 주문 내역 섹션
    const section = document.createElement("div");
    section.className = "section";

    const sectionTitle = document.createElement("h4");
    sectionTitle.textContent = "상세 주문 내역";
    section.appendChild(sectionTitle);

    // const dateInfo = document.createElement("p");
    // dateInfo.innerHTML = `<strong>날짜</strong><br>${reservation.exhibition.startPeriod || "기간 없음"} ~ ${
    //     reservation.exhibition.endPeriod || "기간 없음"
    // }`;
    // section.appendChild(dateInfo);

    const reservationStatus = document.createElement("p");
    reservationStatus.innerHTML = `<strong>예매상태</strong><br>${reservation.reservationStatus}`;
    section.appendChild(reservationStatus);

    const eventTimestamp = document.createElement("p");
    eventTimestamp.innerHTML = `<strong>예매 일시</strong><br>${formatDate(reservation.reservationHistory.reservationEventTimeStamp)}`;
    section.appendChild(eventTimestamp);


    const locationInfo = document.createElement("p");
    locationInfo.innerHTML = `<strong>장소</strong><br>${reservation.exhibition.eventSite || "장소 정보 없음"}`;
    section.appendChild(locationInfo);

    container.appendChild(section);

    // 총 금액 섹션
    const totalAmount = document.createElement("div");
    totalAmount.className = "total-amount";

    const totalTitle = document.createElement("h4");
    totalTitle.textContent = "예매 금액";
    totalAmount.appendChild(totalTitle);

    const ticketInfo = document.createElement("p");
    ticketInfo.textContent = `티켓 수량: ${reservation.reservationQuantity}`;
    totalAmount.appendChild(ticketInfo);

    const totalPrice = document.createElement("p");
    totalPrice.innerHTML = `<strong>금액: ${reservation.reservationPrice.toLocaleString()}원</strong>`;
    totalAmount.appendChild(totalPrice);

    container.appendChild(totalAmount);

    // 안내 문구
    const notice = document.createElement("p");
    notice.className = "notice";
    notice.textContent = "전시 시작일로부터는 취소 및 환불이 되지 않습니다.";
    container.appendChild(notice);
}



export function renderPaymentDetails(container, reservation) {
    if (!container) {
        console.error("렌더링할 컨테이너가 없습니다.");
        return;
    }

    if (!reservation || !reservation.exhibition || !reservation.payment) {
        console.error("결제 내역 데이터가 올바르지 않습니다.", reservation);
        return;
    }

    container.textContent = ""; // 초기화

    // === 전시 정보 렌더링 ===
    const exhibition = reservation.exhibition; // 전시 정보
    const payment = reservation.payment; // 결제 정보

    const paymentHeader = document.createElement("div");
    paymentHeader.className = "payment-header";

    const image = document.createElement("img");
    image.src = exhibition.imageUrl || "img/default.png";
    image.alt = "전시 이미지";
    image.className = "payment-image";
    paymentHeader.appendChild(image);

    const paymentInfo = document.createElement("div");
    paymentInfo.className = "payment-info";

    const title = document.createElement("h3");
    title.textContent = exhibition.title || "제목 없음";
    paymentInfo.appendChild(title);

    const institution = document.createElement("p");
    institution.textContent = exhibition.cntcInsttNm || "기관 정보 없음";
    paymentInfo.appendChild(institution);

    const period = document.createElement("p");
    period.textContent = `${exhibition.startPeriod || "기간 없음"} ~ ${exhibition.endPeriod || "기간 없음"}`;
    paymentInfo.appendChild(period);

    paymentHeader.appendChild(paymentInfo);
    container.appendChild(paymentHeader);

    // === 예매 및 결제 정보 렌더링 ===
    const section = document.createElement("div");
    section.className = "section";

    const sectionTitle = document.createElement("h4");
    sectionTitle.textContent = "결제 내역";
    section.appendChild(sectionTitle);

    const reservationPrice = document.createElement("p");
    reservationPrice.innerHTML = `<strong>예매 금액:</strong> ${formatPrice(reservation.reservationPrice || 0)}`;
    section.appendChild(reservationPrice);

    const paymentPrice = document.createElement("p");
    paymentPrice.innerHTML = `<strong>결제 금액:</strong> ${formatPrice(payment.paymentPrice || 0)}`;
    section.appendChild(paymentPrice);

    const paymentMethod = document.createElement("p");
    paymentMethod.innerHTML = `<strong>결제 수단:</strong> ${payment.paymentMethod || "정보 없음"}`;
    section.appendChild(paymentMethod);

    const paymentTimestamp = document.createElement("p");
    paymentTimestamp.innerHTML = `<strong>결제 일시:</strong> ${payment.formattedPaymentDate || "결제 일시 없음"}`;
    section.appendChild(paymentTimestamp);

    const refundPolicy = document.createElement("p");
    refundPolicy.innerHTML = `<strong>환불 기한:</strong> 티켓 유효기간 전까지 신청 가능`;
    section.appendChild(refundPolicy);

    container.appendChild(section);

    // === 환불 버튼 추가 ===
    const refundButton = document.createElement("button");
    refundButton.className = "refund-btn";
    refundButton.textContent = "환불 진행";
    refundButton.addEventListener("click", () => {
        window.location.href = `/user/myPage/reservationRefund?paymentNo=${payment.paymentNo}`;
    });
    container.appendChild(refundButton);
}

