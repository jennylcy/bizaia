package com.bizaia.zhongyin.module.live.ui;

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
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.BaseActivity;
import com.bizaia.zhongyin.module.common.ui.LoadingDialog;
import com.bizaia.zhongyin.module.live.action.LoadPDFAction;
import com.bizaia.zhongyin.module.live.action.LoadSuccessPDFAction;
import com.bizaia.zhongyin.module.monthly.service.DownLoadService;
import com.bizaia.zhongyin.repository.AddressConfiguration;
import com.bizaia.zhongyin.repository.data.CoursewareBean;
import com.bizaia.zhongyin.repository.data.CoursewareBeanDB;
import com.bizaia.zhongyin.util.AnimationGroupManager;
import com.bizaia.zhongyin.util.AnimationUtils;
import com.bizaia.zhongyin.util.FileUtils;
import com.bizaia.zhongyin.util.NetworkUtils;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.ServiceUtils;
import com.bizaia.zhongyin.util.SizeUtils;
import com.bizaia.zhongyin.util.ToastUtils;
import com.bizaia.zhongyin.widget.ScrollControlViewPager;
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
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.mob.MobSDK.getContext;

/**
 * Created by zyz on 2017/5/8.
 */

public class LiveCoursewareDetail extends BaseActivity{

    private final String TAG="LiveCoursewareDetail.this";
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
    @BindView(R.id.iv_down_load)
    ImageView iv_down_load;
    @BindView(R.id.ivShare)
    ImageView ivShare;
    @BindView(R.id.iv_jianyi)
    ImageView ivPreview;
    @BindView(R.id.iv_back_btn)
    ImageView iv_back_btn;
    private RecyclerView.Adapter browseAdapter;
    private String pdfUrl;
    private String pdfPath;
    private boolean isSaved = false;
    boolean isLoadPDFError = false;
    private CoursewareBeanDB coursewareBeanDB ;
    private long liveId;
    private CompositeDisposable mSubscriptions;
    private CoursewareBean coursewareBean;
    private String title;
    private SparseArray<Bitmap> bitmaps;

    private AnimationGroupManager rvBrowseAnimationManager;
    private AnimationGroupManager llBrowseAnimationManager;
    private Disposable isRunningDisposable;
    private LoadingDialog loadingDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_detail_pdf);
        ButterKnife.bind(this);
        setViewParent(vpPdf);
        iv_down_load.setVisibility(View.GONE);
        ivShare.setVisibility(View.GONE);
        coursewareBeanDB = new CoursewareBeanDB(getContext());
        pdfUrl = AddressConfiguration.MAIN_PATH+getIntent().getStringExtra("pdfUrl");
        liveId = getIntent().getLongExtra("id",0);
        title = getIntent().getStringExtra("title");

        tvTitle.setText(title);
        rxActionInit();
        loadPdf();
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
    }


    private void initBrown(final int nbPages) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) rvBrowse.getLayoutParams();
        layoutParams.width = (int) (SizeUtils.getFullScreenSize(this)[0] * 352 / 750);
        browseAdapter = initBrowseAdapter(getBaseContext(), nbPages);
        rvBrowse.setLayoutParams(layoutParams);
        rvBrowse.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        rvBrowse.setAdapter(browseAdapter);
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
                        vpPdf.setCurrentItem(position);
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

    private void rxActionInit() {
        addSubscription(RxBus.getInstance().getEvent(LoadSuccessPDFAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LoadSuccessPDFAction>() {
                    @Override
                    public void onNext(LoadSuccessPDFAction value) {
                        pdfPath = value.filePath;
                        coursewareBeanDB.update(liveId, BIZAIAApplication.getInstance().getUserBean().getId(),pdfPath);
                        isSaved=true;
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



    private void loadPdf() {

        bitmaps = new SparseArray<>();
        vpPdf.addOnPageChangeListener(pageChangeListener);
        ivPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationInit();
                rvBrowseAnimationManager.show();
                llBrowseAnimationManager.show();
            }
        });
        flBrowseContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationInit();
                rvBrowseAnimationManager.hide();
                llBrowseAnimationManager.hide();
            }
        });
        iv_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(BIZAIAApplication.getInstance().getUserBean()!=null)
            coursewareBean =coursewareBeanDB.queryBean(liveId, BIZAIAApplication.getInstance().getUserBean().getId());

        if(coursewareBean==null)
            isSaved=false;
        else {
            pdfPath = coursewareBean.getPdfUrl();
            isSaved = true;
        }

        if (!TextUtils.isEmpty(pdfPath)&&isSaved) {
            pdfInit();
        }else if(!isSaved){
            if (NetworkUtils.getNetworkType(getBaseContext()) == NetworkUtils.NetworkType.NETWORK_NO) {
                if (!isSaved) {
                    ToastUtils.showInUIThead(getApplicationContext(), getString(R.string.no_net));
                }
            } else if (NetworkUtils.getNetworkType(getBaseContext()) != NetworkUtils.NetworkType.NETWORK_WIFI) {
                ToastUtils.showInUIThead(getApplicationContext(),getString(R.string.current_mobile_net));
            }
            starDownloadService();
        }
    }

    private void pdfInit() {
        ((PDFView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.fragment_monthly_pdf, null))
                .fromFile(new File(pdfPath))
                .onLoad(onLoadCompleteListener)
                .onError(onErrorListener)
                .load();
    }

    private OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
        @Override
        public void loadComplete(int nbPages) {
            initViewPagerPdf(nbPages);
            initBrown(nbPages);
            loadingDialog.dismiss();
        }
    };

    private void initViewPagerPdf(final int nbPages) {
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
                        pdfView.fromFile(new File(pdfPath))
                                .pages(position)
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
    }

    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageChanged(int page, int pageCount) {

        }
    };

    private OnPageScrollListener onPageScrollListener = new OnPageScrollListener() {
        @Override
        public void onPageScrolled(int page, float positionOffset) {
            if (positionOffset >= 1
                    || positionOffset <= 0
                    || Float.isNaN(positionOffset)) {
                vpPdf.setScrollable(true);
            } else {
                vpPdf.setScrollable(false);
            }
        }
    };

    /**
     * start download service
     */
    private void starDownloadService() {
        if (!ServiceUtils.isRunning(getBaseContext(), DownLoadService.class)) {
            Intent intent = new Intent(this, DownLoadService.class);
            startService(intent);
            isRunningDisposable = Observable.interval(200, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.newThread()).subscribe(
                    new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {

                            if (ServiceUtils.isRunning(getBaseContext(), DownLoadService.class)) {
                                RxBus.getInstance().post(new LoadPDFAction(liveId,title,pdfUrl));
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
            RxBus.getInstance().post(new LoadPDFAction(liveId,title,pdfUrl));
        }
    }

    private OnErrorListener onErrorListener = new OnErrorListener() {
        @Override
        public void onError(Throwable t) {
            loadingDialog.dismiss();
            if (!isLoadPDFError) {
                isLoadPDFError = true;
                if(BIZAIAApplication.getInstance().getUserBean()!=null)
                    coursewareBeanDB.delete(liveId, BIZAIAApplication.getInstance().getUserBean().getId());
                FileUtils.deleteFile(pdfPath);
                pdfPath=null;
                loadPdf();
                ToastUtils.showInUIThead(getBaseContext(), "文件载入失败,重新下载中");
            }
        }
    };

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

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
