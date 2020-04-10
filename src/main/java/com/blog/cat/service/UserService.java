package com.blog.cat.service;

import com.blog.cat.common.exception.UserException;
import com.blog.cat.controller.view.UserInfo;
import com.blog.cat.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yangyang
 */
public interface UserService {
    /**
     * 用户登陆
     * @param uid String 用户名
     * @param pwd String 密码
     * @return User
     * @throws UserException
     */
    User login(String uid, String pwd) throws UserException;

    /**
     * 用户注册
     * @param user User
     * @return I
     * @throws UserException
     */
    Integer register(User user) throws  UserException;

    /**
     * 判断该Uid是否存在
     * @param uid
     * @return
     * @throws UserException
     */
    Integer isUidExist(String uid) throws UserException;

    /**
     * 判断该昵称是否存在
     * @param nickname
     * @return
     * @throws UserException
     */
    Integer isNicknameExist(String nickname) throws UserException;

    /**
     * 判断该邮箱是否存在
     * @param email
     * @return
     * @throws UserException
     */
    Integer isEmailExist(String email) throws UserException;

    /**
     * 判断该手机号码是否存在
     * @param phone
     * @return
     * @throws UserException
     */
    Integer isPhoneExist(Long phone) throws UserException;

    /**
     * 获得 UserInfo 实例对象 By uid
     * @param uid
     * @return
     * @throws UserException
     */
    UserInfo getUserInfo(String uid) throws  UserException;

    /**
     * 用户上传头像 保存url到数据库
     * @param uid
     * @param pic
     * @return
     * @throws Exception
     */
    boolean updateHeadPic(MultipartFile pic, String uid) throws  Exception;

    /**
     * 返回用户头像 url
     * @param uid
     * @return
     * @throws Exception
     */
    String getHeadPic(String uid) throws Exception;

    /**
     * 更新用户基础信息
     * @param userInfo
     * @return
     * @throws Exception
     */
    Integer updateUserInfo(UserInfo userInfo) throws  Exception;

    /**
     * 获得用户邮箱
     * @param uid
     * @return
     * @throws Exception
     */
    String getEmail(String uid) throws  Exception;

    /**
     * 修改用户密码
     * @param pwd
     * @param uid
     * @return
     * @throws Exception
     */
    Integer updatePwd(String pwd, String uid) throws Exception;
}
