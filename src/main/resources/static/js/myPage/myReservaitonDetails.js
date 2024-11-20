import { fetchReservationDetails } from './myReservationServices.js';
import { renderDetail } from './myReservationHandlers.js';

document.addEventListener("DOMContentLoaded", () => {
    const urlParams = new URLSearchParams(window.location.search);
    const reservationNo = urlParams.get("reservationNo");
    const container = document.getElementById("detail-container");
    // 컨테이너 요소 확인
    if (!container) {
        console.error("detail-container 요소를 찾을 수 없습니다.");
        return;
    }

    // reservationNo 검증
    if (!reservationNo) {
        alert("잘못된 접근입니다.");
        return;
    }
    fetchReservationDetails(
        reservationNo,
        data => renderDetail(container, data),
        error => {
            alert("상세내역 데이터를 가져오는 중 오류가 발생했습니다.");
            console.error("상세내역 가져오기 오류:", error);
        }
    );
});

