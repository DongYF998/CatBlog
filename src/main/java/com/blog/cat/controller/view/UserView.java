package com.blog.cat.controller.view;

import lombok.Data;

/**
 * @author yangyang
 */
@Data
public class UserView {
    private String uid;
    private String pwd;
    private String email;
    private String nickname;
    private String verifyCode;
}
