package com.bizaia.zhongyin.module.video.api;


import com.bizaia.zhongyin.module.common.data.SearchNavData;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.bizaia.zhongyin.module.video.data.RecentlySearchVideoData;
import com.bizaia.zhongyin.module.video.data.RecentlyVideoData;
import com.bizaia.zhongyin.module.video.data.RecommendVideoData;
import com.bizaia.zhongyin.module.video.data.VideoDetailData;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.bizaia.zhongyin.repository.AddressConfiguration.SEARCH;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_GET_CATE_LIST;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_GET_ORDER_NUM;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_RECOMMEND_VIDEO_LIST;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_VIDEO_LIST;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_VOD_DETAILS;

/**
 * Created by yan on 2017/3/1.
 */

public interface Video {

    @FormUrlEncoded
    @POST(URL_VIDEO_LIST)
    Observable<RecentlyVideoData> getVideoList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize,
            @Field("cateId") Long cateId,
            @Field("areaIdList") String areaIdList,
            @Header("token") String token);


    @FormUrlEncoded
    @POST(URL_VIDEO_LIST)
    Observable<RecentlyVideoData> getVideoList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize,
            @Field("cateId") Long cateId,
            @Field("areaIdList") String areaIdList );


    @FormUrlEncoded
    @POST(URL_VIDEO_LIST)
    Observable<RecentlyVideoData> getVideoList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize,
            @Field("cateId") Long cateId  );


    @FormUrlEncoded
    @POST(URL_VIDEO_LIST)
    Observable<RecentlyVideoData> getVideoList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    @FormUrlEncoded
    @POST(URL_VIDEO_LIST)
    Observable<RecentlyVideoData> getCompanyVideoList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize,
            @Field("orgId") Long orgId
    );

    @FormUrlEncoded
    @POST(URL_VIDEO_LIST)
    Observable<RecentlyVideoData> getCompanyVideoList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize,
            @Field("orgId") Long orgId,
            @Field("areaIdList") String areaIdList
    );

    @FormUrlEncoded
    @POST(URL_RECOMMEND_VIDEO_LIST)
    Observable<RecommendVideoData> getRecommendVideoList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize,
            @Field("cateId") Long cateId,
            @Field("areaIdList") String areaIdList);


    @FormUrlEncoded
    @POST(URL_RECOMMEND_VIDEO_LIST)
    Observable<RecommendVideoData> getRecommendVideoList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize,
            @Field("areaIdList") String areaIdList);


    @FormUrlEncoded
    @POST(URL_RECOMMEND_VIDEO_LIST)
    Observable<RecommendVideoData> getRecommendVideoList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize,
            @Field("cateId") Long cateId);


    @FormUrlEncoded
    @POST(URL_RECOMMEND_VIDEO_LIST)
    Observable<RecommendVideoData> getRecommendVideoList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize);


    @FormUrlEncoded
    @POST(URL_GET_ORDER_NUM)
    Observable<OrderData> getOrderNum(
            @Field("productId") long productId
            , @Field("productType") int productType
    );


    @FormUrlEncoded
    @POST(URL_VOD_DETAILS)
    Observable<VideoDetailData> getVideoDetail(
            @Field("vodId") Long vodId,
            @Header("token") String token);

    @GET(URL_GET_CATE_LIST)
    Observable<SearchNavData> getCateList(
            @Query("productTypeId") Integer productTypeId
    );

    @FormUrlEncoded
    @POST(SEARCH)
    Observable<RecentlySearchVideoData> getVideo(
            @Field("pageNum") int pageNum
            , @Field("pageSize") int pageSize
            , @Field("kw") String queryStr
            ,@Field("type") int type
            ,@Field("label") String label
            ,@Field("sid") String sid
    );

}
