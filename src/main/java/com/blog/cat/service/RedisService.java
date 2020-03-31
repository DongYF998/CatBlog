package com.blog.cat.service;

import com.blog.cat.common.exception.UserException;

/**
 * @Description: 实现Redis服务，Rdeis事务接口
 * @Date: 2020/3/27
 * @Author: DongYiFan
 */
public interface RedisService {

    /**
     * 推送验证码到邮箱, 保存验证码到Redis服务器
     * @param email
     * @param verify
     * @param timeout
     * @return
     */
    boolean setEmailVerify(String email, String verify, long timeout) throws UserException;

    /**
     * 保存token到Redis服务器
     * @param uid
     * @param token
     * @param timeout
     * @return
     */
    boolean setToken(String uid, String token, long timeout) throws  Exception;

    /**
     * 取Redis
     * @param flag
     * @param key
     * @return
     * @throws Exception
     */
    public String getKey(String flag,String key) throws Exception;
}
