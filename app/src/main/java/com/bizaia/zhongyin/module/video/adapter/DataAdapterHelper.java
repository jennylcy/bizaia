package com.bizaia.zhongyin.module.video.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.common.adapter.BannerAdapterItem;
import com.bizaia.zhongyin.module.common.adapter.StateAdapterHelper;
import com.bizaia.zhongyin.module.common.data.SearchFixedData;
import com.bizaia.zhongyin.module.common.data.SendSearchDataToSearchAct;
import com.bizaia.zhongyin.module.common.ui.SearchFixedView;
import com.bizaia.zhongyin.module.video.data.BannerRecentlyData;
import com.bizaia.zhongyin.module.video.data.BannerRecommendData;
import com.bizaia.zhongyin.module.video.data.DetailSubItemData;
import com.bizaia.zhongyin.module.video.data.RecentlySearchVideoData;
import com.bizaia.zhongyin.module.video.data.RecentlyVideoData;
import com.bizaia.zhongyin.module.video.data.RecommendVideoData;
import com.bizaia.zhongyin.module.video.ui.detail.DetailActivity;
import com.bizaia.zhongyin.repository.AddressConfiguration;
import com.bizaia.zhongyin.util.ImageLoaderUtils;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.SizeUtils;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.CustomAdapterItem;
import com.bizaia.zhongyin.widget.adapter.ViewHolder;
import com.bizaia.zhongyin.widget.banner.Banner;
import com.bizaia.zhongyin.widget.refresh.PtrClassicFrameLayout;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by yan on 2017/2/28.
 */

public final class DataAdapterHelper extends StateAdapterHelper {
    private Context context;

    public CustomAdapter getAdapter(final Context context, List list) {
        return getAdapter(context, list, null);
    }

