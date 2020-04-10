package com.blog.cat.controller;

import com.blog.cat.common.exception.UserException;
import com.blog.cat.common.type.CommonReturnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception e) {
        CommonReturnType commonReturnType = new CommonReturnType();
        if (e instanceof UserException) {
            //强制转化捕获的错误为UserException
            UserException userException = (UserException) e;
            //将错误信息转化为通用的上传格式
            commonReturnType.setMsg("fail");
            commonReturnType.setCode(userException.getExceptionCode());
            commonReturnType.setData(userException.getExceptionMsg());
        } else {
            e.printStackTrace();
            commonReturnType.setCode(e.hashCode());
            commonReturnType.setMsg(e.getMessage());
            commonReturnType.setData(e.getLocalizedMessage());
        }
        return commonReturnType;

    }
}
