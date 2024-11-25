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
    window.location.href = '/user/myPage/myReservations';
}

function goToRefunds() {
    window.location.href = '/user/myPage/refunds';
}

function goToUpdateInfo() {
    window.location.href = "/user/mypage/myinfo"
}

function logout() {
    Swal.fire({
        icon: 'info',
        title: '로그아웃',
        text: '정말 로그아웃 하시겠습니까?',
        showCancelButton: true,
        confirmButtonText: '네, 로그아웃',
        cancelButtonText: '취소',
        confirmButtonColor: '#007bff',
        cancelButtonColor: '#dc3545'
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire({
                icon: 'success',
                title: '로그아웃 완료',
                text: '성공적으로 로그아웃되었습니다.',
                confirmButtonColor: '#28a745'
            }).then(() => {
                window.location.href = '/logout';
            });
        }
    });
}

function goToLogin() {
    window.location.href = '/login';
}
