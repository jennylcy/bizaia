package com.bizaia.zhongyin.module.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.common.adapter.StateAdapterHelper;
import com.bizaia.zhongyin.module.mine.api.IsAttentionAPI;
import com.bizaia.zhongyin.module.mine.data.AttentionsBean;
import com.bizaia.zhongyin.module.mine.ui.CompanyHostActivity;
import com.bizaia.zhongyin.repository.AddressConfiguration;
import com.bizaia.zhongyin.util.ImageLoaderUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.CustomAdapterItem;
import com.bizaia.zhongyin.widget.adapter.ViewHolder;
import com.bizaia.zhongyin.widget.refresh.PtrClassicFrameLayout;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by yan on 2017/2/28.
 */

public final class DataAttentionsAdapterHelper extends StateAdapterHelper {
    private Context context;
    private IsAttentionAPI isAttentionAPI;
    private CustomAdapter customAdapter;
    private List<Object> listData;
    private int pos;
    private CancelAttion cancelAttion;

    public CustomAdapter getAdapter(final Context context, List list) {
        return getAdapter(context, list, null,null);
    }

    public CustomAdapter getAdapter(final Context context, final List list, final PtrClassicFrameLayout ptrClassicFrameLayout, final CancelAttion cancelAttion) {
        this.cancelAttion = cancelAttion;
        this.context = context;
        listData = list;

        customAdapter=super.getAdapter(context, list)
                //--------------------------- attention ----------------------------
                .addAdapterItem(new CustomAdapterItem<ViewHolder, AttentionsBean.DataEntity.DatasEntity>() {
                    @Override
                    public Class dataType() {
                        return AttentionsBean.DataEntity.DatasEntity.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.item_attention);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final AttentionsBean.DataEntity.DatasEntity
                            item, final int position) {

                        holder.setText(R.id.tvCompanyName,item.getOrgName());
                        holder.setOnClickListener(R.id.rlAttention, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            Intent intent = new Intent(context, CompanyHostActivity.class);
                            intent.putExtra("orgId",item.getOrgId());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            }
                        });
                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH +item.getOrgAvatarUrl(),
                                (ImageView) holder.getView(R.id.ivIcon), ImageLoaderUtils.getImageHighQualityOptions());
                        holder.setOnClickListener(R.id.ivAction, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                cancelAttion.cancel(position);
                            }
                        });
                    }
                })
        ;

        return customAdapter;
    }


    public  interface CancelAttion{
        void cancel(int pos);
    }
}
