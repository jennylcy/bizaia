package com.bizaia.zhongyin.module.live.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.ParcelFileDescriptor;
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
import android.widget.ImageView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.live.action.LoadPDFAction;
import com.bizaia.zhongyin.module.live.action.LoadSuccessPDFAction;
import com.bizaia.zhongyin.module.monthly.action.HasDownloadAction;
import com.bizaia.zhongyin.module.monthly.service.DownLoadService;
import com.bizaia.zhongyin.repository.AddressConfiguration;
import com.bizaia.zhongyin.repository.data.CoursewareBean;
import com.bizaia.zhongyin.repository.data.CoursewareBeanDB;
import com.bizaia.zhongyin.util.AnimationGroupManager;
import com.bizaia.zhongyin.util.AnimationUtils;
import com.bizaia.zhongyin.util.FilePathUtils;
import com.bizaia.zhongyin.util.FileUtils;
import com.bizaia.zhongyin.util.NetworkUtils;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.ServiceUtils;
import com.bizaia.zhongyin.util.SizeUtils;
import com.bizaia.zhongyin.widget.ForgetPop;
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
 * Created by zyz on 2017/5/3.
 */

public class LivePDF {
    private final static String TAG="LivePDF";
    private Activity activity;
    private ScrollControlViewPager vpPdf;
    private String pdfUrl;
    private String pdfPath;
    private boolean isSaved = false;
    boolean isLoadPDFError = false;
    private CoursewareBeanDB coursewareBeanDB ;
    private long liveId;
    private CompositeDisposable mSubscriptions;
    private CoursewareBean coursewareBean;
    private String title;
    private boolean isBuy;
    private RecyclerView rvBrowse;
    private FrameLayout flBrowse;
    private ImageView ivPreview;
    private RecyclerView.Adapter browseAdapter;
    private SparseArray<Bitmap> bitmaps;

    private AnimationGroupManager rvBrowseAnimationManager;
    private AnimationGroupManager llBrowseAnimationManager;
    private Disposable isRunningDisposable;
    private int pageCount;
    private ForgetPop payPop;
    private BuyLive buyLive;

    public LivePDF(Activity activity, ScrollControlViewPager vpPdf,
                   String pdfUrl, long liveId, String title,
                   boolean isBuy, RecyclerView rvBrowse,
                   FrameLayout flBrowse, ImageView ivPreview,
                   BuyLive buyLive){
        this.activity =activity;
        this.vpPdf = vpPdf;
        this.pdfUrl = AddressConfiguration.MAIN_PATH+"download/pdf/2/"+liveId+"/"+pdfUrl;
        this.liveId =liveId;
        this.title =title;
        this.isBuy =isBuy;
        this.rvBrowse = rvBrowse;
        this.flBrowse = flBrowse;
        this.ivPreview = ivPreview;
        this.buyLive =buyLive;
        coursewareBeanDB = new CoursewareBeanDB(getContext());
        rxActionInit();
        loadPdf();
    }

