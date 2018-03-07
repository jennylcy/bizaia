package com.bizaia.zhongyin.module.mine.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bizaia.zhongyin.BIZAIAApplication;
import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.base.ResumeFragment;
import com.bizaia.zhongyin.module.common.ui.LoadingDialog;
import com.bizaia.zhongyin.module.discovery.api.IsCollectionAPI;
import com.bizaia.zhongyin.module.discovery.iml.ICollectionView;
import com.bizaia.zhongyin.module.mine.action.CancelCollectAction;
import com.bizaia.zhongyin.module.mine.action.CollectAction;
import com.bizaia.zhongyin.module.mine.adapter.DataBuyHisAdapterHelper;
import com.bizaia.zhongyin.module.mine.api.BuyHisAPI;
import com.bizaia.zhongyin.module.mine.api.CollectionAPI;
import com.bizaia.zhongyin.module.mine.data.BuyLiveBean;
import com.bizaia.zhongyin.module.mine.data.BuyMonthlyBean;
import com.bizaia.zhongyin.module.mine.data.BuyNewsBean;
import com.bizaia.zhongyin.module.mine.data.BuyVideoBean;
import com.bizaia.zhongyin.module.mine.data.CollectAssociationBean;
import com.bizaia.zhongyin.module.mine.data.CollectLecturesBean;
import com.bizaia.zhongyin.module.mine.data.CollectRecruitBean;
import com.bizaia.zhongyin.module.mine.iml.IBuyHisView;
import com.bizaia.zhongyin.module.monthly.data.SubDetailData;
import com.bizaia.zhongyin.module.monthly.ui.subscibe.MonthlySubSuccessActivity;
import com.bizaia.zhongyin.module.monthly.ui.subscibe.SubscribeDetailContract;
import com.bizaia.zhongyin.module.monthly.ui.subscibe.SubscribeDetailPresenter;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.widget.ForgetPop;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.LoadMore.LoadMoreWrapper;
import com.bizaia.zhongyin.widget.refresh.PtrClassicFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("ValidFragment")
public class BuyHisRecentlyFragment extends ResumeFragment implements IBuyHisView, SubscribeDetailContract.View<SubDetailData>, ICollectionView {
    @BindView(R.id.rvLive)
    RecyclerView rvLive;

    List<Object> dataList;
    LoadMoreWrapper moreWrapper;
    CustomAdapter adapter;
    @BindView(R.id.pfLive)
    PtrClassicFrameLayout pfLive;


    private BuyHisAPI liveAPI;
    private String type;
    private int pageNum = 1;
    private int pageSize = 10;

    private CollectionAPI collectionAPI;

    private LoadingDialog loadingDialog;
    private SubscribeDetailPresenter sub;
    private boolean isCollect;
    private IsCollectionAPI isCollectionAPI;
    private int delePos = -1;
    private boolean isEmpty;

    private ForgetPop forgetPop;

    public BuyHisRecentlyFragment(String type) {
        this.type = type;
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live_recently, container, false);
        ButterKnife.bind(this, view);
        if ("4".equals(type) || "5".equals(type))
            isCollect = true;
        initView();

        loadingDialog = new LoadingDialog(getContext());
        loadingDialog.show();

        liveAPI = new BuyHisAPI(this);
        collectionAPI = new CollectionAPI(this);
        sub = new SubscribeDetailPresenter(getActivity(), this);
        sub.requestSubDetail();

        isCollectionAPI = new IsCollectionAPI(this);

        if ("0".equals(type)) {
            liveAPI.getNews(pageNum, pageSize);
        } else if ("1".equals(type)) {
            liveAPI.getLive(pageNum, pageSize);
        } else if ("2".equals(type)) {
            liveAPI.getVideo(pageNum, pageSize);
        } else if ("3".equals(type)) {
            liveAPI.getMonthly(pageNum, pageSize);
        } else if ("4".equals(type)) {
            collectionAPI.getNews(pageNum, pageSize);
        } else if ("5".equals(type)) {
            collectionAPI.getVideo(pageNum, pageSize);
        } else if ("6".equals(type)) {
            collectionAPI.getChair(pageNum, pageSize);
        } else if ("7".equals(type)) {
            collectionAPI.getAsso(pageNum, pageSize);
        } else if ("8".equals(type)) {
            collectionAPI.getRecr(pageNum, pageSize);
        }

