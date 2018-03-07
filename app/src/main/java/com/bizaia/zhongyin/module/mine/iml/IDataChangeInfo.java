package com.bizaia.zhongyin.module.mine.iml;

import com.bizaia.zhongyin.module.mine.data.ChangeInfoBean;
import com.bizaia.zhongyin.repository.AddressConfiguration;

import io.reactivex.Flowable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wujiaquan on 2017/4/18.
 */

public interface IDataChangeInfo {
    @POST(AddressConfiguration.CHANGE_PHONE)
    Flowable<ChangeInfoBean> changePhone(@Query("mobile") String phone, @Query("interAreaCode") String areaCode);

    @POST(AddressConfiguration.CHANGE_NICK_NAME)
    Flowable<ChangeInfoBean> changeNickName(@Query("nickname") String nickName);

    @POST(AddressConfiguration.GET_MAIL_CODE)
    Flowable<ChangeInfoBean> getMailCode(@Query("email") String mail, @Query("type") int type);

    @POST(AddressConfiguration.CHANGE_MAIL)
    Flowable<ChangeInfoBean> changeMail(@Query("email") String mail, @Query("code") String code);

    @POST(AddressConfiguration.URL_CHECK_CODE)
    Flowable<ChangeInfoBean> codeCheck(@Query("email") String mail, @Query("type") int type,@Query("code") String code);
}
