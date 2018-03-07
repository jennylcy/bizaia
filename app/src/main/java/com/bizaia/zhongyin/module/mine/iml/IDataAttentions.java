package com.bizaia.zhongyin.module.mine.iml;

import com.bizaia.zhongyin.module.mine.data.AttentionsBean;
import com.bizaia.zhongyin.repository.AddressConfiguration;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by zyz on 2017/3/10.
 */

public interface IDataAttentions {
    @GET(AddressConfiguration.USER_ATTENTION)
    Observable<AttentionsBean> getAttentions(@Query("pageNum") int pageNum,
                                             @Query("pageSize") int pageSize, @Header("token") String token);
}
