package com.blog.cat.common.exception;

/**
 * @author admin
 */

public enum CommonExceptionEnum implements CommonException {
    USERNAME_EXIST(101,"用户名已存在"),
    USERNAME_NOT_EXIST(102,"用户名不存在"),
    USERNAME_EMPTY(103,"用户名为空"),
    PASSWORD_ERR(104,"密码错误"),
    PASSWORD_EMPTY(105,"密码为空"),
    EMAIL_EXIST(106,"邮箱已注册"),
    NICKNAME_EXIST(107,"昵称已注册"),
    EMAIL_CODE_ERR(108,"邮箱验证码错误或失效"),
    USER_NAME_NOT_ILL(109,"格式错误"),
    PHONE_EXIST(110,"手机号已存在"),
    EMAIL_NULL(111,"邮箱为空"),

    TOKEN_EMPTY(301,"登录过期"),
    TOKEN_ILLEGAL(302,"非法令牌"),
    TOKEN_TIMEOUT(303,"令牌失效"),

    REDIS_SERVER_ERR(701,"REDIS 服务异常"),
    SEND_VERIFY_MAIL_FAIL(702,"发送邮件失败"),

    UPLOAD_HEAD_PIC_FAIL(701, "上传头像失败"),

    DECODE_FAIL(901,"解码错误");


    private int exceptionCode;
    private String exceptionMsg;

    CommonExceptionEnum(int exceptionCode, String exceptionMsg) {
        this.exceptionCode = exceptionCode;
        this.exceptionMsg = exceptionMsg;
    }

    @Override
    public int getExceptionCode() {
        return this.exceptionCode;
    }

    @Override
    public String getExceptionMsg() {
        return this.exceptionMsg;
    }

    @Override
    public CommonException setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
        return this;
    }
}
