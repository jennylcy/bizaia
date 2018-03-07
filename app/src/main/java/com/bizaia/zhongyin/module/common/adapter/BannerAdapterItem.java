package com.bizaia.zhongyin.module.common.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.util.SizeUtils;
import com.bizaia.zhongyin.widget.OnTouchCallBack;
import com.bizaia.zhongyin.widget.adapter.CustomAdapterItem;
import com.bizaia.zhongyin.widget.adapter.ViewHolder;
import com.bizaia.zhongyin.widget.banner.Banner;
import com.bizaia.zhongyin.widget.banner.BannerIndicator;
import com.bizaia.zhongyin.widget.refresh.PtrClassicFrameLayout;

/**
 * BannerAdapterItem to adjust data type
 *
 * @param <T>
 */
public abstract class BannerAdapterItem<T> extends CustomAdapterItem<ViewHolder, T> {
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    public Banner banner;
    public BannerIndicator bannerIndicator;
    private Context context;

    public BannerAdapterItem(Context context, PtrClassicFrameLayout ptrClassicFrameLayout) {
        this.ptrClassicFrameLayout = ptrClassicFrameLayout;
        this.context = context;
    }

    @Override
    public ViewHolder viewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_item_banner, parent, false);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        layoutParams.height = (int) (SizeUtils.getFullScreenSize(context)[0] * 148 / 750);
        view.setLayoutParams(layoutParams);

        banner = (Banner) view.findViewById(R.id.banner);
        bannerIndicator = (BannerIndicator) view.findViewById(R.id.indicator);

        banner.setInterval(5000);
        banner.setPageChangeDuration(500);
        banner.setOnTouchCallBack(new OnTouchCallBack() {
            public void actionDown() {
                ptrClassicFrameLayout.setCanRefresh(false);
            }

            public void actionMove() {
                ptrClassicFrameLayout.setCanRefresh(false);
            }

            public void actionMoveCancel() {
                ptrClassicFrameLayout.setCanRefresh(true);
            }

            public void actionCancel() {
                ptrClassicFrameLayout.setCanRefresh(true);
            }
        });
        banner.setBannerDataInit(new Banner.BannerDataInit<Object>() {
            @Override
            public ImageView initImageView() {
                return (ImageView) LayoutInflater.from(context).inflate(R.layout.banner_imageview, null);
            }

            @Override
            public void initImgData(ImageView imageView, Object imgPath) {
                imgShow(imageView, imgPath);
            }
        });
        bannerIndicator.setIndicatorSource(
                ContextCompat.getDrawable(context, R.drawable.banner_indicator_select),//select
                ContextCompat.getDrawable(context, R.drawable.banner_indicator_unselect),//unselect
                SizeUtils.dp2px(context, 6)//widthAndHeight
        );
//        banner.setOnBannerItemClickListener(
//                new Banner.OnBannerItemClickListener() {
//                    @Override
//                    public void onItemClick(int position) {
//                        Toast.makeText(context, "position:" + position, Toast.LENGTH_SHORT).show();
//                    }
//                });

        return ViewHolder.createViewHolder(context, view);
    }

    public abstract void imgShow(ImageView imageView, Object imgPath);

}
