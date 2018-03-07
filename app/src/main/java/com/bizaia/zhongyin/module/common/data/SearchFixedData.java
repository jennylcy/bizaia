package com.bizaia.zhongyin.module.common.data;

import com.bizaia.zhongyin.module.discovery.data.SearchNavData;

import java.util.List;

/**
 * Created by yan on 2017/3/17.
 */

public class SearchFixedData {
    public String title;
    public List<String> indexTitles;
    public boolean isLineShow;
    public Object navData;

    public SearchFixedData(String title, List<String> indexTitles) {
        this.title = title;
        this.indexTitles = indexTitles;
    }

    public SearchFixedData(boolean isLineShow, String title, List<String> indexTitles) {
        this.title = title;
        this.indexTitles = indexTitles;
        this.isLineShow = isLineShow;
    }
    public SearchFixedData(boolean isLineShow, String title, List<String> indexTitles,Object navData) {
        this.title = title;
        this.indexTitles = indexTitles;
        this.navData = navData;
        this.isLineShow = isLineShow;
    }
}
