package com.blog.cat.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.blog.cat.common.exception.CommonExceptionEnum;
import com.blog.cat.common.exception.UserException;
import com.blog.cat.dao.UserDao;
import com.blog.cat.entity.User;
import org.springframework.stereotype.Component;

/**
 * @ClassName: TokenUtil
 * @Author: Yangyang
 * @Date: 2019/6/14
 * @Description: 通过Username 获得Token
 */

@Component
public class TokenUtil {

    /**
     * 获得用户token
     * @param user
     * @return
     */
    public String getToken(User user) {
        String token = "";
        token = JWT.create().withAudience(user.getUid()).
                withClaim("role",user.getRole()).
                withClaim("create_time",System.currentTimeMillis()).
                sign(Algorithm.HMAC256(user.getPwd()));

        return token;
    }

    /**
     * 通过Token 获得Username
     * @param token
     * @return
     */
    public String getUid(String token) throws UserException {
        try {
            return JWT.decode(token).getAudience().get(0);
        }catch (Exception e){
            throw new UserException(CommonExceptionEnum.TOKEN_ILLEGAL);
        }
    }

    public boolean handlerToken(String token, UserDao userDao, RedisUtil redisUtil) throws Exception {
        // 检查token是否为空
        if(token==null||token.equals(""))
            throw new UserException(CommonExceptionEnum.TOKEN_EMPTY);

        //解析token获得uid
        String uid = getUid(token);
        if(userDao.isUidExist(uid) == 0)
            throw new UserException(CommonExceptionEnum.TOKEN_ILLEGAL);

        //检查Redis缓存是否有该token
        if(redisUtil.getKey("token",uid) == null || !redisUtil.getKey("token",uid).equals(token))
            throw new UserException(CommonExceptionEnum.TOKEN_TIMEOUT);

        User user = userDao.getUser(uid);

        //验证token是否对应该账号
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(user.getPwd())).build();
        try {
            verifier.verify(token);
        }catch (JWTVerificationException e){
            throw new UserException(CommonExceptionEnum.TOKEN_ILLEGAL);
        }
        //重新设置验证有效时间
        redisUtil.setRedisWithTimeOut("token",uid,token,1000*60*60*2);
        return true;

    }
}
