package com.bizaia.zhongyin.module.common.mvp;

import com.bizaia.zhongyin.base.BasePresenter;
import com.bizaia.zhongyin.module.common.ui.DataView;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface CommonContract {
    interface View<T, V extends Presenter> extends DataView<T, V> {
    }

    interface Presenter extends BasePresenter {
        Presenter setPageNo(int page);

        Presenter setPageSize(int size);

        Presenter setOrgId(long orgId);

        Presenter setAreaId(String areaId);

        Presenter setCateId(long cateId);

        void requireData();
    }
}
