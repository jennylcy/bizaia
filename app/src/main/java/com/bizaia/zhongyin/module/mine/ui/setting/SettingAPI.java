package com.bizaia.zhongyin.module.mine.ui.setting;

import com.bizaia.zhongyin.module.mine.data.AreaData;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_ADD_PROPOSAL;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_GET_AREA_LIST;
import static com.bizaia.zhongyin.repository.AddressConfiguration.URL_PUSH_SWITCH;

/**
 * Created by yan on 2017/3/20.
 */

public interface SettingAPI {
    @FormUrlEncoded
    @POST(URL_PUSH_SWITCH)
    Observable<ResponseBody> pushSwitch(
            @Field("status") Boolean isOpen);

    @FormUrlEncoded
    @POST(URL_ADD_PROPOSAL)
    Observable<ResponseBody> addProposal(
            @Field("content") String content,@Field("contactMethod") String method);

    @POST(URL_GET_AREA_LIST)
    Observable<AreaData> getAreaList();
}
