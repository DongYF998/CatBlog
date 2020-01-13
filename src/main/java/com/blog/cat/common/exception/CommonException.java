package com.blog.cat.common.exception;

public interface CommonException {

    int getExceptionCode();

    String getExceptionMsg();

    CommonException setExceptionMsg(String exceptionMsg);

}
