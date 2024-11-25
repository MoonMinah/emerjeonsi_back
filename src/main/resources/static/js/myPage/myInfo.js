document.addEventListener("DOMContentLoaded", function () {
    axios.get('/api/user/mypage/myinfo')
        .then(response => {
            const user = response.data;
            console.log("불러온 사용자 정보 : ", user);

            document.querySelector("input[name='userId']").value = user.userId;
            document.querySelector("input[name='password']").value = user.password;
            document.querySelector("input[name='email']").value = user.email;
            document.querySelector("input[name='userName']").value = user.userName;
            document.querySelector(`input[name='gender'][value='${user.gender}']`).checked = true;
            document.querySelector("input[name='birthday']").value = user.birthday;
            document.querySelector("input[name='phone']").value = user.phone;
        })
        .catch(error => {
            console.error("회원 정보를 불러오는 중 에러 발생 : ", error);
            Swal.fire({
                icon: 'error',
                title: '에러 발생',
                text: '회원 정보를 불러오는 중 문제가 발생했습니다.',
                confirmButtonColor: '#dc3545'
            });
        });

    // 수정 버튼 클릭 시, 실행되는 함수
    const form = document.getElementById('myInfoForm');
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData(form);

        // FormData -> JSON 변환
        const data = {};
        formData.forEach((value, key) => {
            if(key !== 'userId' && key !== 'password') {
                data[key] = value;
            }
        });

        console.log("보낼 데이터 : ", data);

        // 수정된 정보 서버로 전송
        axios.put('/api/user/mypage/myinfo', data)
            .then(response => {
                Swal.fire({
                    icon: 'success',
                    title: '수정 완료',
                    text: '회원 정보가 성공적으로 수정되었습니다.',
                    confirmButtonColor: '#28a745'
                }).then(() => {
                    window.location.href = '/home';
                });
            })
            .catch(error => {
               if(error.response) {
                   if(error.response.status === 400) {
                       // 유효성 검사 실패 시, 오류 메시지 처리
                       const errorMessages = error.response.data;
                       displayValidationErrors(errorMessages);
                   } else {
                       Swal.fire({
                           icon: 'error',
                           title: '서버 오류',
                           text: '서버에서 문제가 발생했습니다.',
                           confirmButtonColor: '#dc3545'
                       });
                   }
               } else {
                   alert("서버 오류가 발생했습니다.");
               }
            });
    });
});

// 유효성 검사 오류 메시지 표시
function displayValidationErrors(errorMessages) {
    console.log("서버에서 받은 오류 메시지 : ", errorMessages);

    const errorElements = document.querySelectorAll('.error-message');
    errorElements.forEach(element => {
        element.textContent = '';
        element.classList.remove('show', 'text-danger');
    });

    const invalidFields = document.querySelectorAll('.is-invalid');
    invalidFields.forEach(field => {
        field.classList.remove('is-invalid');
    });

    for (let field in errorMessages) {
        const message = errorMessages[field];
        console.log('검사 중인 필드 :', field);

        // 'userId'와 'password'는 수정 불가능한 필드이므로, 유효성 검사에서 제외
        if (field === 'userId' || field === 'password') {
            continue;
        }

        // 여기서 '0', '1' 등 숫자형 키를 텍스트로 변환하거나 필드명을 맞추도록 수정
        const fieldErrorElement = document.getElementById(`${field}Error`); // 숫자형을 문자열로 변환
        console.log('필드 요소 : ', fieldErrorElement);

        if (fieldErrorElement) {
            fieldErrorElement.textContent = message;
            fieldErrorElement.classList.add('show');

            const fieldElement = document.getElementById(field);
            if(fieldElement) {
                fieldElement.classList.add('is-invalid');
            }
        } else {
            console.warn(`오류 필드를 찾을 수 없습니다: ${field}`);
        }
    }
}

function confirmDelete() {
    Swal.fire({
        icon: 'warning',
        title: '회원 탈퇴',
        text: '정말로 탈퇴하시겠습니까? 이 작업은 되돌릴 수 없습니다.',
        showCancelButton: true,
        confirmButtonColor: '#dc3545',
        cancelButtonColor: '#6c757d',
        confirmButtonText: '탈퇴하기',
        cancelButtonText: '취소'
    }).then((result) => {
        if(result.isConfirmed) {
            axios.put('/api/user/mypage/withdrawal')
                .then(response => {
                    Swal.fire({
                        icon: 'success',
                        title: '탈퇴 완료',
                        text: '회원 탈퇴가 완료되었습니다.',
                        confirmButtonColor: '#28a745'
                    }).then(() => {
                        window.location.href = '/logout';
                    });
                })
                .catch(error => {
                    console.error('회원 탈퇴 중 에러 발생: ', error);

                    Swal.fire({
                        icon: 'error',
                        title: '탈퇴 실패',
                        text: '회원 탈퇴 처리 중 문제가 발생했습니다. 다시 시도해주세요.',
                        confirmButtonColor: '#dc3545'
                    });
                });
        }
    });
}

// 검색 조건이 설정되지 않았을 때 경고 메시지 표시
function checkFilter() {
    if (!selectedFilter) {
        Swal.fire({
            icon: 'warning',
            title: '경고',
            text: '설정 아이콘을 클릭해 검색 조건을 설정해주세요.',
            confirmButtonColor: '#ffc107'
        });
    }
}

// 모달 설정 함수
function settingModal() {
    document.getElementById('filterModal').style.display = 'flex';
}

// 모달 닫기 함수
function closeModal() {
    document.getElementById('filterModal').style.display = 'none';
}

// 필터 적용 함수
function applyFilter() {
    // 라디오 버튼에서 선택된 값 가져오기
    const selectedFilter = document.querySelector('input[name="option"]:checked')?.value;
    const searchInput = document.querySelector('.modal input[type="text"]').value.trim();

    if (!selectedFilter || !searchInput) {
        Swal.fire({
            icon: 'error',
            title: '필터 조건 부족',
            text: '필터 조건과 검색어를 입력해주세요.',
            confirmButtonColor: '#dc3545'
        });

        return;
    }

    // 필터 검색 API 호출
    axios.get('/api/home/data/search', {
        params: { selectedFilter, searchInput }
    })
        .then(response => {
            data = response.data;
            renderExhibitions(data); // 필터 결과 렌더링
            closeModal(); // 모달 닫기
        })
        .catch(error => {
            console.error("Error searching exhibitions:", error);

            Swal.fire({
                icon: 'error',
                title: '검색 실패',
                text: '데이터 검색 중 문제가 발생했습니다.',
                confirmButtonColor: '#dc3545'
            });
        });
}