package com.bizaia.zhongyin.module.monthly.data;

import java.io.Serializable;

/**
 * Created by yan on 2017/3/8.
 */

public class BrowseData implements Serializable {
    public String filePath;

    public BrowseData(String filePath) {
        this.filePath = filePath;
    }
}