    private void initBrown(final int nbPages) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) rvBrowse.getLayoutParams();
        layoutParams.width = (int) (SizeUtils.getFullScreenSize(activity)[0] * 352 / 750);
        browseAdapter = initBrowseAdapter(activity.getBaseContext(), nbPages);
        rvBrowse.setLayoutParams(layoutParams);
        rvBrowse.setLayoutManager(new LinearLayoutManager(activity.getBaseContext()));
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
                        Log.e(TAG, "onClick: " + position);
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
                    , SizeUtils.getFullScreenSize(activity.getBaseContext())[0] * 352 / 750
                    , 0
            );
        if (llBrowseAnimationManager == null)
            llBrowseAnimationManager = new AnimationGroupManager(
                    flBrowse
                    , 200
                    , AnimationUtils.AnimationType.ALPHA,
                    new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            flBrowse.setVisibility(View.GONE);
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
                        if(BIZAIAApplication.getInstance().getUserBean()!=null)
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

        addSubscription(RxBus.getInstance().getEvent(HasDownloadAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<HasDownloadAction>() {
                    @Override
                    public void onNext(HasDownloadAction value) {
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


    public void onDestroy() {
        if (mSubscriptions != null) {
            mSubscriptions.clear();
        }
    }

    public void addSubscription(Disposable subscription) {
        if (subscription == null) return;
        if (mSubscriptions == null) {
            mSubscriptions = new CompositeDisposable();
        }
        mSubscriptions.add(subscription);
    }

    private void loadPdf() {

        bitmaps = new SparseArray<>();
        payPop = getPayPop();
        vpPdf.addOnPageChangeListener(pageChangeListener);
        ivPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationInit();
                rvBrowseAnimationManager.show();
                llBrowseAnimationManager.show();
            }
        });
        flBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationInit();
                rvBrowseAnimationManager.hide();
                llBrowseAnimationManager.hide();
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

        if(!isSaved){
            String path = FilePathUtils.getDirDownloadPdfImgPath(activity);
            File pdfFile = null;
            pdfFile = new File(path + "/" + title + "try" + ".pdf");
            if(pdfFile.exists()){
                pdfPath = pdfFile.getAbsolutePath();
                isSaved = true;
            }
        }

        if (!TextUtils.isEmpty(pdfPath)&&isSaved) {
            Log.e(TAG, "loadPdf: ----------->"+pdfPath);
            pdfInit();
        }else if(!isSaved){
            if (NetworkUtils.getNetworkType(activity.getBaseContext()) == NetworkUtils.NetworkType.NETWORK_NO) {
                if (!isSaved) {
//                    ToastUtils.showInUIThead(activity.getApplicationContext(), "当前没有网络");
                }
            } else if (NetworkUtils.getNetworkType(activity.getBaseContext()) != NetworkUtils.NetworkType.NETWORK_WIFI) {
//                ToastUtils.showInUIThead(activity.getApplicationContext(), "当前为数据流量");
            }
            starDownloadService();
        }
    }

    private void pdfInit() {
        ((PDFView) LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.fragment_monthly_pdf, null))
                .fromFile(new File(pdfPath))
                .onLoad(onLoadCompleteListener)
                .onError(onErrorListener)
                .load();
        vpPdf.setScrollable(true);
    }

    public boolean isSaved() {
        return isSaved;
    }

    private OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
        @Override
        public void loadComplete(int nbPages) {
            initViewPagerPdf(nbPages);
            pageCount = nbPages;
            initBrown(nbPages);
        }
    };

    private void initViewPagerPdf(final int nbPages) {
        Log.e(TAG, "initViewPagerPdf: ------------------>"+nbPages);
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
                        Log.e(TAG, "instantiateItem: ----------------->"+position);
                        PDFView pdfView;
                        if (pdfViewArrayList.size() == 0) {
                            pdfView = (PDFView) LayoutInflater.from(activity.getBaseContext()).inflate(R.layout.fragment_monthly_pdf, null);
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
            if (page >= 1
                    || page <= 0
                    || Float.isNaN(page)) {
                vpPdf.setScrollable(true);
                Log.e(TAG, "onPageScrolled: ----------------->"+positionOffset+"----"+page );
            } else {
                vpPdf.setScrollable(false);
            }
        }
    };

    /**
     * start download service
     */
    private void starDownloadService() {
        if (!ServiceUtils.isRunning(activity.getBaseContext(), DownLoadService.class)) {
            Intent intent = new Intent(activity, DownLoadService.class);
            activity.startService(intent);
            isRunningDisposable = Observable.interval(200, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.newThread()).subscribe(
                    new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {

                            if (ServiceUtils.isRunning(activity.getBaseContext(), DownLoadService.class)) {
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

            if (!isLoadPDFError) {
                isLoadPDFError = true;
                if(BIZAIAApplication.getInstance().getUserBean()!=null)
                coursewareBeanDB.delete(liveId, BIZAIAApplication.getInstance().getUserBean().getId());
                FileUtils.deleteFile(pdfPath);
                pdfPath=null;
                loadPdf();
//                ToastUtils.showInUIThead(activity.getBaseContext(), "文件载入失败,重新下载中");
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
                    PdfiumCore pdfiumCore = new PdfiumCore(activity.getBaseContext());
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
            if (position + 1 == pageCount && !isBuy) {
                payPop.show();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private ForgetPop getPayPop() {
        return new ForgetPop(activity.getBaseContext(), vpPdf, R.layout.pop_pay) {
            @Override
            public void viewInit() {
                getView(R.id.ivClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        payPop.dismiss();
                    }
                });
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
                    payPop.dismiss();
                    buyLive.buy();
                    break;
            }
        }
    };

    public void setBuy(boolean buy) {
        isBuy = buy;
    }

    public void reload(){
        FileUtils.deleteFile(pdfPath);
        RxBus.getInstance().post(new LoadPDFAction(liveId,title,pdfUrl));
    }

    public interface BuyLive{
        void buy();
    }
}
