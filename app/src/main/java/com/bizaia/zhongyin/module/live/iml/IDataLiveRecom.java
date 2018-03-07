package com.bizaia.zhongyin.module.live.iml;

import com.bizaia.zhongyin.module.live.data.LiveDailyBean;
import com.bizaia.zhongyin.module.live.data.LiveNewBean;
import com.bizaia.zhongyin.module.live.data.LiveRecomBean;
import com.bizaia.zhongyin.module.live.data.SearchLivingData;
import com.bizaia.zhongyin.module.mine.data.CompanyHostBean;
import com.bizaia.zhongyin.module.mine.data.UserInfoBean;
import com.bizaia.zhongyin.repository.AddressConfiguration;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

import static com.bizaia.zhongyin.repository.AddressConfiguration.SEARCH;

/**
 * Created by zyz on 2017/3/6.
 */

public interface IDataLiveRecom {
    @GET(AddressConfiguration.LIVE_RECOM)
    Observable<LiveRecomBean> getRecom(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize, @Query("areaIdList") String areaId,
                                       @Header("token") String token);
    @GET(AddressConfiguration.LIVE_NEW)
    Observable<LiveNewBean> getNew(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize,
                                   @Query("areaIdList") String areaId,
                                   @Header("token") String token);

    @GET(AddressConfiguration.LIVE_NEW)
    Observable<LiveNewBean> getNew(@Query("pageNum") int pageNum
            , @Query("pageSize") int pageSize
            , @Query("cateId") long cateId
            , @Query("areaId") String areaId
    );

    @GET(AddressConfiguration.LIVE_NEW)
    Observable<LiveNewBean> getNew(@Query("pageNum") int pageNum
            , @Query("pageSize") int pageSize
            , @Query("cateId") long cateId
    );

    @GET(AddressConfiguration.MY_LIVE_LIST)
    Observable<LiveDailyBean> getMyList(@Query("day") String day, @Query("lecturerAccount") String lecturerAccount,
//                                   @Query("orgId") long orgId, @Query("areaId") long areaId,
//                                   @Query("cateId") long cateId,
                                        @Header("token") String token);


    @Multipart
    @POST(AddressConfiguration.USER_UPDATE_ICON)
    Observable<ResponseBody> updateIcon(@Part MultipartBody.Part file, @Header("token") String token);


    @FormUrlEncoded
    @POST(AddressConfiguration.UPDATE_PHONE)
    Observable<CompanyHostBean> changePhone(@Field("mobile") String mobile, @Field("interAreaCode") String interAreaCode, @Header("token") String token);

    @FormUrlEncoded
    @POST(AddressConfiguration.UPDATE_EMAIL)
    Observable<CompanyHostBean> changeEmail(@Field("email") String email, @Field("code") String code, @Header("token") String token);

    @FormUrlEncoded
    @POST(AddressConfiguration.UPDATE_PWD)
    Observable<CompanyHostBean> changePwd(@Field("newPwd") String newPwd, @Field("oldPwd") String oldPwd, @Header("token") String token);

    @GET(AddressConfiguration.URL_MEMBER_INFO)
    Observable<UserInfoBean> getInfo(@Header("token") String token);

    @FormUrlEncoded
    @POST(SEARCH)
    Observable<SearchLivingData> findVideo(
            @Field("pageNum") int pageNum
            , @Field("pageSize") int pageSize
            , @Field("kw") String kw
            ,@Field("type") int type
            ,@Field("label") String label
    );
}
