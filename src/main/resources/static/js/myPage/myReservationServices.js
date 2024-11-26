// 예매 목록 데이터를 가져오는 함수
export function fetchReservations(page, size, onSuccess, onError) {
    axios.get('/api/user/myPage/reservations', { params: { page, size } })
        .then(response => {
            //console.log("예매 목록 데이터 : ", response.data);
            onSuccess(response.data);
        })
        .catch(error => {
            if (error.response) {
                //console.error("응답 오류:", error.response.status, error.response.data);
            } else if (error.request) {
                //console.error("요청 오류:", error.request);
            } else {
                //console.error("일반 오류:", error.message);
            }
            if (onError) onError(error);
        });
}

// 상세내역 데이터를 가져오는 함수
export function fetchReservationDetails(reservationNo, onSuccess, onError) {
    axios.get(`/api/user/myPage/reservationDetail`, { params: { reservationNo } })
        .then(response => {
            //console.log("상세내역 데이터 : ", response.data);
            onSuccess(response.data);
        })
        .catch(error => {
            if (error.response) {
                //console.error("응답 오류:", error.response.status, error.response.data);
            } else if (error.request) {
                //console.error("요청 오류:", error.request);
            } else {
                //console.error("일반 오류:", error.message);
            }
            if (onError) onError(error);
        });
}

// 결제내역 데이터를 가져오는 함수
export function fetchPaymentDetails(paymentNo, onSuccess, onError) {
    axios.get(`/api/user/myPage/reservationPaymentDetail`, { params: { paymentNo } })
        .then(response => {
            //console.log("결제내역 데이터 : ", response.data);
            onSuccess(response.data);
        })
        .catch(error => {
            if (error.response) {
                //console.error("응답 오류:", error.response.status, error.response.data);
            } else if (error.request) {
                //console.error("요청 오류:", error.request);
            } else {
                //console.log("오류 : " + error.message);
                //console.error("일반 오류:", error.message);
            }
            if (onError) onError(error);
        });
}
