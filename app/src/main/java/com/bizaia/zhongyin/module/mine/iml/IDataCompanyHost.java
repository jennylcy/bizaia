package com.bizaia.zhongyin.module.mine.iml;

import com.bizaia.zhongyin.module.mine.data.CompanyHostBean;
import com.bizaia.zhongyin.repository.AddressConfiguration;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by zyz on 2017/3/10.
 */

public interface IDataCompanyHost {
    @FormUrlEncoded
    @POST(AddressConfiguration.COMPANY_HOST)
    Observable<CompanyHostBean> getCompanyHost(@Field("orgId") long orgId, @Header("token") String token);
}
