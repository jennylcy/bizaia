package com.bizaia.zhongyin.base;

import io.reactivex.disposables.Disposable;

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);

    void addSubscription(Disposable subscription);
}
