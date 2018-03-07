package com.bizaia.zhongyin.module.monthly.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.common.adapter.StateAdapterHelper;
import com.bizaia.zhongyin.module.common.data.SearchFixedData;
import com.bizaia.zhongyin.module.common.data.SendSearchDataToSearchAct;
import com.bizaia.zhongyin.module.common.ui.SearchFixedView;
import com.bizaia.zhongyin.module.monthly.action.DeleteNotifyMineAction;
import com.bizaia.zhongyin.module.monthly.data.AllJSData;
import com.bizaia.zhongyin.module.monthly.data.AllMonthlyData;
import com.bizaia.zhongyin.module.monthly.data.CommendData;
import com.bizaia.zhongyin.module.monthly.data.MonthlyJSData;
import com.bizaia.zhongyin.module.monthly.data.MonthlyJSSearchData;
import com.bizaia.zhongyin.module.monthly.data.SubscribeData;
import com.bizaia.zhongyin.module.monthly.ui.detail.JSDetailActivity;
import com.bizaia.zhongyin.module.monthly.ui.detail.js.JSActivity;
import com.bizaia.zhongyin.module.monthly.ui.pdfdetail.PDFDetailActivity;
import com.bizaia.zhongyin.repository.AddressConfiguration;
import com.bizaia.zhongyin.repository.data.MonthlyNewestBean;
import com.bizaia.zhongyin.util.ACache;
import com.bizaia.zhongyin.util.ImageLoaderUtils;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.SizeUtils;
import com.bizaia.zhongyin.util.TimeUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.CustomAdapterItem;
import com.bizaia.zhongyin.widget.adapter.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by yan on 2017/2/28.
 */

