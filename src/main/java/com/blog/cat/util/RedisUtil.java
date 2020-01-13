package com.blog.cat.util;

import com.blog.cat.common.exception.CommonExceptionEnum;
import com.blog.cat.common.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: RedisUtil
 * @Author: Yangyang
 * @Date: 2019/6/18
 * @Description: TODO
 */

@Component
public class RedisUtil {

    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    private StringRedisTemplate template = null;

    /**
     * 存入token到 Redis 并设置失效时间
     * @param key uid
     * @param value uid对应的token
     * @param timeout 失效时间(毫秒 ms)
     * @return boolean true: 存入成功;
     * @throws UserException .REDIS_SERVER_ERR 存入失败
     */
    public boolean setRedisWithTimeOut(String flag,String key, String value, long timeout) throws UserException {
        try {
            template.opsForValue().set( flag+":"+key,value,timeout,TimeUnit.MILLISECONDS);
            return true;
        }catch (Exception e){
            if(e instanceof RedisConnectionFailureException) {
                e.printStackTrace();
                throw new UserException(CommonExceptionEnum.REDIS_SERVER_ERR);
            }else{
                e.printStackTrace();
                throw e;
            }
        }
    }

    /**
     * 获得token
     * @param key User.uid
     * @return String token
     * @throws Exception
     */
    public String getKey(String flag,String key)throws Exception{
        String val= template.opsForValue().get( flag+":"+key);
        return val == null? "": val;
    }

    public boolean deleteKey(String flag, String key){
        return template.delete(flag+":"+key);
    }

}
