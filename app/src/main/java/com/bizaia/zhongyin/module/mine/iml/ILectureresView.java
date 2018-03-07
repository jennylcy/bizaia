package com.bizaia.zhongyin.module.mine.iml;

import com.bizaia.zhongyin.module.mine.data.LectureresBean;
import com.bizaia.zhongyin.module.mine.data.LectureresDetail;

/**
 * Created by zyz on 2017/3/6.
 */

public interface ILectureresView {
    void showLectureres(LectureresBean data);
    void showLecturereDetail(LectureresDetail data);
    void showLectureresError();
}
