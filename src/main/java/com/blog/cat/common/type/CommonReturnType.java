package com.blog.cat.common.type;

import lombok.Data;

/**
 * 通用的返回体，用于向前端返回一个通用的数据结构体
 */
@Data
public class CommonReturnType {

    private int code;
    private String msg;
    private Object data;

    public CommonReturnType() {
    }

    public CommonReturnType(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public CommonReturnType(Object data){
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }
}
