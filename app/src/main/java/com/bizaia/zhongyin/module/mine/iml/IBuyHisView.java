package com.bizaia.zhongyin.module.mine.iml;

import com.bizaia.zhongyin.module.mine.data.BuyLiveBean;
import com.bizaia.zhongyin.module.mine.data.BuyMonthlyBean;
import com.bizaia.zhongyin.module.mine.data.BuyNewsBean;
import com.bizaia.zhongyin.module.mine.data.BuyVideoBean;
import com.bizaia.zhongyin.module.mine.data.CollectAssociationBean;
import com.bizaia.zhongyin.module.mine.data.CollectLecturesBean;
import com.bizaia.zhongyin.module.mine.data.CollectRecruitBean;

/**
 * Created by zyz on 2017/3/6.
 */

public interface IBuyHisView {
    void showBuyNews(BuyNewsBean data);
    void showBuyLive(BuyLiveBean data);
    void showBuyVideo(BuyVideoBean data);
    void showBuyMonthly(BuyMonthlyBean data);
    void showLectures(CollectLecturesBean data);
    void showAsso(CollectAssociationBean data);
    void showRecr(CollectRecruitBean data);
    void showBuyHisError();
    void onRelogin();
}
