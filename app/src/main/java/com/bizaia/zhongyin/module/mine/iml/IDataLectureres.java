package com.bizaia.zhongyin.module.mine.iml;

import com.bizaia.zhongyin.module.mine.data.LectureresBean;
import com.bizaia.zhongyin.module.mine.data.LectureresDetail;
import com.bizaia.zhongyin.repository.AddressConfiguration;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by zyz on 2017/3/10.
 */

public interface IDataLectureres {
    @GET(AddressConfiguration.COMPANY_LECTURERES)
    Observable<LectureresBean> getLectureres(@Query("orgId") long orgId, @Query("pageNum")int pageNum,
                                             @Query("pageSize") int pageSize, @Header("token") String token);


    @GET(AddressConfiguration.LECTURE_DETAIL)
    Observable<LectureresDetail> getDetail(@Query("lectureId") long lectureId, @Header("token") String token);
}
