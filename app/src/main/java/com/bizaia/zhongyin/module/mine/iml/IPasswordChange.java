package com.bizaia.zhongyin.module.mine.iml;

import com.bizaia.zhongyin.module.mine.data.BillBean;
import com.bizaia.zhongyin.repository.AddressConfiguration;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zyz on 2017/3/10 .
 */

public interface IPasswordChange {
    @FormUrlEncoded
    @POST(AddressConfiguration.UPDATE_PWD)
    Observable<ResponseBody> changePassword(
            @Field("oldPwd") String oldPwd
            , @Field("newPwd") String newPwd);
}
