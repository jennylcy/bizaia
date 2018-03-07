package com.bizaia.zhongyin.module.mine.iml;

import com.bizaia.zhongyin.module.mine.data.InfoBean;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator
 * Created on 2017/7/7 15:43
 */

public interface IDataInfo {
    @POST("v1/api/getMemberDetail")
    Observable<InfoBean> getInfo(@Query("memberId") String memberId);

    @POST("v1/api/saveMemberInfo")
    Observable<InfoBean> changeInfo(@Query("memberId") String memberId,
                                    @Query("name") String name,
                                    @Query("kana") String kana,
                                    @Query("email") String email,
                                    @Query("birthday") String birthday,
                                    @Query("gender") int gender,
                                    @Query("career") String career,
                                    @Query("industry") String industry,
                                    @Query("position") String position,
                                    @Query("duty") String duty,
                                    @Query("country") String country,
                                    @Query("mobile") String mobile,
                                    @Query("company") String company,
                                    @Query("personalUrl") String personalUrl,
                                    @Query("companyUrl") String companyUrl);
}
