function loadReservationDetail(reservationNo, exhibitionNo, additionalData) {
    axios.get(`/api/user/reservation/${reservationNo}`,{ withCredentials: true })
        .then(response => {
            const data = response.data;

            // DOM 업데이트
            updateReservationDetail({
                reservationNo: reservationNo,
                exhibitionNo: exhibitionNo,
                exhibitionTitle: additionalData.title || data.exhibitionTitle,
                reservationPrice: additionalData.price || data.reservationPrice,
                reservationQuantity: additionalData.quantity || data.reservationQuantity,
                adultCount: additionalData.adultCount || 0,
                infantCount: additionalData.infantCount || 0,
                seniorCount: additionalData.seniorCount || 0,
                imageUrl: additionalData.imageUrl || data.imageUrl,
            });
        })
        .catch(error => {
            console.error("데이터를 가져오는 중 오류 발생:", error);
        });
}

function updateReservationDetail(detail) {
    const imageElement = document.getElementById('exhibitionImage');
    if (imageElement) {
        imageElement.src = detail.imageUrl || 'default.jpg';
    } else {
        console.error("ID 'exhibitionImage' not found in DOM");
    }

    document.getElementById('exhibitionTitle').textContent = detail.exhibitionTitle || '제목 없음';
    document.getElementById('exhibitionNo').textContent = detail.exhibitionNo;
    document.getElementById('reservationPrice').textContent = `${detail.reservationPrice.toLocaleString()}원`;
    document.getElementById('reservationQuantity').textContent = `${detail.reservationQuantity}매`;
    document.getElementById('totalPrice').textContent = `${detail.reservationPrice.toLocaleString()}원`;

    const details = [];
    if (detail.adultCount > 0) details.push(`성인: ${detail.adultCount}매`);
    if (detail.infantCount > 0) details.push(`아동: ${detail.infantCount}매`);
    if (detail.seniorCount > 0) details.push(`노인: ${detail.seniorCount}매`);
    document.getElementById('selectedDetails').textContent = details.length > 0 ? details.join(', ') : '선택된 옵션이 없습니다.';
}

document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const reservationNo = urlParams.get('reservationNo');
    const exhibitionNo = urlParams.get('exhibitionNo');

    if (reservationNo) {
        loadReservationDetail(reservationNo, exhibitionNo, {
            adultCount: parseInt(urlParams.get('adult')) || 0,
            infantCount: parseInt(urlParams.get('infant')) || 0,
            seniorCount: parseInt(urlParams.get('senior')) || 0,
            price: parseInt(urlParams.get('price')) || 0,
            quantity: parseInt(urlParams.get('quantity')) || 0,
            title: decodeURIComponent(urlParams.get('title')) || '제목 없음',
            imageUrl: decodeURIComponent(urlParams.get('imageUrl')) || '',
        });
    }
});


document.addEventListener("DOMContentLoaded", function() {
    let IMP = window.IMP; // Iamport 객체 초기화
    IMP.init("imp01827560"); // PortOne 가맹점 식별코드

    const paymentMethodSelect = document.getElementById('paymentMethodSelect');
    const paymentIcon = document.getElementById('currentPaymentIcon');

    paymentMethodSelect.addEventListener('change', function() {
        const selectedOption = this.options[this.selectedIndex];
        const iconUrl = selectedOption.getAttribute('data-icon');
        paymentIcon.src = iconUrl;
    });
    document.getElementById('payment').addEventListener('click', function () {
        let paymentMethod = paymentMethodSelect.value;
        // URL에서 reservationNo 가져오기
        const reservationNo = new URLSearchParams(window.location.search).get('reservationNo');
        if (!reservationNo || reservationNo === '0') {
            alert("Reservation number is missing or invalid!");
            return;
        }
        processPayment(paymentMethod, reservationNo);
    });

    function isMobile() {
        const userAgent = navigator.userAgent || navigator.vendor || window.opera;
        return /android|iPad|iPhone|iPod/i.test(userAgent);
    }

    function processPayment(paymentMethod, reservationNo) {
        axios.get('/api/check-login') // 로그인된 사용자 정보 확인
            .then((response) => {
                const user = response.data; // 로그인된 사용자 정보
                if (!user) {
                    alert('로그인이 필요합니다. 로그인 페이지로 이동합니다.');
                    window.location.href = '/login';
                    return;
                }

                let paymentPG = paymentMethod === 'kakaopay' ? 'kakaopay' : 'html5_inicis';
                let makeMerchantUid = 'ORD' + new Date().getTime();
                // PortOne에 전달할 데이터
                const exhibitionTitle = document.getElementById('exhibitionTitle')?.textContent?.trim() || '제목 없음';
                const reservationPrice = parseInt(document.getElementById('reservationPrice')?.textContent.replace(/[^\d]/g, '')) || 0;
                const exhibitionNo = parseInt(document.getElementById('exhibitionNo')?.textContent || '');
                const reservationQuantity = parseInt(document.getElementById('reservationQuantity')?.textContent || '');

                IMP.request_pay({
                    pg: paymentPG,
                    pay_method: 'card',
                    merchant_uid: makeMerchantUid,
                    name: exhibitionTitle,
                    amount: reservationPrice,  // totalPrice 변수를 사용
                    buyer_email: user.email,
                    buyer_name: user.userName,
                    //...(isMobile() ? { m_redirect_url: "http://192.168.240.7:9400/myReservations" } : {})
                }, function (rsp) {
                    if (rsp.success) {
                        // 결제 성공 시 axios로 결제 정보 전송
                        axios.post(`/api/user/reservation-payment`, {
                            reservation: {
                                reservationNo: reservationNo,
                                userNo: user.userNo,
                                exhibitionNo: exhibitionNo,  // exhibitionNo 변수를 사용
                                reservationPrice: reservationPrice,  // reservationPrice 변수를 사용
                                reservationQuantity: reservationQuantity,  // reservationQuantity 변수를 사용
                                reservationStatus: "결제대기"
                            },
                            payment: {
                                reservationNo: reservationNo,
                                paymentUid: rsp.imp_uid,
                                paymentMethod: paymentMethod,
                                paymentStatus: "결제성공",
                                paymentPrice: reservationPrice  // totalPrice 변수를 사용
                            }
                        })
                            .then(response => {
                                alert("결제가 성공적으로 완료되었습니다.");
                                window.location.href = "/user/myPage/myReservations";
                            })
                            .catch(error => {
                                alert("결제 처리 중 오류가 발생했습니다.");
                                console.error(error);
                            });
                    } else {
                        alert("결제에 실패했습니다: " + rsp.error_msg);
                    }

                });
            })
            .catch((error) => {
                console.error('사용자 정보를 가져오는 중 오류 발생:', error);
                alert('로그인이 필요합니다. 로그인 페이지로 이동합니다.');
                window.location.href = '/login';
            });
    }
});
