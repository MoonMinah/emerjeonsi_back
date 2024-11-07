
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>숙소 결제</title>
    <!-- 포트원 결제 라이브러리 -->
    <script src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
    <!-- jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
        }
        .container {
            width: 90%;
            max-width: 800px;
            margin: 20px auto;
        }
        .accommodation-info-container {
            display: flex;
            justify-content: space-between;
            border: 1px solid #ddd;
            border-radius: 10px;
            padding: 20px;
            background-color: #f9f9f9;
            margin-bottom: 20px;
        }
        .accommodation-info {
            width: 60%; /* 왼쪽에 정보를 표시할 영역 */
        }
        .accommodation-image {
            width: 35%; /* 오른쪽에 이미지를 표시할 영역 */
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .accommodation-image img {
            max-width: 100%;
            border-radius: 10px;
        }
        .payment-box {
            border: 1px solid #ddd;
            border-radius: 10px;
            padding: 20px;
            background-color: #f9f9f9;
            margin-bottom: 20px;
        }
        .btn-primary {
            background-color: #007bff;
            border: none;
            padding: 10px 20px;
            color: white;
            border-radius: 5px;
            cursor: pointer;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        .payment-method-icons {
            margin-top: 10px;
            margin-bottom: 10px;
        }
        .payment-method-icons img {
            width: 40px;
            margin-right: 10px;
        }
        .custom-select-box {
            display: flex;
            align-items: center;
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 10px;
            width: 100%;
            max-width: 500px; /* 원하는 만큼 select box의 크기를 조정 */
            position: relative;
        }
        .custom-select-box select {
            flex: 1;
            border: none;
            background: transparent;
            font-size: 16px;
            padding: 10px;
            appearance: none;
            -webkit-appearance: none;
            -moz-appearance: none;
        }
        .custom-select-box select:focus {
            outline: none;
        }
        .payment-icon {
            width: 36px;
            height: 36px;
            margin-right: 10px;
        }
        /* select 박스의 오른쪽에 선택 표시 추가 */
        .custom-select-box::after {
            content: '▼';
            font-size: 18px;
            position: absolute;
            right: 15px;
            pointer-events: none;
            color: #333;
        }
        @media (max-width: 768px) {
            .container {
                width: 100%;
            }
            .accommodation-info-container {
                flex-direction: column;
            }
            .accommodation-image {
                margin-top: 20px;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <!-- 숙소 정보 및 이미지 영역 -->
    <div class="accommodation-info-container">
        <div class="accommodation-info">
            <h1>결제</h1>
            <h1>행복한 해방</h1>
            <p>연령 옵션: 아동</p>
            <p>티켓 가격: 1,000(원)</p>
            <p>수량: 1매</p>
            <p>이용자 이름: 강성준</p>
            <p>휴대폰 번호: 010-4454-1274</p>
            <p>이메일 주소: xian980@naver.com</p>
            <p>총 결제 금액:1,000(원)</p>
        </div>

        <div class="accommodation-image">
            <img src="https://a0.muscache.com/im/pictures/49d6005d-dcae-41cf-b2b5-70338d80e3e3.jpg?im_w=720" alt="숙소 이미지">
        </div>
    </div>

    <!-- 결제 정보 박스 -->
    <div class="payment-box">
        <h3>요금 세부정보</h3>

        <p>₩<fmt:formatNumber value="${accommodation.pricePerNight}" minFractionDigits="0"/> x ${guests}명 x ${checkin} ~ ${checkout} = ₩<fmt:formatNumber value="${totalPrice}" minFractionDigits="0"/></p>

        <p>총 합계: ₩<fmt:formatNumber value="${totalPrice}" minFractionDigits="0"/>원</p>

        <!-- 결제 수단 선택 -->
        <h3>결제 수단</h3>
        <div class="payment-method-icons">
            <img src="https://upload.wikimedia.org/wikipedia/commons/0/04/Visa.svg" alt="Visa">
            <img src="https://upload.wikimedia.org/wikipedia/commons/a/a4/Mastercard_2019_logo.svg" alt="MasterCard">
        </div>

        <div class="custom-select-box">
            <img id="currentPaymentIcon" class="payment-icon" src="/images/payment_icon_yellow_medium.png" alt="KakaoPay">
            <select id="paymentMethodSelect" class="form-control">
                <option value="kakaopay" data-icon="/images/payment_icon_yellow_medium.png">카카오페이</option>
                <option value="inicis" data-icon="/images/ci_KG_JPG.jpg">KG이니시스</option>
            </select>
        </div>
        <br>

        <!-- 결제하기 버튼 -->
        <button id="payment" class="btn btn-primary">결제하기</button>
    </div>
</div>

<form id="paymentForm" action="/paymentOk" method="post">
    <!-- 예약 정보 관련 hidden 필드 -->

    <input type="hidden" name="userNo" value="1">
    <input type="hidden" name="exhibitionNo" value="1">
    <input type="hidden" name="reservationNo" value="1">
    <input type="hidden" name="reservationPrice" value="1000">
    <input type="hidden" name="reservationQuantity" value="1">


    <!-- 결제 정보 관련 hidden 필드 -->
    <input type="hidden" name="paymentMethod" id="paymentMethod">
    <input type="hidden" name="paymentStatus" value="성공">
    <input type="hidden" name="paymentAmount" value="1000">
</form>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        // 포트원 라이브러리가 로드되었는지 확인
        if (!window.IMP) {
            console.error("PortOne 라이브러리가 로드되지 않았습니다.");
            return;
        }

        let IMP = window.IMP; // Iamport 객체 초기화
        IMP.init("0000"); // PortOne에서 발급된 실제 가맹점 식별코드로 대체

        // 결제 수단 변경 시 아이콘 변경 기능 추가
        const paymentMethodSelect = document.getElementById('paymentMethodSelect');
        const paymentIcon = document.getElementById('currentPaymentIcon');

        paymentMethodSelect.addEventListener('change', function() {
            const selectedOption = this.options[this.selectedIndex];
            const iconUrl = selectedOption.getAttribute('data-icon');
            paymentIcon.src = iconUrl; // 선택된 결제 수단의 아이콘을 동적으로 변경
        });

        // 결제 버튼 클릭 시 결제창 호출
        const buyButton = document.getElementById('payment');
        buyButton.addEventListener('click', function () {
            let paymentMethod = paymentMethodSelect.value; // 선택된 결제 수단
            document.getElementById('paymentMethod').value = paymentMethod; // hidden 필드에 결제 수단 값 설정
            processPayment(paymentMethod);
        });

        function processPayment(paymentMethod) {
            let paymentPG;
            switch (paymentMethod) {
                case 'kakaopay':
                    paymentPG = 'kakaopay';
                    break;
                case 'inicis':
                    paymentPG = 'html5_inicis';
                    break;
                default:
                    return;
            }

            let today = new Date();
            let makeMerchantUid = 'ORD' + today.getTime();  // 주문번호 생성

            IMP.request_pay({
                pg: paymentPG,
                pay_method: 'card',
                merchant_uid: makeMerchantUid,  // 주문번호
                name: '행복한 해방',  // 상품명
                amount: 1000,  // 결제 금액
                buyer_email: 'xian12@naver.com', //예약자 이메일
                buyer_name: 'sungjun' //예약자 이름
            }, function (rsp) {
                if (rsp.success) {
                    // 결제 성공 시 서버에 결제 정보 전송
                    document.getElementById('paymentForm').submit();
                } else {
                    alert("결제에 실패했습니다: " + rsp.error_msg);
                }
            });
        }
    });
</script>
</body>
</html>
