package com.bizaia.zhongyin.module.mine.iml;

import com.bizaia.zhongyin.module.mine.data.BuyLiveBean;
import com.bizaia.zhongyin.module.mine.data.BuyMonthlyBean;
import com.bizaia.zhongyin.module.mine.data.BuyNewsBean;
import com.bizaia.zhongyin.module.mine.data.BuyVideoBean;
import com.bizaia.zhongyin.repository.AddressConfiguration;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by zyz on 2017/3/10.
 */

public interface IDataBuyHis {

    @GET(AddressConfiguration.BUY_NEWS_LIST)
    Observable<BuyNewsBean> getNews(@Query("pageNum") int pageNum,
                                    @Query("pageSize") int pageSize, @Header("token") String token);

    @GET(AddressConfiguration.BUY_LIVE_LIST)
    Observable<BuyLiveBean> getLive(@Query("pageNum") int pageNum,
                                    @Query("pageSize") int pageSize, @Header("token") String token);

    @GET(AddressConfiguration.BUY_VIDEO_LIST)
    Observable<BuyVideoBean> getVideo(@Query("pageNum") int pageNum,
                                      @Query("pageSize") int pageSize, @Header("token") String token);

    @GET(AddressConfiguration.BUY_MONTHLY_LIST)
    Observable<BuyMonthlyBean> getMonthly(@Query("pageNum") int pageNum,
                                          @Query("pageSize") int pageSize, @Header("token") String token);
}
