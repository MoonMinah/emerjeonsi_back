function inferTicketCounts(totalPrice, adultPrice, infantPrice, seniorPrice, totalQuantity) {
    let result = { adultCount: 0, infantCount: 0, seniorCount: 0 };

    for (let adultCount = 0; adultCount <= totalQuantity; adultCount++) {
        for (let infantCount = 0; infantCount <= totalQuantity - adultCount; infantCount++) {
            const seniorCount = totalQuantity - adultCount - infantCount;

            const calculatedPrice =
                adultCount * adultPrice +
                infantCount * infantPrice +
                seniorCount * seniorPrice;

            if (calculatedPrice === totalPrice) {
                return { adultCount, infantCount, seniorCount };
            }
        }
    }

    console.error("유효한 조합을 찾지 못함");
    return result;
}

function loadReservationDetail(reservationNo) {
    const urlParams = new URLSearchParams(window.location.search);
    const adultFromUrl = parseInt(urlParams.get('adult')) || 0;
    const infantFromUrl = parseInt(urlParams.get('infant')) || 0;
    const seniorFromUrl = parseInt(urlParams.get('senior')) || 0;
    const imageUrlFromUrl = urlParams.get('imageUrl') || '';
    const titleFromUrl = urlParams.get('title') || '';

    axios.get(`/api/user/reservation/${reservationNo}`, { withCredentials: true })
        .then(response => {
            const data = response.data;

            let inferredCounts;
            if (adultFromUrl || infantFromUrl || seniorFromUrl) {
                // 새로운 예매: URL 기반 데이터 사용
                inferredCounts = {
                    adultCount: adultFromUrl,
                    infantCount: infantFromUrl,
                    seniorCount: seniorFromUrl,
                };
            } else {
                // 중복 예매: 백엔드 데이터 기반 유추
                inferredCounts = inferTicketCounts(
                    data.reservationPrice,
                    data.exhibition.adultPrice,
                    data.exhibition.infantPrice,
                    data.exhibition.seniorPrice,
                    data.reservationQuantity
                );
            }

            // 최종 데이터 결정
            updateReservationDetail({
                reservationNo: data.reservationNo,
                exhibitionNo: data.exhibition.exhibitionNo,
                exhibitionTitle: titleFromUrl || data.exhibition.title || '제목 없음',
                reservationPrice: data.reservationPrice || 0,
                reservationQuantity: data.reservationQuantity || 0,
                adultCount: inferredCounts.adultCount,
                infantCount: inferredCounts.infantCount,
                seniorCount: inferredCounts.seniorCount,
                imageUrl: imageUrlFromUrl || data.exhibition.imageUrl || '/images/default.jpg',
            });
        })
        .catch(error => {
            console.error("예약 데이터를 가져오는 중 오류 발생:", error);
            Swal.fire({
                title: "오류 발생",
                text: "예약 데이터를 불러오는 중 문제가 발생했습니다.",
                icon: "error",
                confirmButtonText: "확인",
            });
        });
}

function updateReservationDetail(detail) {
    document.getElementById('exhibitionImage').src = detail.imageUrl;
    document.getElementById('exhibitionTitle').textContent = detail.exhibitionTitle;
    document.getElementById('reservationPrice').textContent = `${detail.reservationPrice.toLocaleString()}원`;
    document.getElementById('reservationQuantity').textContent = `${detail.reservationQuantity}매`;
    // // 총 금액 업데이트
    // document.getElementById('totalPrice').textContent = `${detail.reservationPrice.toLocaleString()}원`;
    const details = [];
    if (detail.adultCount > 0) details.push(`성인: ${detail.adultCount}매`);
    if (detail.infantCount > 0) details.push(`아동: ${detail.infantCount}매`);
    if (detail.seniorCount > 0) details.push(`노인: ${detail.seniorCount}매`);
    document.getElementById('selectedDetails').textContent = details.join(', ') || '선택된 옵션이 없습니다.';
}

document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const reservationNo = urlParams.get('reservationNo');

    if (reservationNo) {
        loadReservationDetail(reservationNo);
    }
});







document.addEventListener("DOMContentLoaded", function() {
    let IMP = window.IMP; // Iamport 객체 초기화
    IMP.init("imp01827560"); // PortOne 가맹점 식별코드

    // const paymentMethodSelect = document.getElementById('paymentMethodSelect');
    // const paymentIcon = document.getElementById('currentPaymentIcon');
    //
    // paymentMethodSelect.addEventListener('change', function() {
    //     const selectedOption = this.options[this.selectedIndex];
    //     const iconUrl = selectedOption.getAttribute('data-icon');
    //     paymentIcon.src = iconUrl;
    // });
    // document.getElementById('payment').addEventListener('click', function () {
    //     let paymentMethod = paymentMethodSelect.value;
    //     // URL에서 reservationNo 가져오기
    //     const reservationNo = new URLSearchParams(window.location.search).get('reservationNo');
    //     if (!reservationNo || reservationNo === '0') {
    //         alert("Reservation number is missing or invalid!");
    //         return;
    //     }
    //     processPayment(paymentMethod, reservationNo);
    // });

    // 기존 셀렉트 박스 대신 카카오페이 버튼 클릭 이벤트 추가
    document.getElementById('payment').addEventListener('click', function () {
        const reservationNo = new URLSearchParams(window.location.search).get('reservationNo');
        if (!reservationNo || reservationNo === '0') {
            Swal.fire({
                title: "오류",
                text: "예매 실패",
                icon: "error",
                confirmButtonText: "확인"
            });
            return;
        }
        processPayment('kakaopay', reservationNo);
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
                    Swal.fire({
                        title: "로그인 필요",
                        text: "로그인이 필요합니다. 로그인 페이지로 이동합니다.",
                        icon: "warning",
                        confirmButtonText: "확인"
                    }).then(() => {
                        window.location.href = '/login';
                    });
                    return;
                }

                // let paymentPG = paymentMethod === 'kakaopay' ? 'kakaopay' : 'html5_inicis';
                 let paymentPG = 'kakaopay';

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
                    popup: true
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
                                Swal.fire({
                                    title: "결제 성공",
                                    text: "결제가 성공적으로 완료되었습니다.",
                                    icon: "success",
                                    confirmButtonText: "확인"
                                }).then(() => {
                                    window.location.href = "/user/myPage/myReservations";
                                });
                            })
                            .catch(error => {
                                Swal.fire({
                                    title: "결제 실패",
                                    text: "결제 처리 중 오류가 발생했습니다.",
                                    icon: "error",
                                    confirmButtonText: "확인"
                                });
                                console.error(error);
                            });
                    } else {
                        Swal.fire({
                            title: "결제 실패",
                            text: `결제에 실패했습니다: ${rsp.error_msg}`,
                            icon: "error",
                            confirmButtonText: "확인"
                        });
                    }

                });
            })
            .catch((error) => {
                console.error('사용자 정보를 가져오는 중 오류 발생:', error);
                Swal.fire({
                    title: "로그인 필요",
                    text: "로그인이 필요합니다. 로그인 페이지로 이동합니다.",
                    icon: "warning",
                    confirmButtonText: "확인"
                }).then(() => {
                    window.location.href = '/login';
                });
            });
    }
});
