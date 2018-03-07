package com.bizaia.zhongyin.module.common.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yan on 2017/3/17.
 */

public class SearchFixedView extends FrameLayout implements View.OnClickListener{
    @BindView(R.id.flKeyworld)
    FlexboxLayout flKeyworld;
    private TextView tvTitle;
    private String title;

    private List<String> searchStrs;
//    private CustomAdapter adapter;

    private View line;
    private boolean isLineShow;

    private OnClickListener onClickListener;

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private void init(Context context) {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayout container = (LinearLayout) LayoutInflater.from(context)
                .inflate(R.layout.activity_search_fixed, this, false);
        addView(container);
        ButterKnife.bind(this, container);
        searchStrs = new ArrayList<>();
        tvTitle = (TextView) container.findViewById(R.id.tv_title);
        line = container.findViewById(R.id.line);
//        adapter = initAdapter();
//        rvSearch.setLayoutManager(new GridLayoutManager(getContext(), 3));
//        rvSearch.setAdapter(adapter);
    }

//    private CustomAdapter initAdapter() {
//        return new CustomAdapter(searchStrs)
//                .addAdapterItem(new CustomAdapterItem<ViewHolder, String>() {
//                    @Override
//                    public Class dataType() {
//                        return String.class;
//                    }
//
//                    @Override
//                    public ViewHolder viewHolder(ViewGroup parent) {
//                        return ViewHolder.createViewHolder(getContext(), parent, R.layout.activity_search_item);
//                    }
//
//                    @Override
//                    public void bindData(ViewHolder holder, String item, final int position) {
//                        holder.setText(R.id.tv_search_item, searchStrs.get(position));
//                        holder.setOnClickListener(R.id.tv_search_item, new OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                v.setTag(position);
//                                if (onItemClickListener != null) {
//                                    onItemClickListener.onClick(v);
//                                }
//                            }
//                        });
//                    }
//                });
//    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIndexTitles(List<String> searchFixedDatas) {
        this.searchStrs.clear();
        this.searchStrs.addAll(searchFixedDatas);
    }

    private OnClickListener onItemClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onClick(v);
            }
        }
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setData();
    }

    public void setLineShow(boolean lineShow) {
        isLineShow = lineShow;
    }

    private void setData() {
                   flKeyworld.removeAllViews();
                for (int i = 0; i < searchStrs.size(); i++) {
                    TextView view = (TextView) LayoutInflater.from(getContext())
                            .inflate(R.layout.activity_search_item, null);
                    FlexboxLayout.LayoutParams layoutParams =
                            new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                    FlexboxLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(30,10,30,10);
                    view.setText(searchStrs.get(i));
                    view.setTag(i);
                    view.setLayoutParams(layoutParams);
                    view.setOnClickListener(this);
                    flKeyworld.addView(view);
                }

//        tvTitle.setText(title);
//        line.setVisibility(isLineShow ? VISIBLE : GONE);
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search_item:
            if (onItemClickListener != null) {
                 onItemClickListener.onClick(v);
               }
                break;
        }
    }

    public SearchFixedView(Context context) {
        super(context);
        init(context);
    }

    public SearchFixedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SearchFixedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

}