public class DataAdapterHelper extends StateAdapterHelper implements View.OnClickListener {
    private static final String TAG = "DataAdapterHelper";
    private Context context;
    private CustomAdapter adapter;
    public CustomAdapter getAdapter(final Context context, List list) {
        this.context = context;
        adapter = super.getAdapter(context, list)

                //--------------------------- monthly ----------------------------
                .addAdapterItem(new CustomAdapterItem<ViewHolder, MonthlyJSData.DataBean.MonthlyNewestBean>() {
                    @Override
                    public Class dataType() {
                        return MonthlyJSData.DataBean.MonthlyNewestBean.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.fragment_monthly_item_newest_1btn);
                    }

                    @Override
                    public void bindData(final ViewHolder holder, final MonthlyJSData.DataBean.MonthlyNewestBean
                            item, int position) {
                        if (position == 0) {
                            View container = holder.getView(R.id.monthly_container);
                            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) container.getLayoutParams();
                            layoutParams.topMargin = SizeUtils.dp2px(context, 5);
                            container.setLayoutParams(layoutParams);
                        }

                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + item.getCoverUrl()
                                , (ImageView) holder.getView(R.id.iv_monthly_cover)
                                , ImageLoaderUtils.getImageOptions(R.drawable.magazine_default));
                        holder.setText(R.id.tv_time, item.getTitle());
                        if (item.getPrice() != 0) {
                            holder.setText(R.id.tv_price, "JPY" + String.format("%.2f", item.getPrice()));
                        } else {
                            holder.setText(R.id.tv_price, context.getString(R.string.video_free));
                        }
                        holder.setText(R.id.tv_introduce, item.getDescription());

                        if (item.getChapterTitles() != null && item.getChapterTitles().size() >= 1) {
                            holder.setVisible(R.id.ll_label1, true);
                            holder.setText(R.id.tv_sub_title1, item.getChapterTitles().get(0).getTitle());
                            holder.setOnClickListener(R.id.ll_label1, new View.OnClickListener() {
                                public void onClick(View v) {
                                    Log.e(TAG, "onClick: --------------->"+item.getShareUrl());
                                    context.startActivity(new Intent(context, JSDetailActivity.class)
                                            .putExtra("monthId", item.getId())
                                            .putExtra("id", item.getChapterTitles().get(0).getId())
                                            .putExtra("url", item.getChapterTitles().get(0).getH5Url())
                                            .putExtra("title", item.getTitle())
                                            .putExtra("author", item.getChapterTitles().get(0).getAuthor())
                                            .putExtra("time", item.getChapterTitles().get(0).getCreateTime())
                                            .putExtra("subTile", item.getChapterTitles().get(0).getTitle())
                                            .putExtra("totalCount", String.valueOf(item.getChapterTitles().get(0).getCommentNum()))
                                            .putExtra("image", item.getCoverUrl())
                                            .putExtra("price", item.getPrice())
                                            .putExtra("share", item.getH5Url())
                                            .putExtra("filePath",item.getFileUrl())
                                            .addFlags(FLAG_ACTIVITY_NEW_TASK));
                                }
                            });
                        } else {
                            holder.setVisible(R.id.ll_label1, false);
                        }
                        if (item.getChapterTitles() != null && item.getChapterTitles().size() >= 2) {
                            holder.setVisible(R.id.ll_label2, true);
                            holder.setText(R.id.tv_sub_title2, item.getChapterTitles().get(1).getTitle());
                            holder.setOnClickListener(R.id.ll_label2, new View.OnClickListener() {
                                public void onClick(View v) {
                                    Log.e(TAG, "onClick: --------------->"+item.getShareUrl());
                                    context.startActivity(new Intent(context, JSDetailActivity.class)
                                            .putExtra("monthId", item.getId())
                                            .putExtra("id", item.getChapterTitles().get(1).getId())
                                            .putExtra("url", item.getChapterTitles().get(1).getH5Url())
                                            .putExtra("title", item.getTitle())
                                            .putExtra("author", item.getChapterTitles().get(1).getAuthor())
                                            .putExtra("time", item.getChapterTitles().get(1).getCreateTime())
                                            .putExtra("subTile", item.getChapterTitles().get(1).getTitle())
                                            .putExtra("totalCount", String.valueOf(item.getChapterTitles().get(1).getCommentNum()))
                                            .putExtra("image", item.getCoverUrl())
                                            .putExtra("price", item.getPrice())
                                            .putExtra("share", item.getH5Url())
                                            .putExtra("filePath",item.getFileUrl())
                                            .addFlags(FLAG_ACTIVITY_NEW_TASK));
                                }
                            });
                        } else {
                            holder.setVisible(R.id.ll_label2, false);
                        }
                        holder.setTag(R.id.monthly_container, item);

                        holder.setOnClickListener(R.id.monthly_container, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ACache.get(context).put("MonthlyJSData", item);
                                context.startActivity(new Intent(context, PDFDetailActivity.class)
//                                        .putExtra("MonthlyJSData", item)
                                        .addFlags(FLAG_ACTIVITY_NEW_TASK
                                                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                                                | Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                );
                            }
                        });
                    }
                })


                //--------------------------- monthly for db ----------------------------
                .addAdapterItem(new CustomAdapterItem<ViewHolder, MonthlyNewestBean>() {
                    @Override
                    public Class dataType() {
                        return MonthlyNewestBean.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.activity_monthly_item_db);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final MonthlyNewestBean
                            item, int position) {
                        if (position == 0) {
                            View container = holder.getView(R.id.monthly_container);
                            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) container.getLayoutParams();
                            layoutParams.topMargin = SizeUtils.dp2px(context, 5);
                            container.setLayoutParams(layoutParams);
                        }

                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + item.getCoverUrl()
                                , (ImageView) holder.getView(R.id.iv_monthly_cover)
                                , ImageLoaderUtils.getImageOptions(R.drawable.magazine_default));
                        holder.setText(R.id.tv_time, item.getTitle());
                        if (item.getDataJS1() != null) {
                            holder.setVisible(R.id.ll_label1, true);
                            holder.setText(R.id.tv_sub_title1, item.getDataJS1());

                        } else {
                            holder.setVisible(R.id.ll_label1, false);
                        }
                        if (item.getDataJS1() != null) {
                            holder.setVisible(R.id.ll_label2, true);
                            holder.setText(R.id.tv_sub_title2, item.getDataJS2());
                        } else {
                            holder.setVisible(R.id.ll_label2, false);
                        }

                        holder.setVisible(R.id.iv_delete, isModifyAble);

                        holder.setText(R.id.tv_time, item.getTitle());
                        holder.setText(R.id.tv_price, "JPY" + String.format("%.2f", item.getPrice()));
                        holder.setText(R.id.tv_introduce, item.getDescription());
                        holder.setVisible(R.id.view_cover, (item.getDownloadPath() == null));

                        holder.setTag(R.id.monthly_container, item);
                        holder.setTag(R.id.iv_delete, item);
                        holder.setOnClickListener(R.id.iv_delete, DataAdapterHelper.this);

                        holder.setOnClickListener(R.id.monthly_container, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                MonthlyJSData.DataBean.MonthlyNewestBean bean = new MonthlyJSData.DataBean.MonthlyNewestBean(
                                        item.getId()
                                        , item.getTitle()
                                        , item.getCoverUrl()
                                        , item.getFileUrl()
                                        , item.getPrice()
                                        , "0"
                                        , item.getDescription()
                                        , 0
                                        , null
                                        , item.getShareUrl()

                                );
                                bean.setShareUrl(item.getShareUrl());
                                ACache.get(context).put("MonthlyJSData", bean);
                                context.startActivity(new Intent(context, PDFDetailActivity.class)
//                                        .putExtra("MonthlyJSData", bean)
                                        .putExtra("isSaved", true)
                                        .putExtra("isBuy", true)
                                        .addFlags(FLAG_ACTIVITY_NEW_TASK));
                            }
                        });
                    }
                })


                //--------------------------- all monthly ----------------------------
                .addAdapterItem(new CustomAdapterItem<ViewHolder, AllMonthlyData.DataBean.DatasBean>() {
                    @Override
                    public Class dataType() {
                        return AllMonthlyData.DataBean.DatasBean.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.fragment_monthly_item_newest_2btn);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final AllMonthlyData.DataBean.DatasBean
                            allMonthlyData, int position) {
                        if (position == 0) {
                            View container = holder.getView(R.id.monthly_container);
                            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) container.getLayoutParams();
                            layoutParams.topMargin = SizeUtils.dp2px(context, 5);
                            container.setLayoutParams(layoutParams);
                        }

                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + allMonthlyData.getCoverUrl()
                                , (ImageView) holder.getView(R.id.iv_monthly_cover)
                                , ImageLoaderUtils.getImageOptions(R.drawable.magazine_default));
                        holder.setText(R.id.tv_time, allMonthlyData.getTitle());
                        if (allMonthlyData.getPrice() != 0) {
                            holder.setText(R.id.tv_price, "JPY" + String.format("%.2f", allMonthlyData.getPrice()));
                        } else {
                            holder.setText(R.id.tv_price, context.getString(R.string.video_free));
                        }
                        holder.setText(R.id.tv_introduce, allMonthlyData.getDescription());
                        if (allMonthlyData.getChapterTitles() != null && allMonthlyData.getChapterTitles().size() >= 1) {
                            holder.setText(R.id.tv_sub_title1, allMonthlyData.getChapterTitles().get(0).getTitle());
                            holder.setVisible(R.id.ll_label1, true);
                            holder.setOnClickListener(R.id.ll_label1, new View.OnClickListener() {
                                public void onClick(View v) {
                                    Log.e(TAG, "onClick: --------------->"+allMonthlyData.getChapterTitles().get(0).getH5Url());
                                    context.startActivity(new Intent(context, JSDetailActivity.class)
                                            .putExtra("monthId", allMonthlyData.getId())
                                            .putExtra("id", allMonthlyData.getChapterTitles().get(0).getId())
                                            .putExtra("url", allMonthlyData.getChapterTitles().get(0).getH5Url())
                                            .putExtra("title", allMonthlyData.getTitle())
                                            .putExtra("author", allMonthlyData.getChapterTitles().get(0).getAuthor())
                                            .putExtra("time", allMonthlyData.getChapterTitles().get(0).getCreateTime())
                                            .putExtra("subTile", allMonthlyData.getChapterTitles().get(0).getTitle())
                                            .putExtra("totalCount", String.valueOf(allMonthlyData.getChapterTitles().get(0).getCommentNum()))
                                            .putExtra("image", allMonthlyData.getCoverUrl())
                                            .putExtra("price", allMonthlyData.getPrice())
                                            .putExtra("share", allMonthlyData.getChapterTitles().get(0).getH5Url())
                                            .putExtra("filePath",allMonthlyData.getFileUrl())
                                            .addFlags(FLAG_ACTIVITY_NEW_TASK));
                                }
                            });
                        } else {
                            holder.setVisible(R.id.ll_label1, false);
                        }
                        if (allMonthlyData.getChapterTitles() != null && allMonthlyData.getChapterTitles().size() >= 2) {
                            holder.setText(R.id.tv_sub_title2, allMonthlyData.getChapterTitles().get(1).getTitle());
                            holder.setVisible(R.id.ll_label2, true);
                            holder.setOnClickListener(R.id.ll_label2, new View.OnClickListener() {
                                public void onClick(View v) {
                                    Log.e(TAG, "onClick: --------------->"+allMonthlyData.getChapterTitles().get(1).getH5Url());
                                    context.startActivity(new Intent(context, JSDetailActivity.class)
                                            .putExtra("monthId", allMonthlyData.getId())
                                            .putExtra("id", allMonthlyData.getChapterTitles().get(1).getId())
                                            .putExtra("url", allMonthlyData.getChapterTitles().get(1).getH5Url())
                                            .putExtra("title", allMonthlyData.getTitle())
                                            .putExtra("author", allMonthlyData.getChapterTitles().get(1).getAuthor())
                                            .putExtra("time", allMonthlyData.getChapterTitles().get(1).getCreateTime())
                                            .putExtra("subTile", allMonthlyData.getChapterTitles().get(1).getTitle())
                                            .putExtra("totalCount", String.valueOf(allMonthlyData.getChapterTitles().get(1).getCommentNum()))
                                            .putExtra("image", allMonthlyData.getCoverUrl())
                                            .putExtra("price", allMonthlyData.getPrice())
                                            .putExtra("share", allMonthlyData.getChapterTitles().get(1).getH5Url())
                                            .putExtra("filePath",allMonthlyData.getFileUrl())
                                            .addFlags(FLAG_ACTIVITY_NEW_TASK));
                                }
                            });
                        } else {
                            holder.setVisible(R.id.ll_label2, false);
                        }

                        holder.setTag(R.id.monthly_container, allMonthlyData);
                        holder.setOnClickListener(R.id.monthly_container, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MonthlyJSData.DataBean.MonthlyNewestBean bean = new MonthlyJSData.DataBean.MonthlyNewestBean(
                                        allMonthlyData.getId(), allMonthlyData.getTitle(), allMonthlyData.getCoverUrl(), allMonthlyData.getFileUrl(),
                                        allMonthlyData.getPrice(),
                                        allMonthlyData.getCreateTime(), allMonthlyData.getDescription(),
                                        allMonthlyData.getChapterNum(),
                                        null, allMonthlyData.getShareUrl()
                                );
                                bean.setShareUrl(allMonthlyData.getShareUrl());
                                ACache.get(context).put("MonthlyJSData", bean);
                                context.startActivity(new Intent(context, PDFDetailActivity.class)
//                                        .putExtra("MonthlyJSData", bean)
                                        .addFlags(FLAG_ACTIVITY_NEW_TASK));
                            }
                        });
                        holder.setOnClickListener(R.id.tv_js, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.e("onClick", "onClick: " + v.getTag());
                                MonthlyJSData.DataBean.MonthlyNewestBean bean = new MonthlyJSData.DataBean.MonthlyNewestBean(
                                        allMonthlyData.getId(), allMonthlyData.getTitle(), allMonthlyData.getCoverUrl(), allMonthlyData.getFileUrl(),
                                        allMonthlyData.getPrice(),
                                        allMonthlyData.getCreateTime(), allMonthlyData.getDescription(),
                                        allMonthlyData.getChapterNum(),
                                        null, allMonthlyData.getShareUrl()
                                );
                                context.startActivity(new Intent(context, JSActivity.class)
                                        .putExtra("id", allMonthlyData.getId())
                                        .putExtra("title", allMonthlyData.getTitle())
                                        .putExtra("shareUrl", allMonthlyData.getShareUrl())
                                        .putExtra("filePath",allMonthlyData.getFileUrl())
                                        .putExtra("month",bean)
                                        .addFlags(FLAG_ACTIVITY_NEW_TASK));
                            }
                        });
                    }
                })

                //--------------------------- monthly sub ----------------------------
                .addAdapterItem(new CustomAdapterItem<ViewHolder, MonthlyJSData.DataBean.ChapterListBean.DatasBean>() {
                    @Override
                    public Class dataType() {
                        return MonthlyJSData.DataBean.ChapterListBean.DatasBean.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.fragment_monthly_item_newest_sub);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final MonthlyJSData.DataBean.ChapterListBean.DatasBean
                            item, int position) {
                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + item.getCoverUrl()
                                , (ImageView) holder.getView(R.id.iv_head)
                                , ImageLoaderUtils.getImageOptions(R.drawable.magazine_default));
                        holder.setText(R.id.tv_title, item.getTitle());
                        holder.setHrmlText(R.id.tv_content, Html.fromHtml(item.getSubhead()));
                        holder.setHrmlText(R.id.tv_digest, Html.fromHtml(item.getDigest()));
                        holder.setText(R.id.tv_author, item.getAuthor());
                        holder.setText(R.id.tv_name, item.getArea());
                        holder.setText(R.id.tv_browse_num, String.valueOf(context.getString(R.string.brown_count) + item.getPageViewNum()));
                        holder.setText(R.id.tv_comment_num, String.valueOf(context.getString(R.string.reply_count) + item.getCommentNum()));
                        holder.setOnClickListener(R.id.monthly_sub_container, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.e(TAG, "onClick: --------------->"+item.getShareUrl());
                                MonthlyJSData.DataBean.MonthlyNewestBean  bean = (MonthlyJSData.DataBean.MonthlyNewestBean)adapter.getObject();
                                Log.e("DataAdapterHelper", "setObject: --------------------->" +bean);
                                String subTitle;
                                context.startActivity(new Intent(context, JSDetailActivity.class)
                                        .putExtra("monthId", getMonthlyId())
                                        .putExtra("id", item.getId())
                                        .putExtra("url", item.getH5Url())
                                        .putExtra("title",bean.getTitle())
                                        .putExtra("author", item.getAuthor())
                                        .putExtra("time", item.getCreateTime())
                                        .putExtra("subTile", bean.getTitle())
                                        .putExtra("totalCount", String.valueOf(item.getCommentNum()))
                                        .putExtra("image", bean.getCoverUrl())
                                        .putExtra("share", item.getShareUrl())
                                        .putExtra("filePath",item.getFileUrl())
                                        .addFlags(FLAG_ACTIVITY_NEW_TASK));
                            }
                        });
                    }
                })
                //  search
                .addAdapterItem(new CustomAdapterItem<ViewHolder, MonthlyJSSearchData.DataEntity.DatasEntity>() {
                    @Override
                    public Class dataType() {
                        return MonthlyJSSearchData.DataEntity.DatasEntity.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.fragment_monthly_item_newest_2btn);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final MonthlyJSSearchData.DataEntity.DatasEntity
                            item, int position) {
                        if (position == 0) {
                            View container = holder.getView(R.id.monthly_container);
                            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) container.getLayoutParams();
                            layoutParams.topMargin = SizeUtils.dp2px(context, 5);
                            container.setLayoutParams(layoutParams);
                        }

                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + item.getCoverUrl()
                                , (ImageView) holder.getView(R.id.iv_monthly_cover)
                                , ImageLoaderUtils.getImageOptions(R.drawable.magazine_default));
                        holder.setText(R.id.tv_time, item.getTitle());
                        if (item.getPrice() != 0) {
                            holder.setText(R.id.tv_price, "JPY" + String.format("%.2f", item.getPrice()));
                        } else {
                            holder.setText(R.id.tv_price, context.getString(R.string.video_free));
                        }
                        holder.setText(R.id.tv_introduce, item.getDescription());
                        if (item.getChapterTitles() != null&&!item.getChapterTitles().isEmpty()) {
                            holder.setVisible(R.id.ll_label1, true);
                            holder.setText(R.id.tv_sub_title1, item.getChapterTitles().get(0).getTitle());

                        } else {
                            holder.setVisible(R.id.ll_label1, false);
                        }
                        if (item.getChapterTitles() != null&&!item.getChapterTitles().isEmpty()&&item.getChapterTitles().size()>1) {
                            holder.setVisible(R.id.ll_label2, true);
                            holder.setText(R.id.tv_sub_title2, item.getChapterTitles().get(1).getTitle());
                        } else {
                            holder.setVisible(R.id.ll_label2, false);
                        }

                        holder.setTag(R.id.monthly_container, item);
                        holder.setOnClickListener(R.id.monthly_container, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MonthlyJSData.DataBean.MonthlyNewestBean bean = new MonthlyJSData.DataBean.MonthlyNewestBean(
                                        item.getId(), item.getTitle(), item.getCoverUrl(), item.getFileUrl(), item.getPrice(),
                                        item.getCreateTime(), item.getDescription(), item.getChapterNum(), null, item.getShareUrl()
                                );
                                ACache.get(context).put("MonthlyJSData", bean);
                                context.startActivity(new Intent(context, PDFDetailActivity.class)
//                                        .putExtra("MonthlyJSData", bean)
                                        .addFlags(FLAG_ACTIVITY_NEW_TASK));
                            }
                        });
                        holder.setOnClickListener(R.id.tv_js, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.e("onClick", "onClick: " + v.getTag());
                                MonthlyJSData.DataBean.MonthlyNewestBean bean = new MonthlyJSData.DataBean.MonthlyNewestBean(
                                        item.getId(), item.getTitle(), item.getCoverUrl(), item.getFileUrl(), item.getPrice(),
                                        item.getCreateTime(), item.getDescription(), item.getChapterNum(), null, item.getShareUrl()
                                );
                                context.startActivity(new Intent(context, JSActivity.class)
                                        .putExtra("id", item.getId())
                                        .putExtra("shareUrl", item.getShareUrl())
                                        .putExtra("title", item.getTitle())
                                        .putExtra("filePath",item.getFileUrl())
                                        .putExtra("month",bean)
                                        .addFlags(FLAG_ACTIVITY_NEW_TASK));
                            }
                        });
                        holder.setOnClickListener(R.id.iv_monthly_cover, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MonthlyJSData.DataBean.MonthlyNewestBean bean = new MonthlyJSData.DataBean.MonthlyNewestBean(
                                        item.getId(), item.getTitle(), item.getCoverUrl(), item.getFileUrl(), item.getPrice(),
                                        item.getCreateTime(), item.getDescription(), item.getChapterNum(), null, item.getShareUrl()
                                );
                                bean.setShareUrl(item.getShareUrl());
                                ACache.get(context).put("MonthlyJSData", bean);
                                context.startActivity(new Intent(context, PDFDetailActivity.class)
//                                        .putExtra("MonthlyJSData", bean)
                                        .addFlags(FLAG_ACTIVITY_NEW_TASK));
                            }
                        });
                    }
                })
                //--------------------------- all js data ----------------------------
                .addAdapterItem(new CustomAdapterItem<ViewHolder, AllJSData.DataBean.DatasBean>() {
                    @Override
                    public Class dataType() {
                        return AllJSData.DataBean.DatasBean.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.fragment_monthly_item_newest_sub);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final AllJSData.DataBean.DatasBean
                            item, int position) {
                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + item.getCoverUrl()
                                , (ImageView) holder.getView(R.id.iv_head)
                                , ImageLoaderUtils.getImageOptions(R.drawable.magazine_default));
                        holder.setText(R.id.tv_title, item.getTitle());
                        holder.setHrmlText(R.id.tv_content, Html.fromHtml(item.getSubhead()));
                        holder.setHrmlText(R.id.tv_digest, Html.fromHtml(item.getDigest()));
                        holder.setText(R.id.tv_author, item.getAuthor());
                        holder.setText(R.id.tv_name, item.getArea());
                        holder.setText(R.id.tv_browse_num, String.valueOf(context.getString(R.string.brown_count) + item.getPageViewNum()));
                        holder.setText(R.id.tv_comment_num, String.valueOf(context.getString(R.string.reply_count) + item.getCommentNum()));
                        holder.setOnClickListener(R.id.monthly_sub_container, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MonthlyJSData.DataBean.MonthlyNewestBean  bean = (MonthlyJSData.DataBean.MonthlyNewestBean)adapter.getObject();
                                Log.e(TAG, "onClick: --------------->"+item.getShareUrl());
                                context.startActivity(new Intent(context, JSDetailActivity.class)
                                        .putExtra("id", item.getId())
                                        .putExtra("monthId", getMonthlyId())
                                        .putExtra("url", item.getH5Url())
                                        .putExtra("title", bean.getTitle())
                                        .putExtra("author", item.getAuthor())
                                        .putExtra("time", item.getCreateTime())
                                        .putExtra("totalCount", String.valueOf(item.getCommentNum()))
                                        .putExtra("subTile", bean.getTitle())
                                        .putExtra("image", bean.getCoverUrl())
                                        .putExtra("share", item.getShareUrl())
                                        .putExtra("filePath",item.getFileUrl())
                                        .addFlags(FLAG_ACTIVITY_NEW_TASK));

                            }
                        });
                    }
                })
                //--------------------------- all commend data ----------------------------
                .addAdapterItem(new CustomAdapterItem<ViewHolder, CommendData.DataBean.DatasBean>() {
                    @Override
                    public Class dataType() {
                        return CommendData.DataBean.DatasBean.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.activity_monthly_commend_list_item);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final CommendData.DataBean.DatasBean
                            item, int position) {
                        if (item.getAvatarUrl() != null && item.getAvatarUrl().startsWith("http")) {
                            ImageLoader.getInstance().displayImage(item.getAvatarUrl()
                                    , (ImageView) holder.getView(R.id.riv_head)
                                    , ImageLoaderUtils.getImageOptions(R.drawable.head));
                        } else {
                            ImageLoader.getInstance().displayImage(
                                    AddressConfiguration.MAIN_PATH + item.getAvatarUrl()
                                    , (ImageView) holder.getView(R.id.riv_head)
                                    , ImageLoaderUtils.getImageOptions(R.drawable.head));
                        }
                        holder.setText(R.id.tv_name, item.getNickname());
                        holder.setText(R.id.tv_info, item.getContent());
                        holder.setText(R.id.tv_time, TimeUtils.timeTransGBToCN(item.getCreateTime()));
                    }
                })

                //--------------------------- subscribe data ----------------------------
                .addAdapterItem(new CustomAdapterItem<ViewHolder, SubscribeData.DataBean.DatasBean>() {
                    @Override
                    public Class dataType() {
                        return SubscribeData.DataBean.DatasBean.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        View view = LayoutInflater.from(context).inflate(R.layout.fragment_monthly_subscribe_item, parent, false);
                        return ViewHolder.createViewHolder(context, view);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final SubscribeData.DataBean.DatasBean
                            item, int position) {
                        holder.setText(R.id.tv_title_price, item.getTitle() + "JPY" + String.format("%.2f", item.getPrice()));
                        holder.setOnClickListener(R.id.tv_title_price, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (onClickListener != null) {
                                    onClickListener.onClick(item);
                                }
                            }
                        });
                    }
                })

                //--------------------------- search fixed ----------------------------
                .addAdapterItem(new CustomAdapterItem<ViewHolder, SearchFixedData>() {
                    @Override
                    public Class dataType() {
                        return SearchFixedData.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, new SearchFixedView(context));
                    }

                    @Override
                    public void bindData(ViewHolder holder, final SearchFixedData
                            item, final int position) {
                        SearchFixedView searchFixedView = (SearchFixedView) holder.getConvertView();
                        searchFixedView.setTitle(item.title);
                        searchFixedView.setIndexTitles(item.indexTitles);
                        searchFixedView.setLineShow(item.isLineShow);
                        searchFixedView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int tempPosition = (int) view.getTag();
                                RxBus.getInstance().post(new SendSearchDataToSearchAct(item.navData, item.indexTitles.get(tempPosition), position, tempPosition));
                            }
                        });
                    }
                })
        ;

        return adapter;
    }

    public String getMonthlyTitle() {
        return "";
    }



    public long getMonthlyId() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.monthly_container:
