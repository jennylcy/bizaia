package com.bizaia.zhongyin.module.live.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.ResumeFragment;
import com.bizaia.zhongyin.repository.AddressConfiguration;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zyz on 2017/4/5.
 */

public class LivePdfFragment extends ResumeFragment {

    @BindView(R.id.ivCourseware)
    ImageView ivCourseware;
    @BindView(R.id.btnCheck)
    Button btnCheck;

    private DisplayImageOptions options;
    private ShowPdf showPdf;
    private String url;
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live_pdf, container, false);
        ButterKnife.bind(this, view);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ad_default_img) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ad_default_img)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ad_default_img) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                // .imageScaleType(ImageScaleType.NONE)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                // .decodingOptions(android.graphics.BitmapFactory.Options.decodingOptions)//设置图片的解码配置
                // .delayBeforeLoading(int delayInMillis)//int
                // delayInMillis为你设置的下载前的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                // .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPdf.show();
            }
        });
        return view;
    }

    public void setShowPdf(ShowPdf showPdf) {
        this.showPdf = showPdf;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser&&url!=null&&ivCourseware!=null) {
            ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH+url,
                    ivCourseware, options);
        }
    }


    public void setData(String url){
        this.url = url;
            if (url!=null&&ivCourseware!=null){
                ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH+url,ivCourseware,options);
            }
    }

    public interface  ShowPdf{
        void show();
    }
}
