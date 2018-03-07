package com.bizaia.zhongyin.module.message.adpter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.common.adapter.StateAdapterHelper;
import com.bizaia.zhongyin.module.discovery.ui.chair.detail.DetailActivity;
import com.bizaia.zhongyin.module.live.ui.LiveDetailActivity;
import com.bizaia.zhongyin.module.message.PushDataConfig;
import com.bizaia.zhongyin.module.message.data.MessageData;
import com.bizaia.zhongyin.util.TimeUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.CustomAdapterItem;
import com.bizaia.zhongyin.widget.adapter.ViewHolder;

import java.util.List;

/**
 * Created by yan on 2017/2/28.
 */

public final class DataAdapterHelper extends StateAdapterHelper {
    private static final String TAG = "DataAdapterHelper";
    private Context context;
    private boolean isModify;
    private onReadListener onReadListener;

    public CustomAdapter getAdapter(final Context context, List list,onReadListener listener) {
        this.context = context;
        this.onReadListener = listener;
        return super.getAdapter(context, list)

                //--------------------------- recruitData ----------------------------
                .addAdapterItem(new CustomAdapterItem<ViewHolder, MessageData.DataBean.DatasBean>() {
                    @Override
                    public Class dataType() {
                        return MessageData.DataBean.DatasBean.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.item_message);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final MessageData.DataBean.DatasBean
                            item, int position) {
                        holder.setText(R.id.tv_title, item.getTitle());
                        holder.setText(R.id.tv_body, item.getBody());
                        holder.setText(R.id.tv_time, TimeUtils.timeTransGBToCN(item.getCreateTs()));
                        holder.setTag(R.id.ll_container, item);
                        holder.setVisible(R.id.iv_delete, isModify);
                        holder.setTag(R.id.iv_delete, item);
                        if(item.isRead()) {
                            holder.setVisible(R.id.ivItemTips,false);
                        }else{
                            holder.setVisible(R.id.ivItemTips,true);
                        }
                        holder.setOnClickListener(R.id.iv_delete, onClickListener);
                        holder.setOnClickListener(R.id.ll_container, onClickListener);
                    }
                })
                ;
    }

    public interface onReadListener{
        void  read(long id , int pos);
    }

    public void triggerModify() {
        isModify = !isModify;
    }

    public boolean isModify() {
        return isModify;
    }

    private View.OnClickListener onDelClickListener;

    public void setOnDelClickListener(View.OnClickListener onDelClickListener) {
        this.onDelClickListener = onDelClickListener;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.iv_delete) {
                if (onDelClickListener != null) {
                    onDelClickListener.onClick(v);
                }
                return;
            }

            MessageData.DataBean.DatasBean item = (MessageData.DataBean.DatasBean) v.getTag();
            if(onReadListener!=null)
            onReadListener.read(item.getId(),item.getPosition());
            if(item.getType()==null)
                return;
            switch (item.getType()) {
                case PushDataConfig.LECTURE:

                    context.startActivity(new Intent(context, DetailActivity.class)
                            .putExtra("url", item.getUrl())
                            .putExtra("title", item.getTitle())
                            .putExtra("id", item.getPid())
                            .putExtra("type", 5)
                            .putExtra("orgId",item.getOrgId())
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    );
                    break;
                case PushDataConfig.VOD:
                    context.startActivity(new Intent(context,
                            com.bizaia.zhongyin.module.video.ui.detail.DetailActivity.class)
                            .putExtra("title", item.getTitle())
                            .putExtra("videoId", item.getPid())
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    );
                    break;
                case PushDataConfig.LIVE:
                    context.startActivity(new Intent(context, LiveDetailActivity.class)
                            .putExtra("id", item.getPid())
                            .putExtra("liveType", 0)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    );
                    break;
            }
        }
    };
}