//                MonthlyJSData.DataBean.MonthlyNewestBean.ChapterTitlesBean monthlyNewestBean;
//                if (view.getTag() instanceof AllMonthlyData) {
//                    monthlyNewestBean = ((AllMonthlyData) view.getTag()).getDatasBean();
//                } else if (view.getTag() instanceof MonthlyNewestBean) {
//                    MonthlyNewestBean newestBean = (MonthlyNewestBean) view.getTag();
//                    monthlyNewestBean = new MonthlyJSData.DataBean.MonthlyNewestBean();
//                    monthlyNewestBean.setId((int) newestBean.getId());
//                    monthlyNewestBean.setTitle(newestBean.getTitle());
//                    monthlyNewestBean.setFileUrl(newestBean.getFileUrl());
//                    monthlyNewestBean.setCoverUrl(newestBean.getCoverUrl());
//                    monthlyNewestBean.setDescription(newestBean.getDescription());
//                    monthlyNewestBean.setPrice(newestBean.getPrice());
//                } else {
//                    monthlyNewestBean = (MonthlyJSData.DataBean.MonthlyNewestBean) view.getTag();
//                }


                break;
            case R.id.monthly_sub_container:
                break;

            case R.id.iv_delete:
                MonthlyNewestBean newestBean = (MonthlyNewestBean) view.getTag();
                RxBus.getInstance().post(new DeleteNotifyMineAction(newestBean.getId()));
                break;

        }
    }



    boolean isModifyAble = false;

    public void setMonthlyModifyAble(boolean isModifyAble) {
        this.isModifyAble = isModifyAble;
        adapter.notifyDataSetChanged();
    }

    private OnClickListener onClickListener;

    public DataAdapterHelper setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }

    public interface OnClickListener {
        void onClick(SubscribeData.DataBean.DatasBean dataEntity);
    }

}
