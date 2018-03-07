package com.bizaia.zhongyin.module.mine.iml;

import com.bizaia.zhongyin.module.mine.data.BuyNewsBean;
import com.bizaia.zhongyin.module.mine.data.BuyVideoBean;
import com.bizaia.zhongyin.module.mine.data.CollectAssociationBean;
import com.bizaia.zhongyin.module.mine.data.CollectLecturesBean;
import com.bizaia.zhongyin.module.mine.data.CollectRecruitBean;
import com.bizaia.zhongyin.repository.AddressConfiguration;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by zyz on 2017/3/10.
 */

public interface IDataCollection {

    @GET(AddressConfiguration.COLLECTION_NEWS)
    Observable<BuyNewsBean> getNews(@Query("pageNum") int pageNum,
                                    @Query("pageSize") int pageSize, @Header("token") String token);

    @GET(AddressConfiguration.COLLECTION_VOD)
    Observable<BuyVideoBean> getVideo(@Query("pageNum") int pageNum,
                                      @Query("pageSize") int pageSize, @Header("token") String token);

    @GET(AddressConfiguration.COLLECTION_LECTURE)
    Observable<CollectLecturesBean> getLectures(@Query("pageNum") int pageNum,
                                             @Query("pageSize") int pageSize, @Header("token") String token);

    @GET(AddressConfiguration.COLLECTION_ASSOCIATION)
    Observable<CollectAssociationBean> getAsso(@Query("pageNum") int pageNum,
                                                @Query("pageSize") int pageSize, @Header("token") String token);

    @GET(AddressConfiguration.COLLECTION_RECRUIT)
    Observable<CollectRecruitBean> getRecr(@Query("pageNum") int pageNum,
                                            @Query("pageSize") int pageSize, @Header("token") String token);
}
