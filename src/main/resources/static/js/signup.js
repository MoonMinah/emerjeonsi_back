document.getElementById('signUpForm').addEventListener('submit', function (event) {
    event.preventDefault();

    const formData = new FormData(event.target);
    const data = {
        userId: formData.get('userId'),
        password: formData.get('password'),
        email: formData.get('email'),
        userName: formData.get('userName'),
        gender: formData.get('gender'),
        birthday: formData.get('birthday'),
        phone: formData.get('phone')
    };

    // Axios를 이용하여 회원가입 진행
    axios.post('/api/signup', data)
        .then(response => {
            alert('회원가입이 완료되었습니다.');
            window.location.href = '/api/login';
        })
        .catch(error => {
            console.error('회원가입 중 오류 발생 : ', error);
            alert('회원가입 중 오류가 발생했습니다. 다시 시도해주세요.');
        });
});