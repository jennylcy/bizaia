package com.bizaia.zhongyin.module.pay.api;

import com.bizaia.zhongyin.module.pay.data.ExchangeRates;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.bizaia.zhongyin.module.pay.data.RemainPayData;
import com.bizaia.zhongyin.module.pay.wx.WXResoultData;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

import static com.bizaia.zhongyin.module.pay.unionpay.UnionPayManager.TN_URL;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_BALANCE_PAY;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_EXCHANGE_RATE;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_WECHAT_GPP;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_WEIXIN_GET_ORDER;

/**
 * Created by yan on 2017/3/20.
 */

public interface Pay {
    @GET(TN_URL)
    Observable<String> getTn();

    @FormUrlEncoded
    @POST(URL_BALANCE_PAY)
    Observable<RemainPayData> remainingPay(
            @Field("productId") Long productId
            , @Field("productType") Integer productType
            , @Field("orderNum") String orderNum
    );


    @FormUrlEncoded
    @POST(URL_WEIXIN_GET_ORDER)
    Observable<OrderData> getOrder(
            @Field("productId") Long productId
            , @Field("productType") Integer productType
            , @Field("amount") Double amount
            , @Field("feeType") String feeType
    );

    @FormUrlEncoded
    @POST(URL_WEIXIN_GET_ORDER)
    Observable<OrderData> getOrder(
            @Field("productId") Long productId
            , @Field("productType") Integer productType
    );

    @FormUrlEncoded
    @POST(URL_WECHAT_GPP)
    Observable<WXResoultData> getWechatOrder(
            @Field("orderNum") String orderNum
            , @Field("code") String code
    );

    @GET(URL_EXCHANGE_RATE)
    Observable<ExchangeRates> getWxchangeList();
}
