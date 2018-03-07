package com.bizaia.zhongyin.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.monthly.action.PaySuccessAction;
import com.bizaia.zhongyin.module.pay.wx.WXPayManager;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, WXPayManager.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == -2) {
                ToastUtils.show(this, R.string.pay_cancel);
            } else if (resp.errCode == 0) {
                ToastUtils.show(this, R.string.pay_success);
                RxBus.getInstance().post(new PaySuccessAction("weixin"));
            }
            finish();
        }
    }
}