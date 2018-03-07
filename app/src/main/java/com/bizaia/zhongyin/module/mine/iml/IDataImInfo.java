package com.bizaia.zhongyin.module.mine.iml;

import com.bizaia.zhongyin.module.mine.data.ImInfo;
import com.bizaia.zhongyin.repository.AddressConfiguration;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by zyz on 2017/3/18.
 */

public interface IDataImInfo {

    @GET(AddressConfiguration.IM_INFO)
    Observable<ImInfo> imInfo(@Header("token") String token);

}
