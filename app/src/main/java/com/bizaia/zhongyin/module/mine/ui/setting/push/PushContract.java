package com.bizaia.zhongyin.module.mine.ui.setting.push;

import com.bizaia.zhongyin.base.BasePresenter;

import io.reactivex.disposables.Disposable;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface PushContract {
    interface View {
        void setSwitchState(boolean isPushOpen);

        void addSubscription(Disposable subscription);
    }

    interface Presenter extends BasePresenter {
        boolean getPushState();

        void setPushState(boolean isPush);

        void requireSwitchState();

        void setRemoteSwitchState(boolean isPush);
    }
}
