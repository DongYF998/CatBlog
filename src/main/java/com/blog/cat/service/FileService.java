package com.blog.cat.service;

import com.blog.cat.common.exception.UserException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Description:
 * @Date: 2020/4/7
 * @Author: DongYiFan
 */

public interface FileService {

    /**
     * 保存头像图片
     * @param file
     * @param token
     * @return
     * @throws UserException
     * @throws IOException
     */
    public boolean saveHaeadPic(MultipartFile file, String token) throws UserException, IOException;


    public String getHeadPicUrl(String uid);
}
