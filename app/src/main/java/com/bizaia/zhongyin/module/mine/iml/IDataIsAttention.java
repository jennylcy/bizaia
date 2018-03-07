package com.bizaia.zhongyin.module.mine.iml;

import com.bizaia.zhongyin.module.mine.data.IsAttentionBean;
import com.bizaia.zhongyin.repository.AddressConfiguration;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zyz on 2017/3/10.
 */

public interface IDataIsAttention {

    @FormUrlEncoded
    @POST(AddressConfiguration.COMPANY_IS_ATTENTION)
    Observable<IsAttentionBean> isAttention(@Field("orgId") long orgId, @Header("token") String token);

    @FormUrlEncoded
    @POST(AddressConfiguration.COMPANY_ADD_ATTENTION)
    Observable<IsAttentionBean> addAttention(@Field("orgId") long orgId,@Field("isAttention") int isAttention ,@Header("token") String token);

//    @FormUrlEncoded
//    @POST(AddressConfiguration.COMPANY_DEL_ATTENTION)
//    Observable<IsAttentionBean> delAttention(@Field("orgId") long orgId, @Header("token") String token);
}
