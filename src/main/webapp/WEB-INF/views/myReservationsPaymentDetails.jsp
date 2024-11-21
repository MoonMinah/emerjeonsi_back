<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <title>결제 내역</title>
    <link rel="stylesheet" href=/css/myReservationsPaymentDetails.css?after" type="text/css">
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
</head>
<body>
<div class="header">
    <button class="back-btn" onclick="history.back()">←</button>
    <span>결제 내역</span>
</div>
<div class="payment-container">

    <script type="module" src="/js/myPage/myReservationPaymentDetail.js" ></script>

</body>
</html>
