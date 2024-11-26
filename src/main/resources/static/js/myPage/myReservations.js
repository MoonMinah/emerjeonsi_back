import { fetchReservations } from './myReservationServices.js';
import { renderReservations } from './myReservationHandlers.js';

// DOM 컨테이너
const container = document.getElementById("reservation-container");
const pagination = document.getElementById("pagination");

// 현재 페이지와 페이지 크기
let currentPage = 1;
const pageSize = 4;

// 예약 데이터를 가져오고 렌더링하는 함수
function fetchPage(page) {
    fetchReservations(
        page,
        pageSize,
        data => {
            currentPage = page; // 현재 페이지 업데이트
            if (!data.reservations || data.reservations.length === 0) {
                // 예매 내역이 없는 경우
                container.textContent = ""; // 기존 컨테이너 초기화
                const noDataMessage = document.createElement("p");
                noDataMessage.textContent = "예매 내역이 없습니다!";
                noDataMessage.style.textAlign = "center"; // 텍스트 중앙 정렬
                noDataMessage.style.marginTop = "20px";
                noDataMessage.style.fontSize = "18px"; // 텍스트 크기
                noDataMessage.style.color = "#666"; // 색상 설정
                container.appendChild(noDataMessage);
                pagination.textContent = ""; // 페이지네이션 초기화
                return;
            }
            renderReservations(container, data.reservations, showDetails, showPaymentDetails); // 예약 목록 렌더링
            updatePagination(data.totalPages, currentPage); // 페이지네이션 업데이트
        },
        error => {
            //console.error("페이지 데이터 가져오기 오류:", error);
            container.textContent = ""; // 기존 컨테이너 초기화
            const errorMessage = document.createElement("p");
            errorMessage.textContent = "데이터를 가져오는 중 오류가 발생했습니다.";
            errorMessage.style.textAlign = "center"; // 텍스트 중앙 정렬
            errorMessage.style.marginTop = "20px";
            errorMessage.style.fontSize = "18px"; // 텍스트 크기
            errorMessage.style.color = "red"; // 색상 설정
            container.appendChild(errorMessage);
            pagination.textContent = ""; // 페이지네이션 초기화
        }
    );
}

// 페이지네이션을 업데이트하고 렌더링하는 함수
function updatePagination(totalPages, currentPage) {
    const maxVisiblePages = 3; // 한 번에 보이는 최대 페이지 수
    const startPage = Math.max(1, currentPage - Math.floor(maxVisiblePages / 2));
    const endPage = Math.min(totalPages, startPage + maxVisiblePages - 1);

    pagination.textContent = ""; // 기존 페이지네이션 초기화

    // 이전 버튼 추가
    if (currentPage > 1) {
        const prevButton = document.createElement("button");
        prevButton.textContent = "<";
        prevButton.className = "prev-page";
        prevButton.onclick = () => fetchPage(currentPage - 1);
        pagination.appendChild(prevButton);
    }

    // 페이지 번호 버튼 추가
    for (let i = startPage; i <= endPage; i++) {
        const pageButton = document.createElement("button");
        pageButton.textContent = i;
        pageButton.className = i === currentPage ? "active-page" : "";
        pageButton.onclick = () => fetchPage(i);
        pagination.appendChild(pageButton);
    }

    // 다음 버튼 추가
    if (currentPage < totalPages) {
        const nextButton = document.createElement("button");
        nextButton.textContent = ">";
        nextButton.className = "next-page";
        nextButton.onclick = () => fetchPage(currentPage + 1);
        pagination.appendChild(nextButton);
    }
}

// 상세내역 보기 버튼 클릭 시 호출되는 함수
function showDetails(reservationNo) {
    //console.log("상세내역 클릭됨, 예약 번호:", reservationNo);
    window.location.href = `/user/myPage/reservationDetails?reservationNo=${reservationNo}`;
}

// 결제내역 보기 버튼 클릭 시 호출되는 함수
function showPaymentDetails(paymentNo) {
    //console.log("결제내역 클릭됨, 결제 번호:", paymentNo);
    window.location.href = `/user/myPage/reservationPaymentDetails?paymentNo=${paymentNo}`;
}

// 초기 데이터 로드
document.addEventListener("DOMContentLoaded", () => fetchPage(1));
