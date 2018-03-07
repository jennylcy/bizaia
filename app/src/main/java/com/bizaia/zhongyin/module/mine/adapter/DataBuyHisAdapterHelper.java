package com.bizaia.zhongyin.module.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.common.adapter.StateAdapterHelper;
import com.bizaia.zhongyin.module.live.ui.LiveDetailActivity;
import com.bizaia.zhongyin.module.mine.data.BuyLiveBean;
import com.bizaia.zhongyin.module.mine.data.BuyMonthlyBean;
import com.bizaia.zhongyin.module.mine.data.BuyNewsBean;
import com.bizaia.zhongyin.module.mine.data.BuyVideoBean;
import com.bizaia.zhongyin.module.mine.data.CollectAssociationBean;
import com.bizaia.zhongyin.module.mine.data.CollectLecturesBean;
import com.bizaia.zhongyin.module.mine.data.CollectRecruitBean;
import com.bizaia.zhongyin.module.mine.ui.BuyHisRecentlyFragment;
import com.bizaia.zhongyin.module.mine.ui.ChairPayActivity;
import com.bizaia.zhongyin.module.mine.ui.detail.DetailActivity;
import com.bizaia.zhongyin.module.monthly.data.MonthlyJSData;
import com.bizaia.zhongyin.module.monthly.ui.detail.js.JSActivity;
import com.bizaia.zhongyin.module.monthly.ui.pdfdetail.PDFDetailActivity;
import com.bizaia.zhongyin.repository.AddressConfiguration;
import com.bizaia.zhongyin.util.ImageLoaderUtils;
import com.bizaia.zhongyin.util.SizeUtils;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.TimeUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.CustomAdapterItem;
import com.bizaia.zhongyin.widget.adapter.ViewHolder;
import com.bizaia.zhongyin.widget.refresh.PtrClassicFrameLayout;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by yan on 2017/2/28.
 */

public final class DataBuyHisAdapterHelper extends StateAdapterHelper {
    private Context context;
    private BuyHisRecentlyFragment.OnClick onClick;
    private boolean isCollect;
    private BuyHisRecentlyFragment.Ondelete delete;
    private BuyHisRecentlyFragment mFragement;

