package com.bizaia.zhongyin.module.login.data;

import java.io.Serializable;

/**
 * Created by wujiaquan on 2017/4/17.
 */

public class PlaceBean implements Serializable {
    private String name;
    private String number;

    public PlaceBean(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