        addSubscription(RxBus.getInstance().getEvent(CancelCollectAction.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CancelCollectAction>() {
                    @Override
                    public void onNext(CancelCollectAction action) {
                        loadingDialog = new LoadingDialog(getContext());
                        loadingDialog.show();

                        rvLive.setVisibility(View.INVISIBLE);

                        if ("0".equals(type)) {
                            liveAPI.getNews(pageNum, pageSize);
                        } else if ("1".equals(type)) {
                            liveAPI.getLive(pageNum, pageSize);
                        } else if ("2".equals(type)) {
                            liveAPI.getVideo(pageNum, pageSize);
                        } else if ("3".equals(type)) {
                            liveAPI.getMonthly(pageNum, pageSize);
                        } else if ("4".equals(type)) {
                            collectionAPI.getNews(pageNum, pageSize);
                        } else if ("5".equals(type)) {
                            collectionAPI.getVideo(pageNum, pageSize);
                        } else if ("6".equals(type)) {
                            collectionAPI.getChair(pageNum, pageSize);
                        } else if ("7".equals(type)) {
                            collectionAPI.getAsso(pageNum, pageSize);
                        } else if ("8".equals(type)) {
                            collectionAPI.getRecr(pageNum, pageSize);
                        }
                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {
                    }
                }));
        return view;
    }


    private void initView() {
        pfLive.setLastUpdateTimeRelateObject(this);
        pfLive.setPtrHandler(defaultHandler);

        rvLive.setLayoutManager(new LinearLayoutManager(getContext()));
        dataList = new ArrayList<>();
        adapter = new DataBuyHisAdapterHelper().getAdapter(getContext(), dataList, pfLive, new OnClick() {
            @Override
            public void onClick() {
//                if(isBuy) {
                startActivity(new Intent(getActivity(), MonthlySubSuccessActivity.class));
//                }else{
//                    ToastUtils.show(getActivity(),);
//                }
            }
        }, isCollect, new Ondelete() {
            @Override
            public void onClick(int pos, long id) {
                int classType = 0;
                delePos = pos;
                if ("4".equals(type)) {
                    classType = 5;
                } else if ("5".equals(type)) {
                    classType = 3;
                }
                isCollectionAPI.addCollection(id, classType, 2);
            }
        }, this);
        moreWrapper = new LoadMoreWrapper(adapter);
        moreWrapper.setLoadMoreView(getLoadMoreView());
        moreWrapper.setOnLoadMoreListener(onLoadMoreListener);
        rvLive.setAdapter(moreWrapper);
    }


    PtrClassicFrameLayout.PtrDefaultHandler defaultHandler = new PtrClassicFrameLayout.PtrDefaultHandler() {
        public void onRefreshBegin(PtrFrameLayout frame) {
            pageNum = 1;
            rvLive.scrollToPosition(0);
            pfLive.refreshComplete();
            if ("0".equals(type)) {
                liveAPI.getNews(pageNum, pageSize);
            } else if ("1".equals(type)) {
                liveAPI.getLive(pageNum, pageSize);
            } else if ("2".equals(type)) {
                liveAPI.getVideo(pageNum, pageSize);
            } else if ("3".equals(type)) {
                liveAPI.getMonthly(pageNum, pageSize);
            } else if ("4".equals(type)) {
                collectionAPI.getNews(pageNum, pageSize);
            } else if ("5".equals(type)) {
                collectionAPI.getVideo(pageNum, pageSize);
            } else if ("6".equals(type)) {
                collectionAPI.getChair(pageNum, pageSize);
            } else if ("7".equals(type)) {
                collectionAPI.getAsso(pageNum, pageSize);
            } else if ("8".equals(type)) {
                collectionAPI.getRecr(pageNum, pageSize);
            }
        }
    };

    LoadMoreWrapper.OnLoadMoreListener onLoadMoreListener = new LoadMoreWrapper.OnLoadMoreListener() {
        @Override
        public void onLoadMoreRequested() {
            if (canLoadMore()) {
                if ("0".equals(type)) {
                    liveAPI.getNews(++pageNum, pageSize);
                } else if ("1".equals(type)) {
                    liveAPI.getLive(++pageNum, pageSize);
                } else if ("2".equals(type)) {
                    liveAPI.getVideo(++pageNum, pageSize);
                } else if ("3".equals(type)) {
                    liveAPI.getMonthly(++pageNum, pageSize);
                } else if ("4".equals(type)) {
                    collectionAPI.getNews(++pageNum, pageSize);
                } else if ("5".equals(type)) {
                    collectionAPI.getVideo(++pageNum, pageSize);
                } else if ("6".equals(type)) {
                    collectionAPI.getChair(++pageNum, pageSize);
                } else if ("7".equals(type)) {
                    collectionAPI.getAsso(++pageNum, pageSize);
                } else if ("8".equals(type)) {
                    collectionAPI.getRecr(++pageNum, pageSize);
                }
            }
        }
    };


