package com.bizaia.zhongyin.module.login.iml;

import com.bizaia.zhongyin.module.live.data.ThumbState;
import com.bizaia.zhongyin.module.login.data.OtherLoginBean;
import com.bizaia.zhongyin.module.login.data.UserBean;
import com.bizaia.zhongyin.repository.AddressConfiguration;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zyz on 2017/3/6.
 */

public interface IDataLogin {
    @FormUrlEncoded
    @POST(AddressConfiguration.LOGIN)
    Observable<UserBean> login(@Field("account") String account, @Field("pwd") String pwd, @Field("nid") String nid);

    @POST(AddressConfiguration.OTHER_LOGIN)
    Flowable<OtherLoginBean> otherLogin(@Query("openID") String openId, @Query("token") String token, @Query("type") int type);

    @POST(AddressConfiguration.LOG_OUT)
    Flowable<ResponseBody> logOut();


    @FormUrlEncoded
    @POST(AddressConfiguration.URL_FIND_PHONE)
    Observable<ThumbState> findPhone(@Field("account") String account, @Field("pwd") String pwd, @Field("type") String type, @Field("code") String code);

}
