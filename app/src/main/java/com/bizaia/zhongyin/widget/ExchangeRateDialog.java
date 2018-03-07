package com.bizaia.zhongyin.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.pay.data.ExchangeRates;

import java.util.ArrayList;
import java.util.List;



public class ExchangeRateDialog extends Dialog {

    private Context mContext;
    private View mView;
    private RecyclerView mWeekRecyclerView;
    private TextView tvCancel;
    private TextView tvEnter;

    private List<ExchangeRates.DataEntity> rateList;

    private Adapter mWeekAdapter;

    private String payType = "0";
    private String payMathrod = "-1";
    private ExchangeRates.DataEntity data;

    public interface OnSelectTimeDialogListener {
        void onOkBtnClick(Dialog dialog, ExchangeRates.DataEntity dataEntity);
    }

    public ExchangeRateDialog(@NonNull Context context, List<ExchangeRates.DataEntity> list, @NonNull final OnSelectTimeDialogListener onSelectTimeDialogListener) {
        super(context, R.style.ExchangeRateDialog);
        mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.dialog_exchangerate, null);
        setContentView(mView);
//        mWeekRecyclerView = (RecyclerView) mView.findViewById(R.id.rvExchangeRates);
        tvCancel = (TextView) mView.findViewById(R.id.tvCancel);
        tvEnter = (TextView) mView.findViewById(R.id.tvEnter);


        if(rateList==null)
            rateList = new ArrayList<>();
        rateList.clear();
        rateList.addAll(list);

        mWeekAdapter = new Adapter( rateList);

        mWeekRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mWeekRecyclerView.hasFixedSize();
        mWeekRecyclerView.setAdapter(mWeekAdapter);

        mWeekRecyclerView.scrollToPosition(0);

        tvEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectTimeDialogListener.onOkBtnClick(ExchangeRateDialog.this, data);
            }
        });
    }

    private class Adapter extends RecyclerView.Adapter {

        private int type;
        private List<ExchangeRates.DataEntity> list;

        public Adapter(List<ExchangeRates.DataEntity> list) {
            this.list = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NormalViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_exchange, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof NormalViewHolder) {
                final NormalViewHolder viewHolder = (NormalViewHolder) holder;

                String str = list.get(position).getCurrencyName()+"1"+
                        list.get(position).getCurrencySymbol()+"="+
                        list.get(position).getExchangeRate()+"JPY";
                viewHolder.valueText.setText(str);
                viewHolder.valueText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            Log.e("ExchangeRateDialog", "onFocusChange: --------->"+position );
                               data = list.get(position);
                               payType = list.get(position).getExchangeRate()+"";
                               payMathrod = list.get(position).getPayMethodId();
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class NormalViewHolder extends RecyclerView.ViewHolder {

            public TextView valueText;

            public NormalViewHolder(View itemView) {
                super(itemView);
                valueText = (TextView) itemView.findViewById(R.id.tvValueText);
            }
        }
    }
}
