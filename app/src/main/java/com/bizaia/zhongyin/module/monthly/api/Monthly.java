package com.bizaia.zhongyin.module.monthly.api;

import com.bizaia.zhongyin.module.monthly.data.AllJSData;
import com.bizaia.zhongyin.module.monthly.data.AllMonthlyData;
import com.bizaia.zhongyin.module.monthly.data.CommendData;
import com.bizaia.zhongyin.module.monthly.data.IsNeedToBuyData;
import com.bizaia.zhongyin.module.monthly.data.MonthlyDetail;
import com.bizaia.zhongyin.module.monthly.data.MonthlyJSData;
import com.bizaia.zhongyin.module.monthly.data.MonthlyJSSearchData;
import com.bizaia.zhongyin.module.monthly.data.SubDetailData;
import com.bizaia.zhongyin.module.monthly.data.SubscribeData;
import com.bizaia.zhongyin.module.pay.data.OrderData;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.bizaia.zhongyin.repository.AddressConfiguration.SEARCH;
import static com.bizaia.zhongyin.repository.AddressConfiguration.SUB_DETAIL;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_CHAPTER_COMMENT;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_DETAIL_CHAPER;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_GET_CHAPTER_COMMENT_LIST;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_GET_CHAPTER_LIST;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_GET_ORDER_NUM;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_MONTHLY_DETAIL;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_MONTHLY_LIST_Data;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_MONTHLY_NEWEST;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_MONTHLY_PRICE_LIST;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_WHETHER_TO_BUY;

/**
 * Created by yan on 2017/3/1.
 */

public interface Monthly {
    @FormUrlEncoded
    @POST(URL_MONTHLY_NEWEST)
    Observable<MonthlyJSData> getMonthlyList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize);

    @FormUrlEncoded
    @POST(URL_MONTHLY_NEWEST)
    Observable<MonthlyJSData> getMonthlyList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize,
            @Field("monthlyId") int monthlyId);


    @POST(URL_MONTHLY_PRICE_LIST)
    Observable<SubscribeData> getSubscribeData( );

    @FormUrlEncoded
    @POST(URL_MONTHLY_LIST_Data)
    Observable<AllMonthlyData> getAllMonthlyList(
            @Field("pageNum") int pageNum
            , @Field("pageSize") int pageSize);

    @FormUrlEncoded
    @POST(URL_WHETHER_TO_BUY)
    Observable<IsNeedToBuyData> isWhetherToBuy(
            @Field("monthlyId") long monthlyId
    );

    @FormUrlEncoded
    @POST(URL_GET_CHAPTER_LIST)
    Observable<AllJSData> getChapterList(
            @Field("monthlyId") long monthlyId
            , @Field("pageNum") int pageNum
            , @Field("pageSize") int pageSize
    );

    @FormUrlEncoded
    @POST(URL_GET_CHAPTER_COMMENT_LIST)
    Observable<CommendData> getCommentList(
            @Field("chapterId") long chapterId
            , @Field("pageNum") int pageNum
            , @Field("pageSize") int pageSize
    );
    @FormUrlEncoded
    @POST(URL_CHAPTER_COMMENT)
    Observable<ResponseBody> publishComment(
            @Field("chapterId") long chapterId
            , @Field("content") String content
    );

    @FormUrlEncoded
    @POST(URL_GET_ORDER_NUM)
    Observable<OrderData> getOrderNum(
            @Field("productId") long productId
            , @Field("productType") int productType
    );


    @FormUrlEncoded
    @POST(SEARCH)
    Observable<MonthlyJSSearchData> getSearch(
            @Field("pageNum") int pageNum
            , @Field("pageSize") int pageSize
            , @Field("kw") String queryStr
            ,@Field("type") int type
            ,@Field("label") String label
    );

    @GET(SUB_DETAIL)
    Observable<SubDetailData> getSubDetail();


    @FormUrlEncoded
    @POST(URL_MONTHLY_DETAIL)
    Observable<MonthlyDetail> getMonthlyDetail(@Field("monthlyId") long monthlyId);

    @GET(URL_DETAIL_CHAPER)
    Observable<SubDetailData> getJSDetail(@Query("chapterId") long  chapterId);
}
