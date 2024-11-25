package com.kosa.emerjeonsiBack.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserHistory {
    private int userHistoryNo;
    private int userNo;
    private String userStatus;
    private LocalDateTime userEventTimestamp;
    private LocalDateTime retentionUntil;
}
