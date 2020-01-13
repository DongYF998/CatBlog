package com.blog.cat.dao;

import com.blog.cat.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface UserDao {
    User getUser(String uid);

    Integer addUser(User user);

    Integer isUidExist(String uid);

    Integer isEmailExit(String email);

    Integer isNicknameExist(String nickname);

    Integer isPhoneExist(Long phone);

}
