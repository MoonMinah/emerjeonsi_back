import { fetchReservations } from './myReservationServices.js';
import { renderReservations } from './myReservationHandlers.js';



// 상세내역 버튼 클릭 시 호출될 함수
function showDetails(reservationNo) {
    console.log("상세내역 클릭됨, 예약 번호:", reservationNo);
    window.location.href = `/user/myPage/reservationDetails?reservationNo=${reservationNo}`;
}

// 결제내역 버튼 클릭 시 호출될 함수
function showPaymentDetails(paymentNo) {
    console.log("결제내역 클릭됨, 결제 번호:", paymentNo);
    window.location.href = `/user/myPage/reservationPaymentDetails?paymentNo=${paymentNo}`;
}

document.addEventListener("DOMContentLoaded", () => {
    const container = document.getElementById("reservation-container");
    const pagination = document.getElementById("pagination");

    function fetchPage(page) {
        fetchReservations(
            page,
            4,
            data => {
                renderReservations(container, data.reservations, showDetails, showPaymentDetails);
                updatePagination(data.totalPages, page);
            },
            error => {
                alert("데이터를 가져오는 중 오류가 발생했습니다.");
                console.error("페이지 데이터 가져오기 오류:", error);
            }
        );
    }

    function updatePagination(totalPages, currentPage) {
        pagination.textContent = "";
        for (let i = 1; i <= totalPages; i++) {
            const button = document.createElement("button");
            button.textContent = i;
            if (i === currentPage) {
                button.style.fontWeight = "bold";
            }
            button.addEventListener("click", () => fetchPage(i));
            pagination.appendChild(button);
        }
    }

    fetchPage(1);
});
