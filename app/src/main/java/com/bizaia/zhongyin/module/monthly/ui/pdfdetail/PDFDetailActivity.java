package com.bizaia.zhongyin.module.monthly.ui.pdfdetail;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.common.ui.LoadingDialog;
import com.bizaia.zhongyin.module.live.action.LoadPDFAction;
import com.bizaia.zhongyin.module.live.action.LoadSuccessPDFAction;
import com.bizaia.zhongyin.module.login.ui.LoginActivity;
import com.bizaia.zhongyin.module.mine.action.ForceOffLine;
import com.bizaia.zhongyin.module.monthly.action.HasDownloadAction;
import com.bizaia.zhongyin.module.monthly.action.PaySuccessAction;
import com.bizaia.zhongyin.module.monthly.action.ToServiceAction;
import com.bizaia.zhongyin.module.monthly.data.AllMonthlyData;
import com.bizaia.zhongyin.module.monthly.data.IsNeedToBuyData;
import com.bizaia.zhongyin.module.monthly.data.MonthlyDetail;
import com.bizaia.zhongyin.module.monthly.data.MonthlyJSData;
import com.bizaia.zhongyin.module.monthly.service.DownLoadService;
import com.bizaia.zhongyin.module.pay.PayActivity;
import com.bizaia.zhongyin.module.pay.data.OrderData;
import com.bizaia.zhongyin.repository.AddressConfiguration;
import com.bizaia.zhongyin.repository.data.MonthlyNewestBean;
import com.bizaia.zhongyin.repository.data.MonthlyNewestBeanDB;
import com.bizaia.zhongyin.util.ACache;
import com.bizaia.zhongyin.util.AnimationGroupManager;
import com.bizaia.zhongyin.util.AnimationUtils;
import com.bizaia.zhongyin.util.FilePathUtils;
import com.bizaia.zhongyin.util.NetworkUtils;
import com.bizaia.zhongyin.util.NotifyBarUtils;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.ServiceUtils;
import com.bizaia.zhongyin.util.SizeUtils;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.ForgetPop;
import com.bizaia.zhongyin.widget.ScrollControlViewPager;
import com.bizaia.zhongyin.widget.ShareDialog;
import com.bizaia.zhongyin.widget.adapter.ViewHolder;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yan on 2017/3/9.
 */

