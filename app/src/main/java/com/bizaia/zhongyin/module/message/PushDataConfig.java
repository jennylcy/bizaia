package com.bizaia.zhongyin.module.message;

/**
 * Created by yan on 2017/6/5.
 */

public class PushDataConfig {
    public static final String NAME_TYPE = "type";
    public static final String NAME_ID = "id";
    public static final String NAME_TITLE = "title";
    public static final String NAME_SUB_TITLE = "subTitle";
    public static final String NAME_URL = "url";

    //lecture,vod,monthly,live
    public static final String LECTURE = "lecture";
    public static final String VOD = "vod";
    public static final String MONTHLY = "monthly";
    public static final String LIVE = "live";
    public static final String MSG = "msg";
    //{id=10000106, type=live}
    public static String currentType;
    public static String currentId;
    public static String currentUrl;
    public static String currentTitle;
    public static String currentSubTitle;

}
