package com.bizaia.zhongyin.module.pay.wx;


import android.app.Activity;
import android.util.Log;
import android.util.Xml;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.pay.PayActivity;
import com.bizaia.zhongyin.module.pay.api.Pay;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.bizaia.zhongyin.util.AppRetrofit;
import com.bizaia.zhongyin.util.MD5;
import com.bizaia.zhongyin.util.ToastUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yan on 2017/3/20.
 */

public class WXPayManager {
    private static final String TAG = "WXPayManager";

    public static final String URL_WEIXIN_PAY_PRE = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //appid
    public static final String APP_ID = "wxf98fe90e52ea0052";
    //商户号
    public static final String MCH_ID = "1460488802";
    //  API密钥，在商户平台设置
//    public static final String APP_KEY = "af5b66e02c56f066a2ab36b4d623f271";
    public static final String APP_KEY = "daac666b72fff4b8de315cd8fc694712";
    public static final String NOTIFY_URL = "http://203.85.72.9:8090/api/v1/paycallback/wechat";


    private Activity activity;
    private IWXAPI api;
    private PayService payService;
    private PayActivity.PayPalResult payPalResult;

    public WXPayManager(Activity activity) {
        this.activity = activity;
        api = WXAPIFactory.createWXAPI(activity, APP_ID);
        api.registerApp(APP_ID);
        payService = new PayService(this);
    }

    public void startPay(OrderData value,String code) {
        if (!api.isWXAppInstalled()) {
            ToastUtils.showInUIThead(activity.getApplication(), activity.getString(R.string.without_wechact));
            return;
        }
        ToastUtils.showInUIThead(activity.getApplicationContext(), activity.getString(R.string.paying));
        payService.startPay(value,code);
    }

    public void setOnPayListener(PayActivity.PayPalResult onPayListener) {
        this.payPalResult = onPayListener;
    }

    private static class PayService {
        WXPayManager wxPayManager;
        PayReq req;
        Map<String, String> stringMap;

        PayService(WXPayManager wxPayManager) {
            this.wxPayManager = wxPayManager;
            req = new PayReq();
        }

