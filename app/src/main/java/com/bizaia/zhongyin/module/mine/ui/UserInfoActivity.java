package com.bizaia.zhongyin.module.mine.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.live.action.LoginSuccessAction;
import com.bizaia.zhongyin.module.live.imhelper.IMHelper;
import com.bizaia.zhongyin.module.login.data.UserBean;
import com.bizaia.zhongyin.module.login.ui.LoginActivity;
import com.bizaia.zhongyin.module.mine.action.ForceOffLine;
import com.bizaia.zhongyin.module.mine.action.UserInfoModifyAction;
import com.bizaia.zhongyin.module.mine.api.UserInfoAPI;
import com.bizaia.zhongyin.module.mine.data.UserInfoBean;
import com.bizaia.zhongyin.module.mine.iml.IUserInfoView;
import com.bizaia.zhongyin.repository.AddressConfiguration;
import com.bizaia.zhongyin.util.ImageLoaderUtils;
import com.bizaia.zhongyin.util.OtherUtils;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.SelectPhotoPop;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/3/13.
 */

public class UserInfoActivity extends BaseActivity implements IUserInfoView {

    private final String TAG = "UserInfoActivity";

    @BindView(R.id.back_img)
    ImageView back_img;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivCall)
    ImageView ivCall;
    @BindView(R.id.rivUserIcon)
    RoundedImageView rivUserIcon;
    @BindView(R.id.rlNickName)
    RelativeLayout rlNickName;
    @BindView(R.id.rlPhone)
    RelativeLayout rlPhone;
    @BindView(R.id.rlEmail)
    RelativeLayout rlEmail;
    @BindView(R.id.rlPassword)
    RelativeLayout rlPassword;
    @BindView(R.id.btnLogout)
    Button btnLogout;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.phone_number)
    TextView mPhoneNumber;
    @BindView(R.id.mail_address)
    TextView mMailAddress;
    @BindView(R.id.phone_view)
    View mPhoneView;
    @BindView(R.id.mail_view)
    View mMailView;
    @BindView(R.id.pwd_view)
    View mPwdView;
    @BindView(R.id.info_img)
    ImageView mInfoImg;
    @BindView(R.id.name_img)
    ImageView mNameImg;
    @BindView(R.id.phone_img)
    ImageView mPhoneImg;
    @BindView(R.id.email_img)
    ImageView mEmailImg;
    @BindView(R.id.password_img)
    ImageView mPasswordImg;
    @BindView(R.id.hint_text)
    TextView mHintText;

    private UserInfoBean.DataEntity userInfoBean;
    private UserInfoAPI userInfoAPI;
    private static final int CHANGE_INFO_REQUEST = 10;
    public static final int CHANGE_INFO_RESULT = 11;
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private static final int REQUEST_CHECK_RIGHTS = 1;
    private SelectPhotoPop myPhotoPopuWindow;
    private static final String PHOTO_FILE_NAME = "vip_photo.jpg";
    private File tempFile;
    private Bitmap bitmap;

    private static final int CHANGE_PHONE = 101;
    private static final int CHANGE_EMAIL = 102;
    private static final int CHANGE_PWD = 103;
    private static final int CHANGE_NICKNAME = 104;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        setViewParent(tvTitle);
        tvTitle.setText(R.string.mine_info_change);
        userInfoBean = BIZAIAApplication.getInstance().getUserInfo();
        ivCall.setVisibility(View.GONE);
        showIcon(userInfoBean == null ? null : userInfoBean.getAvatarUrl());
        userInfoAPI = new UserInfoAPI(this);
        onCheckRights();
        if (BIZAIAApplication.getInstance().getUserBean().isThirdparty()) {
            rlPhone.setVisibility(View.GONE);
            rlEmail.setVisibility(View.GONE);
            rlPassword.setVisibility(View.GONE);
            mPhoneView.setVisibility(View.GONE);
            mMailView.setVisibility(View.GONE);
            mPwdView.setVisibility(View.GONE);
            mNameImg.setVisibility(View.GONE);
            mHintText.setVisibility(View.GONE);
        }

        if (BIZAIAApplication.getInstance().getUserBean().getUserType() == 1) {
            mInfoImg.setVisibility(View.GONE);
            mNameImg.setVisibility(View.GONE);
            mPhoneImg.setVisibility(View.GONE);
            mEmailImg.setVisibility(View.GONE);
            mPasswordImg.setVisibility(View.GONE);
            mHintText.setVisibility(View.GONE);
        }

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

    private void onCheckRights() {
        List<String> rights = new ArrayList<>();
        if (PermissionChecker.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)
            rights.add(Manifest.permission.CAMERA);
        if (PermissionChecker.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_DENIED)
            rights.add(Manifest.permission.READ_PHONE_STATE);
        if (PermissionChecker.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED)
            rights.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (PermissionChecker.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)
                == PackageManager.PERMISSION_DENIED)
            rights.add(Manifest.permission.ACCESS_NETWORK_STATE);
        if (PermissionChecker.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE)
                == PackageManager.PERMISSION_DENIED)
            rights.add(Manifest.permission.ACCESS_WIFI_STATE);
        if (PermissionChecker.checkSelfPermission(this, Manifest.permission.WAKE_LOCK)
                == PackageManager.PERMISSION_DENIED)
            rights.add(Manifest.permission.WAKE_LOCK);

        if (!rights.isEmpty()) {
            String[] s = new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.WAKE_LOCK};
            ActivityCompat.requestPermissions(this, rights.toArray(new String[rights.size()]),
                    REQUEST_CHECK_RIGHTS);
        }
    }

    @OnClick({R.id.back_img, R.id.rivUserIcon, R.id.rlInfo, R.id.rlPhone, R.id.rlEmail, R.id.rlPassword, R.id.rlNickName,
            R.id.btnLogout})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.rivUserIcon:
                if (!BIZAIAApplication.getInstance().getUserBean().isThirdparty() && 1 != BIZAIAApplication.getInstance().getUserBean().getUserType())
                    postPhoto();
                break;
            case R.id.rlInfo:
                if (BIZAIAApplication.getInstance().getUserInfo() != null && 1 != BIZAIAApplication.getInstance().getUserBean().getUserType())
                    PerfectInfoActivity.showForEdit(this);
                break;
            case R.id.rlPhone:
                if (1 != BIZAIAApplication.getInstance().getUserBean().getUserType()) {
                    intent = new Intent(UserInfoActivity.this, PhoneChangeActivity.class);
                    intent.putExtra("value", "");
                    startActivityForResult(intent, CHANGE_PHONE);
                }
                break;
            case R.id.rlEmail:
                if (1 != BIZAIAApplication.getInstance().getUserBean().getUserType()) {
                    intent = new Intent(UserInfoActivity.this, EmailChangeActivity.class);
                    intent.putExtra("value", "");
                    startActivityForResult(intent, CHANGE_EMAIL);
                }
                break;
            case R.id.rlPassword:
                if (1 != BIZAIAApplication.getInstance().getUserBean().getUserType()) {
                    intent = new Intent(UserInfoActivity.this, PwdChangeActivity.class);
                    intent.putExtra("value", "");
                    startActivityForResult(intent, CHANGE_PWD);
                }
                break;
            case R.id.rlNickName:
                if (!BIZAIAApplication.getInstance().getUserBean().isThirdparty() && 1 != BIZAIAApplication.getInstance().getUserBean().getUserType()) {
                    intent = new Intent(UserInfoActivity.this, NickNameChangeActivity.class);
                    intent.putExtra("value", "");
                    startActivityForResult(intent, CHANGE_NICKNAME);
                }
                break;
            case R.id.btnLogout:
                userInfoAPI.logOut();
                BIZAIAApplication.getInstance().setToken("");
                BIZAIAApplication.getInstance().setUserBean(null);
                IMHelper.getInstance().imLoginout();
                RxBus.getInstance().post(new LoginSuccessAction(false).setLogIn(false));
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 上传头像
     */
    private void postPhoto() {
        myPhotoPopuWindow = new SelectPhotoPop(this, myPhotoPopuWindowClick);
        myPhotoPopuWindow.showAtLocation(
                findViewById(R.id.rivUserIcon),
                Gravity.BOTTOM | Gravity.CLIP_HORIZONTAL, 0, 0);
    }

    private View.OnClickListener myPhotoPopuWindowClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.select_photo_camera:
                    myPhotoPopuWindow.dismiss();
                    camera();
                    break;
                case R.id.select_photo_album:
                    myPhotoPopuWindow.dismiss();
                    gallery();
                    break;
                default:
                    break;
            }
        }
    };

    /*
  * 从相机获取
  */
    private void camera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (OtherUtils.hasSdcard()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), PHOTO_FILE_NAME)));
        }
        try {

            startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
        } catch (Exception o) {
            ToastUtils.show(this, R.string.notice_camera);
        }
    }

    /*
     * 从相册获取
     */
    private void gallery() {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }


    /**
     * 剪切图片
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                // 得到图片的全路径
                Log.e(TAG, "onActivityResult: --------->PHOTO_REQUEST_GALLERY");
                Uri uri = data.getData();
                crop(uri);
            }
        } else if (requestCode == PHOTO_REQUEST_CAMERA) {
            if (OtherUtils.hasSdcard()) {
                // 头像文件路径
                if (-1 == resultCode) {
                    tempFile = new File(Environment.getExternalStorageDirectory(),
                            PHOTO_FILE_NAME);
                    crop(Uri.fromFile(tempFile));
                }

            } else {
                ToastUtils.show(UserInfoActivity.this, R.string.find_no_storage);
            }
        } else if (requestCode == PHOTO_REQUEST_CUT) {
            try {
                Log.e(TAG, "onActivityResult: --------->PHOTO_REQUEST_CUT");
                bitmap = data.getParcelableExtra("data");
                if (tempFile != null) {
                    tempFile.delete();
                }
                // 保存文件在本地并上传
                userInfoAPI.updateIcon(bitmap);
//                sdvIcon.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == CHANGE_EMAIL) {
            UserBean.DataEntity userEntity = BIZAIAApplication.getInstance().getUserBean();
            mMailAddress.setText(userEntity.getEmail());
        } else if (requestCode == CHANGE_PHONE) {
            UserBean.DataEntity userEntity = BIZAIAApplication.getInstance().getUserBean();
            if (!StringUtils.isEmpty(userEntity.getMobile()))
                mPhoneNumber.setText(userEntity.getMobile());
        } else if (requestCode == CHANGE_NICKNAME) {
            UserBean.DataEntity userEntity = BIZAIAApplication.getInstance().getUserBean();
            userName.setText(userEntity.getNickname());
            RxBus.getInstance().post(new UserInfoModifyAction());
        }
    }


    @Override
    public void showUserInfo(UserInfoBean userInfoBean) {

    }

    @Override
    public void showIcon(String imgPath) {
        UserBean.DataEntity userEntity = BIZAIAApplication.getInstance().getUserBean();
        if (userEntity == null) return;

//        ToastUtils.show(this, R.string.icon_update_success);
        if (1 != BIZAIAApplication.getInstance().getUserBean().getUserType()) {
            userName.setText(String.valueOf(userEntity.getNickname() + ""));
            if (!StringUtils.isEmpty(userEntity.getMobile()))
                mPhoneNumber.setText(String.valueOf(userEntity.getMobile() + ""));
        }
        if (!StringUtils.isEmpty(userEntity.getEmail())) {
            mMailAddress.setText(String.valueOf(userEntity.getEmail() + ""));
        } else {
            mMailAddress.setText("");
        }
        if (!TextUtils.isEmpty(imgPath)) {
            if (imgPath.startsWith("http")) {
                ImageLoader.getInstance().displayImage(imgPath
                        , rivUserIcon, ImageLoaderUtils.getImageOptions(R.drawable.head));
            } else {
                ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + imgPath
                        , rivUserIcon, ImageLoaderUtils.getImageOptions(R.drawable.head));
            }
            userEntity.setAvatarUrl(imgPath);
            BIZAIAApplication.getInstance().setUserBean(userEntity);
            RxBus.getInstance().post(new UserInfoModifyAction());
        } else {
            if (!userEntity.isThirdparty()) {
                if (userEntity.getAvatarUrl().startsWith("http")) {
                    ImageLoader.getInstance().displayImage(userEntity.getAvatarUrl()
                            , rivUserIcon, ImageLoaderUtils.getImageOptions(R.drawable.head));
                } else {
                    ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + userEntity.getAvatarUrl()
                            , rivUserIcon, ImageLoaderUtils.getImageOptions(R.drawable.head));
                }
            } else {
                ImageLoader.getInstance().displayImage(userInfoBean.getAvatarUrl()
                        , rivUserIcon, ImageLoaderUtils.getImageOptions(R.drawable.head));
            }
        }
    }

    @Override
    public void showUserInfoError() {
        ToastUtils.show(this, R.string.icon_update_error);
    }

    @OnClick(R.id.ivCall)
    public void onViewClicked() {
        Intent dialIntent = new Intent(Intent.ACTION_DIAL
                , Uri.parse("tel:" + getResources().getString(R.string.tel)));
        startActivity(dialIntent);
    }
}
