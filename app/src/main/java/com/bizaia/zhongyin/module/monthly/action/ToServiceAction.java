package com.bizaia.zhongyin.module.monthly.action;

/**
 * Created by yan on 2017/3/13.
 */

public class ToServiceAction {
    public long pdfId;
    public boolean shouldDown;

    public ToServiceAction(long pdfId,boolean shouldDown) {
        this.pdfId = pdfId;
        this.shouldDown =shouldDown;
    }
}
