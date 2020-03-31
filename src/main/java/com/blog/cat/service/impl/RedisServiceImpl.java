package com.blog.cat.service.impl;

import com.blog.cat.common.exception.CommonExceptionEnum;
import com.blog.cat.common.exception.UserException;
import com.blog.cat.service.RedisService;
import com.blog.cat.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Description: 实现RedisService的接口
 * @Date: 2020/3/27
 * @Author: DongYiFan
 */

@Service
public class RedisServiceImpl implements RedisService {

    private EmailUtil emailUtil;

    private StringRedisTemplate srt;


    @Override
    public boolean setEmailVerify(String email, String verify, long timeout) throws UserException {
        //开启Redis事务权限
        srt.setEnableTransactionSupport(true);
        try {
            //开启事务
            srt.multi();
            srt.opsForValue().set("email"+":"+email, verify, timeout, TimeUnit.MILLISECONDS);
            emailUtil.sendMail(email, verify);
            //成功时提交
            srt.exec();
            return true;
        }catch (Exception e) {
            srt.discard();
            e.printStackTrace();
            if(e instanceof  RedisConnectionFailureException) {
                throw new UserException(CommonExceptionEnum.REDIS_SERVER_ERR);
            }
            return  false;
        }
    }

    @Override
    public boolean setToken(String uid, String token, long timeout) throws Exception {
        try {
            srt.opsForValue().set( "token"+":"+uid,token,timeout,TimeUnit.MILLISECONDS);
        }catch (Exception e){
            if(e instanceof RedisConnectionFailureException) {
                e.printStackTrace();
                throw new UserException(CommonExceptionEnum.REDIS_SERVER_ERR);
            }else{
                e.printStackTrace();
                throw e;
            }
        }
        return  false;
    }

    @Override
    public String getKey(String flag, String key) throws Exception {
        String val= srt.opsForValue().get( flag+":"+key);
        return val == null? "": val;
    }

    @Autowired
    public void setEmailUtil(EmailUtil emailUtil) {
        this.emailUtil = emailUtil;
    }

    @Autowired
    public void setSrt(StringRedisTemplate srt) {
        this.srt = srt;
    }
}
