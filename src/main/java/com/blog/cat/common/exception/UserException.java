package com.blog.cat.common.exception;


/**
 * @author yangyang
 */
public class UserException extends Exception implements CommonException {

    private CommonException commonException;

    public UserException(CommonException commonException) {
        super();
        this.commonException = commonException;
    }

    public UserException(CommonException commonException, String exceptionMsg) {
        super();
        this.commonException = commonException;
        this.commonException.setExceptionMsg(exceptionMsg);
    }

    public void setCommonException(CommonException commonException) {
        this.commonException = commonException;
    }

    @Override
    public int getExceptionCode() {
        return this.commonException.getExceptionCode();
    }

    @Override
    public String getExceptionMsg() {
        return this.commonException.getExceptionMsg();
    }

    @Override
    public CommonException setExceptionMsg(String exceptionMsg) {
        this.commonException.setExceptionMsg(exceptionMsg);
        return this.commonException;
    }
}