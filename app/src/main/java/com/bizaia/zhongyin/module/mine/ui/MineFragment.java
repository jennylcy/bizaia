package com.bizaia.zhongyin.module.mine.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.ResumeFragment;
import com.bizaia.zhongyin.module.live.action.LoginSuccessAction;
import com.bizaia.zhongyin.module.login.data.UserBean;
import com.bizaia.zhongyin.module.login.ui.LoginActivity;
import com.bizaia.zhongyin.module.mine.action.PerfectInfoAction;
import com.bizaia.zhongyin.module.mine.action.UserInfoModifyAction;
import com.bizaia.zhongyin.module.mine.api.BillAPI;
import com.bizaia.zhongyin.module.mine.api.UserInfoAPI;
import com.bizaia.zhongyin.module.mine.data.BillBean;
import com.bizaia.zhongyin.module.mine.data.UserInfoBean;
import com.bizaia.zhongyin.module.mine.iml.IBillView;
import com.bizaia.zhongyin.module.mine.iml.IUserInfoView;
import com.bizaia.zhongyin.module.mine.ui.setting.SettingActivity;
import com.bizaia.zhongyin.repository.AddressConfiguration;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.StringUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.bizaia.zhongyin.R.id.perfect_login_layout;

/**
 * Created by zyz on 2017/3/7.
 */

public class MineFragment extends ResumeFragment implements IBillView, IUserInfoView {

    private final String TAG = "MineFragment";

    @BindView(R.id.rivLiver)
    RoundedImageView rivLiver;
    @BindView(R.id.tvBalance)
    TextView tvBalance;
    //    @BindView(R.id.tvLogin)
//    TextView tvLogin;
    @BindView(R.id.perfect_login_layout)
    RelativeLayout mLoginLayout;
    @BindView(R.id.perfect_layout)
    RelativeLayout mPerfectInfoLayout;
    @BindView(R.id.nick_name)
    TextView mNickname;
    @BindView(R.id.info_hint_text)
    TextView mInfoHintText;

