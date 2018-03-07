package com.bizaia.zhongyin.module.pay.paypel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bizaia.zhongyin.module.pay.DoResult;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalItem;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalProfileSharingActivity;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

/**
 * Created by yan on 2017/3/20.
 */

public class PayPalManager {

    private static final String TAG = "PayPalManager";
    //配置何种支付环境，一般沙盒，正式
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_PRODUCTION;
    //你所注册的APP Id
    private static final String CONFIG_CLIENT_ID = "Af1vdGRB_ROZY7ATIMUpqRQB_RANivEeI5369HJyAjh5oonrmi8HSslctKXUULVAs3qIuodmbl3zJ11C";
//    private static final String CONFIG_CLIENT_ID = "ARQOi_D9r22F0OrqiZ385hdA10ZaRDVL9R5zikTPTcOMcfNfcNNMN01W0KGBDvTDzQsvSqDD04dJZog4";

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static final int REQUEST_CODE_PROFILE_SHARING = 3;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID);
    //以下配置是授权支付的时候用到的
//            .merchantName("Example Merchant")
//            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
//            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));

    /**
     * 启动PayPal服务
     *
     * @param context
     */
    public void startPayPalService(Context context) {
        Intent intent = new Intent(context, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        context.startService(intent);
    }

    /**
     * 停止PayPal服务
     *
     * @param context
     */
    public void stopPayPalService(Context context) {
        context.stopService(new Intent(context, PayPalService.class));
    }

    /**
     * 开始执行支付操作
     *
     * @param context
     */
    public void doPayPalPay(Context context) {
        PayPalPayment thingToBuy = getStuffToBuy(PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(context, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    /**
     * 开始执行支付操作
     *
     * @param context
     */
    public void doPayPalPay(Context context, OrderData orderData,String code) {
        PayPalPayment thingToBuy = getStuffToBuy(PayPalPayment.PAYMENT_INTENT_SALE, orderData,code);
        Intent intent = new Intent(context, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    private PayPalPayment getThingToBuy(String paymentIntent, OrderData orderData,String code) {
        Log.e(TAG, "getThingToBuy: ------------------>"+new BigDecimal(orderData.getData().getOrder().getPrice()));
        PayPalItem[] items = {
                new PayPalItem(orderData.getData().getOrder().getProductTitle(),
                        1,
                        new BigDecimal(orderData.getData().getOrder().getPrice()),
                        code,
                        orderData.getData().getOrder().getOrderNum())
        };
        BigDecimal subtotal = PayPalItem.getItemTotal(items);
        BigDecimal shipping = new BigDecimal(0.00);
        BigDecimal tax = new BigDecimal(0.00);
        PayPalPaymentDetails paymentDetails = new PayPalPaymentDetails(shipping, subtotal, tax);
        BigDecimal amount = subtotal.add(shipping).add(tax);
        PayPalPayment payment =new PayPalPayment(amount
                , code
                , orderData.getData().getOrder().getProductTitle()
                , paymentIntent);
        payment.items(items).paymentDetails(paymentDetails);
        Log.e(TAG, "getThingToBuy: ------->"+subtotal.toString()+"-----"+items[0].getSku()+"-----");
        return payment;
    }

    private PayPalPayment getPayMent(String paymentIntent, OrderData orderData,String code){
        Log.e(TAG, "getThingToBuy: ------------------>"+new BigDecimal(orderData.getData().getOrder().getPrice()));
        PayPalItem[] items = {
                new PayPalItem(orderData.getData().getOrder().getProductTitle(),
                        1,
                        new BigDecimal(orderData.getData().getOrder().getPrice()),
                        code,
                        orderData.getData().getOrder().getOrderNum())
        };
        BigDecimal subtotal = PayPalItem.getItemTotal(items);
        PayPalPayment payment =new PayPalPayment(subtotal
                , code
                , orderData.getData().getOrder().getProductTitle()
                , paymentIntent);
        return  payment;
    }

    private PayPalPayment getStuffToBuy(String paymentIntent, OrderData orderData,String code){
        PayPalItem[] items = {
                new PayPalItem(orderData.getData().getOrder().getProductTitle(),
                        1,
                        new BigDecimal(String.valueOf(orderData.getData().getOrder().getPrice())),
                        code,
                        ""+orderData.getData().getOrder().getOrderNum())
        };
        BigDecimal subtotal = PayPalItem.getItemTotal(items);
        BigDecimal shipping = new BigDecimal("0.00");
        BigDecimal tax = new BigDecimal("0.00");
        PayPalPaymentDetails paymentDetails = new PayPalPaymentDetails(shipping, subtotal, tax);
        BigDecimal amount = subtotal.add(shipping).add(tax);
        PayPalPayment payment = new PayPalPayment(amount, code, orderData.getData().getOrder().getProductTitle(), paymentIntent);
        payment.items(items).paymentDetails(paymentDetails);
        //--- set other optional fields like invoice_number, custom field, and soft_descriptor
        payment.custom(orderData.getData().getOrder().getProductTitle());
        return payment;
    }

    /**
     * This method shows use of optional payment details and item list.
     * 直接给PP创建支付的信息，支付对象实体信息
     *
     * @param paymentIntent
     * @return
     */
    private PayPalPayment getStuffToBuy(String paymentIntent) {
        //--- include an item list, payment amount details
        //具体的产品信息列表
        PayPalItem[] items = {
                new PayPalItem("sample item #1", 2, new BigDecimal("0.50"), "USD",
                        "sku-12345678"),
                new PayPalItem("free sample item #2", 1, new BigDecimal("0.00"),
                        "USD", "sku-zero-price"),
                new PayPalItem("sample item #3 with a longer name", 6, new BigDecimal("0.99"),
                        "USD", "sku-33333")
        };
        BigDecimal subtotal = PayPalItem.getItemTotal(items);
        BigDecimal shipping = new BigDecimal("0.21");
        BigDecimal tax = new BigDecimal("0.67");
        PayPalPaymentDetails paymentDetails = new PayPalPaymentDetails(shipping, subtotal, tax);
        BigDecimal amount = subtotal.add(shipping).add(tax);
        PayPalPayment payment = new PayPalPayment(amount, "USD", "sample item", paymentIntent);
        payment.items(items).paymentDetails(paymentDetails);
        //--- set other optional fields like invoice_number, custom field, and soft_descriptor
        payment.custom("This is text that will be associated with the payment that the app can use.");
        return payment;
    }

    /**
     * 处理支付之后的结果
     *
     * @param context
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void confirmPayResult(final Context context, int requestCode, int resultCode, Intent data, final DoResult doResult) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                doResult.confirmSuccess();
                if (confirm != null) {
                    try {
                        Log.e(TAG, "confirmPayResult: --->"+confirm.toJSONObject().toString() );
                        Log.e(TAG, confirm.toJSONObject().toString(4));
                        Log.e(TAG, confirm.getPayment().toJSONObject().toString(4));

                        /**
                         *  TODO: send 'confirm' (and possibly confirm.getPayment() to your server for verification
                         * or consent completion.
                         * See https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                         * for more details.
                         *
                         * For sample mobile backend interactions, see
                         * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
                         */
                        // displayResultText("PaymentConfirmation info received from PayPal");
                        // 这里直接跟服务器确认支付结果，支付结果确认后回调处理结果
                        JSONObject jsonObject = confirm.toJSONObject();
                        if (jsonObject != null) {
                            JSONObject response = jsonObject.optJSONObject("response");
                            if (response != null) {
                                String id = response.optString("id");

//                                    try {
//
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                        doResult.confirmNetWorkError();
//                                    }
                            }
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "an extremely unlikely failure occurred: ", e);
                        doResult.confirmNetWorkError();
                    }
                }
                doResult.confirmSuccess();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.e(TAG, "The user canceled.");
                doResult.customerCanceled();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                doResult.invalidPaymentConfiguration();
                Log.e(TAG, "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth =
                        data.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        doResult.confirmFuturePayment();
                        Log.e("FuturePaymentExample", auth.toJSONObject().toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.e("FuturePaymentExample", authorization_code);

                        // sendAuthorizationToServer(auth);
                        // displayResultText("Future Payment code received from PayPal");

                    } catch (JSONException e) {
                        doResult.confirmNetWorkError();
                        Log.e("FuturePaymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.e("FuturePaymentExample", "The user canceled.");
                doResult.customerCanceled();
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                doResult.invalidPaymentConfiguration();
                Log.e("FuturePaymentExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        } else if (requestCode == REQUEST_CODE_PROFILE_SHARING) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth =
                        data.getParcelableExtra(PayPalProfileSharingActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.e("ProfileSharingExample", auth.toJSONObject().toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.e("ProfileSharingExample", authorization_code);

//                        sendAuthorizationToServer(auth);
//                        displayResultText("Profile Sharing code received from PayPal");

                    } catch (JSONException e) {
                        Log.e("ProfileSharingExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.e("ProfileSharingExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.e("ProfileSharingExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        }
    }


}
