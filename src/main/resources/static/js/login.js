// 로그인 실패 시 오류 메시지
window.onload = function () {
    const urlParams = new URLSearchParams(window.location.search);

    if(urlParams.has('error')) {
        Swal.fire({
            icon: 'error',
            title: '로그인 실패',
            text: '아이디나 비밀번호가 잘못되었습니다. 다시 시도해 주세요.',
            timer: 5000,
            confirmButtonText: '확인'
        });

    }
}