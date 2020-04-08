package com.blog.cat.service.impl;

import com.blog.cat.bean.FilePathBean;
import com.blog.cat.common.exception.CommonExceptionEnum;
import com.blog.cat.common.exception.UserException;
import com.blog.cat.service.FileService;
import com.blog.cat.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Description:
 * @Date: 2020/4/7
 * @Author: DongYiFan
 */

@Service
public class FileServiceImpl implements FileService {

    private FilePathBean fpb;


    @Override
    public boolean saveHaeadPic(MultipartFile pic, String uid) throws UserException, IOException {
        String basePath = fpb.getHeadPicBase();
        String picPath = basePath + "/" + uid + "-head";
        File file = new File(picPath);
        if(!file.exists()){
            if(!file.mkdir()){
                throw new UserException(CommonExceptionEnum.UPLOAD_HEAD_PIC_FAIL);
            }
        }
        File destPaht = new File(picPath +"/"+uid + "-head.jpg");
        pic.transferTo(destPaht);
        return true;
    }

    @Override
    public String getHeadPicUrl(String uid) {
        String picPath = fpb.getHeadPicBase() + "/" + uid + "-head";
        File picDir = new File(picPath);
        if(!picDir.exists()){
            return fpb.getDefaultHead();
        }
        File[] files = picDir.listFiles();
        if(files == null || files.length == 0){
            return fpb.getDefaultHead();
        }
        String fileName = "";
        for (File file: picDir.listFiles()){
           fileName = file.getName();
        }

        return "/head/" + uid + "-head" + "/" + fileName;
    }

    @Autowired
    public void setFpb(FilePathBean fpb) {
        this.fpb = fpb;
    }

}