    public CustomAdapter getAdapter(final Context context, List list, final PtrClassicFrameLayout ptrClassicFrameLayout,
                                    final BuyHisRecentlyFragment.OnClick onClick,
                                    final boolean isCollect,
                                    BuyHisRecentlyFragment.Ondelete ondelete,
                                    BuyHisRecentlyFragment fragement
    ) {
        this.context = context;
        this.onClick = onClick;
        this.isCollect = isCollect;
        this.delete = ondelete;
        this.mFragement = fragement;
        return super.getAdapter(context, list)
                //   news
                .addAdapterItem(new CustomAdapterItem<ViewHolder, BuyNewsBean.DataEntity.DatasEntity>() {
                    @Override
                    public Class dataType() {
                        return BuyNewsBean.DataEntity.DatasEntity.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.fragment_chair_item);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final BuyNewsBean.DataEntity.DatasEntity
                            item, final int position) {
                        holder.setText(R.id.tv_title, item.getTitle());
                        holder.setText(R.id.tv_time, TimeUtils.timeTransGBToCN(item.getLectureTime()));
                        holder.setText(R.id.tv_company, item.getOrgName());
                        holder.setText(R.id.tv_type, item.getSubcategory());
                        holder.setText(R.id.tv_work_place, item.getProvince());
                        holder.setText(R.id.tv_info, item.getCategory());
                        if (item.getAdmissionFee() == 0) {
                            holder.setText(R.id.tv_price, context.getString(R.string.video_free));
                        } else {
                            holder.setText(R.id.tv_price, String.valueOf("JPY" + String.format("%.2f", item.getAdmissionFee())));
                        }
                        if (isCollect && item.isEdit()) {
                            holder.setVisible(R.id.iv_delete, true);
                        } else {
                            holder.setVisible(R.id.iv_delete, false);
                        }
                        holder.setOnClickListener(R.id.iv_delete, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                delete.onClick(position, item.getId());
                            }
                        });
                        holder.setText(R.id.tv_browse_num, String.valueOf(context.getString(R.string.information_viewnum) + item.getPageViewNum()));
                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + item.getCoverUrl(),
                                (ImageView) holder.getView(R.id.iv_img), ImageLoaderUtils.getImageOptions(R.drawable.magazine_default));
                        holder.setOnClickListener(R.id.container, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = null;
                                if (!isCollect) {
                                    intent = new Intent(context,
                                            ChairPayActivity.class);
                                    intent.putExtra("data", item);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                } else {

                                    intent = new Intent(context,
                                            com.bizaia.zhongyin.module.discovery.ui.chair.detail.DetailActivity.class);
                                    intent.putExtra("url", item.getH5Url());
                                    intent.putExtra("title", item.getTitle());
                                    intent.putExtra("id", item.getId());
                                    intent.putExtra("orgId", item.getOrgId());
                                    intent.putExtra("type", 5);
                                    if (item.getAdmissionFee() == 0) {
                                        intent.putExtra("isPay", true);
                                    }
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                }
                            }
                        });
                    }
                })
                //  live
                .addAdapterItem(new CustomAdapterItem<ViewHolder, BuyLiveBean.DataEntity.DatasEntity>() {
                    @Override
                    public Class dataType() {
                        return BuyLiveBean.DataEntity.DatasEntity.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.item_live_recom);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final BuyLiveBean.DataEntity.DatasEntity
                            item, int position) {
                        holder.setText(R.id.tvName, item.getOrganizationName());
                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + item.getOrganizationLogo(),
                                (ImageView) holder.getView(R.id.ivIcon), ImageLoaderUtils.getImageHighQualityOptions());
                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + item.getCoverUrl(),
                                (ImageView) holder.getView(R.id.iv_organization_cover), ImageLoaderUtils.getImageHighQualityOptions());
                        if ("0".equals(item.getStatus())) {
                            holder.setBackgroundRes(R.id.ivTag, R.drawable.tag_03);
                            holder.setText(R.id.ivTag, context.getResources().getString(R.string.live_tag_foreshow));
                            holder.setText(R.id.tvNumber, context.getString(R.string.bought_count) + item.getBuyNum());
                            holder.setBackgroundColor(R.id.viewTagTop, R.color.cr_307f11);
                            holder.setText(R.id.tvTime, TimeUtils.timeTransGBToCN(item.getStartTime()) + TimeUtils.getWeekDateTwo(item.getStartTime()));
                        } else if ("1".equals(item.getStatus())) {
                            holder.setBackgroundRes(R.id.ivTag, R.drawable.tag_01);
                            holder.setText(R.id.ivTag, context.getResources().getString(R.string.live_tag_live));
                            holder.setText(R.id.tvTime, context.getString(R.string.bought_count) + item.getBuyNum());
                            holder.setText(R.id.tvNumber, context.getString(R.string.live_player_nums) + item.getPageViewNum());
                            holder.setBackgroundColor(R.id.viewTagTop, R.color.cr_443cb2);
                        } else if ("2".equals(item.getStatus())) {
                            holder.setBackgroundRes(R.id.ivTag, R.drawable.tag_02);
                            holder.setText(R.id.ivTag, context.getResources().getString(R.string.live_tag_end));
                            holder.setBackgroundColor(R.id.viewTagTop, R.color.cr_6b2c93);
                        }
                        if (item.getPrice() != 0) {
                            holder.setText(R.id.tvPrice, "JPY" + String.format("%.2f", item.getPrice()));
                        } else {
                            holder.setText(R.id.tvPrice, context.getString(R.string.video_free));
                        }
                        if (0 == item.getPrice()) {
                            holder.setVisible(R.id.tvFree, false);
                        } else {
                            holder.setVisible(R.id.tvFree, true);
                        }
                        holder.setText(R.id.tvTitle, item.getTitle());
                        holder.setText(R.id.tvType, item.getCateName());
                        holder.setText(R.id.tvTeacher, context.getString(R.string.live_teacher) + item.getLecturer());

                        holder.setOnClickListener(R.id.linContent, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, LiveDetailActivity.class);
                                intent.putExtra("id", item.getId());
                                intent.putExtra("roomNub", item.getChatroomId());
                                intent.putExtra("liveType", Integer.parseInt(item.getStatus()));
                                intent.putExtra("coursewareStreamAddress", item.getCoursewareStreamAddress());
                                intent.putExtra("cameraStreamAddress", item.getCameraStreamAddress());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });
                    }
                })
                // video
                .addAdapterItem(new CustomAdapterItem<ViewHolder, BuyVideoBean.DataEntity.DatasEntity>() {
                    @Override
                    public Class dataType() {
                        return BuyVideoBean.DataEntity.DatasEntity.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.fragment_video_item_recently);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final BuyVideoBean.DataEntity.DatasEntity
                            item, final int position) {

                        String imgPath = item.getCoverUrl();

                        if (imgPath!=null&&imgPath.startsWith("http")) {
                            ImageLoader.getInstance().displayImage(imgPath,
                                    (ImageView) holder.getView(R.id.iv_organization_cover), ImageLoaderUtils.getImageHighQualityOptions());

                            ImageLoader.getInstance().displayImage(imgPath,
                                    (ImageView) holder.getView(R.id.iv_head), ImageLoaderUtils.getImageHighQualityOptions());
                        } else {
                            ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + imgPath,
                                    (ImageView) holder.getView(R.id.iv_organization_cover), ImageLoaderUtils.getImageHighQualityOptions());

                            ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + imgPath,
                                    (ImageView) holder.getView(R.id.iv_head), ImageLoaderUtils.getImageHighQualityOptions());
                        }

                        if (item.getType() == 1) {
                            holder.setImageResource(R.id.iv_tag, R.drawable.tag_01);
                            holder.setText(R.id.tv_tag, context.getResources().getString(R.string.video_look_back));
                        } else if (item.getType() == 2) {
                            holder.setImageResource(R.id.iv_tag, R.drawable.tag_02);
                            holder.setText(R.id.tv_tag, context.getResources().getString(R.string.video_video));
                        } else if (item.getType() == 3) {
                            holder.setImageResource(R.id.iv_tag, R.drawable.tag_03);
                            holder.setText(R.id.tv_tag, context.getResources().getString(R.string.video_audio));
                        }
                        if (isCollect && item.isEdit()) {
                            holder.setVisible(R.id.iv_delete, true);
                        } else {
                            holder.setVisible(R.id.iv_delete, false);
                        }

                        holder.setOnClickListener(R.id.iv_delete, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                delete.onClick(position, item.getId());
                            }
                        });
                        if (!StringUtils.isEmpty(item.getExpireDate())) {
                            holder.setVisible(R.id.tvLimit, true);
                            holder.setText(R.id.tvLimit, item.getExpireDate() + context.getString(R.string.video_sold));
                            holder.setVisible(R.id.tvSold, true);
                            holder.setText(R.id.tvSold,
                                    context.getString(R.string.provide_look) + "    ");
                        } else {
                            holder.setVisible(R.id.tvLimit, false);
                        }
                        if (item.getPrice() != 0) {
                            holder.setVisible(R.id.tvSold, true);
                            holder.setText(R.id.tv_price, "JPY" + String.format("%.2f", item.getPrice()));
                            holder.setText(R.id.tv_num_size, String.valueOf(context.getString(R.string.bought_count) + item.getBuyNum()));
                        } else {
                            holder.setText(R.id.tv_price, context.getString(R.string.video_free));
                            holder.setVisible(R.id.tvSold, false);
                            holder.setText(R.id.tv_num_size, String.valueOf(context.getString(R.string.bought_count) + "なし"));
                        }
                        holder.setText(R.id.tv_name, item.getOrgName());
                        holder.setText(R.id.tv_info, String.valueOf(context.getString(R.string.bought_count) + item.getBuyNum()));
                        holder.setText(R.id.tv_info_name, item.getTitle());
                        holder.setText(R.id.tv_org_name, item.getSubcategory());
                        holder.setText(R.id.tv_talker, String.valueOf(context.getString(R.string.jz_talker) + item.getLecturers()));

                        holder.setOnClickListener(R.id.container, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                context.startActivity(new Intent(context,
                                        com.bizaia.zhongyin.module.video.ui.detail.DetailActivity.class)
                                        .putExtra("title", item.getTitle())
                                        .putExtra("videoId", item.getId())
                                        .putExtra("imgUrl", item.getCoverUrl()));
                            }
                        });

                    }
                })
                //  magazine
                .addAdapterItem(new CustomAdapterItem<ViewHolder, BuyMonthlyBean.DataEntity.DatasEntity>() {
                    @Override
                    public Class dataType() {
                        return BuyMonthlyBean.DataEntity.DatasEntity.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.fragment_monthly_item_newest_2btn);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final BuyMonthlyBean.DataEntity.DatasEntity
                            item, int position) {


                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + item.getCoverUrl()
                                , (ImageView) holder.getView(R.id.iv_monthly_cover)
                                , ImageLoaderUtils.getImageOptions(R.drawable.magazine_default));
                        holder.setVisible(R.id.ll_label1, false);
                        holder.setVisible(R.id.ll_label2, false);
                        holder.setText(R.id.tv_time, item.getTitle());
                        holder.setText(R.id.tv_price, "JPY" + String.format("%.2f", item.getPrice()));
                        holder.setText(R.id.tv_introduce, item.getDescription());
                        holder.setTag(R.id.monthly_container, item);
                        holder.setOnClickListener(R.id.monthly_container, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MonthlyJSData.DataBean.MonthlyNewestBean data = new MonthlyJSData.DataBean.MonthlyNewestBean();
                                data.setChapterNum(item.getChapterNum());
                                data.setCoverUrl(item.getCoverUrl());
                                data.setCreateTime(item.getCreateTime());
                                data.setDescription(item.getDescription());
                                data.setFileUrl(item.getFileUrl());
                                data.setId(item.getId());
                                data.setPrice(item.getPrice());
                                data.setTitle(item.getTitle());
                                data.setH5Url(item.getShareUrl());
                                context.startActivity(new Intent(context, PDFDetailActivity.class)
                                        .putExtra("MonthlyJSData", data)
                                        .addFlags(FLAG_ACTIVITY_NEW_TASK));
                            }
                        });
                        holder.setOnClickListener(R.id.tv_js, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.e("onClick", "onClick: " + v.getTag());
                                MonthlyJSData.DataBean.MonthlyNewestBean data = new MonthlyJSData.DataBean.MonthlyNewestBean();
                                data.setChapterNum(item.getChapterNum());
                                data.setCoverUrl(item.getCoverUrl());
                                data.setCreateTime(item.getCreateTime());
                                data.setDescription(item.getDescription());
                                data.setFileUrl(item.getFileUrl());
                                data.setId(item.getId());
                                data.setPrice(item.getPrice());
                                data.setTitle(item.getTitle());
                                data.setH5Url(item.getShareUrl());
                                context.startActivity(new Intent(context, JSActivity.class)
                                        .putExtra("id", item.getId())
                                        .putExtra("shareUrl", item.getShareUrl())
                                        .putExtra("filePath",item.getFileUrl())
                                        .putExtra("month",data)
                                        .addFlags(FLAG_ACTIVITY_NEW_TASK));
                            }
                        });
                    }
                })
                // Lecture
                .addAdapterItem(new CustomAdapterItem<ViewHolder, CollectLecturesBean.DataEntity.DatasEntity>() {
                    @Override
                    public Class dataType() {
                        return CollectLecturesBean.DataEntity.DatasEntity.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.fragment_discovery_item_chair);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final CollectLecturesBean.DataEntity.DatasEntity
                            item, final int position) {
                        holder.setText(R.id.rv_recently_title, item.getTitle());
                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + item.getCoverUrl(),
                                (ImageView) holder.getView(R.id.iv_recently_img), ImageLoaderUtils.getImageOptions());
                        holder.setText(R.id.tv_news_detail,
                                TimeUtils.timeTransGBToCN(item.getLectureTime()) + "\n"
                                        + item.getCategory() + "•" + item.getSubcategory() + "\n"
                                        + item.getProvince() + "\n"
                                        + item.getAdmissionFee() + "\n"
                                        + item.getOrgName());
                        holder.setOnClickListener(R.id.container, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context,
                                        com.bizaia.zhongyin.module.discovery.ui.chair.detail.DetailActivity.class);
                                intent.putExtra("url", item.getH5Url());
                                intent.putExtra("title", item.getTitle());
                                intent.putExtra("id", item.getId());
                                intent.putExtra("type", 1);
                                intent.putExtra("orgId", item.getOrgId());

                                intent.putExtra("h5", item.getH5Url());
                                intent.putExtra("title", item.getTitle());
                                intent.putExtra("cover", item.getCoverUrl());
                                intent.putExtra("des", item.getTitle());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                    }
                })
                // associ
                .addAdapterItem(new CustomAdapterItem<ViewHolder, CollectAssociationBean.DataEntity.DatasEntity>() {
                    @Override
                    public Class dataType() {
                        return CollectAssociationBean.DataEntity.DatasEntity.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.fragment_discovery_item_organization);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final CollectAssociationBean.DataEntity.DatasEntity
                            item, final int position) {
                        View cover = holder.getView(R.id.iv_organization_cover);
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) cover.getLayoutParams();
                        layoutParams.height = (int) (SizeUtils.getFullScreenSize(context)[0] * (float) 360 / (float) 720);
                        cover.setLayoutParams(layoutParams);
                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + item.getCoverUrl(),
                                (ImageView) cover, ImageLoaderUtils.getImageOptions());
                        holder.setText(R.id.tv_title, item.getTitle());

                        holder.setOnClickListener(R.id.container, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context,
                                        DetailActivity.class);
                                intent.putExtra("url", item.getH5Url());
                                intent.putExtra("title", item.getTitle());
                                intent.putExtra("id", item.getId());
                                intent.putExtra("type", 6);
