package com.blog.cat.dao;

import com.blog.cat.controller.view.UserInfo;
import com.blog.cat.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * @author yangyang
 */
@Repository
@Mapper
public interface UserDao {

    /**
     * 获得 User对象
     * @param uid
     * @return
     */
    User getUser(String uid);

    /**
     * 新增 User
     * @param user
     * @return
     */
    Integer addUser(User user);

    /**
     * 判断uid是否被注册
     * @param uid
     * @return
     */
    Integer isUidExist(String uid);

    /**
     * 判断Email是否被注册
     * @param email
     * @return
     */
    Integer isEmailExit(String email);

    /**
     * 判断昵称是否被使用
     * @param nickname
     * @return
     */
    Integer isNicknameExist(String nickname);

    /**
     * 判断电话号码是否被注册
     * @param phone
     * @return
     */
    Integer isPhoneExist(Long phone);

    /**
     * 用户上传头像
     * @param head
     * @param uid
     * @return
     *
     */
    Integer updateHeadPic(@Param("head") String head, @Param("uid") String uid);

    /**
     * 取用户头像Url
     * @param uid
     * @return
     */
    String getHeadUrl(String uid);

    /**
     * 更新用户的非关键信息
     * @param userInfo
     * @return
     */
    Integer updateUserInfo(UserInfo userInfo);

    /**
     * 获得用户email
     * @param uid
     * @return
     */
    String getEmail(String uid);

    /**
     * 修改用户密码
     * @param pwd
     * @param uid
     * @return
     */
    Integer updatePwd(@Param("pwd") String pwd, @Param("uid") String uid);

}
