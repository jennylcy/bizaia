package com.bizaia.zhongyin.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.pay.data.ExchangeRates;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import cn.addapp.pickers.common.LineConfig;
import cn.addapp.pickers.util.ConvertUtils;
import cn.addapp.pickers.widget.WheelListView;

/**
 * Created by zyz on 2017/3/6.
 */

public  class ExchangeRatePop {


    private Context mContext;
    private TextView tvCancel;
    private TextView tvEnter;
    private WheelListView wlvExchangeRates;

    private List<ExchangeRates.DataEntity> rateList;
    private String [] showList;

    private Adapter mWeekAdapter;

    private String payType = "0";
    private String payMathrod = "-1";
    private ExchangeRates.DataEntity dataSelect;

    PopupWindow popupWindow;//弹窗
    View popupWindowView;
    View parentView;
    OnDismissFinishListener onDismissFinishListener;

    public interface OnSelectTimeDialogListener {
        void onOkBtnClick( ExchangeRates.DataEntity dataEntity);
    }

    public ExchangeRatePop(Context context, List<ExchangeRates.DataEntity> list, View pView,
                           final OnSelectTimeDialogListener onSelectTimeDialogListener) {
        parentView = pView;
        this.mContext = context;
        popupWindowView = LayoutInflater.from(this.mContext).inflate(R.layout.dialog_exchangerate, null);

        //内容，高度，宽度
        popupWindow = new PopupWindow(popupWindowView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                true
        );

        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setClippingEnabled(false);

        //菜单背景色
        ColorDrawable dw = new ColorDrawable(0x00000000);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (onDismissFinishListener != null) {
                    parentView.postDelayed(new TimerTask() {
                        @Override
                        public void run() {
                            onDismissFinishListener.OnDismissFinish();
                        }
                    }, 500);
                }
            }
        });



        tvCancel = (TextView) popupWindowView.findViewById(R.id.tvCancel);
        tvEnter = (TextView) popupWindowView.findViewById(R.id.tvEnter);
        wlvExchangeRates = (WheelListView) popupWindowView.findViewById(R.id.wlvExchangeRates);


        if(rateList==null)
            rateList = new ArrayList<>();
        if(showList==null)
            showList= new String[list.size()];
        rateList.clear();
        rateList.addAll(list);
        for(int i=0;i<rateList.size();i++){
            String str = list.get(i).getCurrencyName()+"1"+
                    list.get(i).getCurrencySymbol()+"="+
                    list.get(i).getExchangeRate()+"JPY";
            showList[i]=str;
        }
        dataSelect = rateList.get(0);

        wlvExchangeRates.setItems(showList, 0);
        wlvExchangeRates.setSelectedTextColor(0xFF1156bf);
        wlvExchangeRates.setTextSize(28);
        LineConfig config = new LineConfig();
        config.setColor(Color.parseColor("#00000000"));//线颜色
        config.setAlpha(100);//线透明度
//        config.setItemHeight();
        config.setRatio((float) (1.0 / 5.0));//线比率
        config.setThick(ConvertUtils.toPx(mContext, 1));//线粗
        config.setShadowVisible(false);
        wlvExchangeRates.setLineConfig(config);
        wlvExchangeRates.setOnWheelChangeListener(new WheelListView.OnWheelChangeListener() {
            @Override
            public void onItemSelected(boolean isUserScroll, int index, String item) {
                dataSelect = rateList.get(index);
            }
        });

//
//        mWeekAdapter = new Adapter( rateList);
//
//        mWeekRecyclerView.setLayoutManager(new LinearLayoutManager(context));
//        mWeekRecyclerView.hasFixedSize();
//        mWeekRecyclerView.setAdapter(mWeekAdapter);
//
//        mWeekRecyclerView.scrollToPosition(0);

        tvEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectTimeDialogListener.onOkBtnClick(dataSelect);
                dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
//        popupWindow.setHeight(DisplayUtils.getFullScreenSize(context)[1]);
    }

    public void show() {
        try {
            popupWindow.showAtLocation(parentView, Gravity.TOP, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show(int gravity, int x, int y) {
        try {
            popupWindow.showAtLocation(parentView, gravity, x, y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public View findViewById(int idView) {
        return popupWindowView.findViewById(idView);
    }

    public void setOnFocusListener(int idView, View.OnFocusChangeListener onFocusChangeListener) {
        View view = findViewById(idView);
        view.setFocusable(true);
        view.setOnFocusChangeListener(onFocusChangeListener);
    }


    public void setOnClickListener(int idView, View.OnClickListener onClickListener) {
        findViewById(idView).setOnClickListener(onClickListener);
    }

    public void setText(int idView, String text) {
        if (popupWindowView.findViewById(idView) instanceof TextView) {
            ((TextView) findViewById(idView)).setText(text);
        }
    }

    public View getView(int idView) {
        return findViewById(idView);
    }

    public void setImage(int idView, int res) {
        ((ImageView) findViewById(idView)).setImageDrawable(ContextCompat.getDrawable(mContext, res));
    }

    public void setVisibility(int idView, int visibility) {
        findViewById(idView).setVisibility(visibility);
    }

    /**
     * 弹窗消失
     */
    public void dismiss() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    /**
     * 设置监听
     *
     * @param onDismissFinishListener dismiss完成监听
     */
    public void setOnDismissFinishListener(OnDismissFinishListener onDismissFinishListener) {
        this.onDismissFinishListener = onDismissFinishListener;
    }

    /**
     * dismiss结束监听
     */
    public interface OnDismissFinishListener {
        void OnDismissFinish();
    }


    private class Adapter extends RecyclerView.Adapter {

        private int type;
        private List<ExchangeRates.DataEntity> list;

        public Adapter(List<ExchangeRates.DataEntity> list) {
            this.list = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Adapter.NormalViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_exchange, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof Adapter.NormalViewHolder) {
                final Adapter.NormalViewHolder viewHolder = (Adapter.NormalViewHolder) holder;

                String str = list.get(position).getCurrencyName()+"1"+
                        list.get(position).getCurrencySymbol()+"="+
                        list.get(position).getExchangeRate()+"JPY";
                viewHolder.valueText.setText(str);
                viewHolder.valueText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            Log.e("ExchangeRateDialog", "onFocusChange: --------->"+position );
//                            data = list.get(position);
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