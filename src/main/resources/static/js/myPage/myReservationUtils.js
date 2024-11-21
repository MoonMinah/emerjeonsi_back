// reservationUtils.js
//화면 설정 및 공통적으로 사용할 함수들을 포함.
// 동적으로 뷰포트 높이를 계산하여 설정하는 함수
export function setViewportHeight() {
    const vh = window.innerHeight * 0.01;
    document.documentElement.style.setProperty("--vh", `${vh * 100}px`);
}

// 금액을 통화 형식으로 변환하는 함수
export function formatPrice(price) {
    if (price == null) return "가격 정보 없음";
    return price.toLocaleString("ko-KR") + "원";
}

// 기본 이미지 URL 반환 함수
export function getDefaultImage(imageUrl) {
    return imageUrl || "img/default.png";
}