    private View getLoadMoreView() {
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                getContext().getResources().getDimensionPixelSize(R.dimen.load_more_height));
        View loadMore = LayoutInflater.from(getContext()).inflate(R.layout.item_loadmore, null);
        loadMore.setLayoutParams(layoutParams);
        return loadMore;
    }


    @Override
    public void showBuyNews(BuyNewsBean data) {
        loadingDialog.dismiss();
        if (pageNum >= data.getData().getTotalPage())
            moreWrapper.clearLoadMore(true);
        else
            moreWrapper.clearLoadMore(false);
        if (pageNum == 1)
            dataList.clear();

        if (data.getData() != null && data.getData().getDatas() != null && !data.getData().getDatas().isEmpty()) {
            dataList.addAll(data.getData().getDatas());
        } else {
            adapter.show("DATA_EMPTY");
        }
        moreWrapper.notifyDataSetChanged();
        rvLive.setVisibility(View.VISIBLE);

        if (dataList.isEmpty() && pageNum == 1) {
            isEmpty = true;
            RxBus.getInstance().post(new CollectAction(0));
        } else {
            isEmpty = false;
        }
    }


    @Override
    public void showBuyLive(BuyLiveBean data) {
        loadingDialog.dismiss();
        if (pageNum >= data.getData().getTotalPage())
            moreWrapper.clearLoadMore(true);
        else
            moreWrapper.clearLoadMore(false);
        if (pageNum == 1)
            dataList.clear();
        if (data.getData() != null && data.getData().getDatas() != null && !data.getData().getDatas().isEmpty()) {
            dataList.addAll(data.getData().getDatas());
        } else {
            adapter.show("DATA_EMPTY");
        }
        moreWrapper.notifyDataSetChanged();
        rvLive.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBuyVideo(BuyVideoBean data) {
        loadingDialog.dismiss();
        if (pageNum >= data.getData().getTotalPage())
            moreWrapper.clearLoadMore(true);
        else
            moreWrapper.clearLoadMore(false);
        if (pageNum == 1)
            dataList.clear();
        if (data.getData() != null && data.getData().getDatas() != null && !data.getData().getDatas().isEmpty()) {
            dataList.addAll(data.getData().getDatas());
        } else {
            adapter.show("DATA_EMPTY");
        }
        moreWrapper.notifyDataSetChanged();
        rvLive.setVisibility(View.VISIBLE);
        if (dataList.isEmpty() && pageNum == 1) {
            isEmpty = true;
            RxBus.getInstance().post(new CollectAction(0));
        } else {
            isEmpty = false;
        }
    }

    @Override
    public void showBuyMonthly(BuyMonthlyBean data) {
        loadingDialog.dismiss();
        if (pageNum >= data.getData().getTotalPage())
            moreWrapper.clearLoadMore(true);
        else
            moreWrapper.clearLoadMore(false);
        if (pageNum == 1) {
            dataList.clear();
            if (BIZAIAApplication.getInstance().getUserInfo() != null && BIZAIAApplication.getInstance().getUserInfo().isSubscribe()) {
                dataList.add("monthly");
            } else {
                Log.e("BuyHisRecentlyFragment", "showBuyMonthly: -------> subscribe = false");
            }
        }
        if (data.getData() != null && data.getData().getDatas() != null && !data.getData().getDatas().isEmpty()) {
            dataList.addAll(data.getData().getDatas());
        } else {
            adapter.show("DATA_EMPTY");
        }
        moreWrapper.notifyDataSetChanged();
        rvLive.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLectures(CollectLecturesBean data) {
        loadingDialog.dismiss();
        if (pageNum >= data.getData().getTotalPage())
            moreWrapper.clearLoadMore(true);
        else
            moreWrapper.clearLoadMore(false);
        if (pageNum == 1)
            dataList.clear();
        if (data.getData() != null && data.getData().getDatas() != null && !data.getData().getDatas().isEmpty()) {
            dataList.addAll(data.getData().getDatas());
        } else {
            adapter.show("DATA_EMPTY");
        }
        moreWrapper.notifyDataSetChanged();
        rvLive.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAsso(CollectAssociationBean data) {
        loadingDialog.dismiss();
        if (pageNum >= data.getData().getTotalPage())
            moreWrapper.clearLoadMore(true);
        else
            moreWrapper.clearLoadMore(false);
        if (pageNum == 1)
            dataList.clear();
        if (data.getData() != null && data.getData().getDatas() != null && !data.getData().getDatas().isEmpty()) {
            dataList.addAll(data.getData().getDatas());
        } else {
            adapter.show("DATA_EMPTY");
        }
        moreWrapper.notifyDataSetChanged();
        rvLive.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRecr(CollectRecruitBean data) {
        loadingDialog.dismiss();
        if (pageNum >= data.getData().getTotalPage())
            moreWrapper.clearLoadMore(true);
        else
            moreWrapper.clearLoadMore(false);
        if (pageNum == 1)
            dataList.clear();
        if (data.getData() != null && data.getData().getDatas() != null && !data.getData().getDatas().isEmpty()) {
            dataList.addAll(data.getData().getDatas());
        } else {
            adapter.show("DATA_EMPTY");
        }
        moreWrapper.notifyDataSetChanged();
        rvLive.setVisibility(View.VISIBLE);
    }

    private boolean canLoadMore() {
        if (getRvLastItemToTop() > rvLive.getMeasuredHeight()) {
            return true;
        }
        return false;
    }

    private int getRvLastItemToTop() {
        if (rvLive.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) rvLive.getLayoutManager();
            View view = rvLive.getChildAt(layoutManager.findLastVisibleItemPosition());
            if (view != null) {
                return view.getTop() + view.getMeasuredHeight();
            }
        }
        return 0;
    }

    @Override
    public void showBuyHisError() {
        loadingDialog.dismiss();
        moreWrapper.clearLoadMore(true);
        adapter.show("NET_ERROR");
    }


    private boolean isBuy;

    @Override
    public void dataSuccess(SubDetailData news) {
        if (news.getData() == null) {
            isBuy = false;
        } else {
            isBuy = true;
        }
    }

    @Override
    public void dataError() {

    }

    @Override
    public void onRelogin() {
        loadingDialog.dismiss();
        reLogin();
    }

    @Override
    public void setPresenter(SubscribeDetailContract.Presenter presenter) {

    }

    public boolean editState;

    public void eidtAll(boolean isEdit) {
        editState = isEdit;
        super.eidtAll(isEdit);
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i) instanceof BuyNewsBean.DataEntity.DatasEntity) {
                BuyNewsBean.DataEntity.DatasEntity datasEntity = (BuyNewsBean.DataEntity.DatasEntity) dataList.get(i);
                if (isEdit)
                    datasEntity.setEdit(true);
                else
                    datasEntity.setEdit(false);
                dataList.set(i, datasEntity);
            } else if (dataList.get(i) instanceof BuyVideoBean.DataEntity.DatasEntity) {
                BuyVideoBean.DataEntity.DatasEntity datasEntity = (BuyVideoBean.DataEntity.DatasEntity) dataList.get(i);
                if (isEdit)
                    datasEntity.setEdit(true);
                else
                    datasEntity.setEdit(false);
                dataList.set(i, datasEntity);
            }
        }
        moreWrapper.notifyDataSetChanged();
        rvLive.setVisibility(View.VISIBLE);
    }

    public boolean getSate() {
        return editState;
    }


    public void delete() {
        if (forgetPop != null)
            forgetPop.dismiss();
        forgetPop = new ForgetPop(getActivity(), pfLive, R.layout.pop_im) {
            @Override
            public void viewInit() {
                ((TextView) findViewById(R.id.content)).setText("すべてのデータをお気に入りに解除しますか");
                TextView start = (TextView) findViewById(R.id.tv_cancel);
                start.setText("いいえ");
                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        forgetPop.dismiss();
                    }
                });
                TextView cancel = (TextView) findViewById(R.id.tv_sure);
                cancel.setText("はい");
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        forgetPop.dismiss();
                        int classType = 0;
                        if ("4".equals(type)) {
                            classType = 5;
                        } else if ("5".equals(type)) {
                            classType = 3;
                        }
                        delePos = -1;
                        isCollectionAPI.deletAll(classType, 2);
                    }
                });
                ImageView ivClose = (ImageView) findViewById(R.id.ivClose);
                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        forgetPop.dismiss();
                    }
                });
            }
        };
        forgetPop.show();
    }

    @Override
    public void netError() {

    }

    @Override
    public void showIsCollection(boolean isAttention) {

    }

    @Override
    public void showAddCollection(boolean isAttention) {

    }

    @Override
    public void showDelCollection(boolean isAttention) {
        if (isAttention) {
            if ("4".equals(type) && delePos != -1) {
                dataList.remove(delePos);
            } else if ("5".equals(type) && delePos != -1) {
                dataList.remove(delePos);
            }
            if (delePos == -1) {
                pageNum = 1;
                if ("4".equals(type)) {
                    collectionAPI.getNews(pageNum, pageSize);
                } else if ("5".equals(type)) {
                    collectionAPI.getVideo(pageNum, pageSize);
                }
            } else {
                moreWrapper.notifyDataSetChanged();
                rvLive.setVisibility(View.VISIBLE);
                delePos = -1;
            }

        }
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    @Override
    public void showCollectionError() {

    }

    public interface OnClick {
        void onClick();
    }

    public interface Ondelete {
        void onClick(int pos, long id);
    }
}
