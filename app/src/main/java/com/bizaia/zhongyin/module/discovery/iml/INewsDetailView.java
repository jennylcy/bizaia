package com.bizaia.zhongyin.module.discovery.iml;

import com.bizaia.zhongyin.module.discovery.data.NewsDetailBean;

/**
 * Created by zyz on 2017/3/6.
 */

public interface INewsDetailView {
    void showNewsDetail(NewsDetailBean newsDetailBean);
    void showNewsDetailError();
}
