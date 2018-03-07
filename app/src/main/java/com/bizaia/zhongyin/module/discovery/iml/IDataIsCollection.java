package com.bizaia.zhongyin.module.discovery.iml;

import com.bizaia.zhongyin.module.mine.data.IsAttentionBean;
import com.bizaia.zhongyin.repository.AddressConfiguration;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by zyz on 2017/3/10.
 */

public interface IDataIsCollection {

    @FormUrlEncoded
    @POST(AddressConfiguration.IS_COLLECTION)
    Observable<IsAttentionBean> isCollection(@Field("productType") int productType,@Field("productId") long productId, @Header("token") String token);

    @FormUrlEncoded
    @POST(AddressConfiguration.ADD_COLLECTION)
    Observable<IsAttentionBean> addCollection(@Field("productId") long productId,
                                              @Field("productType") int productType,
                                              @Field("operat") int operat,
                                              @Header("token") String token);

    @FormUrlEncoded
    @POST(AddressConfiguration.ADD_COLLECTION)
    Observable<IsAttentionBean> addCollection(@Field("productType") int productType,
                                              @Field("operat") int operat,
                                              @Header("token") String token);
//
//    @FormUrlEncoded
//    @POST(AddressConfiguration.DEL_COLLECTION)
//    Observable<IsAttentionBean> delCollection(@Field("newsId") long orgId, @Header("token") String token);
}