public class PDFDetailActivity extends BaseActivity implements PDFContract.View {
    private static final String TAG = "PDFDetailActivity";
    @BindView(R.id.rv_browse)
    RecyclerView rvBrowse;
    @BindView(R.id.vp_pdf)
    ScrollControlViewPager vpPdf;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.fl_browse_container)
    FrameLayout flBrowseContainer;
    @BindView(R.id.content_view)
    CoordinatorLayout contentView;
    private RecyclerView.Adapter browseAdapter;

    private AnimationGroupManager rvBrowseAnimationManager;
    private AnimationGroupManager llBrowseAnimationManager;
    private Disposable isRunningDisposable;

    private ForgetPop payPop;

    private MonthlyJSData.DataBean.MonthlyNewestBean monthlyNewestBean;
    private MonthlyNewestBeanDB newestBeanDB;

    private String pdfPath;
    private PDFContract.Presenter presenter;

    private boolean isBuy;
    private boolean shouldDown;

    /**
     * pdf page count
     */
    private int pageCount;

    /**
     * pdf is saved
     */
    private boolean isSaved = false;

    /**
     * pdf load error
     */
    boolean isLoadPDFError = false;

    /**
     * brown recycler view data
     */
    private SparseArray<Bitmap> bitmaps;

    protected LoadingDialog loadingDialog;

    private boolean hasSave = false;

    private MonthlyDetail monthlyDetail;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_detail_pdf);
        NotifyBarUtils.StatusBarDarkMode(this);
        ButterKnife.bind(this);
        setViewParent(rvBrowse);
        init(savedInstanceState);
        rxActionInit();
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();

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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        bitmaps = new SparseArray<>();
        payPop = getPayPop(getString(R.string.monthly_see_tips));
        vpPdf.addOnPageChangeListener(pageChangeListener);
        newestBeanDB = new MonthlyNewestBeanDB(getBaseContext());

        if (savedInstanceState != null) {
            monthlyNewestBean = (MonthlyJSData.DataBean.MonthlyNewestBean) ACache.get(getApplicationContext()).getAsObject("MonthlyJSData");
        } else {
            setMonthlyNewestBean();
        }
        Log.e(TAG, "init: ----------------------->" + monthlyNewestBean.getShareUrl());
        tvTitle.setText(monthlyNewestBean.getTitle());
        isBuy = getIntent().getBooleanExtra("isBuy", false);
        isSaved = getIntent().getBooleanExtra("isSaved", false);
        new PDFPresenter(this);
        presenter.isNeedToBuy(monthlyNewestBean.getId());

        animationInit();
        rvBrowseAnimationManager.hide();
        llBrowseAnimationManager.hide();
        presenter.getMonthlyDetail(monthlyNewestBean.getId());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putSerializable("MonthlyJSData", monthlyNewestBean);
    }

    private void rxActionInit() {
        addSubscription(RxBus.getInstance().getEvent(PaySuccessAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<PaySuccessAction>() {
                    @Override
                    public void onNext(PaySuccessAction value) {

                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                }));

        addSubscription(RxBus.getInstance().getEvent(HasDownloadAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<HasDownloadAction>() {
                    @Override
                    public void onNext(HasDownloadAction value) {
                        String path = FilePathUtils.getDirDownloadPdfImgPath(getApplicationContext());
                        File pdfFile = new File(path + "/" + monthlyNewestBean.getTitle() + ".pdf");
                        if (isBuy && pdfFile.exists()) {
                            hasSave = true;
                        }
                        pdfPath = value.filePath;
                        pdfInit();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));

        addSubscription(RxBus.getInstance().getEvent(LoadSuccessPDFAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LoadSuccessPDFAction>() {
                    @Override
                    public void onNext(LoadSuccessPDFAction value) {
                        Log.e(TAG, "LoadSuccessPDFAction: _________________>" + value.filePath);
                        pdfPath = value.filePath;
                        pdfInit();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }


    /**
     * load pdf
     */
    private void loadPdf() {
        if (!TextUtils.isEmpty(pdfPath)) {
            ToastUtils.showInUIThead(getApplicationContext(), getString(R.string.has_down_load) + pdfPath);
        }

        onDownClick();
    }

    /**
     * set monthly data the data may from all monthly data
     */
    private void setMonthlyNewestBean() {
        if (getIntent().getSerializableExtra("MonthlyJSData") instanceof AllMonthlyData.DataBean.DatasBean) {
            AllMonthlyData.DataBean.DatasBean datasBean = (AllMonthlyData.DataBean.DatasBean) getIntent()
                    .getSerializableExtra("MonthlyJSData");
            monthlyNewestBean = new MonthlyJSData.DataBean.MonthlyNewestBean();
            monthlyNewestBean.setPrice(datasBean.getPrice());
            monthlyNewestBean.setDescription(datasBean.getDescription());
            monthlyNewestBean.setCoverUrl(datasBean.getCoverUrl());
            monthlyNewestBean.setChapterNum(datasBean.getChapterNum());
            monthlyNewestBean.setCreateTime(datasBean.getCreateTime());

            List<MonthlyJSData.DataBean.MonthlyNewestBean.ChapterTitlesBean> chapterTitlesBeanList =
                    new ArrayList<>();

            if (datasBean.getChapterTitles() != null) {
                for (AllMonthlyData.DataBean.DatasBean.ChapterTitlesBean bean :
                        datasBean.getChapterTitles()) {
                    MonthlyJSData.DataBean.MonthlyNewestBean.ChapterTitlesBean chapterTitlesBean =
                            new MonthlyJSData.DataBean.MonthlyNewestBean.ChapterTitlesBean();
                    chapterTitlesBean.setTitle(bean.getTitle());
                    chapterTitlesBean.setId(bean.getId());
                    chapterTitlesBean.setAdvertiseUrl(bean.getAdvertiseUrl());
                    chapterTitlesBean.setArea(bean.getArea());
                    chapterTitlesBean.setAuthor(bean.getAuthor());
                    chapterTitlesBean.setBrowseNum(bean.getBrowseNum());
                    chapterTitlesBean.setContent(bean.getContent());
                    chapterTitlesBean.setCoverUrl(bean.getCoverUrl());
                    chapterTitlesBean.setH5Url(bean.getH5Url());
                    chapterTitlesBean.setHrefUrl(bean.getHrefUrl());
                    chapterTitlesBeanList.add(chapterTitlesBean);
                }
                monthlyNewestBean.setChapterTitles(chapterTitlesBeanList);
            }
            monthlyNewestBean.setFileUrl(datasBean.getFileUrl());
            monthlyNewestBean.setId(datasBean.getId());
            monthlyNewestBean.setTitle(datasBean.getTitle());
            return;
        }
//        monthlyNewestBean = (MonthlyJSData.DataBean.MonthlyNewestBean) getIntent().getSerializableExtra("MonthlyJSData");
        monthlyNewestBean = (MonthlyJSData.DataBean.MonthlyNewestBean) ACache.get(getApplicationContext()).getAsObject("MonthlyJSData");
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (position + 1 == pageCount && !isBuy) {
                payPop = getPayPop(getString(R.string.monthly_see_tips));
                payPop.show();
            }
        }

        @Override
        public void onPageSelected(int position) {
            Log.e(TAG, "onPageSelected: " + position + "    " + pageCount);
            if (position + 1 == pageCount && !isBuy) {
                payPop = getPayPop(getString(R.string.monthly_see_tips));
                payPop.show();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected boolean isNotifyBarLight() {
        return false;
    }

    @OnClick({R.id.iv_jianyi, R.id.fl_browse_container, R.id.iv_back_btn, R.id.iv_down_load})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_jianyi:
                animationInit();
                rvBrowseAnimationManager.show();
                llBrowseAnimationManager.show();
                break;
            case R.id.fl_browse_container:
                animationInit();
                rvBrowseAnimationManager.hide();
                llBrowseAnimationManager.hide();
                break;
            case R.id.iv_back_btn:
                finish();
                break;
            case R.id.iv_down_load:
                if (StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    if (hasSave) {
                        ToastUtils.show(PDFDetailActivity.this, R.string.month_has_down);
                        return;
                    }

                    if (isBuy) {
                        shouldDown = true;
                        ToastUtils.show(PDFDetailActivity.this, R.string.month_create);
                        loadingDialog.show();
                        onDownClick();
                    } else {
                        payPop = getPayPop(getString(R.string.monthly_download_buy));
                        payPop.show();
                    }

                }
                break;
        }
    }

    private void onDownClick() {
        Log.e(TAG, "onDownClick: _________________>" + isSaved);
        if (!isSaved) {
            if (NetworkUtils.getNetworkType(getBaseContext()) == NetworkUtils.NetworkType.NETWORK_NO) {
                if (!isSaved) {
                    ToastUtils.showInUIThead(getApplicationContext(), getString(R.string.no_net));
                }
                isSaved = true;
            } else if (NetworkUtils.getNetworkType(getBaseContext()) != NetworkUtils.NetworkType.NETWORK_WIFI) {
                ToastUtils.showInUIThead(getApplicationContext(), getString(R.string.current_mobile_net));
//                presenter.isNeedToBuy(monthlyNewestBean.getId());
                isSaved = true;
            } else {
                isSaved = true;
//                presenter.isNeedToBuy(monthlyNewestBean.getId());
            }
//            ToastUtils.show(PDFDetailActivity.this, R.string.month_create);
        }
        if (isSaved) {
            pdfPath = download();
            if (!TextUtils.isEmpty(pdfPath)) {
                pdfInit();
            } else {
                if (shouldDown) {
//                    ToastUtils.show(PDFDetailActivity.this, R.string.month_has_down);
                    String path = FilePathUtils.getDirDownloadPdfImgPath(this);
                    File pdfFile = null;
                    pdfFile = new File(path + "/" + monthlyNewestBean.getTitle() + "try" + ".pdf");
                    if (pdfFile.exists()) {
                        pdfPath = pdfFile.getAbsolutePath();
                    }
                }
            }
        }
    }

    private void animationInit() {
        if (rvBrowseAnimationManager == null)
            rvBrowseAnimationManager = new AnimationGroupManager(
                    rvBrowse
                    , 200
                    , AnimationUtils.AnimationType.TRANSLATEX
                    , null
                    , new AccelerateInterpolator()
                    , SizeUtils.getFullScreenSize(getBaseContext())[0] * 352 / 750
                    , 0
            );
        if (llBrowseAnimationManager == null)
            llBrowseAnimationManager = new AnimationGroupManager(
                    flBrowseContainer
                    , 200
                    , AnimationUtils.AnimationType.ALPHA,
                    new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            flBrowseContainer.setVisibility(View.GONE);
                        }
                    }
                    , new AccelerateInterpolator()
                    , 0
                    , 1
            );
    }

    /**
     * download pdf
     *
     * @return
     */
    private String download() {
        Log.e(TAG, "download: _________________>");
        if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
            List<MonthlyNewestBean> monthlyNewestBeens = newestBeanDB.queryList();
            for (MonthlyNewestBean newestBean : monthlyNewestBeens) {
                if (newestBean.getId() == monthlyNewestBean.getId()) {
                    if (!TextUtils.isEmpty(newestBean.getDownloadPath())) {
                        return newestBean.getDownloadPath();
                    }
                    newestBeanDB.delete(newestBean.getId());
                    break;
                }
            }
        }
        // add data to database
        if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken()) && shouldDown) {
            String title1 = (monthlyNewestBean.getChapterTitles() != null
                    && monthlyNewestBean.getChapterTitles().size() >= 1)
                    ? monthlyNewestBean.getChapterTitles().get(0).getTitle()
                    : null;
            if (title1 == null && monthlyDetail != null
                    && monthlyDetail.getData() != null
                    && monthlyDetail.getData().getChapterList() != null
                    && !monthlyDetail.getData().getChapterList().isEmpty())
                title1 = monthlyDetail.getData().getChapterList().get(0).getTitle();
            String title2 = (monthlyNewestBean.getChapterTitles() != null
                    && monthlyNewestBean.getChapterTitles().size() >= 2)
                    ? monthlyNewestBean.getChapterTitles().get(1).getTitle()
                    : null;
            if (title2 == null && monthlyDetail != null
                    && monthlyDetail.getData() != null
                    && monthlyDetail.getData().getChapterList() != null
                    && !monthlyDetail.getData().getChapterList().isEmpty()
                    && monthlyDetail.getData().getChapterList().size() > 1)
                title2 = monthlyDetail.getData().getChapterList().get(1).getTitle();

            newestBeanDB.insert(new MonthlyNewestBean(
                    monthlyNewestBean.getId()
                    , monthlyNewestBean.getTitle()
                    , monthlyNewestBean.getCoverUrl()
                    , monthlyNewestBean.getFileUrl()
                    , monthlyNewestBean.getPrice()
                    , isBuy
                    , monthlyNewestBean.getDescription()
                    , null
                    , title1
                    , title2,
                    BIZAIAApplication.getInstance().getUserBean().getId() + "",
                    monthlyNewestBean.getH5Url()
            ));
        }

        starDownloadService();
        return null;
    }

    /**
     * start download service
     */
    private void starDownloadService() {
        Log.e(TAG, "canService: " + "download");
        if (!ServiceUtils.isRunning(getBaseContext(), DownLoadService.class)) {
            Intent intent = new Intent(this, DownLoadService.class);
            intent.putExtra("isBuy", isBuy);
            startService(intent);
            isRunningDisposable = Observable.interval(200, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.newThread()).subscribe(
                    new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            Log.e(TAG, "canService: " + "post");
                            if (ServiceUtils.isRunning(getBaseContext(), DownLoadService.class)) {
                                if (shouldDown) {
                                    Log.e(TAG, "starDownloadService: " + "111111111111111111111111111");
                                    RxBus.getInstance().post(new ToServiceAction(monthlyNewestBean.getId(), shouldDown));
                                } else {
                                    Log.e(TAG, "starDownloadService: " + "2222222222222222222222222");
                                    RxBus.getInstance().post(new LoadPDFAction(monthlyNewestBean.getId(), monthlyNewestBean.getTitle(),
                                            monthlyNewestBean.getFileUrl()));
                                }
                                isRunningDisposable.dispose();
                            }
                        }
                    },
                    new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            throwable.printStackTrace();
                        }
                    });
            addSubscription(isRunningDisposable);
        } else {
            if (shouldDown) {
                Log.e(TAG, "starDownloadService: " + "3333333333333333333333333333333");
                RxBus.getInstance().post(new ToServiceAction(monthlyNewestBean.getId(), shouldDown));
            } else {
                Log.e(TAG, "starDownloadService: " + "444444444444444444444444444444444");

                RxBus.getInstance().post(new LoadPDFAction(monthlyNewestBean.getId(), monthlyNewestBean.getTitle(),
                        monthlyNewestBean.getFileUrl()));
            }

//            RxBus.getInstance().post(new ToServiceAction(monthlyNewestBean.getId(),shouldDown));
        }
    }

    /**
     * pop need to buy
     *
     * @return
     */
    private ForgetPop getPayPop(String content) {
        return new ForgetPop(getBaseContext(), contentView, R.layout.pop_pay) {
            @Override
            public void viewInit() {
                getView(R.id.ivClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        payPop.dismiss();
                    }
                });
                TextView tvContent = (TextView) findViewById(R.id.content);
                getView(R.id.dialog_pay_continue).setOnClickListener(popOnClickListener);
                getView(R.id.dialog_pay).setOnClickListener(popOnClickListener);
            }
        };
    }

    private View.OnClickListener popOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dialog_pay_continue:
                    payPop.dismiss();
                    break;
                case R.id.dialog_pay:
                    Log.e(TAG, "onClick: " + monthlyNewestBean.getId());
                    payPop.dismiss();
                    if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                        startActivity(new Intent(getBaseContext(), PayActivity.class)
                                .putExtra("price", monthlyNewestBean.getPrice())
                                .putExtra("id", monthlyNewestBean.getId())
                                .putExtra("title", getString(R.string.monthly_pdf_buy))
                                .putExtra("type", 4)
                        );
                    } else {
                        startActivity(new Intent(getBaseContext(), LoginActivity.class));
                    }
                    break;
            }
        }
    };

    //------------------------------------------- pdf --------------------------------------------------
    private OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
        @Override
        public void loadComplete(int nbPages) {
            Log.e(TAG, "loadComplete: " + nbPages);
            initViewPagerPdf(nbPages);
            pageCount = nbPages;
            initBrown(nbPages);
            loadingDialog.dismiss();
        }
    };


    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageChanged(int page, int pageCount) {
            Log.e(TAG, "onPageScrolled: " + page + "    " + pageCount);

        }
    };

    private OnPageScrollListener onPageScrollListener = new OnPageScrollListener() {
        @Override
        public void onPageScrolled(int page, float positionOffset) {
            Log.e(TAG, "onPageScrolled: " + page + "    " + positionOffset);
            if (positionOffset >= 1
                    || positionOffset <= 0
                    || Float.isNaN(positionOffset)) {
                vpPdf.setScrollable(true);
            } else {
                vpPdf.setScrollable(false);
            }
        }
    };

    private OnErrorListener onErrorListener = new OnErrorListener() {
        @Override
        public void onError(Throwable t) {

            if (!isLoadPDFError) {
                isLoadPDFError = true;
                ToastUtils.showInUIThead(getBaseContext(), getString(R.string.file_load_error_down_load_again));
                new MonthlyNewestBeanDB(getBaseContext()).update(monthlyNewestBean.getId(), "");
                loadingDialog.dismiss();
                Log.e(TAG, "onError: pdf");
                presenter.isNeedToBuy(monthlyNewestBean.getId());
            }
        }
    };

    /**
     * init brown recycler view
     *
     * @param nbPages pdf page count
     */
    private void initBrown(final int nbPages) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) rvBrowse.getLayoutParams();
        layoutParams.width = (int) (SizeUtils.getFullScreenSize(getBaseContext())[0] * 352 / 750);
        browseAdapter = initBrowseAdapter(getBaseContext(), nbPages);
        rvBrowse.setLayoutParams(layoutParams);
        rvBrowse.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        rvBrowse.setAdapter(browseAdapter);
    }

    /**
     * get bitmap from pdf file
     *
     * @param position   page num
     * @param disposable disposable
     */
    private void getBitmap(final int position, DisposableObserver<Bitmap> disposable) {
        addSubscription(Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> e) throws Exception {
                if (bitmaps.get(position) != null) {
                    e.onNext(bitmaps.get(position));
                } else {
                    ParcelFileDescriptor fd = ParcelFileDescriptor.open(new File(pdfPath), ParcelFileDescriptor.MODE_READ_ONLY);
                    PdfiumCore pdfiumCore = new PdfiumCore(getBaseContext());
                    PdfDocument pdfDocument = pdfiumCore.newDocument(fd);
                    pdfiumCore.openPage(pdfDocument, position);
                    int width = pdfiumCore.getPageWidthPoint(pdfDocument, position);
                    int height = pdfiumCore.getPageHeightPoint(pdfDocument, position);
                    Bitmap bitmap = Bitmap.createBitmap(width, height,
                            Bitmap.Config.ARGB_4444);
                    pdfiumCore.renderPageBitmap(pdfDocument, bitmap, position, 0, 0,
                            width, height);
                    pdfiumCore.closeDocument(pdfDocument);
                    bitmaps.put(position, getSmallBitmap(bitmap, 20));
                    e.onNext(bitmap);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(disposable));
    }

    /**
     * compress bitmap
     *
     * @param bitmap bitmap
     * @param size   compress final size
     * @return final bitmap
     */
    private Bitmap getSmallBitmap(Bitmap bitmap, int size) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, out);
        float zoom = (float) Math.sqrt(size * 1024 / (float) out.toByteArray().length);

        Matrix matrix = new Matrix();
        matrix.setScale(zoom, zoom);

        Bitmap result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        out.reset();
        result.compress(Bitmap.CompressFormat.JPEG, 85, out);
        while (out.toByteArray().length > size * 1024) {
            System.out.println(out.toByteArray().length);
            matrix.setScale(0.9f, 0.9f);
            result = Bitmap.createBitmap(result, 0, 0, result.getWidth(), result.getHeight(), matrix, true);
            out.reset();
            result.compress(Bitmap.CompressFormat.JPEG, 85, out);
        }
        return result;
    }

    private void pdfInit() {
        ((PDFView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.fragment_monthly_pdf, null))
                .fromFile(new File(pdfPath))
                .onLoad(onLoadCompleteListener)
                .onError(onErrorListener)
                .load();
    }

    private int size;

    private void initViewPagerPdf(final int nbPages) {
        size = nbPages;
        vpPdf.setAdapter(
                new PagerAdapter() {
                    private ArrayList<PDFView> pdfViewArrayList = new ArrayList<>();


                    @Override
                    public int getCount() {
                        return nbPages;
                    }

                    @Override
                    public boolean isViewFromObject(View view, Object object) {
                        return view == object;
                    }

                    @Override
                    public Object instantiateItem(ViewGroup container, int position) {
                        PDFView pdfView;
                        if (pdfViewArrayList.size() == 0) {
                            pdfView = (PDFView) LayoutInflater.from(getBaseContext()).inflate(R.layout.fragment_monthly_pdf, null);
                        } else {
                            pdfView = pdfViewArrayList.remove(pdfViewArrayList.size() - 1);
                        }
                        Log.e(TAG, "instantiateItem: -------------->" + pdfPath);
                        pdfView.fromFile(new File(pdfPath))
                                .pages(nbPages - position - 1)
                                .enableSwipe(true)
                                .swipeHorizontal(true)
                                .enableDoubletap(true)
                                .onPageChange(onPageChangeListener)
                                .onPageScroll(onPageScrollListener)
                                .enableAnnotationRendering(true)
                                .password(null)
                                .scrollHandle(null)
                                .load();

                        container.addView(pdfView);
                        return pdfView;
                    }

                    @Override
                    public void destroyItem(ViewGroup container, int position, Object object) {
                        container.removeView((View) object);
                        pdfViewArrayList.add((PDFView) object);
                    }
                });
        vpPdf.setCurrentItem(nbPages - 1);
    }


    private RecyclerView.Adapter<ViewHolder> initBrowseAdapter(final Context baseContext, final int size) {
        return new RecyclerView.Adapter<ViewHolder>() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(baseContext).inflate(R.layout.activity_monthly_detail_item_brower, parent, false);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                layoutParams.height = (int) ((SizeUtils.getFullScreenSize(baseContext)[1] - SizeUtils.getNotifyBarHeight(baseContext)) / 3);
                view.setLayoutParams(layoutParams);
                return ViewHolder.createViewHolder(baseContext, view);
            }

            @Override
            public void onBindViewHolder(final ViewHolder holder, final int position) {
                holder.setTag(R.id.pdf, position);
                holder.setOnClickListener(R.id.click_view, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = size-1-position;
                        Log.e(TAG, "onClick: ---------------------->"+pos);
                        vpPdf.setCurrentItem(pos);
                        animationInit();
                        rvBrowseAnimationManager.hide();
                        llBrowseAnimationManager.hide();
                    }
                });
                getBitmap(position, new DisposableObserver<Bitmap>() {
                    public void onNext(Bitmap value) {
                        holder.setImageBitmap(R.id.pdf, value);
                    }

                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    public void onComplete() {

                    }
                });
            }

            @Override
            public int getItemCount() {
                return size;
            }

        };
    }


    @Override
    public void setPresenter(PDFContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setIsNeedToBuy(IsNeedToBuyData isNeedToBuy) {
        if (isNeedToBuy.getData() != null) {
            isBuy = isNeedToBuy.getData().isIsBuy();
//            if (isBuy && BIZAIAApplication.getInstance().getUserBean() != null) {
//                if (!newestBeanDB.isStateBuy(monthlyNewestBean.getId(), String.valueOf(BIZAIAApplication.getInstance().getUserBean().getId()))) {
//                    newestBeanDB.updateBuyState(monthlyNewestBean.getId(), true, String.valueOf(BIZAIAApplication.getInstance().getUserBean().getId()));
//                    newestBeanDB.emptyDownloadPath(monthlyNewestBean.getId());
//                }
//            } else if (BIZAIAApplication.getInstance().getUserBean() != null) {
//                newestBeanDB.updateBuyState(monthlyNewestBean.getId(), false, String.valueOf(BIZAIAApplication.getInstance().getUserBean().getId()));
//            }
            // /download/pdf/1/3389/2016.pdf
            // /pdf/1/9527/2017.pdf   月刊下载地址 1为固定值 9527表示当前产品的ID，比如月刊的ID，2017.pdf为具体文件
            // /pdf/2/3389/2016.pdf   课件下载地址 2位固定值 3389表示当前课件的ID，比如课程的ID，2016.pdf为具体文件

            if (TextUtils.isEmpty(monthlyNewestBean.getFileUrl())) {
                return;
            }
            String path = FilePathUtils.getDirDownloadPdfImgPath(this);
            File pdfFile = new File(path + "/" + monthlyNewestBean.getTitle() + ".pdf");
            if (isBuy && pdfFile.exists()) {
                pdfPath = pdfFile.getAbsolutePath();
                pdfInit();
                hasSave = true;
                Log.e(TAG, "setIsNeedToBuy: _________has  save________>" + hasSave);
            } else {
                if (!monthlyNewestBean.getFileUrl().contains(AddressConfiguration.MAIN_PATH)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(AddressConfiguration.MAIN_PATH)
                            .append("download/pdf/1/")
                            .append(monthlyNewestBean.getId())
                            .append("/")
                            .append(monthlyNewestBean.getFileUrl());
                    monthlyNewestBean.setFileUrl(stringBuilder.toString());
                }
                localResource();
            }
        }
    }

    private void localResource() {
        String path = FilePathUtils.getDirDownloadPdfImgPath(this);
        File pdfFile = null;
        Log.e(TAG, "localResource: _________________>" + isBuy);
        if (!isBuy) {
            pdfFile = new File(path + "/" + monthlyNewestBean.getTitle() + "try" + ".pdf");
            if (pdfFile.exists()) {
                pdfPath = pdfFile.getAbsolutePath();
                pdfInit();
                loadingDialog.dismiss();
            } else {
                loadPdf();
            }
        } else {
            pdfFile = new File(path + "/" + monthlyNewestBean.getTitle() + "try" + ".pdf");
            if (pdfFile.exists()) {
                pdfPath = pdfFile.getAbsolutePath();
                pdfInit();
                loadingDialog.dismiss();
                return;
            }

            if (!StringUtils.isEmpty(BIZAIAApplication.getInstance().getToken())) {
                List<MonthlyNewestBean> monthlyNewestBeens = newestBeanDB.queryList();
                if (monthlyNewestBeens != null) {
                    for (MonthlyNewestBean newestBean : monthlyNewestBeens) {
                        if (newestBean.getId() == monthlyNewestBean.getId()) {
                            if (!TextUtils.isEmpty(newestBean.getDownloadPath())) {
                                pdfPath = newestBean.getDownloadPath();
                                pdfFile = new File(pdfPath);
                                if (pdfFile.exists()) {
                                    pdfInit();
                                    loadingDialog.dismiss();
                                    return;
                                } else {
                                    loadPdf();
                                    return;
                                }
                            } else {
                                loadPdf();
                                return;
                            }
                        }
                    }
                    loadPdf();
                } else {
                    loadPdf();
                }
            } else {
                loadPdf();
            }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
//            if (data.getBooleanExtra("isPaySuccess", false)) {
//                ToastUtils.showInUIThead(getBaseContext(), getString(R.string.downloading_whole_pdf));
//                if (payPop != null) {
//                    payPop.dismiss();
//                }
//                newestBeanDB.updateBuyState(monthlyNewestBean.getId(), true);
//                newestBeanDB.emptyDownloadPath(monthlyNewestBean.getId());
//                animationInit();
//                rvBrowseAnimationManager.show();
//                llBrowseAnimationManager.show();
//                onDownClick();
//            }
        }
    }

    @Override
    public void setOrderData(OrderData orderData) {
        startActivityForResult(new Intent(this, PayActivity.class)
                .putExtra("price", monthlyNewestBean.getPrice())
                .putExtra("order", orderData), 99);
    }

    @Override
    public void error(String msg) {
        ToastUtils.showInUIThead(getBaseContext(), msg);
    }

    @Override
    public void setJSDetail() {

    }

    @Override
    public void setMonthlyDetail(MonthlyDetail detail) {
        monthlyDetail = detail;
    }

    @Override
    public void reLogin() {
        super.reLogin();
        this.finish();
    }

    @Override
    public void netError() {
        ToastUtils.showInUIThead(getBaseContext(), getString(R.string.net_error));
    }

    @OnClick(R.id.ivShare)
    public void onViewClicked() {
        if (monthlyNewestBean != null) {
            ShareDialog.share(this, monthlyNewestBean.getTitle(), monthlyNewestBean.getDescription(),
                    AddressConfiguration.MAIN_PATH + monthlyNewestBean.getCoverUrl(),
                    monthlyNewestBean.getShareUrl());
        }
    }
}


