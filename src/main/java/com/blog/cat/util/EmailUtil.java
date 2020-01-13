package com.blog.cat.util;

import com.blog.cat.common.exception.CommonExceptionEnum;
import com.blog.cat.common.exception.UserException;
import com.blog.cat.common.type.CommonReturnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender mailSender;

    private static Logger logger = LoggerFactory.getLogger(EmailUtil.class);


    public CommonReturnType sendMail(String to, String verifyCode) throws UserException {
        try {
            mailSender.send(createMessage(to,verifyCode));
            return new CommonReturnType("发送邮件成功");
        }catch (Exception e){
            logger.info("发送Email失败 :" + e.getLocalizedMessage());
            throw new UserException(CommonExceptionEnum.SEND_VERIFY_MAIL_FAIL);
        }
    }

    public SimpleMailMessage createMessage(String to, String verifyCode){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("329640258@qq.com");
        message.setTo(to);
        message.setSubject("CatBlog: 注册验证");
        String text = "亲爱的用户您好: \n"+
                "   您正在注册CatBlog的账号，您的注册码是:"+verifyCode;
        message.setText(text);
        return message;
    }
}
