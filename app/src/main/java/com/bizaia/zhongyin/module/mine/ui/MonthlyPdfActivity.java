package com.bizaia.zhongyin.module.mine.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.mine.action.DeleteNotifyServiceAction;
import com.bizaia.zhongyin.module.mine.action.ForceOffLine;
import com.bizaia.zhongyin.module.mine.action.PDFDownloadUpdateAction;
import com.bizaia.zhongyin.module.monthly.action.DeleteNotifyMineAction;
import com.bizaia.zhongyin.module.monthly.adapter.DataAdapterHelper;
import com.bizaia.zhongyin.repository.data.MonthlyNewestBean;
import com.bizaia.zhongyin.repository.data.MonthlyNewestBeanDB;
import com.bizaia.zhongyin.util.FileUtils;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.widget.ForgetPop;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/3/15.
 */

public class MonthlyPdfActivity extends BaseActivity {
    private static final String TAG = "MonthlyPdfActivity";

    @BindView(R.id.rv_pdf)
    RecyclerView rvPdf;
    @BindView(R.id.tv_modify)
    TextView tvModify;
    @BindView(R.id.container)
    RelativeLayout container;

    private MonthlyNewestBeanDB monthlyNewestBeanDB;
    private CustomAdapter adapter;
    private DataAdapterHelper adapterHelper;
    private List<MonthlyNewestBean> objectList;

    private long currentId = -1;
    private int index;
    private List<Long> selectIds = new ArrayList<>();

    private boolean stateIsModify;
    private ForgetPop popDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_monthly_pdf);
        ButterKnife.bind(this);
        setViewParent(rvPdf);
        init();
        rxActionInit();
        initDeletePop();
        addSubscription(RxBus.getInstance().getEvent(ForceOffLine.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ForceOffLine>() {
                    @Override
                    public void onNext(ForceOffLine value) {
                        reLogin();
                        finish();
                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                }));
    }

    private void init() {
        initView();
        requestDataFromDB();
    }

    private void initView() {
        objectList = new ArrayList<>();
        adapterHelper = new DataAdapterHelper();
        adapter = adapterHelper.getAdapter(getBaseContext(), objectList);
        rvPdf.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        rvPdf.setAdapter(adapter);
    }

    private void requestDataFromDB() {
        monthlyNewestBeanDB = new MonthlyNewestBeanDB(getBaseContext());
        List<MonthlyNewestBean> monthlyNewestBeanList = monthlyNewestBeanDB.queryList();
        if (monthlyNewestBeanList != null && !monthlyNewestBeanList.isEmpty()) {
            for (MonthlyNewestBean newestBean:monthlyNewestBeanList){
                if (newestBean.getIsBuy()) {
                    objectList.add(newestBean);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void rxActionInit() {
        addSubscription(RxBus.getInstance().getEvent(PDFDownloadUpdateAction.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<PDFDownloadUpdateAction>() {
                    public void onNext(PDFDownloadUpdateAction value) {
                        progressUpdate(value);
                    }

                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    public void onComplete() {

                    }
                }));

        addSubscription(RxBus.getInstance().getEvent(DeleteNotifyMineAction.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<DeleteNotifyMineAction>() {
                    public void onNext(DeleteNotifyMineAction value) {
                        onDelete(value);
                    }

                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    public void onComplete() {
                    }
                }));
    }

    private void onDelete(DeleteNotifyMineAction value) {
        selectIds.clear();
        selectIds.add(value.id);
        popDelete.show();
    }

    private void progressUpdate(PDFDownloadUpdateAction value) {
        if (!value.id.equals(currentId)) {
            currentId = value.id;
            setIndex(currentId);
        }

        if (rvPdf.findViewHolderForAdapterPosition(index) != null) {
            ViewHolder viewHolder = ((ViewHolder) rvPdf.findViewHolderForAdapterPosition(index));
            viewHolder.setVisible(R.id.view_cover, true);
            TextView progress = viewHolder.getView(R.id.tv_progress);
            progress.setText(String.valueOf(((int) value.updateNum) + " %"));
            if (value.updateNum == 100) {
                ((ViewHolder) rvPdf.findViewHolderForAdapterPosition(index))
                        .setVisible(R.id.view_cover, false);
                progress.setText("");
            }
        }
    }

    private void setIndex(long currentId) {
        for (int i = 0; i < objectList.size(); i++) {
            if (objectList.get(i).getId()==currentId) {
                index = i;
                return;
            }
        }
    }

    @OnClick({R.id.btn_back, R.id.tv_modify})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_modify:
                onModify();
                break;
        }
    }

    private void onModify() {
        stateIsModify = !stateIsModify;
        if (stateIsModify) {
            adapterHelper.setMonthlyModifyAble(true);
            tvModify.setText(getString(R.string.complete));

        } else {
            tvModify.setText(R.string.edit);
            adapterHelper.setMonthlyModifyAble(false);
        }
    }

    private void initDeletePop() {

        popDelete = new ForgetPop(getBaseContext(), container, R.layout.pop_delete) {
            @Override
            public void viewInit() {
                setOnClickListener(R.id.container, popOnClickListener);
                setOnClickListener(R.id.tv_sure, popOnClickListener);
                setOnClickListener(R.id.tv_cancel, popOnClickListener);
            }
        };
    }

    private View.OnClickListener popOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_sure:
                    deleteMonthly();
                case R.id.tv_cancel:
                case R.id.container:
                    popDelete.dismiss();
                    break;
            }
        }
    };

    private void deleteMonthly() {
        List<MonthlyNewestBean> monthlyNewestBeen = new ArrayList<>();
        for (MonthlyNewestBean newestBean : objectList) {
            for (Long selectId : selectIds) {
                if (selectId.equals(newestBean.getId())) {
                    deletePdf(newestBean);
                    monthlyNewestBeen.add(newestBean);
                }
            }
        }

        monthlyNewestBeanDB.deleteBatch(monthlyNewestBeen);
        objectList.removeAll(monthlyNewestBeen);
        adapter.notifyDataSetChanged();
    }

    private void deletePdf(final MonthlyNewestBean newestBean) {
        addSubscription(Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                        RxBus.getInstance().post(new DeleteNotifyServiceAction(newestBean.getId()));
                        FileUtils.deleteFile(newestBean.getDownloadPath());
                        e.onNext(true);
                    }
                }).subscribeOn(Schedulers.newThread())
                        .subscribeWith(new DisposableObserver<Boolean>() {
                            public void onNext(Boolean value) {
                                Log.e(TAG, "onNext: " + value);
                            }

                            public void onError(Throwable e) {
                                e.printStackTrace();

                            }

                            public void onComplete() {

                            }
                        })
        );
    }

}
