package com.bizaia.zhongyin.module.mine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.mine.action.ForceOffLine;
import com.bizaia.zhongyin.module.mine.api.LectureresAPI;
import com.bizaia.zhongyin.module.mine.data.BuyNewsBean;
import com.bizaia.zhongyin.module.mine.data.LectureresBean;
import com.bizaia.zhongyin.module.mine.data.LectureresDetail;
import com.bizaia.zhongyin.module.mine.iml.ILectureresView;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.TimeUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zyz on 2017/6/2.
 */

public class ChairPayActivity extends BaseActivity implements ILectureresView {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivCall)
    ImageView ivCall;
    @BindView(R.id.relDetail)
    RelativeLayout relDetail;
    @BindView(R.id.tvPayTitle)
    TextView tvPayTitle;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.ivCode)
    ImageView ivCode;
    @BindView(R.id.back_img)
    ImageView back_img;

    private BuyNewsBean.DataEntity.DatasEntity  datasEntity;
    private LectureresAPI lectureresAPI;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chair_pass);
        ButterKnife.bind(this);
        setViewParent(tvTitle);
        datasEntity = (BuyNewsBean.DataEntity.DatasEntity)getIntent().getSerializableExtra("data");
        lectureresAPI = new LectureresAPI(this);
        if(datasEntity!=null) {
            lectureresAPI.getDetail(datasEntity.getId());
            init();
        }
    }

    private void init(){
        tvTitle.setText(R.string.chair_detail_title);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvPayTitle.setText(datasEntity.getTitle());
        tvDate.setText(TimeUtils.timeTransGBToCN(datasEntity.getLectureTime())+ TimeUtils.getWeekDate
                (TimeUtils.timeTranstimestamp(datasEntity.getLectureTime()))+getString(R.string.chair_detail_start));
        ivCode.setImageBitmap(CodeUtils.createImage(datasEntity.getOrderNum(), 400, 400, null));
        relDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChairPayActivity.this,
                        com.bizaia.zhongyin.module.discovery.ui.chair.detail.DetailActivity.class);
                intent.putExtra("url", datasEntity.getH5Url());
                intent.putExtra("title", datasEntity.getTitle());
                intent.putExtra("id", datasEntity.getId());
                int type = datasEntity.getType();
                if (type == 1) {
                    intent.putExtra("type", 5);
                } else if (type == 2) {
                    intent.putExtra("type", 6);
                } else if (type == 3) {
                    intent.putExtra("type", 7);
                } else if (type == 4) {
                    intent.putExtra("type", 1);
                }
                intent.putExtra("orgId", datasEntity.getOrgId());

                intent.putExtra("h5", datasEntity.getH5Url());
                intent.putExtra("title", datasEntity.getTitle());
                intent.putExtra("cover", datasEntity.getCoverUrl());
                intent.putExtra("des", datasEntity.getTitle());
                startActivity(intent);
            }
        });
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

    @Override
    public void showLectureres(LectureresBean data) {

    }

    @Override
    public void showLecturereDetail(LectureresDetail data) {
      tvContent.setText(data.getData().getIntroduction());
    }

    @Override
    public void showLectureresError() {

    }
}
