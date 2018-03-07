package com.bizaia.zhongyin.module.live.action;

/**
 * Created by yan on 2017/3/13.
 */

public class LoadPDFAction {
    public long pdfId;
    public String title;
    public String filePath;

    public LoadPDFAction(long pdfId,String title,String filePath) {
        this.pdfId = pdfId;
        this.title = title;
        this.filePath = filePath;
    }
}
