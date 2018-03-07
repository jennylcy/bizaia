package com.bizaia.zhongyin.module.pay.unionpay;

import android.app.Activity;
import android.content.Intent;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.pay.DoResult;
import com.bizaia.zhongyin.module.pay.api.Pay;
import com.bizaia.zhongyin.util.AppRetrofit;
import com.bizaia.zhongyin.util.ToastUtils;
import com.unionpay.UPPayAssistEx;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yan on 2017/3/20.
 */

public class UnionPayManager {
    public static final String TN_URL = "http://101.231.204.84:8091/sim/getacptn";

    // mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
    private final String mMode = "01";
    private UnionPayService unionPayService;
    private Activity activity;
    private CompositeDisposable compositeDisposable;

    public UnionPayManager(Activity activity) {
        compositeDisposable = new CompositeDisposable();
        unionPayService = new UnionPayService(this);
        this.activity = activity;
    }

    public void startPay() {
        //当判断用户手机上已安装银联Apk，商户客户端可以做相应个性化处理
        if (UPPayAssistEx.checkInstalled(activity)) {
            unionPayService.getTn();
        } else {
            ToastUtils.show(activity.getApplicationContext(), R.string.without_unionpay);
        }
    }

    public void confirmPayResult(int requestCode, int resultCode, Intent data, DoResult doResult) {
        if (data == null) {
            return;
        }
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            // 支付成功后，extra中如果存在result_data，取出校验
            // result_data结构见c）result_data参数说明
            if (data.hasExtra("result_data")) {
                String result = data.getExtras().getString("result_data");
                try {
                    JSONObject resultJson = new JSONObject(result);
                    String sign = resultJson.getString("sign");
                    String dataOrg = resultJson.getString("data");
                    // 验签证书同后台验签证书
                    // 此处的verify，商户需送去商户后台做验签
                    boolean ret = RSAUtil.verify(dataOrg, sign, mMode);
                    if (ret) {
                        doResult.confirmSuccess();
                    } else {
                        doResult.invalidPaymentConfiguration();
                    }
                } catch (JSONException e) {
                }
            } else {
//                 未收到签名信息 建议通过商户后台查询支付结果
                doResult.confirmSuccess();
            }
        } else if (str.equalsIgnoreCase("fail")) {
            doResult.confirmNetWorkError();
        } else if (str.equalsIgnoreCase("cancel")) {
            doResult.customerCanceled();
        }
    }

    private void setTn(String tn) {
        UPPayAssistEx.startPay(activity, null, null, tn, mMode);
    }

    private void error() {
        ToastUtils.showInUIThead(activity, "get tn error");
    }

    public void onDestroy() {
        compositeDisposable.clear();
    }

    private static class UnionPayService {
        private UnionPayManager unionPayManager;

        private UnionPayService(UnionPayManager unionPayManager) {
            this.unionPayManager = unionPayManager;
        }

        private void getTn() {
            Pay unionPay = AppRetrofit.getAppRetrofit().retrofit().create(Pay.class);
            unionPayManager.compositeDisposable.add(unionPay.getTn()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<String>() {
                        @Override
                        public void onNext(String value) {
                            unionPayManager.setTn(value);
                        }

                        @Override
                        public void onError(Throwable e) {
                            unionPayManager.error();
                        }

                        @Override
                        public void onComplete() {

                        }
                    })
            );
        }
    }
}
