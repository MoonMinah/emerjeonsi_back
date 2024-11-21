import { fetchPaymentDetails } from './myReservationServices.js';
import { renderPaymentDetails } from './myReservationHandlers.js';

document.addEventListener("DOMContentLoaded", () => {
    const paymentNo = new URLSearchParams(window.location.search).get('paymentNo');
    const container = document.querySelector('.payment-container');

    if (!paymentNo) {
        console.error("PaymentNo가 없습니다.");
        alert("잘못된 접근입니다.");
        return;
    }

    fetchPaymentDetails(
        paymentNo,
        data => renderPaymentDetails(container, data),
        error => alert("결제 내역 데이터를 가져오는 중 오류가 발생했습니다.")
    );
});