    private DisplayImageOptions options;
    private BillAPI billAPI;
    private UserInfoAPI userInfoAPI;
//    private LoadingDialog loadingDialog;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.head) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.head)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.head) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                // .imageScaleType(ImageScaleType.NONE)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                // .decodingOptions(android.graphics.BitmapFactory.Options.decodingOptions)//设置图片的解码配置
                // .delayBeforeLoading(int delayInMillis)//int
                // delayInMillis为你设置的下载前的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                // .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();
//        loadingDialog = new LoadingDialog(getContext());
//        loadingDialog.show();
        userInfoAPI = new UserInfoAPI(this);
        init();
        initRxAction();
        return view;
    }

    private void init() {
        billAPI = new BillAPI(this);
        if (BIZAIAApplication.getInstance().getUserBean() != null && !StringUtils.isEmpty(BIZAIAApplication.getInstance().getUserBean().getAvatarUrl())) {
            String imgPath = BIZAIAApplication.getInstance().getUserBean().getAvatarUrl();
            if (imgPath.startsWith("http")) {
                ImageLoader.getInstance().displayImage(imgPath,
                        rivLiver, options);
            } else {
                ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + imgPath,
                        rivLiver, options);
            }
        } else {
            ImageLoader.getInstance().displayImage("",
                    rivLiver, options);
        }
        if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
            mLoginLayout.setVisibility(View.INVISIBLE);
            mPerfectInfoLayout.setVisibility(View.VISIBLE);
            UserBean.DataEntity userEntity = BIZAIAApplication.getInstance().getUserBean();
            if (userEntity == null) return;
            if (1 == BIZAIAApplication.getInstance().getUserBean().getUserType()) {
                mNickname.setText(userEntity.getEmail() == null ? "" : String.valueOf(userEntity.getEmail() + ""));
            } else {
                mNickname.setText(String.valueOf(userEntity.getNickname()));
            }
        } else {
            mLoginLayout.setVisibility(View.VISIBLE);
            tvBalance.setText(getString(R.string.remaining_pay) + "  0");
            mPerfectInfoLayout.setVisibility(View.INVISIBLE);
        }
        billAPI.getBill(1, 10);
        userInfoAPI.getInfo();
    }

    private void initRxAction() {
        addSubscription(RxBus.getInstance().getEvent(UserInfoModifyAction.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<UserInfoModifyAction>() {
                    @Override
                    public void onNext(UserInfoModifyAction value) {
                        String imgPath = BIZAIAApplication.getInstance().getUserBean().getAvatarUrl();
                        if (!StringUtils.isEmpty(imgPath))
                            if (imgPath.startsWith("http")) {
                                ImageLoader.getInstance().displayImage(imgPath,
                                        rivLiver, options);
                            } else {
                                ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + imgPath,
                                        rivLiver, options);
                            }

                        UserBean.DataEntity userEntity = BIZAIAApplication.getInstance().getUserBean();
                        if (userEntity == null) return;
                        if (1 == BIZAIAApplication.getInstance().getUserBean().getUserType()) {
                            mNickname.setText(userEntity.getEmail() == null ? "" : String.valueOf(userEntity.getEmail() + ""));
                        } else {
                            mNickname.setText(String.valueOf(userEntity.getNickname()));
                        }
                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                }));


        addSubscription(RxBus.getInstance().getEvent(LoginSuccessAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LoginSuccessAction>() {
                    @Override
                    public void onNext(LoginSuccessAction value) {
                        if (value.isLogIn) {
                            init();
                        } else {
                            init();
                        }
                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                }));

        addSubscription(RxBus.getInstance().getEvent(PerfectInfoAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<PerfectInfoAction>() {
                    @Override
                    public void onNext(PerfectInfoAction value) {
                        userInfoAPI.getInfo();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.e(TAG, "setUserVisibleHint: ---->");
        if (isVisibleToUser) {
            billAPI.getBill(1, 10);
            userInfoAPI.getInfo();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    public void requestInfo() {
        if (billAPI != null && !StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
            billAPI.getBill(1, 10);
        }
        if (userInfoAPI != null)
            userInfoAPI.getInfo();
        if (StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken()))
            empty.sendEmptyMessage(0);
    }

    @OnClick({R.id.ivSetting, R.id.relAccount, R.id.relPurchase, R.id.relAttention,
            R.id.relCollection, R.id.relMagazine, R.id.rivLiver, perfect_login_layout, R.id.perfect_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivSetting:
                getActivity().startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.perfect_layout:
            case R.id.rivLiver:
                if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                    getActivity().startActivity(new Intent(getActivity(), UserInfoActivity.class));
                } else
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.relAccount:
                if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken()))
                    getActivity().startActivity(new Intent(getActivity(), AccountActivity.class));
                else
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.relPurchase:
                if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken()))
                    getActivity().startActivity(new Intent(getActivity(), BuyHistoryActivity.class));
                else
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.relAttention:
                if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken()))
                    getActivity().startActivity(new Intent(getActivity(), AttentionActivity.class));
                else
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.relCollection:
                if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken()))
                    getActivity().startActivity(new Intent(getActivity(), CollectionActivity.class));
                else
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.relMagazine:
                if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken()))
                    getActivity().startActivity(new Intent(getActivity(), MonthlyPdfActivity.class));
                else
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case perfect_login_layout:
                if (StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken()))
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            default:
                break;

        }
    }

    @Override
    public void showBill(BillBean data) {
//        if(loadingDialog!=null)
//        loadingDialog.dismiss();
        if (data != null && data.getData() != null)
            tvBalance.setText(getString(R.string.remaining_pay) + data.getData().getBalance());
    }

    @Override
    public void showBillError() {
//        if(loadingDialog!=null)
//            loadingDialog.dismiss();
    }

    @Override
    public void onRelogin() {
//        if(loadingDialog!=null)
//            loadingDialog.dismiss();
        reLogin();
    }

    @Override
    public void showUserInfo(UserInfoBean userInfoBean) {
        Log.e(TAG, "showUserInfo: ------------>save userinfo");
        BIZAIAApplication.getInstance().setUserInfo(userInfoBean.getData());
        if (userInfoBean.getData().isHasDetail()) {
            mInfoHintText.setText(R.string.mine_has_perfect_info);
        } else {
            mInfoHintText.setText(R.string.mine_please_perfect_info);
        }

        if (BIZAIAApplication.getInstance().getUserBean().isThirdparty()) {
            if (userInfoBean.getData() != null) {
                String imgPath = userInfoBean.getData().getAvatarUrl();
                if (imgPath.startsWith("http")) {
                    ImageLoader.getInstance().displayImage(imgPath,
                            rivLiver, options);
                } else {
                    ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + imgPath,
                            rivLiver, options);
                }
            } else {
                ImageLoader.getInstance().displayImage("",
                        rivLiver, options);
            }
        }
    }

    @Override
    public void showIcon(String path) {

    }

    @Override
    public void showUserInfoError() {
        mLoginLayout.setVisibility(View.VISIBLE);
        tvBalance.setText(getString(R.string.remaining_pay) + "  0");
        mPerfectInfoLayout.setVisibility(View.INVISIBLE);
    }

    private Handler empty = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            mLoginLayout.setVisibility(View.VISIBLE);
            tvBalance.setText(getString(R.string.remaining_pay) + "  0");
            mPerfectInfoLayout.setVisibility(View.INVISIBLE);

            super.handleMessage(msg);
        }
    };

}
