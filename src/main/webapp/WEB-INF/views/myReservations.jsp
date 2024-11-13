<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <title>나의 예약목록</title>
    <link rel="stylesheet" href="css/myReservations.css">
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
</head>
<body>
<div class="header">
    <img src="img/logo.png" alt="Logo" class="logo">
    <span>예약목록</span>
</div>
<div class="reservation-list">
    <c:forEach var="reservation" items="${myReservations}">
        <c:if test="${reservation.reservationStatus == '예매완료' && reservation.payment.paymentStatus == '결제성공'}">
            <div class="reservation-item">
                <img src="${reservation.exhibition.imageUrl != null ? reservation.exhibition.imageUrl : 'img/default.png'}" alt="전시 이미지" class="reservation-image">

                <div class="reservation-info">
                    <h3>${reservation.exhibition.title}</h3>
                    <p>${reservation.exhibition.cntcInsttNm}</p>
                    <%--         <p>[유효기간] ${reservation.exhibition.startPeriodStr} - ${reservation.exhibition.endPeriodStr}</p>            --%>

                    <%--       <p>${reservation.reservationQuantity > 1 ? '수량 : ' + reservation.reservationQuantity + '매' : ''}</p>              --%>

                    <p>가격: <fmt:formatNumber value="${reservation.reservationPrice}" pattern="#,###"/>원</p>

                    <div class="buttons">
                        <button class="detail-btn" onclick="showDetails(${reservation.reservationNo})">상세내역</button>
                        <button class="payment-btn" onclick="showPaymentDetails(${reservation.payment.paymentNo})">결제내역</button>
                    </div>
                </div>
            </div>
        </c:if>
    </c:forEach>
</div>
    <!-- 추가 예약 항목들 -->
<%--
    <c:forEach var="reservation" items="${myReservations}">
        <div class="reservation-card">
            <p>전시 번호: ${reservation.exhibitionNo}</p>
            <p>예약 가격: ${reservation.reservationPrice}원</p>
            <p>예약 수량: ${reservation.reservationQuantity}매</p>
            <p>총 금액: ${reservation.reservationPrice * reservation.reservationQuantity}원</p>
            <p>예약 상태: ${reservation.reservationStatus}</p>
        </div>
    </c:forEach>
    --%>
</div>
<div class="bottom-nav">
    <button>Home</button>
    <button>Explore</button>
    <button>Menu</button>
</div>
</body>
<script>

    // 예약 번호와 결제 번호는 각각의 버튼 클릭 시 동적으로 할당됩니다.
    function showDetails(reservationNo) {
        console.log("예매 번호 : " + reservationNo);
        // 상세내역 버튼 클릭 시, axios로 상세 내역에 필요한 데이터 전송
        axios.get(`/api/myPage/reservationDetail`, {
            params: {
                reservationNo: reservationNo
            }
        })
            .then(response => {
                console.log("예매번호 : " + reservationNo);
                window.location.href = '/reservationDetails?reservationNo=' + reservationNo;
            })
            .catch(error => {
                alert("페이지 이동 중 오류가 발생하였습니다.");
                console.error(error);
            });
    }

    function showPaymentDetails(paymentNo) {
        console.log("결제 번호 : " + paymentNo);
        // 결제내역 버튼 클릭 시, axios로 결제 내역에 필요한 데이터 전송
        axios.get(`/api/myPage/reservationPaymentDetail`, {
            params: {
                paymentNo: paymentNo
            }
        })
            .then(response => {
                console.log("결제번호 : " + paymentNo);
                window.location.href = '/reservationPaymentDetails?paymentNo=' + paymentNo;
            })
            .catch(error => {
                alert("페이지 이동 중 오류가 발생하였습니다.");
                console.error(error);
            });
    }
</script>
</html>
