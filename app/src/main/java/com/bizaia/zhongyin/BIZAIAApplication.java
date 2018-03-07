package com.bizaia.zhongyin;

import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.bizaia.zhongyin.module.live.agora.MediaManager;
import com.bizaia.zhongyin.module.live.imhelper.IMHelper;
import com.bizaia.zhongyin.module.login.data.UserBean;
import com.bizaia.zhongyin.module.mine.data.ImInfo;
import com.bizaia.zhongyin.module.mine.data.UserInfoBean;
import com.bizaia.zhongyin.repository.SPConfiguration;
import com.bizaia.zhongyin.repository.greendao.DBManager;
import com.bizaia.zhongyin.util.DevUtils;
import com.bizaia.zhongyin.util.SPUtils;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.UiUtils;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Locale;

import okhttp3.Headers;

public class BIZAIAApplication extends MultiDexApplication {

    private UserBean.DataEntity userBean;
    private UserInfoBean.DataEntity userInfo;
    public static BIZAIAApplication instance;
    private long cateId;
    private long areaId;
    private long orgId;
    private String token;
    private String memberSig;
    private String memberAccount;
    private String email;
    private static MediaManager mediaManager;
    private boolean isAllArea;
    private int msgNum;
    private  boolean isIgnore;

    @Override
    public void onCreate() {
        super.onCreate();
//        LeakCanary.install(this);
//        Bugtags.start("d61e894a17978676ff21fc59e61b2d35", this, Bugtags.BTGInvocationEventBubble);
        UiUtils.initialize(this);
        instance = this;
        initImageLoader();
        initGreenDao();
        initIm();
    }

    private void initGreenDao() {
        DBManager.getInstance(this);
    }

    private void initImageLoader() {
        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(
                this).memoryCacheExtraOptions(480, 800).threadPoolSize(3)
                // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCache(new WeakMemoryCache())
                .build());
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public long getCateId() {
        return cateId;
    }

    public void setCateId(long cateId) {
        this.cateId = cateId;
    }

    public String getAreaId() {
        String areaID = SPUtils.getString(getApplicationContext(), MODE_PRIVATE, SPConfiguration.APP_NAME, SPConfiguration.AREA_ID);
        if(StringUtils.isEmpty(areaID))
            areaID = "";
        return areaID;
    }


    public void setAreaId(String areaId) {
        SPUtils.putString(getApplicationContext(), MODE_PRIVATE, SPConfiguration.APP_NAME, SPConfiguration.AREA_ID, areaId);
    }

    public UserBean.DataEntity getUserBean() {
        if (SPUtils.getUser(getApplicationContext(), MODE_PRIVATE, SPConfiguration.APP_NAME, SPConfiguration.USER_LOGIN) != null)
            userBean = SPUtils.getUser(getApplicationContext(), MODE_PRIVATE, SPConfiguration.APP_NAME, SPConfiguration.USER_LOGIN);
        return userBean;
    }

    public void setUserBean(UserBean.DataEntity userBean) {
        this.userBean = userBean;
        SPUtils.putUser(getApplicationContext(), MODE_PRIVATE, SPConfiguration.APP_NAME, SPConfiguration.USER_LOGIN, userBean);
        if (userBean != null)
            SPUtils.putString(getApplicationContext(), MODE_PRIVATE, SPConfiguration.APP_NAME, SPConfiguration.USER_TOKEN, userBean.getAuthToken());
        if (this.userBean != null) {
            String userName = this.userBean.getNickname();
            userName = TextUtils.isEmpty(userName) ? this.userBean.getMobile() : userName;
        }
    }

    public void setToken(String token) {
        SPUtils.putString(getApplicationContext(), MODE_PRIVATE, SPConfiguration.APP_NAME, SPConfiguration.USER_TOKEN, token);
    }

    public String getToken() {
        if (SPUtils.getString(getApplicationContext(), MODE_PRIVATE, SPConfiguration.APP_NAME, SPConfiguration.USER_TOKEN) == null)
            return null;
        return SPUtils.getString(getApplicationContext(), MODE_PRIVATE, SPConfiguration.APP_NAME, SPConfiguration.USER_TOKEN);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserIM(ImInfo data) {
        if (data != null && data.getData() != null) {
            SPUtils.putString(getApplicationContext(), MODE_PRIVATE, SPConfiguration.APP_NAME, SPConfiguration.IM_SIG, data.getData().getMemberSig());
            SPUtils.putString(getApplicationContext(), MODE_PRIVATE, SPConfiguration.APP_NAME, SPConfiguration.IM_ACCOUNT, data.getData().getMemberAccount());
        }
    }

    public String getMemberSig() {
        memberSig = SPUtils.getString(getApplicationContext(), MODE_PRIVATE, SPConfiguration.APP_NAME, SPConfiguration.IM_SIG);
        return memberSig;
    }

    public String getMemberAccount() {
        memberAccount = SPUtils.getString(getApplicationContext(), MODE_PRIVATE, SPConfiguration.APP_NAME, SPConfiguration.IM_ACCOUNT);
        return memberAccount;
    }

    public boolean isAllArea() {
        return SPUtils.getBoolean(getApplicationContext(), MODE_PRIVATE, SPConfiguration.APP_NAME, SPConfiguration.AREA_ALL);
    }

    public int getMsgNum() {
        return msgNum;
    }

    public void setMsgNum(int msgNum) {
        this.msgNum = msgNum;
    }

    public void setAllArea(boolean allArea) {
        isAllArea = allArea;
        SPUtils.putBoolean(getApplicationContext(), MODE_PRIVATE, SPConfiguration.APP_NAME, SPConfiguration.AREA_ALL,allArea);
    }

    public static BIZAIAApplication getInstance() {
        return instance;
    }

    public static MediaManager getMediaManager() {
        return mediaManager;
    }

    public static void setMediaManager(MediaManager mediaManager) {
        BIZAIAApplication.mediaManager = mediaManager;
    }

    public UserInfoBean.DataEntity getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean.DataEntity userInfo) {
        this.userInfo = userInfo;
    }

    public boolean isIgnore() {
        return isIgnore;
    }

    public void setIgnore(boolean ignore) {
        isIgnore = ignore;
    }

    public Headers getHeaders() {
        return new Headers.Builder()
                .add("token", userBean == null ? "" : userBean.getAuthToken())
                .add("deviceName", DevUtils.getOs())
                .add("deviceModel", DevUtils.getModel())
                .add("OSVer", DevUtils.getVersion())
                .add("deviceID", DevUtils.getUUID(this))
                .add("lang", Locale.getDefault().getLanguage())
                .add("token", BIZAIAApplication.getInstance().getToken())
                .build();
    }

    private void initIm(){
        if(!StringUtils.isEmpty(getToken()))
        IMHelper.getInstance().init(this);
    }

}
