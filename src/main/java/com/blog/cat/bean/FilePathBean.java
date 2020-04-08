package com.blog.cat.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Date: 2020/4/3
 * @Author: DongYiFan
 */

@Component
@ConfigurationProperties(prefix = "file.path")
@PropertySource("classpath:config.properties")
public class FilePathBean {

    private  String headPicBase;

    private String defaultHead;

    public String getHeadPicBase() {
        return headPicBase;
    }

    public void setHeadPicBase(String headPicBase) {
        this.headPicBase = headPicBase;
    }

    public String getDefaultHead() {
        return defaultHead;
    }

    public void setDefaultHead(String defaultHead) {
        this.defaultHead = defaultHead;
    }
}