//                                intent.putExtra("orgId",item.getOrgId());

                                intent.putExtra("h5", item.getH5Url());
                                intent.putExtra("title", item.getTitle());
                                intent.putExtra("cover", item.getCoverUrl());
                                intent.putExtra("des", item.getTitle());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                    }
                })
                // recr
                .addAdapterItem(new CustomAdapterItem<ViewHolder, CollectRecruitBean.DataEntity.DatasEntity>() {
                    @Override
                    public Class dataType() {
                        return CollectRecruitBean.DataEntity.DatasEntity.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.fragment_discovery_item_jobs);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final CollectRecruitBean.DataEntity.DatasEntity
                            item, final int position) {
                        holder.setText(R.id.tv_jobs_title, item.getTitle());
                        holder.setText(R.id.tv_work_place, context.getString(R.string.work_place) + item.getWorkCountry() + "/" + item.getWorkCity());
                        holder.setText(R.id.tv_company, item.getCompany());
//                        holder.setText(R.id.tv_price, item.getSalery());
                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + item.getCoverUrl()
                                , (ImageView) holder.getView(R.id.iv_jobs_img)
                                , ImageLoaderUtils.getImageOptions());

                        holder.setOnClickListener(R.id.container, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context,
                                        DetailActivity.class);
                                intent.putExtra("url", item.getH5Url());
                                intent.putExtra("title", item.getTitle());
                                intent.putExtra("id", item.getId());
//                                intent.putExtra("orgId",item.getOrgId());
                                intent.putExtra("type", 7);

                                intent.putExtra("h5", item.getH5Url());
                                intent.putExtra("title", item.getTitle());
                                intent.putExtra("cover", item.getCoverUrl());
                                intent.putExtra("des", item.getTitle());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                    }
                })
                .addAdapterItem(new CustomAdapterItem<ViewHolder, String>() {
                    @Override
                    public Class dataType() {
                        return String.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.item_sub_monthly_detail);
                    }

                    @Override
                    public void bindData(ViewHolder holder, String
                            item, final int position) {
                        holder.setOnClickListener(R.id.tvSubAll, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                onClick.onClick();
                            }
                        });

                    }
                })
                ;
    }

}
