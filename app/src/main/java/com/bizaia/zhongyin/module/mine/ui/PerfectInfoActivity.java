package com.bizaia.zhongyin.module.mine.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.mine.action.ForceOffLine;
import com.bizaia.zhongyin.module.mine.action.PerfectInfoAction;
import com.bizaia.zhongyin.module.mine.api.PerfectAPI;
import com.bizaia.zhongyin.module.mine.data.InfoBean;
import com.bizaia.zhongyin.module.mine.data.InfoData;
import com.bizaia.zhongyin.module.mine.iml.IPerfectView;
import com.bizaia.zhongyin.util.ErrorUtil;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.addapp.pickers.picker.DatePicker;
import cn.addapp.pickers.picker.DateTimePicker;
import cn.addapp.pickers.util.ConvertUtils;
import cn.qqtheme.framework.picker.OptionPicker;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class PerfectInfoActivity extends BaseActivity implements IPerfectView {

    private static final int TYPE_REGISTER = 0;
    private static final int TYPE_EDIT = 1;

    @BindView(R.id.back_img)
    ImageView mBackImg;
    @BindView(R.id.ivCall)
    ImageView mIvCall;
    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.edit_name)
    EditText mEditName;
    @BindView(R.id.edit_fake_name)
    EditText mEditFakeName;
    @BindView(R.id.edit_mail)
    EditText mEditMail;
    @BindView(R.id.text_birthday)
    TextView mEditBirthday;
    @BindView(R.id.man_check)
    CheckBox mManCheck;
    @BindView(R.id.woman_check)
    CheckBox mWomanCheck;
    @BindView(R.id.professional_select_text)
    TextView mProfessionalSelectText;
    @BindView(R.id.industry_select_text)
    TextView mIndustrySelectText;
    @BindView(R.id.position_select_text)
    TextView mPositionSelectText;
    @BindView(R.id.post_select_text)
    TextView mPostSelectText;
    @BindView(R.id.place_select_text)
    TextView mPlaceSelectText;
    @BindView(R.id.edit_phone)
    EditText mEditPhone;
    @BindView(R.id.edit_company)
    EditText mEditCompany;
    @BindView(R.id.edit_personal_web)
    EditText mEditPersonalWeb;
    @BindView(R.id.edit_company_web)
    EditText mEditCompanyWeb;
    @BindView(R.id.agree_check)
    CheckBox mAgreeCheck;
    @BindView(R.id.agree_layout)
    LinearLayout mAgreeLayout;
    @BindView(R.id.ok_btn)
    Button mOkBtn;
    @BindView(R.id.tv_modify)
    TextView mTvModify;
    @BindView(R.id.layout)
    LinearLayout mLayout;
    @BindView(R.id.industry_layout)
    LinearLayout mIndustryLayout;
    @BindView(R.id.industry_view)
    View mIndustryView;
    @BindView(R.id.position_layout)
    LinearLayout mPositionLayout;
    @BindView(R.id.position_view)
    View mPositionView;
    @BindView(R.id.post_layout)
    LinearLayout mPostLayout;
    @BindView(R.id.post_view)
    View mPostView;

    private int type;

    private String year = "2000";
    private String month = "01";
    private String day = "01";

    private String professional;
    private String industry;
    private String position;
    private String post;
    private String place;

    private PerfectAPI mAPI;

    private InfoBean mInfoBean;

    public static void showForRegister(Context context, String place, String phone) {
        context.startActivity(new Intent(context, PerfectInfoActivity.class)
                .putExtra("type", TYPE_REGISTER)
                .putExtra("place", place)
                .putExtra("phone", phone));
    }

    public static void showForEdit(Context context) {
        context.startActivity(new Intent(context, PerfectInfoActivity.class)
                .putExtra("type", TYPE_EDIT));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_info);
        ButterKnife.bind(this);
        initView();

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

    private void initView() {
        mAPI = new PerfectAPI(this);

        type = getIntent().getIntExtra("type", -1);

        if (type == TYPE_REGISTER) {
            mTvModify.setText(R.string.skip);
            mBackImg.setVisibility(View.GONE);
            mTvModify.setVisibility(View.VISIBLE);
            mAgreeLayout.setVisibility(View.VISIBLE);
        } else {
            mTvModify.setText(R.string.complete);
            mOkBtn.setVisibility(View.GONE);
            mLayout.setVisibility(View.GONE);
            mAPI.getInfo(String.valueOf(BIZAIAApplication.getInstance().getUserInfo().getId()));
        }

        mTvTitle.setText(R.string.perfect_title);
        mIvCall.setVisibility(View.INVISIBLE);

        mManCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) mWomanCheck.setChecked(false);
            }
        });
        mWomanCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) mManCheck.setChecked(false);
            }
        });

        String place = getIntent().getStringExtra("place");
        String phone = getIntent().getStringExtra("phone");
        if (!TextUtils.isEmpty(place)) {
            mPlaceSelectText.setText(place);
        } else if(!StringUtils.isEmpty(BIZAIAApplication.getInstance().getUserBean().getInterAreaCode())){
            String code = BIZAIAApplication.getInstance().getUserBean().getInterAreaCode().replace("+","");
            try {
                JSONObject jsonObject = new JSONObject(ConvertUtils.toString(getAssets().open("area.json")));
                JSONArray jsonArray = jsonObject.getJSONArray("countries");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject placeJSON = jsonArray.getJSONObject(i);
                   if(placeJSON.getString("id").equals(code)){
                       mPlaceSelectText.setText(placeJSON.getString("name"));
                       this.place = placeJSON.getString("name");
                   }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

        }

        if(!StringUtils.isEmpty(BIZAIAApplication.getInstance().getUserBean().getEmail())){
            mEditMail.setText(BIZAIAApplication.getInstance().getUserBean().getEmail());
        }

        if (!TextUtils.isEmpty(phone)) {
            if (StringUtils.isMail(phone)) {
                mEditMail.setText(phone);
            } else {
                mEditPhone.setText(phone);
            }
        }

        if (type == TYPE_EDIT && BIZAIAApplication.getInstance().getUserInfo() != null&&BIZAIAApplication.getInstance().getUserInfo().getMobile()!=null) {
            String text = BIZAIAApplication.getInstance().getUserInfo().getMobile();
            if (StringUtils.isMail(text)) {
                mEditMail.setText(text);
            } else {
                mEditPhone.setText(text);
            }
        }
    }

    @OnClick({R.id.back_img, R.id.text_birthday, R.id.professional_select_text, R.id.professional_select_img
            , R.id.industry_select_text, R.id.industry_select_img, R.id.position_select_text
            , R.id.position_select_img, R.id.post_select_text, R.id.post_select_img, R.id.place_select_text
            , R.id.place_select_img, R.id.ok_btn, R.id.rule_text, R.id.tv_modify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.text_birthday:
                DatePicker datePicker = new DatePicker(this, DateTimePicker.YEAR_MONTH_DAY);
                datePicker.setCancelText("");
                datePicker.setRangeStart(1900, 1, 1);
                String[] times = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).split("-");
                try {
                    datePicker.setRangeEnd(Integer.valueOf(times[0]), Integer.valueOf(times[1]), Integer.valueOf(times[2]));
                } catch (Exception e) {
                    e.printStackTrace();
                    datePicker.setRangeEnd(2099, 12, 31);
                }
                try {
                    datePicker.setSelectedItem(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        mEditBirthday.setText(year + "/" + month + "/" + day);
                        PerfectInfoActivity.this.year = year;
                        PerfectInfoActivity.this.month = month;
                        PerfectInfoActivity.this.day = day;
                    }
                });
                datePicker.show();
                break;
            case R.id.professional_select_text:
            case R.id.professional_select_img:
                try {
                    List<String> list = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(ConvertUtils.toString(getAssets().open("professional.json")));
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        list.add(jsonArray.getString(i));
                    }
                    OptionPicker picker = new OptionPicker(this, list);
                    if (!TextUtils.isEmpty(professional)) picker.setSelectedItem(professional);
                    picker.setCancelText("");
                    picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            if ("無職".equals(item)) {
                                industry = item;
                                mIndustrySelectText.setText(item);
                                position = item;
                                mPositionSelectText.setText(item);
                                post = item;
                                mPostSelectText.setText(item);
                                mIndustryLayout.setVisibility(View.GONE);
                                mIndustryView.setVisibility(View.GONE);
                                mPositionLayout.setVisibility(View.GONE);
                                mPositionView.setVisibility(View.GONE);
                                mPostLayout.setVisibility(View.GONE);
                                mPostView.setVisibility(View.GONE);
                            } else {
                                industry = null;
                                mIndustrySelectText.setText("");
                                position = null;
                                mPositionSelectText.setText("");
                                post = null;
                                mPostSelectText.setText("");
                                mIndustryLayout.setVisibility(View.VISIBLE);
                                mIndustryView.setVisibility(View.VISIBLE);
                                mPositionLayout.setVisibility(View.VISIBLE);
                                mPositionView.setVisibility(View.VISIBLE);
                                mPostLayout.setVisibility(View.VISIBLE);
                                mPostView.setVisibility(View.VISIBLE);
                            }
                            professional = item;
                            mProfessionalSelectText.setText(item);
                        }
                    });
                    picker.show();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.industry_select_text:
            case R.id.industry_select_img:
                try {
                    List<String> list = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(ConvertUtils.toString(getAssets().open("industry.json")));
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        list.add(jsonArray.getString(i));
                    }
                    OptionPicker picker = new OptionPicker(this, list);
                    if (!TextUtils.isEmpty(industry)) picker.setSelectedItem(industry);
                    picker.setCancelText("");
                    picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            industry = item;
                            mIndustrySelectText.setText(item);
                        }
                    });
                    picker.show();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.position_select_text:
            case R.id.position_select_img:
                try {
                    List<String> list = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(ConvertUtils.toString(getAssets().open("position.json")));
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        list.add(jsonArray.getString(i));
                    }
                    OptionPicker picker = new OptionPicker(this, list);
                    if (!TextUtils.isEmpty(position)) picker.setSelectedItem(position);
                    picker.setCancelText("");
                    picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            position = item;
                            mPositionSelectText.setText(item);
                        }
                    });
                    picker.show();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.post_select_text:
            case R.id.post_select_img:
                try {
                    List<String> list = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(ConvertUtils.toString(getAssets().open("post.json")));
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        list.add(jsonArray.getString(i));
                    }
                    OptionPicker picker = new OptionPicker(this, list);
                    if (!TextUtils.isEmpty(post)) picker.setSelectedItem(post);
                    picker.setCancelText("");
                    picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            post = item;
                            mPostSelectText.setText(item);
                        }
                    });
                    picker.show();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.place_select_text:
            case R.id.place_select_img:
                try {
                    List<String> list = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(ConvertUtils.toString(getAssets().open("place.json")));
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        list.add(jsonArray.getString(i));
                    }
                    OptionPicker picker = new OptionPicker(this, list);
                    if (!TextUtils.isEmpty(place)) picker.setSelectedItem(place);
                    picker.setCancelText("");
                    picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            place = item;
                            mPlaceSelectText.setText(item);
                        }
                    });
                    picker.show();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ok_btn:
            case R.id.tv_modify:
                if (type == TYPE_REGISTER) {
                    finish();
                } else {
                    perfectInfo();
                }
                break;
            case R.id.rule_text:
                startActivity(new Intent(this, AgreementActivity.class));
                break;
        }
    }

    private void perfectInfo() {
        String name = mEditName.getText().toString();
        String fakeName = mEditFakeName.getText().toString();
        String mail = mEditMail.getText().toString();
        String birthday = mEditBirthday.getText().toString();
        String phone = mEditPhone.getText().toString();
        String company = mEditCompany.getText().toString();
        String personalWeb = mEditPersonalWeb.getText().toString();
        String companyWeb = mEditCompanyWeb.getText().toString();

        if (TextUtils.isEmpty(name)) {
            ToastUtils.show(this, R.string.perfect_please_input_name);
            return;
        }

        if (TextUtils.isEmpty(fakeName)) {
            ToastUtils.show(this, R.string.perfect_please_input_fake_name);
            return;
        }

        if (!StringUtils.isMail(mail)) {
            ToastUtils.show(this, R.string.perfect_please_input_mail);
            return;
        }

        if (TextUtils.isEmpty(birthday)) {
            ToastUtils.show(this, R.string.perfect_please_input_birthday);
            return;
        }

        if (TextUtils.isEmpty(professional)) {
            ToastUtils.show(this, R.string.perfect_please_select_professional);
            return;
        }

        if (TextUtils.isEmpty(industry)) {
            ToastUtils.show(this, R.string.perfect_please_select_industry);
            return;
        }

        if (TextUtils.isEmpty(position)) {
            ToastUtils.show(this, R.string.perfect_please_select_position);
            return;
        }

        if (TextUtils.isEmpty(post)) {
            ToastUtils.show(this, R.string.perfect_please_select_post);
            return;
        }

        if (TextUtils.isEmpty(place)) {
            ToastUtils.show(this, R.string.perfect_please_select_place);
            return;
        }

        if (!mAgreeCheck.isChecked()) {
            ToastUtils.show(this, R.string.perfect_please_agree);
            return;
        }

        InfoData infoData = new InfoData();
        infoData.setName(name);
        infoData.setFakeName(fakeName);
        infoData.setMail(mail);
        infoData.setBirthday(birthday);
        infoData.setGender(mManCheck.isChecked() ? 1 : 0);
        infoData.setProfessional(professional);
        infoData.setIndustry(industry);
        infoData.setPosition(position);
        infoData.setPost(post);
        infoData.setPlace(place);
        infoData.setCompany(TextUtils.isEmpty(company) ? null : company);
        infoData.setPhone(TextUtils.isEmpty(phone) ? null : phone);
        infoData.setPersonalWeb(TextUtils.isEmpty(personalWeb) ? null : personalWeb);
        infoData.setCompanyWeb(TextUtils.isEmpty(companyWeb) ? null : companyWeb);

        mAPI.changeInfo(String.valueOf(BIZAIAApplication.getInstance().getUserInfo().getId()), infoData);
    }

    @Override
    public void getInfoSuccess(InfoBean bean) {
        mInfoBean = bean;
        mLayout.setVisibility(View.VISIBLE);
        if (type == TYPE_EDIT) {
            mTvModify.setVisibility(View.VISIBLE);
        }

        InfoBean.DataBean dataBean = bean.getData();
        if (dataBean.getName() == null) {
            return;
        }

        mEditName.setText(dataBean.getName());
        mEditFakeName.setText(dataBean.getKana());
        mEditMail.setText(dataBean.getEmail());
        mEditBirthday.setText(dataBean.getBirthday());
        try {
            String[] birth = dataBean.getBirthday().split("/");
            year = birth[0];
            month = birth[1];
            day = birth[2];
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean isMan = dataBean.getGender().equals("1");
        mManCheck.setChecked(isMan);
        mWomanCheck.setChecked(!isMan);

        mProfessionalSelectText.setText(dataBean.getCareer());
        professional = dataBean.getCareer();
        mIndustrySelectText.setText(dataBean.getIndustry());
        industry = dataBean.getIndustry();
        mPositionSelectText.setText(dataBean.getPosition());
        position = dataBean.getPosition();
        mPostSelectText.setText(dataBean.getDuty());
        post = dataBean.getDuty();
        mPlaceSelectText.setText(dataBean.getCountry());
        place = dataBean.getCountry();

        mEditPhone.setText(dataBean.getMobile());
        mEditCompany.setText(dataBean.getCompany());
        mEditPersonalWeb.setText(dataBean.getPersonalUrl());
        mEditCompanyWeb.setText(dataBean.getCompanyUrl());

        if ("無職".equals(professional)) {
            mIndustryLayout.setVisibility(View.GONE);
            mIndustryView.setVisibility(View.GONE);
            mPositionLayout.setVisibility(View.GONE);
            mPositionView.setVisibility(View.GONE);
            mPostLayout.setVisibility(View.GONE);
            mPostView.setVisibility(View.GONE);
        }
    }

    @Override
    public void getInfoError(int errorCode) {
        if (errorCode == 3000) {
            reLogin();
        } else {
            ToastUtils.show(this, ErrorUtil.getMsg(errorCode));
            finish();
        }
    }

    @Override
    public void changeInfoSuccess() {
        RxBus.getInstance().post(new PerfectInfoAction());
        ToastUtils.show(this, R.string.perfect_change_success);
        finish();
    }

    @Override
    public void changeInfoError(int errorCode) {
        if (errorCode == 3000) {
            reLogin();
        } else {
            ToastUtils.show(this, R.string.perfect_change_error);
        }
    }
}
