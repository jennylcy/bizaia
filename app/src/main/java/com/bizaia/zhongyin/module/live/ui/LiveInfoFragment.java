package com.bizaia.zhongyin.module.live.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.ResumeFragment;
import com.bizaia.zhongyin.module.live.api.LiveDetailAPI;
import com.bizaia.zhongyin.module.live.data.LiveDetailBean;
import com.bizaia.zhongyin.module.live.iml.ILiveDetailView;
import com.bizaia.zhongyin.module.mine.adapter.DataLectureresAdapterHelper;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.bizaia.zhongyin.repository.data.LiveRemindBeanDB;
import com.bizaia.zhongyin.util.AppUtils;
import com.bizaia.zhongyin.util.TimeUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.LoadMore.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zyz on 2017/4/5.
 */

@SuppressLint("ValidFragment")
public class LiveInfoFragment extends ResumeFragment implements ILiveDetailView {

    @BindView(R.id.tvLiveDesc)
    TextView tvLiveDesc;
    @BindView(R.id.tvLivePlayDate)
    TextView tvLivePlayDate;
    @BindView(R.id.tvLivePrice)
    TextView tvLivePrice;
    @BindView(R.id.tvLiveTeacher)
    TextView tvLiveTeacher;
    @BindView(R.id.tvLiveNumber)
    TextView tvLiveNumber;
    @BindView(R.id.rvTeacher)
    RecyclerView rvTeacher;

    @BindView(R.id.ivSetRemind)
    ImageView ivSetRemind;
    @BindView(R.id.linRemind)
    LinearLayout linRemind;
    @BindView(R.id.tvRemindText)
    TextView tvRemindText;
    @BindView(R.id.tvLiveTitle)
    TextView tvLiveTitle;

    private List<Object> dataList;
    private CustomAdapter adapter;
    private LoadMoreWrapper moreWrapper;
    private LiveDetailBean data;
    private int liveType;
    private long id;

    private IsetRemind isetRemind;
    private boolean isRemind;
    private LiveDetailAPI liveDetailAPI;

    public LiveInfoFragment(int type, long id) {
        this.liveType = type;
        this.id = id;
    }