        void startPay(final OrderData value, final String code) {


            final Pay pay = AppRetrofit.getAppRetrofit().retrofit().create(Pay.class);
            ((BaseActivity) wxPayManager.activity).addSubscription(
                    pay.getWechatOrder(value.getData().getOrder().getOrderNum(),code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<WXResoultData>() {
                        @Override
                        public void onNext(WXResoultData content) {
                            if(content.getCode()==200) {
                                stringMap = decodeXml(content.getData());
                                Log.e(TAG, "onNext: " + stringMap);
                                if (stringMap.get("err_code_des") != null)
                                    ToastUtils.showInUIThead(wxPayManager.activity, stringMap.get("err_code_des"));
                                genPayReq();
                                sendPayReq();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {

                        }
                    }));

//            ((BaseActivity) wxPayManager.activity).addSubscription(
//                    Observable.create(new ObservableOnSubscribe<String>() {
//                        @Override
//                        public void subscribe(ObservableEmitter<String> e) throws Exception {
//                            if (value.getData() == null
//                                    || value.getData().getOrder() == null) {
//                                ToastUtils.show(wxPayManager.activity, "error");
//                                e.onError(new Exception());
//                            } else {
//                                String url = String.format(URL_WEIXIN_PAY_PRE);
//                                String entity = genProductArgs(value);
//
//                                byte[] buf = new byte[0];
//                                try {
//                                    buf = HttpUtils.httpPost(url, new String(entity.toString().getBytes("utf-8"), "iso8859-1"));
//                                } catch (UnsupportedEncodingException ee) {
//                                    ee.printStackTrace();
//                                }
//
//                                String content = new String(buf);
//                                Log.e(TAG, "subscribe: " + content);
//                                e.onNext(content);
//
//                            }
//                        }
//                    })
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribeWith(new DisposableObserver<String>() {
//                                @Override
//                                public void onNext(String value) {
//                                    stringMap = decodeXml(value);
//                                    Log.e(TAG, "onNext: " + stringMap);
//                                    if (stringMap.get("err_code_des") != null)
//                                        ToastUtils.showInUIThead(wxPayManager.activity, stringMap.get("err_code_des"));
//                                    genPayReq();
//                                    sendPayReq();
//                                }
//
//                                @Override
//                                public void onError(Throwable e) {
//                                    e.printStackTrace();
//                                    if (e instanceof java.net.NoRouteToHostException) {
//                                        startPay(value);
//                                    }
//                                }
//
//                                @Override
//                                public void onComplete() {
//
//                                }
//                            }));
        }

        private long genTimeStamp() {
            return System.currentTimeMillis() / 1000;
        }

        private void sendPayReq() {
            wxPayManager.api.registerApp(APP_ID);
            wxPayManager.api.sendReq(req);
        }

        private void genPayReq() {

            req.appId = APP_ID;
            req.partnerId = MCH_ID;
            req.prepayId = stringMap.get("prepay_id");
            req.packageValue = "Sign=WXPay";
            req.nonceStr = genNonceStr();
            req.timeStamp = String.valueOf(genTimeStamp());


            List<NameValuePair> signParams = new LinkedList<NameValuePair>();
            signParams.add(new BasicNameValuePair("appid", req.appId));
            signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
            signParams.add(new BasicNameValuePair("package", req.packageValue));
            signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
            signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
            signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

            req.sign = genAppSign(signParams);
        }

        private String genAppSign(List<NameValuePair> params) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < params.size(); i++) {
                sb.append(params.get(i).getName());
                sb.append('=');
                sb.append(params.get(i).getValue());
                sb.append('&');
            }
            sb.append("key=");
            sb.append(APP_KEY);

            String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
            return appSign;
        }

        private String genNonceStr() {
            Random random = new Random();
            return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
        }

        private String genProductArgs(OrderData value) {
            StringBuffer xml = new StringBuffer();

            try {
                String nonceStr = genNonceStr();


                xml.append("</xml>");
                List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
                packageParams.add(new BasicNameValuePair("appid", APP_ID));
                packageParams.add(new BasicNameValuePair("body", "sdfasdf"));
                packageParams.add(new BasicNameValuePair("mch_id", MCH_ID));
                packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
                packageParams.add(new BasicNameValuePair("notify_url", NOTIFY_URL));
                packageParams.add(new BasicNameValuePair("out_trade_no", value.getData().getOrder().getOrderNum()));
                packageParams.add(new BasicNameValuePair("spbill_create_ip", "127.0.0.1"));
                float price = toPrice(Float.valueOf(String.valueOf(value.getData().getOrder().getPrice())));
                packageParams.add(new BasicNameValuePair("total_fee", String.valueOf((int) price)));
                packageParams.add(new BasicNameValuePair("trade_type", "APP"));


                String sign = genPackageSign(packageParams);
                packageParams.add(new BasicNameValuePair("sign", sign));


                String xmlstring = toXml(packageParams);

                return xmlstring;

            } catch (Exception e) {
                return null;
            }
        }

        private float toPrice(float s) {
            BigDecimal re1 = new BigDecimal(Float.toString(s));
            BigDecimal re2 = new BigDecimal(Float.toString(100.00f));
            return re1.multiply(re2).floatValue();
        }

        private String genPackageSign(List<NameValuePair> params) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < params.size(); i++) {
                sb.append(params.get(i).getName());
                sb.append('=');
                sb.append(params.get(i).getValue());
                sb.append('&');
            }
            sb.append("key=");
            sb.append(APP_KEY);

            Log.i("TUT", "sign: " + sb.toString());
            String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
            Log.i("TUT", "sign: " + packageSign);
            return packageSign;
        }


        private String toXml(List<NameValuePair> params) {
            StringBuilder sb = new StringBuilder();
            sb.append("<xml>");
            for (int i = 0; i < params.size(); i++) {
                sb.append("<" + params.get(i).getName() + ">");
                sb.append(params.get(i).getValue());
                sb.append("</" + params.get(i).getName() + ">");
            }
            sb.append("</xml>");

            Log.e("orion", sb.toString());
            return sb.toString();
        }


        public Map<String, String> decodeXml(String content) {

            try {
                Map<String, String> xml = new HashMap<String, String>();
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(new StringReader(content));
                int event = parser.getEventType();
                while (event != XmlPullParser.END_DOCUMENT) {

                    String nodeName = parser.getName();
                    switch (event) {
                        case XmlPullParser.START_DOCUMENT:

                            break;
                        case XmlPullParser.START_TAG:
                            if ("xml".equals(nodeName) == false) {
                                xml.put(nodeName, parser.nextText());
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            break;
                    }
                    event = parser.next();
                }

                return xml;
            } catch (Exception e) {
            }
            return null;

        }
    }


}
