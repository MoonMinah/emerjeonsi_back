document.addEventListener("DOMContentLoaded", function() {
    let IMP = window.IMP; // Iamport 객체 초기화
    IMP.init(""); // PortOne 가맹점 식별코드

    const paymentMethodSelect = document.getElementById('paymentMethodSelect');
    const paymentIcon = document.getElementById('currentPaymentIcon');

    paymentMethodSelect.addEventListener('change', function() {
        const selectedOption = this.options[this.selectedIndex];
        const iconUrl = selectedOption.getAttribute('data-icon');
        paymentIcon.src = iconUrl;
    });
    document.getElementById('payment').addEventListener('click', function () {
        let paymentMethod = paymentMethodSelect.value;
        processPayment(paymentMethod);
    });

    function isMobile() {
        const userAgent = navigator.userAgent || navigator.vendor || window.opera;
        return /android|iPad|iPhone|iPod/i.test(userAgent);
    }

    function processPayment(paymentMethod) {
        let paymentPG = paymentMethod === 'kakaopay' ? 'kakaopay' : 'html5_inicis';
        let makeMerchantUid = 'ORD' + new Date().getTime();
        IMP.request_pay({
            pg: paymentPG,
            pay_method: 'card',
            merchant_uid: makeMerchantUid,
            name: '금호강과 길',
            amount: totalPrice,  // totalPrice 변수를 사용
            buyer_email: 'user@example.com',
            buyer_name: '홍길동',
             //...(isMobile() ? { m_redirect_url: "http://192.168.240.7:9400/myReservations" } : {})
        }, function (rsp) {
            if (rsp.success) {
                // 결제 성공 시 axios로 결제 정보 전송
                axios.post(`/api/reservation-payment`, {
                    reservation: {
                        userNo: 1,
                        exhibitionNo: exhibitionNo,  // exhibitionNo 변수를 사용
                        reservationPrice: reservationPrice,  // reservationPrice 변수를 사용
                        reservationQuantity: reservationQuantity,  // reservationQuantity 변수를 사용
                        reservationStatus: "결제대기"
                    },
                    payment: {
                        paymentUid: rsp.imp_uid,
                        paymentMethod: paymentMethod,
                        paymentStatus: "결제성공",
                        paymentPrice: totalPrice  // totalPrice 변수를 사용
                    }
                })
                    .then(response => {
                        alert("결제가 성공적으로 완료되었습니다.");
                        window.location.href = "/myReservations";
                    })
                    .catch(error => {
                        alert("결제 처리 중 오류가 발생했습니다.");
                        console.error(error);
                    });
            } else {
                alert("결제에 실패했습니다: " + rsp.error_msg);
            }
        });
    }
});
