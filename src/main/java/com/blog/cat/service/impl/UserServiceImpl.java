package com.blog.cat.service.impl;

import com.blog.cat.common.exception.CommonException;
import com.blog.cat.common.exception.CommonExceptionEnum;
import com.blog.cat.common.exception.UserException;
import com.blog.cat.dao.UserDao;
import com.blog.cat.entity.User;
import com.blog.cat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User login(String uid, String pwd) throws UserException {
        if(uid == null || "".equals(uid)){
            throw new UserException(CommonExceptionEnum.USERNAME_EMPTY);
        }if(userDao.isUidExist(uid) < 1 ){
            throw new UserException(CommonExceptionEnum.USERNAME_NOT_EXIST);
        }if("".equals(pwd) || pwd == null){
            throw new UserException(CommonExceptionEnum.PASSWORD_EMPTY);
        }
        User compare = userDao.getUser(uid);
        System.out.println(compare);
        System.out.println(pwd);
        if (!compare.getPwd().equals(pwd)){
            throw new UserException(CommonExceptionEnum.PASSWORD_ERR);
        }
        return compare;
    }

    @Override
    public Integer register(User user) throws UserException {
        if(user == null) {
            return 0;
        }
        if(userDao.isUidExist(user.getUid()) > 0){
            throw new UserException(CommonExceptionEnum.USERNAME_EXIST);
        }if(userDao.isEmailExit(user.getEmail()) > 0){
            throw new UserException(CommonExceptionEnum.EMAIL_EXIST);
        }if(userDao.isNicknameExist(user.getNickname())>0){
            throw new UserException(CommonExceptionEnum.NICKNAME_EXIST);
        }
        return userDao.addUser(user);
    }

    @Override
    public Integer isUidExist(String uid) throws UserException {
        if(userDao.isUidExist(uid) == 1){
            throw new UserException(CommonExceptionEnum.USERNAME_EXIST);
        }
        return 0;
    }

    @Override
    public Integer isNicknameExist(String nickname) throws UserException {
        if(userDao.isNicknameExist(nickname)>=1){
            throw new UserException(CommonExceptionEnum.NICKNAME_EXIST);
        }
        return 0;
    }

    @Override
    public Integer isEmailExist(String email) throws UserException {
        if(userDao.isEmailExit(email) >= 1){
            throw new UserException(CommonExceptionEnum.EMAIL_EXIST);
        }
        return 0;
    }

    @Override
    public Integer isPhoneExist(Long phone) throws UserException {
        if(userDao.isPhoneExist(phone) >=1 ){
            throw new UserException(CommonExceptionEnum.PHONE_EXIST);
        }
        return 0;
    }
}
