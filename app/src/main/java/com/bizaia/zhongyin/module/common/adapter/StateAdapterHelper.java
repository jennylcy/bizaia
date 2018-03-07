package com.bizaia.zhongyin.module.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;


import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.ViewHolder;
import com.bizaia.zhongyin.widget.adapter.StateAdapterItem;

import java.util.List;


/**
 * Created by yan on 2017/2/28.
 */

public class StateAdapterHelper {

    public CustomAdapter getAdapter(final Context context, List list) {
        return new CustomAdapter(list)
                //state_data_error
                .addAdapterItem(new StateAdapterItem<ViewHolder>("DATA_ERROR") {
                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.state_data_error);
                    }
                })
                //state_data_empty
                .addAdapterItem(new StateAdapterItem("DATA_EMPTY") {
                    @Override
                    public RecyclerView.ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.state_data_empty);
                    }
                })

                //state_net_error
                .addAdapterItem(new StateAdapterItem("NET_ERROR") {
                    @Override
                    public RecyclerView.ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.state_net_error);
                    }
                });
    }
}
