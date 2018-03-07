package com.bizaia.zhongyin.module.login.iml;

import com.bizaia.zhongyin.module.login.data.RegisterBean;
import com.bizaia.zhongyin.repository.AddressConfiguration;

import io.reactivex.Flowable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wujiaquan on 2017/4/17.
 */

public interface IDataRegister {
    @POST(AddressConfiguration.REGISTER)
    Flowable<RegisterBean> login(@Query("account") String account,
                                 @Query("pwd") String pwd,
                                 @Query("type") int type,
                                 @Query("nickname") String nickName,
                                 @Query("interAreaCode") String areaCode);
}
