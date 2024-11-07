package com.kosa.emerjeonsiBack.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    private int id;
    private String userId; //아이디
    private String name; //이름
    private String password; //비번
    private String phone; //휴대폰 번호
    private String email; //이메일

    public User(int id, String userId, String name, String password, String phone, String email) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }

}
