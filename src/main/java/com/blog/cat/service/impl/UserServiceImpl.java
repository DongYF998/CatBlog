package com.blog.cat.service.impl;

import com.blog.cat.bean.FilePathBean;
import com.blog.cat.common.exception.CommonExceptionEnum;
import com.blog.cat.common.exception.UserException;
import com.blog.cat.controller.view.UserInfo;
import com.blog.cat.dao.UserDao;
import com.blog.cat.entity.User;
import com.blog.cat.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * @author Yangyang
 */
@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    private FilePathBean fpb;



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

    @Override
    public UserInfo getUserInfo(String uid) throws UserException {
        User user = userDao.getUser(uid);
        if(user == null){
            throw new UserException(CommonExceptionEnum.TOKEN_ILLEGAL);
        }
        UserInfo userInfo = userTransToUserInfo(user);
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date birth = userInfo.getBirth();
        String birthStr = "";
        if(birth != null) {
           birthStr = df.format(birth);
        }
        userInfo.setBirthStr(birthStr);
        return userInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateHeadPic(MultipartFile pic,  String uid) throws Exception {
        String randomUUID = UUID.randomUUID().toString();
        String picBasePath = fpb.getHeadPicBase();
        String orgUrl = userDao.getHeadUrl(uid);
        String newUrl = picBasePath + randomUUID + ".jpg";
        userDao.updateHeadPic(randomUUID + ".jpg", uid);

        //当 当前文件存在时, 删除他
        if(orgUrl != null && !"".equals(orgUrl)){
            File orgPic = new File(picBasePath + orgUrl);
            if(orgPic.exists()) {
                orgPic.delete();
            }
        }

        pic.transferTo(new File(newUrl));

        return true;
    }

    @Override
    public String getHeadPic(String uid) throws Exception {
        String picName =  userDao.getHeadUrl(uid);
        String headPicUrl = fpb.getHeadPicBase() + picName;

        File pic = new File(headPicUrl);
        if(!pic.exists()){
            return fpb.getDefaultHead();
        }

        return "/head/"+picName;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateUserInfo(UserInfo userInfo) throws Exception {
        return userDao.updateUserInfo(userInfo);
    }

    @Override
    public String getEmail(String uid) throws Exception {
        return userDao.getEmail(uid);
    }

    @Override
    public Integer updatePwd(String pwd, String uid) throws Exception {
        return userDao.updatePwd(pwd, uid);
    }


    public UserInfo userTransToUserInfo(User user){
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user, userInfo);
        return userInfo;
    }

    @Autowired
    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    @Autowired
    public void setFpb(FilePathBean fpb) {
        this.fpb = fpb;
    }
}
