package com.blog.cat.controller.view;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Date: 2020/4/8
 * @Author: DongYiFan
 */

@Data
public class UserInfo {
    private String uid;
    private long phone;
    private String email;
    private String nickname;
    private Date birth;
    private int level;
    private int gender;
    private int role;
    private String info;
    private String profession;
    private String birthStr;
}
