package com.bizaia.zhongyin.util.entity;

/**
 * Created by yan on 2016/12/16.
 */
public interface DownloadProListener {
    void onProgress(long currentLength, long totalLength);
    //46%
    //46.45%
    //24M/96M
}
