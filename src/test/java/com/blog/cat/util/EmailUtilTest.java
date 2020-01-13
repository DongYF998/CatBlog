package com.blog.cat.util;

import com.blog.cat.common.exception.UserException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmailUtilTest {

    @Autowired
    private EmailUtil emailUtil;

    @Test
    public void sendMail() {
        try {
            emailUtil.sendMail("329640258@qq.com","566322");
        } catch (UserException e) {
            e.printStackTrace();
        }
    }
}