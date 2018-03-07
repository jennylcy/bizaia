package com.bizaia.zhongyin.module.discovery.iml;

import com.bizaia.zhongyin.module.discovery.data.NewsDetailBean;
import com.bizaia.zhongyin.repository.AddressConfiguration;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by zyz on 2017/3/10.
 */

public interface IDataNewsDetail {

    @FormUrlEncoded
    @POST(AddressConfiguration.NEWS_DETAILS)
    Observable<NewsDetailBean> newsDetail(@Field("newsId") long orgId, @Header("token") String token);

}
