package com.bizaia.zhongyin.module.common.mvp;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.bizaia.zhongyin.BIZAIAApplication;

/**
 * Created by yan on 2017/3/21.
 */

public class CommonPresent implements CommonContract.Presenter {
    private static final String TAG = "CommonPresent";

    protected CommonContract.View view;
    protected Context context;
    protected int page;
    protected int size;
    protected Long orgId;
    protected String areaId;
    protected Long cateId;

    protected boolean isAreaIdEmpty() {
        areaId = BIZAIAApplication.getInstance().getAreaId();
        Log.e(TAG, "CommonPresent: "+areaId);

        return TextUtils.isEmpty(areaId);
    }

    public CommonPresent(Context context, CommonContract.View view) {
        this.view = view;
        this.context = context;
        areaId = BIZAIAApplication.getInstance().getAreaId();
        this.view.setPresenter(this);
    }

    @Override
    public CommonPresent setPageNo(int page) {
        this.page = page;
        return this;
    }

    @Override
    public CommonPresent setPageSize(int size) {
        this.size = size;
        return this;
    }

    @Override
    public CommonPresent setOrgId(long orgId) {
        this.orgId = orgId;
        return this;
    }

    @Override
    public CommonPresent setAreaId(String areaId) {
        this.areaId = areaId;
        return this;
    }

    @Override
    public CommonPresent setCateId(long cateId) {
        this.cateId = cateId;
        return this;
    }

    @Override
    public void requireData() {

    }

}
