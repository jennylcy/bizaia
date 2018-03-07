package com.bizaia.zhongyin.module.live.iml;

import com.bizaia.zhongyin.module.login.data.UserBean;
import com.bizaia.zhongyin.repository.AddressConfiguration;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by zyz on 2017/4/12.
 */

public interface IDataStartStream {
    @FormUrlEncoded
    @POST(AddressConfiguration.LIVE_START_STREAM)
    Observable<UserBean> start(@Field("liveId") long liveId,
                               @Field("pushPlatformType") int pushPlatformType,
                               @Field("startTime") long startTime,
                               @Header("token") String token);

    @FormUrlEncoded
    @POST(AddressConfiguration.LIVE_END_STREAM)
    Observable<UserBean> end(@Field("liveId") long liveId,
                               @Field("endTime") long startTime);
}
