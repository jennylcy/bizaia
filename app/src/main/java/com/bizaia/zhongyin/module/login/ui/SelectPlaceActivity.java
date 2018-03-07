package com.bizaia.zhongyin.module.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.login.adapter.SelectPlaceAdapter;
import com.bizaia.zhongyin.module.login.data.PlaceBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.addapp.pickers.util.ConvertUtils;

public class SelectPlaceActivity extends BaseActivity {

    @BindView(R.id.back_img)
    ImageView mBackImg;
    @BindView(R.id.search_edit)
    EditText mSearchEdit;
    @BindView(R.id.place_recycler_view)
    RecyclerView mPlaceRecyclerView;
    @BindView(R.id.select_place_no_data_text)
    TextView mNoDataText;

    private List<PlaceBean> mList;
    private SelectPlaceAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_place);
        ButterKnife.bind(this);
        setViewParent(mBackImg);
        init();
    }

    private void init() {
        mList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(ConvertUtils.toString(getAssets().open("area.json")));
            JSONArray jsonArray = jsonObject.getJSONArray("countries");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject placeJSON = jsonArray.getJSONObject(i);
                PlaceBean bean = new PlaceBean(placeJSON.getString("name"), placeJSON.getString("id"));
                mList.add(bean);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        mAdapter = new SelectPlaceAdapter(this, mList);
        mPlaceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPlaceRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SelectPlaceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PlaceBean placeBean) {
                Intent intent = new Intent();
                intent.putExtra(PlaceBean.class.getName(), placeBean);
                setResult(101, intent);
                finish();
            }
        });

        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = mSearchEdit.getText().toString().replace(" ", "");
                if (searchText.equals("")) {
                    mAdapter.setData(mList);
                } else {
                    List<PlaceBean> searchList = new ArrayList<>();
                    char[] chars = searchText.toCharArray();
                    for (char c : chars) {
                        for (int n = 0; n < mList.size(); n++) {
                            if (mList.get(n).getName().contains(String.valueOf(c))) {
                                boolean isHad = false;
                                for (PlaceBean bean : searchList) {
                                    if (bean.getNumber().equals(mList.get(n).getNumber())) {
                                        isHad = true;
                                        break;
                                    }
                                }
                                if (!isHad) {
                                    searchList.add(mList.get(n));
                                }
                            }
                        }
                    }
                    mAdapter.setData(searchList);
                    if (searchList.size() == 0) {
                        mNoDataText.setVisibility(View.VISIBLE);
                    } else {
                        mNoDataText.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.back_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
        }
    }
}
