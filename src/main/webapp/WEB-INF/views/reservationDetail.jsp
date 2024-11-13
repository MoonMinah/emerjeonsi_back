<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>예매 상세</title>
</head>
<script src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<body>
<div class="container">
    <h1>예매 상세 정보</h1>
    <p>전시 번호: ${exhibitionNo}</p>
    <p>가격: ${reservationPrice}원</p>
    <p>수량: ${reservationQuantity}매</p>
    <p>총 금액: ${totalPrice}원</p>
</div>
<h3>결제 수단</h3>
<div class="custom-select-box">
    <img id="currentPaymentIcon" class="payment-icon" src="/images/payment_icon_yellow_medium.png" alt="KakaoPay">
    <select id="paymentMethodSelect" class="form-control">
        <option value="kakaopay" data-icon="/images/payment_icon_yellow_medium.png">카카오페이</option>
        <option value="inicis" data-icon="/images/ci_KG_JPG.jpg">KG이니시스</option>
    </select>
</div>
<br>
<button id="payment" class="btn btn-primary">결제하기</button>
<script>
    const exhibitionNo = "${exhibitionNo}";
    const reservationPrice = "${reservationPrice}";
    const reservationQuantity = "${reservationQuantity}";
    const totalPrice = "${totalPrice}";
</script>
<script src="/js/reservation/reservation_Detail.js"></script>
</body>
</html>
