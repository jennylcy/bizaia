package com.bizaia.zhongyin.module.video.api;

import com.bizaia.zhongyin.module.video.data.SaveResultBean;
import com.bizaia.zhongyin.repository.AddressConfiguration;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zyz on 2017/3/10.
 */

public interface IDataSavePlay {

    @GET(AddressConfiguration.URL_PLAY_STATE_SAVE)
    Observable<SaveResultBean> save(@Query("id")long id, @Query("type") int  type
            , @Query("point") int  point);

    @GET(AddressConfiguration.URL_PLAY_STATE_GET)
    Observable<SaveResultBean> get(@Query("id")long id,@Query("type") int  type);

//    @FormUrlEncoded
//    @POST(AddressConfiguration.COMPANY_DEL_ATTENTION)
//    Observable<IsAttentionBean> delAttention(@Field("orgId") long orgId, @Header("token") String token);
}