    public CustomAdapter getAdapter(final Context context, List list, final PtrClassicFrameLayout ptrClassicFrameLayout) {
        this.context = context;
        return super.getAdapter(context, list)

                //--------------------------- banner recommend ----------------------------
                .addAdapterItem(new BannerAdapterItem<BannerRecommendData>(context, ptrClassicFrameLayout) {
                    @Override
                    public Class dataType() {
                        return BannerRecommendData.class;
                    }

                    @Override
                    public void imgShow(ImageView imageView, Object imgPath) {
                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH +
                                        ((RecommendVideoData.DataBean.FocusDataBean) imgPath).getCoverUrl()
                                , imageView, ImageLoaderUtils.getImageOptions(R.drawable.banner_default_img));
                    }

                    @Override
                    public void bindData(ViewHolder holder, final BannerRecommendData
                            item, int position) {
                        banner.setDataSource(item.getFocusDataBeen());
                        banner.attachIndicator(bannerIndicator);
                        banner.resumeScroll();
                        banner.setOnBannerItemClickListener(new Banner.OnBannerItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                if (item.getFocusDataBeen().get(position).getRecommendType() != 3) {
                                    context.startActivity(new Intent(context,
                                            DetailActivity.class)
                                            .putExtra("title", item.getFocusDataBeen().get(position).getTitle())
                                            .putExtra("videoId", item.getFocusDataBeen().get(position).getId())
                                            .putExtra("cover", item.getFocusDataBeen().get(position).getCoverUrl()));
                                } else {
                                    Intent intent = new Intent();
                                    intent.setAction("android.intent.action.VIEW");
                                    Uri url = Uri.parse(item.getFocusDataBeen().get(position).getH5Url());
                                    intent.setData(url);
                                    context.startActivity(intent);
                                }
                            }
                        });
                    }
                })

                //--------------------------- banner recently ----------------------------
                .addAdapterItem(new BannerAdapterItem<BannerRecentlyData>(context, ptrClassicFrameLayout) {
                    @Override
                    public Class dataType() {
                        return BannerRecentlyData.class;
                    }

                    @Override
                    public void imgShow(ImageView imageView, Object imgPath) {
                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH +
                                        ((RecentlyVideoData.DataBean.FocusDataBean) imgPath).getCoverUrl()
                                , imageView, ImageLoaderUtils.getImageOptions(R.drawable.banner_default_img));
                    }

                    @Override
                    public void bindData(ViewHolder holder, final BannerRecentlyData
                            item, int position) {
                        banner.setDataSource(item.getFocusDataBeen());
                        banner.attachIndicator(bannerIndicator);
                        banner.resumeScroll();
                        banner.setOnBannerItemClickListener(new Banner.OnBannerItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                if (item.getFocusDataBeen().get(position).getRecommendType() != 3) {
                                    context.startActivity(new Intent(context,
                                            DetailActivity.class)
                                            .putExtra("title", item.getFocusDataBeen().get(position).getTitle())
                                            .putExtra("videoId", item.getFocusDataBeen().get(position).getId())
                                            .putExtra("cover", item.getFocusDataBeen().get(position).getCoverUrl()));
                                } else {
                                    Intent intent = new Intent();
                                    intent.setAction("android.intent.action.VIEW");
                                    Uri url = Uri.parse(item.getFocusDataBeen().get(position).getH5Url());
                                    intent.setData(url);
                                    context.startActivity(intent);
                                }
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
                    public void bindData(ViewHolder holder, RecentlyVideoData.DataBean.ListDataBean.DatasBean
                            item, int position) {
                        holder.setText(R.id.tv_name, item.getTitle());

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
                        } else if (item.getType() == 2) {
                            holder.setImageResource(R.id.iv_tag, R.drawable.tag_02);
                            holder.setText(R.id.tv_tag, context.getResources().getString(R.string.video_video));
                        } else if (item.getType() == 3) {
                            holder.setImageResource(R.id.iv_tag, R.drawable.tag_03);
                            holder.setText(R.id.tv_tag, context.getResources().getString(R.string.video_audio));
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
                    }
                })
                // search
                .addAdapterItem(new CustomAdapterItem<ViewHolder, RecentlySearchVideoData.DataEntity.DatasEntity>() {
                    @Override
                    public Class dataType() {
                        return RecentlySearchVideoData.DataEntity.DatasEntity.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.fragment_video_item_recently);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final RecentlySearchVideoData.DataEntity.DatasEntity
                            item, final int position) {
                        holder.setText(R.id.tv_name, item.getTitle());

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
                        } else if (item.getType() == 2) {
                            holder.setImageResource(R.id.iv_tag, R.drawable.tag_02);
                            holder.setText(R.id.tv_tag, context.getResources().getString(R.string.video_video));
                        } else if (item.getType() == 3) {
                            holder.setImageResource(R.id.iv_tag, R.drawable.tag_03);
                            holder.setText(R.id.tv_tag, context.getResources().getString(R.string.video_audio));
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
                                        DetailActivity.class)
                                        .putExtra("title", item.getTitle())
                                        .putExtra("videoId", item.getId())
                                        .putExtra("cover", item.getCoverUrl())
                                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                            }
                        });
                    }
                })

                //--------------------------- recommend ----------------------------
                .addAdapterItem(new CustomAdapterItem<ViewHolder, RecommendVideoData.DataBean.ListDataBean.DatasBean>() {
                    @Override
                    public Class dataType() {
                        return RecommendVideoData.DataBean.ListDataBean.DatasBean.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        View view = LayoutInflater.from(context).inflate(R.layout.fragment_video_item_recently, parent, false);

                        View cover = view.findViewById(R.id.iv_organization_cover);
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) cover.getLayoutParams();
                        layoutParams.height = (int) (SizeUtils.getFullScreenSize(context)[0] * (float) 450 / (float) 720);
                        cover.setLayoutParams(layoutParams);

                        return ViewHolder.createViewHolder(context, view);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final RecommendVideoData.DataBean.ListDataBean.DatasBean
                            item, int position) {
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
                        } else if (item.getType() == 2) {
                            holder.setImageResource(R.id.iv_tag, R.drawable.tag_02);
                            holder.setText(R.id.tv_tag, context.getResources().getString(R.string.video_video));
                        } else if (item.getType() == 3) {
                            holder.setImageResource(R.id.iv_tag, R.drawable.tag_03);
                            holder.setText(R.id.tv_tag, context.getResources().getString(R.string.video_audio));
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
                                        DetailActivity.class)
                                        .putExtra("title", item.getTitle())
                                        .putExtra("videoId", item.getId())
                                        .putExtra("cover", item.getCoverUrl()));
                            }
                        });
                    }
                })

                //--------------------------- detail sub item ----------------------------
                .addAdapterItem(new CustomAdapterItem<ViewHolder, DetailSubItemData>() {
                    @Override
                    public Class dataType() {
                        return DetailSubItemData.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.fragment_video_detail_sub);
                    }

                    @Override
                    public void bindData(ViewHolder holder, DetailSubItemData
                            item, int position) {
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
    }
}
