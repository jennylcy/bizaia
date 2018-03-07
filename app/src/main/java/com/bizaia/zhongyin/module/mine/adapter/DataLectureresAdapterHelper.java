package com.bizaia.zhongyin.module.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.common.adapter.StateAdapterHelper;
import com.bizaia.zhongyin.module.discovery.data.RecentlyNewsData;
import com.bizaia.zhongyin.module.discovery.ui.chair.detail.DetailActivity;
import com.bizaia.zhongyin.module.live.data.LecturerListEntity;
import com.bizaia.zhongyin.module.mine.data.LectureresBean;
import com.bizaia.zhongyin.module.video.data.RecentlyVideoData;
import com.bizaia.zhongyin.repository.AddressConfiguration;
import com.bizaia.zhongyin.util.ImageLoaderUtils;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.TimeUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.CustomAdapterItem;
import com.bizaia.zhongyin.widget.adapter.ViewHolder;
import com.bizaia.zhongyin.widget.refresh.PtrClassicFrameLayout;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by yan on 2017/2/28.
 */

public final class DataLectureresAdapterHelper extends StateAdapterHelper {
    private Context context;

    public CustomAdapter getAdapter(final Context context, List list, final PtrClassicFrameLayout ptrClassicFrameLayout) {
        this.context = context;
        return super.getAdapter(context, list)
                .addAdapterItem(new CustomAdapterItem<ViewHolder, LectureresBean.DataEntity.DatasEntity>() {
                    @Override
                    public Class dataType() {
                        return LectureresBean.DataEntity.DatasEntity.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.item_company_lecturer);
                    }

                    @Override
                    public void bindData(ViewHolder holder, LectureresBean.DataEntity.DatasEntity
                            item, int position) {
                        holder.setText(R.id.tvLecturerName, item.getName());
                        String imgPath = item.getAvatarUrl();
                        if (imgPath.startsWith("http")) {
                            ImageLoader.getInstance().displayImage(imgPath,
                                    (ImageView) holder.getView(R.id.ivLectureres), ImageLoaderUtils.getImageOptions());
                        } else {
                            ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + imgPath,
                                    (ImageView) holder.getView(R.id.ivLectureres), ImageLoaderUtils.getImageOptions());
                        }
                        holder.setText(R.id.tvJob, item.getOrgName() + "  " + item.getJob());
                        holder.setText(R.id.tvLecIntroduction, item.getIntroduction());
                    }
                })
                //--------------------------- news ----------------------------
                .addAdapterItem(new CustomAdapterItem<ViewHolder, RecentlyNewsData.DataBean.ListDataBean.DatasBean>() {
                    @Override
                    public Class dataType() {
                        return RecentlyNewsData.DataBean.ListDataBean.DatasBean.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.fragment_chair_item);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final RecentlyNewsData.DataBean.ListDataBean.DatasBean
                            item, int position) {
                        holder.setText(R.id.tv_title, item.getTitle());
                        holder.setText(R.id.tv_time, TimeUtils.timeTransGBToCN(item.getLectureTime()));
                        holder.setText(R.id.tv_company, item.getOrgName());
                        holder.setText(R.id.tv_type, item.getSubcategory());
                        holder.setText(R.id.tv_work_place, item.getProvince());
                        holder.setText(R.id.tv_info, item.getCategory());
                        if (item.getAdmissionFee() != 0) {
                            holder.setText(R.id.tv_price, String.valueOf("JPY" + String.format("%.2f", item.getAdmissionFee())));
                        } else {
                            holder.setText(R.id.tv_price, context.getString(R.string.video_free));
                        }
                        holder.setText(R.id.tv_browse_num, String.valueOf(context.getString(R.string.information_viewnum) + item.getPageViewNum()));
                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + item.getCoverUrl(),
                                (ImageView) holder.getView(R.id.iv_img), ImageLoaderUtils.getImageOptions(R.drawable.magazine_default));
                        holder.setOnClickListener(R.id.container, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context,
                                        DetailActivity.class);
                                intent.putExtra("url", item.getH5Url());
                                intent.putExtra("title", item.getTitle());
                                intent.putExtra("id", item.getId());
                                intent.putExtra("orgId", item.getOrgId());
                                intent.putExtra("type", 5);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                    }
                })
                //--------------------------- video ----------------------------
                .addAdapterItem(new CustomAdapterItem<ViewHolder, RecentlyVideoData.DataBean.ListDataBean.DatasBean>() {
                    @Override
                    public Class dataType() {
                        return RecentlyVideoData.DataBean.ListDataBean.DatasBean.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.fragment_video_item_recently);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final RecentlyVideoData.DataBean.ListDataBean.DatasBean
                            item, final int position) {
                        String imgPath = item.getCoverUrl();
                        if (imgPath.startsWith("http")) {
                            ImageLoader.getInstance().displayImage(imgPath,
                                    (ImageView) holder.getView(R.id.iv_organization_cover), ImageLoaderUtils.getImageOptions(R.drawable.news_default));

                            ImageLoader.getInstance().displayImage(item.getAvatarUrl(),
                                    (ImageView) holder.getView(R.id.iv_head), ImageLoaderUtils.getImageHighQualityOptions());
                        } else {
                            ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + imgPath,
                                    (ImageView) holder.getView(R.id.iv_organization_cover), ImageLoaderUtils.getImageOptions(R.drawable.news_default));

                            ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + item.getAvatarUrl(),
                                    (ImageView) holder.getView(R.id.iv_head), ImageLoaderUtils.getImageHighQualityOptions());
                        }

                        if (item.getType() == 1) {
                            holder.setImageResource(R.id.iv_tag, R.drawable.tag_01);
                            holder.setText(R.id.tv_tag, context.getResources().getString(R.string.video_look_back));
                            holder.setBackgroundColor(R.id.viewTagTop, R.color.cr_443cb2);
                        } else if (item.getType() == 2) {
                            holder.setImageResource(R.id.iv_tag, R.drawable.tag_02);
                            holder.setText(R.id.tv_tag, context.getResources().getString(R.string.video_video));
                            holder.setBackgroundColor(R.id.viewTagTop, R.color.cr_6b2c93);
                        } else if (item.getType() == 3) {
                            holder.setImageResource(R.id.iv_tag, R.drawable.tag_03);
                            holder.setText(R.id.tv_tag, context.getResources().getString(R.string.video_audio));
                            holder.setBackgroundColor(R.id.viewTagTop, R.color.cr_307f11);
                        }
                        if (!StringUtils.isEmpty(item.getExpireDate())) {
                            holder.setVisible(R.id.tvLimit, true);
                            holder.setText(R.id.tvLimit, item.getExpireDate() + context.getString(R.string.video_sold));
                            holder.setVisible(R.id.tvSold, true);
                            holder.setText(R.id.tvSold,
                                    context.getString(R.string.provide_look) + "    ");
                        } else {
                            holder.setVisible(R.id.tvLimit, false);
                        }
                        holder.setText(R.id.tv_name, item.getOrgName());
                        if (item.getPrice() != 0) {
                            holder.setVisible(R.id.tvSold, true);
                            holder.setText(R.id.tv_price, "JPY" + String.format("%.2f", item.getPrice()));
                            holder.setText(R.id.tv_num_size, String.valueOf(context.getString(R.string.bought_count) + item.getBuyNum()));
                        } else {
                            holder.setText(R.id.tv_price, context.getString(R.string.video_free));
                            holder.setVisible(R.id.tvSold, false);
                            holder.setText(R.id.tv_num_size, String.valueOf(context.getString(R.string.bought_count) + "なし"));
                        }
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
                                        .putExtra("cover", item.getCoverUrl()));
                            }
                        });

                    }
                })
                //  live teacher list
                .addAdapterItem(new CustomAdapterItem<ViewHolder, LecturerListEntity>() {
                    @Override
                    public Class dataType() {
                        return LecturerListEntity.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.item_live_lecture);
                    }

                    @Override
                    public void bindData(ViewHolder holder, LecturerListEntity
                            item, int position) {
                        String imgPath = item.getAvatarUrl();
                        if (imgPath.startsWith("http")) {
                            ImageLoader.getInstance().displayImage(imgPath,
                                    (ImageView) holder.getView(R.id.ivLectureres), ImageLoaderUtils.getImageHighQualityOptions());
                        } else {
                            ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + imgPath,
                                    (ImageView) holder.getView(R.id.ivLectureres), ImageLoaderUtils.getImageHighQualityOptions());
                        }

                        holder.setText(R.id.tvLecturerName, item.getName());
                        holder.setText(R.id.orgName, item.getOrgName());
                        holder.setText(R.id.tvJob, item.getJob());
                        holder.setText(R.id.tvLecIntroduction, item.getIntroduction());

                        Log.d("TAG", "to string : " + item.toString());
                    }
                });
    }

}
