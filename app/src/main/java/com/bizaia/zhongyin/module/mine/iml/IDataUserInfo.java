package com.bizaia.zhongyin.module.mine.iml;

import com.bizaia.zhongyin.module.mine.data.UserInfoBean;
import com.bizaia.zhongyin.repository.AddressConfiguration;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zyz on 2017/3/6.
 */

public interface IDataUserInfo {
    @FormUrlEncoded
    @POST(AddressConfiguration.LIVE_RECOM)
    Observable<UserInfoBean> getRecom(@Field("pageNum") int pageNum, @Field("pageSize") int pageSize,
                                      @Field("cateId") long cateId, @Field("areaId") long areaId);
}
