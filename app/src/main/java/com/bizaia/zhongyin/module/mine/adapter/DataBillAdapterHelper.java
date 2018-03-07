package com.bizaia.zhongyin.module.mine.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.common.adapter.StateAdapterHelper;
import com.bizaia.zhongyin.module.mine.data.BillBean;
import com.bizaia.zhongyin.util.TimeUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.CustomAdapterItem;
import com.bizaia.zhongyin.widget.adapter.ViewHolder;
import com.bizaia.zhongyin.widget.refresh.PtrClassicFrameLayout;

import java.util.List;

/**
 * Created by yan on 2017/2/28.
 */

public final class DataBillAdapterHelper extends StateAdapterHelper {
    private Context context;

    public CustomAdapter getAdapter(final Context context, List list, final PtrClassicFrameLayout ptrClassicFrameLayout) {
        this.context = context;
        return super.getAdapter(context, list)
                //  news
                .addAdapterItem(new CustomAdapterItem<ViewHolder, BillBean.DataEntity.TradeRecordListEntity.DatasEntity>() {
                    @Override
                    public Class dataType() {
                        return BillBean.DataEntity.TradeRecordListEntity.DatasEntity.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.item_mine_bill);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final BillBean.DataEntity.TradeRecordListEntity.DatasEntity
                            item, int position) {
                        holder.setText(R.id.tvBillTitle, item.getTitle());
                        if(item.getType()==0) {
                            holder.setText(R.id.tvPrice, "-JPY" + String.format("%.2f",item.getPrice()));
                        }else{
                            holder.setText(R.id.tvPrice, "+JPY" + String.format("%.2f",item.getPrice()));
                        }
                        holder.setText(R.id.tvBuyType, item.getPayMethodName());
                        holder.setText(R.id.tvTime,
                                TimeUtils.timeTransGBToCN(item.getCreateTime()));
                    }
                })
                ;
    }

}
