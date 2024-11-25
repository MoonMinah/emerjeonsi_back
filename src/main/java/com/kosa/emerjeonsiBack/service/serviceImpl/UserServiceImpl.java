package com.kosa.emerjeonsiBack.service.serviceImpl;

import com.kosa.emerjeonsiBack.dto.User;
import com.kosa.emerjeonsiBack.dto.UserHistory;
import com.kosa.emerjeonsiBack.mapper.UserHistoryMapper;
import com.kosa.emerjeonsiBack.mapper.UserMapper;
import com.kosa.emerjeonsiBack.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserHistoryMapper userHistoryMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserMapper userMapper, UserHistoryMapper userHistoryMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userMapper = userMapper;
        this.userHistoryMapper = userHistoryMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User selectUserByUserId(String userId) {
        return userMapper.selectUserByUserId(userId);
    }

    @Override
    public Boolean existsByUserId(String userId) {
        return userMapper.existsByUserId(userId);
    }

    @Override
    public int userInsert(User user) {
        return userMapper.userInsert(user);
    }

    @Override
    @Transactional
    public void signup(User user) {
        // 비밀번호 암호화
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        // 제공자 설정
        user.setProvider("origin");

        // TB_Users_Master에 user 정보 저장
        int resultUser = userMapper.userInsert(user);

        if(resultUser <= 0) {
            throw new RuntimeException("회원 정보 저장 실패");
        }

        // user에 저장된 userNo 가져오기
        int userNo = user.getUserNo();

        // TB_Users_History에 회원 이력 정보 저장
        UserHistory userHistory = new UserHistory();
        userHistory.setUserNo(userNo);
        userHistory.setUserStatus("활성");
        userHistory.setUserEventTimestamp(LocalDateTime.now());

        int resultUserHistory = userHistoryMapper.insertUserHistory(userHistory);

        if(resultUserHistory <= 0) {
            throw new RuntimeException("회원 이력 정보 저장 실패");
        }
    }

    @Override
    @Transactional
    public boolean updateUserInfoAndHistory(User user, User existingUser) {
        // 회원 정보 수정
        int resultUser = userMapper.updateUserInfo(user);

        if(resultUser <= 0) {
            throw new RuntimeException("회원 정보 수정 실패");
        }

        // 회원 이력 정보 저장
        UserHistory userHistory = new UserHistory();
        userHistory.setUserNo(existingUser.getUserNo());
        userHistory.setUserStatus("수정");
        userHistory.setUserEventTimestamp(LocalDateTime.now());

        int resultUserHistory = userHistoryMapper.insertUserHistory(userHistory);

        if(resultUserHistory <= 0) {
            throw new RuntimeException("회원 이력 정보 저장 실패");
        }

        return true;
    }

    @Override
    @Transactional
    public void withdrawUser(String userId) {
        // userId로 회원 정보 조회
        User resultUser = userMapper.selectUserByUserId(userId);

        // resultUser가 null이면 예외 처리
        if(resultUser == null) {
            throw new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
        }

        int userNo = resultUser.getUserNo();

        // 회원 상태를 '탈퇴'로 수정
        userMapper.updateUserStatus(userNo, "탈퇴");

        // 회원 이력 테이블에 탈퇴한 회원 정보 추가
        UserHistory userHistory = new UserHistory();
        userHistory.setUserNo(userNo);
        userHistory.setUserStatus("탈퇴");
        userHistory.setUserEventTimestamp(LocalDateTime.now());
        userHistory.setRetentionUntil(LocalDateTime.now().plusYears(5));

        int resultUserHistory = userHistoryMapper.insertUserHistory(userHistory);

        if(resultUserHistory <= 0) {
            throw new RuntimeException("회원 이력 정보 저장 실패");
        }
    }
}
