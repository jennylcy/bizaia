package com.bizaia.zhongyin.module.common.data;

import com.bizaia.zhongyin.module.common.data.SearchData;

import java.io.Serializable;

/**
 * Created by yan on 2017/3/14.
 */

public class SendSearchDataToSearchAct implements Serializable {
    public Object searchData;
    public String strData;
    public int position;
    public int type;


    public SendSearchDataToSearchAct(Object searchData) {
        this.searchData = searchData;
    }

    public SendSearchDataToSearchAct(Object searchData, String strData) {
        this.searchData = searchData;
        this.strData = strData;
    }

    public SendSearchDataToSearchAct(Object searchData, String strData, int type, int position) {
        this.searchData = searchData;
        this.strData = strData;
        this.type = type;
        this.position = position;
    }
}
