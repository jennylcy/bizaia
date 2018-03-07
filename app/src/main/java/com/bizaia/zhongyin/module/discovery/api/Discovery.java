package com.bizaia.zhongyin.module.discovery.api;

import com.bizaia.zhongyin.module.discovery.data.AssociationData;
import com.bizaia.zhongyin.module.discovery.data.BuyStateBean;
import com.bizaia.zhongyin.module.discovery.data.LectureData;
import com.bizaia.zhongyin.module.discovery.data.RecentlyNewsData;
import com.bizaia.zhongyin.module.discovery.data.RecommendNewsData;
import com.bizaia.zhongyin.module.discovery.data.RecruitData;
import com.bizaia.zhongyin.module.discovery.data.SearchFindData;
import com.bizaia.zhongyin.module.discovery.data.SearchNavData;
import com.bizaia.zhongyin.module.pay.data.OrderData;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.bizaia.zhongyin.repository.AddressConfiguration.SEARCH;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_CHECK_STATE_BUY;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_GET_ASSOCIATION_LIST;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_GET_FIND_CATE_LIST;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_GET_LECTURE_DETAIL;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_GET_LECTURE_LIST;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_GET_ORDER_NUM;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_GET_RECRUIT_LIST;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_NEWS_LIST;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_RECOMMEND_NEWS_LIST;

/**
 * Created by yan on 2017/3/1.
 */

public interface Discovery {

    @FormUrlEncoded
    @POST(URL_NEWS_LIST)
    Observable<RecentlyNewsData> getNewsList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize,
            @Field("cateId") Long cateId,
            @Field("areaIdList") String areaIdList
    );


    @FormUrlEncoded
    @POST(URL_NEWS_LIST)
    Observable<RecentlyNewsData> getNewsList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize,
            @Field("cateId") Long cateId
    );


    @FormUrlEncoded
    @POST(URL_NEWS_LIST)
    Observable<RecentlyNewsData> getNewsList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize,
            @Field("areaIdList") String areaIdList
    );


    @FormUrlEncoded
    @POST(URL_NEWS_LIST)
    Observable<RecentlyNewsData> getNewsList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    @FormUrlEncoded
    @POST(URL_NEWS_LIST)
    Observable<RecentlyNewsData> getCompanyNewsList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize,
            @Field("orgId") Long orgId);


    @FormUrlEncoded
    @POST(URL_RECOMMEND_NEWS_LIST)
    Observable<RecommendNewsData> getRecommendNewsList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize,
            @Field("cateId") Long cateId,
            @Field("areaIdList") String areaIdList);


    @FormUrlEncoded
    @POST(URL_RECOMMEND_NEWS_LIST)
    Observable<RecommendNewsData> getRecommendNewsList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );


    @GET(URL_GET_ASSOCIATION_LIST)
    Observable<AssociationData> getAssociationList(
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize,
            @Query("orgId") Long orgId,
            @Query("areaIdList") String areaIdList,
            @Query("cateId") Long cateId
    );

    @GET(URL_GET_ASSOCIATION_LIST)
    Observable<LectureData> getAssociationList(
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize,
            @Query("cateId") Long orgId,
            @Query("areaIdList") String areaIdList
    );

    @GET(URL_GET_ASSOCIATION_LIST)
    Observable<LectureData> getAssociationList(
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize,
            @Query("areaIdList") String areaIdList
    );

    @GET(URL_GET_ASSOCIATION_LIST)
    Observable<LectureData> getAssociationList(
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );

    @GET(URL_GET_ASSOCIATION_LIST)
    Observable<LectureData> getAssociationList(
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize,
            @Query("cateId")  long cateId);


    @GET(URL_GET_RECRUIT_LIST)
    Observable<RecruitData> getRecruitList(
            @Query("pageNum") int pageNum
            , @Query("pageSize") int pageSize
            , @Query("orgId") Long orgId
            , @Query("areaIdList") String areaIdList
            , @Query("cateId") Long cateId
    );


    @GET(URL_GET_RECRUIT_LIST)
    Observable<RecruitData> getRecruitList(
            @Query("pageNum") int pageNum
            , @Query("pageSize") int pageSize
            , @Query("cateId") Long orgId
            , @Query("areaIdList") String areaIdList
    );


    @GET(URL_GET_RECRUIT_LIST)
    Observable<RecruitData> getRecruitList(
            @Query("pageNum") int pageNum
            , @Query("pageSize") int pageSize
            , @Query("cateId") Long orgId
    );


    @GET(URL_GET_RECRUIT_LIST)
    Observable<RecruitData> getRecruitList(
            @Query("pageNum") int pageNum
            , @Query("pageSize") int pageSize
            , @Query("areaIdList") String areaIdList
    );


    @GET(URL_GET_RECRUIT_LIST)
    Observable<RecruitData> getRecruitList(
            @Query("pageNum") int pageNum
            , @Query("pageSize") int pageSize
    );

    @FormUrlEncoded
    @POST(URL_GET_LECTURE_LIST)
    Observable<LectureData> getLectureList(
            @Field("pageNum") int pageNum
            , @Field("pageSize") int pageSize
    );

    @FormUrlEncoded
    @POST(URL_GET_LECTURE_LIST)
    Observable<LectureData> getLectureList(
            @Field("pageNum") int pageNum
            , @Field("pageSize") int pageSize
            , @Field("orgId") Long orgId
            , @Field("areaIdList") String areaIdList
            , @Field("cateId") Long cateId
    );

    @FormUrlEncoded
    @POST(URL_GET_LECTURE_LIST)
    Observable<LectureData> getLectureList(
            @Field("pageNum") int pageNum
            , @Field("pageSize") int pageSize
            , @Field("cateId") Long orgId
            , @Field("areaIdList") String areaIdList
    );

    @FormUrlEncoded
    @POST(URL_GET_LECTURE_LIST)
    Observable<LectureData> getLectureList(
            @Field("pageNum") int pageNum
            , @Field("pageSize") int pageSize
            , @Field("cateId") Long orgId
    );

    @FormUrlEncoded
    @POST(URL_GET_LECTURE_LIST)
    Observable<LectureData> getLectureList(
            @Field("pageNum") int pageNum
            , @Field("pageSize") int pageSize
            , @Field("areaIdList") String areaIdList
    );

    @FormUrlEncoded
    @POST(URL_GET_LECTURE_DETAIL)
    Observable<ResponseBody> getLectureDetail(
            @Field("lectureId") long lectureId
    );

    @FormUrlEncoded
    @POST(URL_GET_ORDER_NUM)
    Observable<OrderData> getOrderNum(
            @Field("productId") long productId
            , @Field("productType") int productType
    );

    @GET(URL_GET_FIND_CATE_LIST)
    Observable<SearchNavData> getFindCateList(
    );

    @FormUrlEncoded
    @POST(SEARCH)
    Observable<SearchFindData> getSearchResult(
            @Field("pageNum") int pageNum
            , @Field("pageSize") int pageSize
            , @Field("kw") String queryStr
            ,@Field("type") int type
            ,@Field("label") String label
            ,@Field("sid") String sid
    );

    @GET(URL_CHECK_STATE_BUY)
    Observable<BuyStateBean> checkBuy(@Query("productId") long productId,
                                      @Query("productType") int productType,
                                      @Query("token") String token);


}
