package com.bizaia.zhongyin.module.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bizaia.zhongyin.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator
 * Created on 2017/7/5 14:00
 */

public class SelectAdapter extends RecyclerView.Adapter {

    public interface OnItemClickListener {
        void onItemClick(String name);
    }

    private Context mContext;
    private List<String> mList;

    private OnItemClickListener mOnItemClickListener;

    public SelectAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_select, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NormalViewHolder viewHolder = (NormalViewHolder) holder;
        viewHolder.mItemName.setText(mList.get(position));
        viewHolder.mItemLayout.setTag(mList.get(position));
        viewHolder.mItemLayout.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) mOnItemClickListener.onItemClick((String) v.getTag());
        }
    };

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_name)
        TextView mItemName;
        @BindView(R.id.item_layout)
        LinearLayout mItemLayout;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
