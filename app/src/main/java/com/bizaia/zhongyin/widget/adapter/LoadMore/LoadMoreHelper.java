package com.bizaia.zhongyin.widget.adapter.LoadMore;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bizaia.zhongyin.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yan on 2016/12/22.
 */

public class LoadMoreHelper {
    private LoadMoreWrapper moreWrapper;

    public LoadMoreHelper(LoadMoreWrapper loadMoreWrapper) {
        this.moreWrapper = loadMoreWrapper;
    }

    public void setStateDataError() {
//        TextView textView = (TextView) moreWrapper.getLoadMoreView().findViewById(R.id.tv_notice);
//        textView.setText("加载失败");
        moreWrapper.clearLoadMore(false, false);

        ImageView ivLoading = (ImageView) moreWrapper.getLoadMoreView().findViewById(R.id.iv_loading);
        AnimationDrawable loadingAnimation = (AnimationDrawable) ivLoading.getDrawable();
        loadingAnimation.stop();
    }

    public Disposable setStateDataNoMore() {
        return Observable.timer(200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
//                        TextView textView = (TextView) moreWrapper.getLoadMoreView().findViewById(R.id.tv_notice);
//                        textView.setText("没有更多了");
                        moreWrapper.clearLoadMore(true, true);
                        ImageView ivLoading = (ImageView) moreWrapper.getLoadMoreView().findViewById(R.id.iv_loading);
                        AnimationDrawable loadingAnimation = (AnimationDrawable) ivLoading.getDrawable();
                        loadingAnimation.stop();
                    }
                });
    }

    public void setStateDataLoading() {
//        TextView textView = (TextView) moreWrapper.getLoadMoreView().findViewById(R.id.tv_notice);
//        textView.setText("正在加载");
        moreWrapper.clearLoadMore(false, false);
        ImageView ivLoading = (ImageView) moreWrapper.getLoadMoreView().findViewById(R.id.iv_loading);
        AnimationDrawable loadingAnimation = (AnimationDrawable) ivLoading.getDrawable();
        loadingAnimation.start();
    }

    public View getLoadMoreView(Context context) {
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                context.getResources().getDimensionPixelSize(R.dimen.load_more_height));
        View loadMore = LayoutInflater.from(context).inflate(R.layout.item_loadmore, null);
        loadMore.setLayoutParams(layoutParams);
        return loadMore;
    }

    public View getLoadMoreView(Context context, int width, int leftMargin, int rightMargin) {
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                width,
                context.getResources().getDimensionPixelSize(R.dimen.load_more_height));
        View loadMore = LayoutInflater.from(context).inflate(R.layout.item_loadmore, null);
        layoutParams.leftMargin = leftMargin;
        layoutParams.rightMargin = rightMargin;
        loadMore.setLayoutParams(layoutParams);


        return loadMore;
    }
}