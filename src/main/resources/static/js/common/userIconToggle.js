
    document.addEventListener("DOMContentLoaded", function() {
    checkLoginStatus();
});

    function checkLoginStatus() {
    // 로그인 상태 확인
    axios.get('/api/check-login')
        .then(response => {
            const userMenu = document.getElementById('userMenu');
            userMenu.innerHTML = `
                    <li onclick="goToUpdateInfo()">정보 수정</li>
                    <li onclick="goToReservations()">예매 내역</li>
                    <li onclick="goToRefunds()">환불 내역</li>
                    <li onclick="logout()">로그아웃</li>
                `;
        })
        .catch(error => {
            // 로그인되지 않은 상태
            if (error.response && error.response.status === 401) {
                const userMenu = document.getElementById('userMenu');
                userMenu.innerHTML = `
                        <li onclick="goToLogin()">로그인</li>
                    `;
            } else {
                console.error("로그인 상태 확인 중 오류 발생:", error);
            }
        });
}

    function toggleMenu() {
    const menu = document.getElementById('userMenu');
    menu.style.display = menu.style.display === 'block' ? 'none' : 'block';
}

    function goToReservations() {
    alert('예매 목록 페이지로 이동합니다.');
    window.location.href = '/user/myPage/myReservations';
}

    function goToRefunds() {
    alert('환불 내역 페이지로 이동합니다.');
}

    function goToUpdateInfo() {
    alert('정보 수정 페이지로 이동합니다.');
}

    function logout() {
    alert('로그아웃되었습니다.');
    window.location.href = '/logout';
}

    function goToLogin() {
    window.location.href = '/login';
}
