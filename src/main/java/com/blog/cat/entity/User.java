package com.blog.cat.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private String uid;
    private String pwd;
    private long phone;
    private String email;
    private String nickname;
    private int sex;
    private Date birth;
    private int level;
    private int role;
}
