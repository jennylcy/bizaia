package com.bizaia.zhongyin.module.live.iml;

import com.bizaia.zhongyin.module.live.data.LiveState;
import com.bizaia.zhongyin.module.live.data.ThumbState;
import com.bizaia.zhongyin.repository.AddressConfiguration;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zyz on 2017/4/11.
 */

public interface IDataLiveState {

    @GET(AddressConfiguration.LIVE_STATE)
    Observable<LiveState> getState(@Query("liveId") long  liveId,
                                    @Header("token") String token);

    @FormUrlEncoded
    @POST(AddressConfiguration.LIVE_THUMBUP)
    Observable<ThumbState> thumbUp(@Field("id") long liveId, @Header("token") String token);


    @FormUrlEncoded
    @POST(AddressConfiguration.LIVE_THUMBDOWN)
    Observable<ThumbState> thumbDown(@Field("id") long liveId,@Header("token") String token);

    @GET(AddressConfiguration.LIVE_IS_THUMB)
    Observable<ThumbState> getThumbState(@Query("id") long  liveId,
                                   @Header("token") String token);

}
