package com.bizaia.zhongyin.module.mine.ui.setting.area;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.MainActivity;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.mine.data.AreaData;
import com.bizaia.zhongyin.module.mine.data.AreaTitleData;
import com.bizaia.zhongyin.module.mine.data.AreaTitleSubData;
import com.bizaia.zhongyin.repository.SPConfiguration;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.SPUtils;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.CustomAdapterItem;
import com.bizaia.zhongyin.widget.adapter.ViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yan on 2017/3/20.
 */

public class AreaActivity extends BaseActivity implements AreaContract.View<AreaData> {
    private static final String TAG = "AreaActivity";

    @BindView(R.id.rv_area)
    RecyclerView rvArea;
    private AreaContract.Presenter presenter;

    private CustomAdapter adapter;

    private AreaData areaData;

    private List<Object> objectList;

    /**
     * key:country value:area
     */
    private LongSparseArray<Long> selectArea;

    /**
     * key:country value:area
     */
    private Map<String, String> mapAreaData;
    /**
     * is showing view
     */
    private AreaSelectView openView;
    /**
     * select area data
     */
    private List<Long> selectDatas;

    private boolean isAllSelect;
    private boolean isFirst;
    private int allNumbs;

    public static void show(Activity context, boolean isFirst) {
        Intent intent = new Intent(context, AreaActivity.class);
        intent.putExtra("isFirst", isFirst);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        {
            Locale myLocale = new Locale("jp");
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_setting_area);
        ButterKnife.bind(this);
        setViewParent(rvArea);
        isFirst = getIntent().getBooleanExtra("isFirst", false);
        isAllSelect = BIZAIAApplication.getInstance().isAllArea();
        new AreaPresent(this);
        initAreaData();
        getSelectData();
    }

    private void initAreaData() {
        selectArea = new LongSparseArray<>();
        selectDatas = new ArrayList<>();
        objectList = new ArrayList<>();
        mapAreaData = new HashMap<>();
        objectList.add(new AreaTitleData(getString(R.string.select_city)));
        objectList.add(new AreaTitleSubData(getString(R.string.select_all), getString(R.string.clear_all)));
        presenter.getArea();
    }

    private CustomAdapter initAdapter() {
        return new CustomAdapter(objectList)
                .addAdapterItem(new CustomAdapterItem<ViewHolder, AreaData.DataBean>() {

                    @Override
                    public Class dataType() {
                        return AreaData.DataBean.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(getBaseContext(), new AreaSelectView(getBaseContext()));
                    }

                    @Override
                    public void bindData(ViewHolder holder, AreaData.DataBean item, int position) {
                        AreaSelectView areaSelectView = (AreaSelectView) holder.getConvertView();
                        areaSelectView.setTitle(item.getCountry());
                        for (AreaData.DataBean.ProvinceListBean bean : item.getProvinceList()) {
                            bean.setSelect(false);
                            if (selectDatas.contains(bean.getId())) {
                                bean.setSelect(true);
                                selectArea.put(bean.getId(), item.getId());
                            }
                        }

                        areaSelectView.setAreaData(item);
                        areaSelectView.setOnItemSelect(onItemSelect);
                        areaSelectView.setOnBarClickListener(onBarClickListener);

                        if (isCearAll) {
                            areaSelectView.clearView();
                        }
                    }
                })
                .addAdapterItem(new CustomAdapterItem<ViewHolder, AreaTitleData>() {
                    @Override
                    public Class dataType() {
                        return AreaTitleData.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(getBaseContext(), parent, R.layout.activity_mine_setting_area_item_title);
                    }

                    @Override
                    public void bindData(ViewHolder holder, AreaTitleData item, int position) {
                        holder.setText(R.id.tv_content, getString(R.string.area_tips));
                    }
                }).addAdapterItem(new CustomAdapterItem<ViewHolder, AreaTitleSubData>() {
                    @Override
                    public Class dataType() {
                        return AreaTitleSubData.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(getBaseContext(), parent, R.layout.activity_mine_setting_area_item_sub_title);
                    }

                    @Override
                    public void bindData(ViewHolder holder, AreaTitleSubData item, int position) {
                        holder.setText(R.id.tv_left, item.title1);
                        holder.setText(R.id.tv_right, item.title2);
                        if (isAllSelect)
                            holder.setSelected(R.id.tv_left, true);
                        else
                            holder.setSelected(R.id.tv_left, false);
                        holder.setOnClickListener(R.id.tv_left, optionClickListener);
                        holder.setOnClickListener(R.id.tv_right, optionClickListener);
                    }
                })
                ;
    }

