package com.bizaia.zhongyin.module.login.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.login.data.PlaceBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wujiaquan on 2017/4/17.
 */

public class SelectPlaceAdapter extends RecyclerView.Adapter {

    public interface OnItemClickListener {
        void onItemClick(PlaceBean placeBean);
    }

    private Context mContext;
    private List<PlaceBean> mList;

    private OnItemClickListener mOnItemClickListener;

    public SelectPlaceAdapter(Context context, List<PlaceBean> list) {
        mContext = context;
        mList = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setData(List<PlaceBean> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_select_place_normal, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NormalViewHolder viewHolder = (NormalViewHolder) holder;
        final PlaceBean bean = mList.get(position);
        viewHolder.mItemPlaceName.setText(bean.getName());
        viewHolder.mItemPlaceNumber.setText("+" + bean.getNumber());
        viewHolder.mItemPlaceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) mOnItemClickListener.onItemClick(bean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_place_name)
        TextView mItemPlaceName;
        @BindView(R.id.item_place_number)
        TextView mItemPlaceNumber;
        @BindView(R.id.item_place_layout)
        LinearLayout mItemPlaceLayout;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
