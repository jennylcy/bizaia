package com.bizaia.zhongyin.module.common.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yan on 2017/3/16.
 */

public class CacheData<T extends Object> implements Serializable {
    List<T> objectList;

    public CacheData(List<T> objectList) {
        this.objectList = objectList;
    }

    public List<T> getDataList() {
        return objectList;
    }

    public void setObjectList(List<T> objectList) {
        this.objectList = objectList;
    }
}
