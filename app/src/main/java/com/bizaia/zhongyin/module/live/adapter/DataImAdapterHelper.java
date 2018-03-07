package com.bizaia.zhongyin.module.live.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.common.adapter.StateAdapterHelper;
import com.bizaia.zhongyin.module.live.data.IMDataBean;
import com.bizaia.zhongyin.module.live.data.IMReDataBean;
import com.bizaia.zhongyin.module.live.iml.IRequestIm;
import com.bizaia.zhongyin.repository.AddressConfiguration;
import com.bizaia.zhongyin.util.AppUtils;
import com.bizaia.zhongyin.util.ImageLoaderUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.CustomAdapterItem;
import com.bizaia.zhongyin.widget.adapter.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by zyz on 2017/3/7.
 */

public class DataImAdapterHelper extends StateAdapterHelper{
    private Context context;
    public CustomAdapter getAdapter(final Context context, List list, final boolean isShowLogo, final IRequestIm iRequestIm) {
        this.context = context;
        return super.getAdapter(context, list)
                //--------------------------- im value ----------------------------
                .addAdapterItem(new CustomAdapterItem<ViewHolder, IMDataBean>() {
                    @Override
                    public Class dataType() {
                        return IMDataBean.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.item_im_live);
                    }

                    @Override
                    public void bindData(ViewHolder holder,IMDataBean item, int position) {

                        Log.e("DataImAdapterHelper", "bindData: ------->"+ AddressConfiguration.MAIN_PATH+item.getHeadImg() );
                        if(isShowLogo) {
                            String url = item.getHeadImg();
                            if(!AppUtils.isUrl(url)){
                                url = AddressConfiguration.MAIN_PATH+url;
                            }
                            ImageLoader.getInstance().displayImage(url,
                                    (ImageView) holder.getView(R.id.rivUserIcon), ImageLoaderUtils.getImageHighQualityOptions());
                            holder.setVisible(R.id.rivUserIcon,true);
                        }else{
                            holder.setVisible(R.id.rivUserIcon,false);
                        }
                        if(item.isTeacher()){
                            holder.setTextColor(R.id.tvImValue, Color.parseColor("#2e76d0"));
                            holder.setText(R.id.tvImValue, "[講師]:" + item.getNews());
                        }else {
                            holder.setTextColor(R.id.tvImValue, Color.parseColor("#a1a1a1"));
                            holder.setText(R.id.tvImValue, item.getNickName() + ":" + item.getNews());
                        }
                    }
                })
                //--------------------------- im request ----------------------------
                .addAdapterItem(new CustomAdapterItem<ViewHolder, IMReDataBean>() {
                    @Override
                    public Class dataType() {
                        return IMReDataBean.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.item_im_request);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final IMReDataBean
                            item, final int position) {
//                        ImageLoader.getInstance().displayImage(item.getHeadImg(),
//                                (ImageView) holder.getView(R.id.rivRequestIcon), ImageLoaderUtils.getImageOptions());
                        holder.setText(R.id.tvRequestId,(position+1)+"");
                        holder.setText(R.id.tvRequestName,item.getNickName());
                        if(item.isAgree())
                            holder.setSelected(R.id.tvRequstIm,true);
                        else
                            holder.setSelected(R.id.tvRequstIm,false);

                        holder.setOnClickListener(R.id.tvRequstIm, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                               if(item.isAgree()){
                                   iRequestIm.agree(false,position);
                               } else{
                                   iRequestIm.agree(true,position);
                               }
                            }
                        });
                    }
                });

    }


}
