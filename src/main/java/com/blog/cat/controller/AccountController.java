package com.blog.cat.controller;


import com.blog.cat.common.exception.UserException;
import com.blog.cat.service.UserService;
import com.blog.cat.util.TokenUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Date: 2020/4/8
 * @Author: DongYiFan
 */

@RestController
@RequestMapping("/api/account")
@CrossOrigin
public class AccountController {

    private UserService userService;

    private TokenUtil tokenUtil;

    @GetMapping("/get/userInfo")
    public void getUserInfo(HttpServletRequest request) throws UserException {
        String token = request.getHeader("token");
        String uid = tokenUtil.getUid(token);

    }
}
