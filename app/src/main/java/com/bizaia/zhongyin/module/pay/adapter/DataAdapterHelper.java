package com.bizaia.zhongyin.module.pay.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.common.adapter.StateAdapterHelper;
import com.bizaia.zhongyin.module.pay.PaymentActivity;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.CustomAdapterItem;
import com.bizaia.zhongyin.widget.adapter.ViewHolder;
import com.bizaia.zhongyin.widget.refresh.PtrClassicFrameLayout;

import java.util.List;

/**
 * Created by yan on 2017/2/28.
 */

public final class DataAdapterHelper extends StateAdapterHelper {
    private Context context;
    private int pos=0;
    private PaymentActivity.PayTypeSelect  payTypeSelect;

    public CustomAdapter getAdapter(final Context context, List list) {
        return getAdapter(context, list, null,null);
    }

    public CustomAdapter getAdapter(final Context context, List list, final PtrClassicFrameLayout ptrClassicFrameLayout, final PaymentActivity.PayTypeSelect payTypeSelect) {
        this.context = context;
        this.payTypeSelect = payTypeSelect;
        return super.getAdapter(context, list)
                //
                .addAdapterItem(new CustomAdapterItem<ViewHolder, String>() {
                    @Override
                    public Class dataType() {
                        return String.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.item_payment);
                    }

                    @Override
                    public void bindData(final ViewHolder holder, String
                            item, final int position) {
                        holder.setText(R.id.tvPaymentName,item);
                        Log.e("DataAdapterHelper", "bindData:------> " +item);
                        if(pos==position)
                            holder.setSelected(R.id.ivPaymentSelect,true);
                        else
                            holder.setSelected(R.id.ivPaymentSelect,false);
                        holder.setOnClickListener(R.id.ivPaymentSelect, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pos = position;
                               payTypeSelect.select(position);
                            }
                        });
                    }
                })
                ;
    }
}