    private LiveRemindBeanDB liveRemindBeanDao;


    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmen_live_detail_info, container, false);
        ButterKnife.bind(this, view);

        rvTeacher.setLayoutManager(new LinearLayoutManager(getContext()));
        dataList = new ArrayList<>();
        adapter = new DataLectureresAdapterHelper().getAdapter(getContext(), dataList, null);
        moreWrapper = new LoadMoreWrapper(adapter);
        moreWrapper.setLoadMoreView(getLoadMoreView());
        moreWrapper.setOnLoadMoreListener(onLoadMoreListener);
        rvTeacher.setAdapter(moreWrapper);

        if (liveType == 0) {
//           int userId= BIZAIAApplication.getInstance().getUserBean().getId();
            linRemind.setVisibility(View.VISIBLE);
            liveRemindBeanDao = new LiveRemindBeanDB(getContext());
//            LiveRemindBean liveRemindBean =liveRemindBeanDao.queryBean(id,userId);
        } else {
            linRemind.setVisibility(View.GONE);
        }
        linRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isetRemind.remind();
            }
        });
        liveDetailAPI = new LiveDetailAPI(this);
        initData();

        return view;
    }

    private View getLoadMoreView() {
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0);
        View loadMore = LayoutInflater.from(getContext()).inflate(R.layout.item_loadmore, null);
        loadMore.setLayoutParams(layoutParams);
        loadMore.setVisibility(View.GONE);
        return loadMore;
    }

    LoadMoreWrapper.OnLoadMoreListener onLoadMoreListener = new LoadMoreWrapper.OnLoadMoreListener() {
        @Override
        public void onLoadMoreRequested() {

        }
    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("LiveInfoFragment", "setUserVisibleHint: ---------->"+ data+"----"+isVisibleToUser+"------"+tvLiveTitle);
        if (isVisibleToUser&&data!=null&&tvLiveTitle!=null) {
            initData();
        }
    }


    private void initData() {
        if (data==null||data.getData()==null)
            return;
        tvLiveTitle.setText(data.getData().getLive().getTitle());
        tvLiveDesc.setText(data.getData().getLive().getIntroduction());
        tvLivePlayDate.setText(getResources().getString(R.string.live_start_date) + TimeUtils.timeTransGBToCN(data.getData().getLive().getStartTime()));
        if(data.getData().getLive().getPrice()==0){
            tvLivePrice.setText(getResources().getString(R.string.live_cost) + getString(R.string.video_free));
        }else {
            tvLivePrice.setText(getResources().getString(R.string.live_cost) + "JPY" + data.getData().getLive().getPrice());
        }
        String teacherName = "";
        if (data.getData().getLecturerList() != null && !data.getData().getLecturerList().isEmpty())
            for (int i = 0; i < data.getData().getLecturerList().size(); i++) {
                if (i == 0) {
                    teacherName = data.getData().getLecturerList().get(i).getName();
                } else {
                    teacherName = teacherName + "," + data.getData().getLecturerList().get(i).getName();
                }
            }

        tvLiveTeacher.setText(getResources().getString(R.string.live_teacher_s) + teacherName);
        if(data.getData().getLive().getPrice()==0){
            tvLiveNumber.setText(getResources().getString(R.string.bought_count) +"なし");
        }else{
            tvLiveNumber.setText(getResources().getString(R.string.bought_count) + data.getData().getLive().getBuyNum());
        }
        if (dataList.isEmpty()) {
            dataList.addAll(data.getData().getLecturerList());
            moreWrapper.notifyDataSetChanged();
        }


        isRemind = data.getData().getLive().isNotifi();
        if (isRemind) {
            linRemind.setBackgroundResource(R.drawable.circle_remind);
            tvRemindText.setTextColor(Color.parseColor("#ffffff"));
            tvRemindText.setText(R.string.live_reminded);
            ivSetRemind.setImageResource(R.drawable.icon_tixing2);
        } else {
            linRemind.setBackgroundResource(R.drawable.circle_unremind);
            tvRemindText.setTextColor(Color.parseColor("#12a345"));
            tvRemindText.setText(R.string.live_remind);
            ivSetRemind.setImageResource(R.drawable.icon_tixing);
        }
    }

    public void setData(LiveDetailBean data) {
        this.data = data;
        if (data!=null&&tvLiveTitle!=null) {
            initData();
        }
    }

    @Override
    public void showLiveDetail(LiveDetailBean data) {

    }

    @Override
    public void showOrder(OrderData data) {

    }

    @Override
    public void showNotifiSuccess() {
        data.getData().getLive().setNotifi(true);
        linRemind.setBackgroundResource(R.drawable.circle_remind);
        tvRemindText.setTextColor(Color.parseColor("#ffffff"));
        tvRemindText.setText(R.string.live_reminded);
        ivSetRemind.setImageResource(R.drawable.icon_tixing2);
        AppUtils.addAlarm(TimeUtils.timeTranstimestamp(data.getData().getLive().getStartTime()),
                data.getData().getLive().getTitle(),getContext());
    }

    @Override
    public void showUnnotifiSuccess() {
        data.getData().getLive().setNotifi(false);
        linRemind.setBackgroundResource(R.drawable.circle_unremind);
        tvRemindText.setTextColor(Color.parseColor("#12a345"));
        tvRemindText.setText(R.string.live_remind);
        ivSetRemind.setImageResource(R.drawable.icon_tixing);
        AppUtils.deleteAlarm(getContext(),data.getData().getLive().getTitle());
    }

    @Override
    public void onRelogin() {

    }

    @Override
    public void showLiveDetailError(int code, String msg) {

    }


    public interface IsetRemind {
        void remind();
    }

    public void setIsetRemind(IsetRemind isetRemind) {
        this.isetRemind = isetRemind;
    }
}
