package com.bizaia.zhongyin.module.live.iml;

import com.bizaia.zhongyin.module.live.data.LiveDetailBean;
import com.bizaia.zhongyin.module.live.data.ThumbState;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.bizaia.zhongyin.repository.AddressConfiguration;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.bizaia.zhongyin.repository.AddressConfiguration.LIVE_NOTIF;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_GET_ORDER_NUM;

/**
 * Created by zyz on 2017/3/6.
 */

public interface IDataLiveDetail {
    @GET(AddressConfiguration.LIVE_DETAIL)
    Observable<LiveDetailBean> getDetail(@Query("id") long id,
                                         @Header("token") String token);

    @FormUrlEncoded
    @POST(URL_GET_ORDER_NUM)
    Observable<OrderData> getOrderNum(
            @Field("productId") long productId
            , @Field("productType") int productType
    );

    @FormUrlEncoded
    @POST(LIVE_NOTIF)
    Observable<ThumbState> setNotif(
            @Field("liveId") long liveId
            , @Field("status") boolean status
    );
}
