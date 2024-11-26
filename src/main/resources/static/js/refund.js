function goBack() {
    window.history.back();
}

const paymentNo = new URLSearchParams(window.location.search).get('paymentNo');

// 환불 데이터 가져오기
function fetchRefundData() {
    axios.get(`/api/user/myPage/refund`, { params: { paymentNo } })
        .then(response => {
            const data = response.data;
            //console.log("환불 데이터:", data);


            // 전시 정보 렌더링
            const exhibition = data.exhibition;
            document.getElementById('refund-image').src = exhibition.imageUrl || 'img/kumho.png';
            document.getElementById('exhibition-title').textContent = exhibition.title || '제목 없음';
            document.getElementById('exhibition-institution').textContent = exhibition.cntcInsttNm || '기관 정보 없음';
            document.getElementById('exhibition-period').textContent = `${exhibition.startPeriod} ~ ${exhibition.endPeriod}` || '기간 없음';

            // 예매 정보 렌더링
            document.getElementById('reservation-price').textContent = `${data.reservationPrice.toLocaleString()}원` || '예매 금액 없음';

            // 결제 정보 렌더링
            const payment = data.payment;
            document.getElementById('payment-price').textContent = `${payment.paymentPrice.toLocaleString()}원` || '-';
            document.getElementById('payment-method').textContent = payment.paymentMethod || '-';
            document.getElementById('payment-datetime').textContent = payment.formattedPaymentDate || '결제 일시 없음';

            // 최종 환불 금액 렌더링
            document.getElementById('refund-amount').textContent = `${payment.paymentPrice.toLocaleString()}원` || '-';

            // 환불 버튼 이벤트 추가
            const refundBtn = document.getElementById('refund-btn');
            refundBtn.addEventListener('click', () => {
                confirmRefund(payment.paymentNo, data.reservationNo, payment.paymentUid, payment.paymentPrice);
                // processRefund(payment.paymentNo, data.reservationNo, payment.paymentUid, payment.paymentPrice);
            });
        })
        .catch(error => {
            //console.error("환불 데이터 가져오기 실패:", error);
            alert("환불 데이터를 가져오는 중 오류가 발생했습니다.");
        });
}


// 사용자 확인 모달 창
function confirmRefund(paymentNo, reservationNo, paymentUid, paymentPrice) {
    Swal.fire({
        title: '환불 진행 확인',
        text: '환불을 진행하시겠습니까?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: '확인',
        cancelButtonText: '취소',
    }).then(result => {
        if (result.isConfirmed) {
            // 확인 버튼 클릭 시 환불 진행
            processRefund(paymentNo, reservationNo, paymentUid, paymentPrice);
        }
    });
}

// 환불 처리 함수
function processRefund(paymentNo, reservationNo, paymentUid, paymentPrice) {
    //console.log("환불 진행 시작 - paymentNo:", paymentNo, "reservationNo:", reservationNo, "paymentUid:", paymentUid, "paymentPrice:", paymentPrice);

    axios.post('/api/user/myPage/processRefund', {
        paymentNo,
        reservationNo,
        imp_uid: paymentUid,
        amount: paymentPrice
    })
        .then(response => {
            Swal.fire({
                title: '환불 성공',
                text: '환불이 성공적으로 처리되었습니다!',
                icon: 'success',
                confirmButtonText: '확인'
            }).then(result => {
                if (result.isConfirmed) {
                    // 사용자가 확인 버튼을 누른 후 실행
                    window.location.href = "/user/myPage/myReservations";  // 예매 목록 페이지로 이동
                }
            });
        })
        .catch(error => {
            //console.error("환불 요청 실패:", error);
            Swal.fire({
                title: '환불 실패',
                text: '환불 요청 처리 중 문제가 발생했습니다.',
                icon: 'error',
                confirmButtonText: '확인'
            });
        });
}




// function processRefund(paymentNo, reservationNo, paymentUid, paymentPrice) {
//     console.log("paymentNo : " + paymentNo + ", reservationNo : " + reservationNo + ", paymentUid : " + paymentUid + ", paymentPrice : " + paymentPrice);
//
//     axios.post('/api/user/myPage/processRefund', {
//         paymentNo,                 // 숫자 그대로 전달
//         reservationNo,             // 숫자 그대로 전달
//         imp_uid: paymentUid,
//         amount: paymentPrice       // 숫자 그대로 전달
//     })
//         .then(response => {
//             Swal.fire({
//                 title: '환불 진행 확인',
//                 text: '환불을 진행하시겠습니까?',
//                 icon: 'warning',
//                 showCancelButton: true,
//                 confirmButtonText: '확인',
//                 cancelButtonText: '취소',
//             }).then(result => {
//                 if (result.isConfirmed) {
//                     // 사용자가 확인 버튼을 누른 후 실행
//                     window.location.href = "/user/myPage/myReservations";  // 예매 목록 페이지로 이동
//                 }
//             });
//         })
//         .catch(error => {
//             console.error("환불 요청 실패:", error);
//             Swal.fire({
//                 title: '환불 실패',
//                 text: '환불 요청 처리 중 문제가 발생했습니다.',
//                 icon: 'error',
//                 confirmButtonText: '확인'
//             });
//         });
// }


// 페이지 로드 시 데이터 가져오기
document.addEventListener('DOMContentLoaded', fetchRefundData);




