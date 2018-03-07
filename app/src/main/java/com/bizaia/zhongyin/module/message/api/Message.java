package com.bizaia.zhongyin.module.message.api;

import com.bizaia.zhongyin.module.message.data.MessageData;
import com.bizaia.zhongyin.module.message.data.UnreadMsgBean;
import com.bizaia.zhongyin.repository.AddressConfiguration;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by yan on 2017/3/14.
 */


public interface Message {

    @FormUrlEncoded
    @POST(AddressConfiguration.URL_USER_MSG)
    Observable<MessageData> getMessage(
            @Field("type") int type
            , @Field("pageNum") int pageNum
            , @Field("pageSize") int pageSize
    );

    @FormUrlEncoded
    @POST(AddressConfiguration.URL_DEL_USER_MSG)
    Observable<ResponseBody> delMessage(
            @Field("id") long id
    );

    @POST(AddressConfiguration.URL_DEL_USER_MSG)
    Observable<ResponseBody> delMessage();

    @GET(AddressConfiguration.URL_MSG_UNREAD)
    Observable<UnreadMsgBean> getUnread( @Header("token") String token);

    @GET(AddressConfiguration.URL_MSG_READ)
    Observable<UnreadMsgBean> read(@Query("id") long id);



}