    private View.OnClickListener onBarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (openView != null && openView != v) {
                openView.hide();
            }
            openView = ((AreaSelectView) v);
        }
    };
    private View.OnClickListener optionClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = v.getTag() == null ? -1 : (int) v.getTag();
            Log.e(TAG, "onClick: " + position);
            switch (v.getId()) {
                case R.id.tv_left:
                    if (!isAllSelect) {
                        isAllSelect = true;
                        areaDataAllControl(true);
                    }
                    break;
                case R.id.tv_right:
                    RxBus.getInstance().post(new ClearAll());
//                    if(isAllSelect) {
                    isAllSelect = false;
                    areaDataAllControl(false);
//                    }
                    break;
            }
        }
    };

    public static class ClearAll {

    }

    private boolean isCearAll;

    private void areaDataAllControl(boolean allSelect) {
        isCearAll = allSelect;
        for (Object obj : objectList) {
            if (obj instanceof AreaData.DataBean) {
                AreaData.DataBean dataBean = (AreaData.DataBean) obj;
                onItemSelect.onSelect(dataBean, dataBean.getProvinceList(), allSelect);
            }
        }
        Log.e(TAG, "onSelect: " + selectArea);
        Log.e(TAG, "onSelect: " + mapAreaData);
        adapter.notifyDataSetChanged();
    }

    private AreaSelectView.OnItemSelect onItemSelect = new AreaSelectView.OnItemSelect() {
        @Override
        public void onSelect(AreaData.DataBean areaData, List<AreaData.DataBean.ProvinceListBean> itemCurrentData, boolean selected) {
            for (AreaData.DataBean.ProvinceListBean bean : itemCurrentData) {
                Log.e(TAG, "onSelect: " + areaData.getCountry() + "   " + bean.getProvince() + "   " + selected);
                if (selected) {
                    isCearAll = false;
                    selectArea.put(bean.getId(), areaData.getId());
                    selectDatas.add(bean.getId());
                } else {
                    selectDatas.remove(bean.getId());
                    selectArea.delete(bean.getId());
                }
                setAreaWholeData(areaData, bean, selected);
            }
        }
    };

    private void setAreaWholeData(AreaData.DataBean areaData, AreaData.DataBean.ProvinceListBean bean, boolean selected) {
        if (selected) {
            mapAreaData.put(bean.getProvince(), areaData.getCountry());
        } else {
            mapAreaData.remove(bean.getProvince());
        }
    }

    @OnClick({R.id.tv_commit, R.id.btn_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:
                commit();

                break;
            case R.id.btn_back:
                if (isFirst)
                    startActivity(new Intent(AreaActivity.this, MainActivity.class));
                finish();
                break;
        }
    }

    private void commit() {
        StringBuilder stringBuilder = new StringBuilder();
        if (allNumbs != selectDatas.size()) {
            for (int i = 0; i < selectArea.size(); i++) {
                Long areaId = selectArea.keyAt(i);
                stringBuilder.append(String.valueOf(areaId));
                stringBuilder.append("@");
            }
            if (stringBuilder.lastIndexOf("@") != -1) {
                stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("@"));
            }
        }
        BIZAIAApplication.getInstance().setAreaId(stringBuilder.toString());

        SPUtils.putString(getApplicationContext(), MODE_PRIVATE, SPConfiguration.APP_NAME, SPConfiguration.AREA_STR_DATA
                , new Gson().toJson(mapAreaData));
        Log.e(TAG, "commit: " + new Gson().toJson(mapAreaData));

        if (isAllSelect)
            BIZAIAApplication.getInstance().setAllArea(true);
        else
            BIZAIAApplication.getInstance().setAllArea(false);
        ToastUtils.showInUIThead(getBaseContext(), getString(R.string.setting_success));
        if (!isFirst) {
            setResult(1, new Intent().putExtra("refreshArea", true));
        } else {
            startActivity(new Intent(AreaActivity.this, MainActivity.class));
        }
        finish();

    }

    @Override
    public void dataSuccess(AreaData news) {
        if (news.getCode() == 200
                && news.getData() != null) {
            areaData = news;
            for (AreaData.DataBean bean : areaData.getData()) {
                objectList.add(bean);
                allNumbs = allNumbs + bean.getProvinceList().size();
            }
            rvArea.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            adapter = initAdapter();
            rvArea.setAdapter(adapter);
        }else{
            Log.e(TAG, "dataSuccess: ------------->area null" );
        }
    }

    @Override
    public void dataError() {
//        ToastUtils.showInUIThead(this.getApplicationContext(), "提交失败");
    }

    @Override
    public void onRelogin() {
        reLogin();
    }

    @Override
    public void setPresenter(AreaContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void getSelectData() {
        String areas = BIZAIAApplication.getInstance().getAreaId();
        if (TextUtils.isEmpty(areas)) return;
        String[] areaStrs = areas.split("@");
        for (String str : areaStrs) {
            selectDatas.add(Long.parseLong(str));
        }

        String countyAndAreas = SPUtils.getString(getBaseContext(), MODE_PRIVATE, SPConfiguration.APP_NAME, SPConfiguration.AREA_STR_DATA);
        if (!TextUtils.isEmpty(countyAndAreas)) {
            mapAreaData = new Gson().fromJson(countyAndAreas, new TypeToken<Map<String, String>>() {
            }.getType());
        }
    }
}
