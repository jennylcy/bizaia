package com.bizaia.zhongyin.module.discovery.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.bizaia.zhongyin.module.discovery.data.AssociationData;
import com.bizaia.zhongyin.module.discovery.data.BannerAssociationData;
import com.bizaia.zhongyin.module.discovery.data.BannerLectureData;
import com.bizaia.zhongyin.module.discovery.data.BannerRecentlyData;
import com.bizaia.zhongyin.module.discovery.data.BannerRecommendData;
import com.bizaia.zhongyin.module.discovery.data.BannerRecruitData;
import com.bizaia.zhongyin.module.discovery.data.LectureData;
import com.bizaia.zhongyin.module.discovery.data.RecentlyNewsData;
import com.bizaia.zhongyin.module.discovery.data.RecommendNewsData;
import com.bizaia.zhongyin.module.discovery.data.RecruitData;
import com.bizaia.zhongyin.module.discovery.ui.chair.detail.DetailActivity;
import com.bizaia.zhongyin.repository.AddressConfiguration;
import com.bizaia.zhongyin.util.ImageLoaderUtils;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.SizeUtils;
import com.bizaia.zhongyin.util.TimeUtils;
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
    private static final String TAG = "DataAdapterHelper";
    private Context context;
    private Banner banner;

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
                        ImageLoader.getInstance().displayImage(
                                AddressConfiguration.MAIN_PATH + ((RecommendNewsData.DataBean.FocusDataBean) imgPath).getCoverUrl()
                                , imageView, ImageLoaderUtils.getImageOptions(R.drawable.banner_default_img));
                    }

                    @Override
                    public void bindData(ViewHolder holder, final BannerRecommendData
                            item, int position) {
                        banner.setDataSource(item.getFocusDataBeen());
                        banner.attachIndicator(bannerIndicator);
                        DataAdapterHelper.this.banner = banner;
                        banner.resumeScroll();
                        banner.setOnBannerItemClickListener(new Banner.OnBannerItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Intent intent = new Intent(context,
                                        DetailActivity.class);
                                intent.putExtra("url", item.getFocusDataBeen().get(position).getH5Url());
                                intent.putExtra("id", item.getFocusDataBeen().get(position).getId());
                                int type = item.getFocusDataBeen().get(position).getFindType();
                                if (type == 1) {
                                    intent.putExtra("type", 5);
                                } else if (type == 2) {
                                    intent.putExtra("type", 6);
                                } else if (type == 3) {
                                    intent.putExtra("type", 7);
                                } else if (type == 4) {
                                    intent.putExtra("type", 1);
                                }
                                intent.putExtra("orgId", item.getFocusDataBeen().get(position).getAdminId());
                                intent.putExtra("h5", item.getFocusDataBeen().get(position).getH5Url());
                                intent.putExtra("title", item.getFocusDataBeen().get(position).getTitle());
                                intent.putExtra("cover", item.getFocusDataBeen().get(position).getCoverUrl());
                                intent.putExtra("des", item.getFocusDataBeen().get(position).getTitle());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
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
                        ImageLoader.getInstance().displayImage(
                                AddressConfiguration.MAIN_PATH + ((RecentlyNewsData.DataBean.FocusDataBean) imgPath).getCoverUrl()
                                , imageView, ImageLoaderUtils.getImageOptions(R.drawable.banner_default_img));
                    }

                    @Override
                    public void bindData(ViewHolder holder, BannerRecentlyData
                            item, int position) {
                        banner.setDataSource(item.getFocusDataBeen());
                        banner.attachIndicator(bannerIndicator);
                        banner.resumeScroll();
                    }
                })

                //--------------------------- banner association ----------------------------
                .addAdapterItem(new BannerAdapterItem<BannerAssociationData>(context, ptrClassicFrameLayout) {
                    @Override
                    public Class dataType() {
                        return BannerAssociationData.class;
                    }

                    @Override
                    public void imgShow(ImageView imageView, Object imgPath) {
                        ImageLoader.getInstance().displayImage(
                                AddressConfiguration.MAIN_PATH + ((AssociationData.DataBean.FocusDataBean) imgPath).getCoverUrl()
                                , imageView, ImageLoaderUtils.getImageOptions(R.drawable.banner_default_img));
                    }

                    @Override
                    public void bindData(ViewHolder holder, final BannerAssociationData
                            item, int position) {
                        banner.setDataSource(item.getFocusDataBeen());
                        banner.attachIndicator(bannerIndicator);
                        banner.resumeScroll();
                        banner.setOnBannerItemClickListener(new Banner.OnBannerItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Intent intent = new Intent(context,
                                        DetailActivity.class);
                                intent.putExtra("url", item.getFocusDataBeen().get(position).getH5Url());
                                intent.putExtra("id", item.getFocusDataBeen().get(position).getId());
                                int type = item.getFocusDataBeen().get(position).getFindType();
                                if (type == 1) {
                                    intent.putExtra("type", 5);
                                } else if (type == 2) {
                                    intent.putExtra("type", 6);
                                } else if (type == 3) {
                                    intent.putExtra("type", 7);
                                } else if (type == 4) {
                                    intent.putExtra("type", 1);
                                }
                                intent.putExtra("orgId", item.getFocusDataBeen().get(position).getAdminId());
                                intent.putExtra("h5", item.getFocusDataBeen().get(position).getH5Url());
                                intent.putExtra("title", item.getFocusDataBeen().get(position).getTitle());
                                intent.putExtra("cover", item.getFocusDataBeen().get(position).getCoverUrl());
                                intent.putExtra("des", item.getFocusDataBeen().get(position).getTitle());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });
                    }
                })

                //--------------------------- banner recruitData ----------------------------
                .addAdapterItem(new BannerAdapterItem<BannerRecruitData>(context, ptrClassicFrameLayout) {
                    @Override
                    public Class dataType() {
                        return BannerRecruitData.class;
                    }

                    @Override
                    public void imgShow(ImageView imageView, Object imgPath) {
                        ImageLoader.getInstance().displayImage(
                                AddressConfiguration.MAIN_PATH + ((RecruitData.DataBean.FocusDataBean) imgPath).getCoverUrl()
                                , imageView, ImageLoaderUtils.getImageOptions(R.drawable.banner_default_img));
                    }

                    @Override
                    public void bindData(ViewHolder holder, final BannerRecruitData
                            item, int position) {
                        banner.setDataSource(item.getFocusDataBeen());
                        banner.attachIndicator(bannerIndicator);
                        banner.resumeScroll();
                        banner.setOnBannerItemClickListener(new Banner.OnBannerItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Intent intent = new Intent(context,
                                        DetailActivity.class);
                                intent.putExtra("url", item.getFocusDataBeen().get(position).getH5Url());
                                intent.putExtra("id", item.getFocusDataBeen().get(position).getId());
                                int type = item.getFocusDataBeen().get(position).getFindType();
                                if (type == 1) {
                                    intent.putExtra("type", 5);
                                } else if (type == 2) {
                                    intent.putExtra("type", 6);
                                } else if (type == 3) {
                                    intent.putExtra("type", 7);
                                } else if (type == 4) {
                                    intent.putExtra("type", 1);
                                }
                                intent.putExtra("orgId", item.getFocusDataBeen().get(position).getAdminId());
                                intent.putExtra("h5", item.getFocusDataBeen().get(position).getH5Url());
                                intent.putExtra("title", item.getFocusDataBeen().get(position).getTitle());
                                intent.putExtra("cover", item.getFocusDataBeen().get(position).getCoverUrl());
                                intent.putExtra("des", item.getFocusDataBeen().get(position).getTitle());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });
                    }
                })

                //--------------------------- banner lectureData ----------------------------
                .addAdapterItem(new BannerAdapterItem<BannerLectureData>(context, ptrClassicFrameLayout) {
                    @Override
                    public Class dataType() {
                        return BannerLectureData.class;
                    }

                    @Override
                    public void imgShow(ImageView imageView, Object imgPath) {
                        ImageLoader.getInstance().displayImage(
                                AddressConfiguration.MAIN_PATH + ((LectureData.DataBean.FocusDataBean) imgPath).getCoverUrl()
                                , imageView, ImageLoaderUtils.getImageOptions(R.drawable.banner_default_img));
                    }

                    @Override
                    public void bindData(ViewHolder holder, final BannerLectureData
                            item, int position) {
                        banner.setDataSource(item.getFocusDataBeen());
                        banner.attachIndicator(bannerIndicator);
                        banner.resumeScroll();
                        banner.setOnBannerItemClickListener(new Banner.OnBannerItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Intent intent = new Intent(context,
                                        DetailActivity.class);
                                intent.putExtra("url", item.getFocusDataBeen().get(position).getH5Url());
                                intent.putExtra("id", item.getFocusDataBeen().get(position).getId());
                                int type = item.getFocusDataBeen().get(position).getFindType();
                                if (type == 1) {
                                    intent.putExtra("type", 5);
                                } else if (type == 2) {
                                    intent.putExtra("type", 6);
                                } else if (type == 3) {
                                    intent.putExtra("type", 7);
                                } else if (type == 4) {
                                    intent.putExtra("type", 1);
                                }
                                intent.putExtra("orgId", item.getFocusDataBeen().get(position).getAdminId());

                                intent.putExtra("h5", item.getFocusDataBeen().get(position).getH5Url());
                                intent.putExtra("title", item.getFocusDataBeen().get(position).getTitle());
                                intent.putExtra("cover", item.getFocusDataBeen().get(position).getCoverUrl());
                                intent.putExtra("des", item.getFocusDataBeen().get(position).getTitle());

                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);

                            }
                        });
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
                        return ViewHolder.createViewHolder(context, parent, R.layout.fragment_discovery_item_organization);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final RecentlyNewsData.DataBean.ListDataBean.DatasBean
                            item, int position) {
                        View cover = holder.getView(R.id.iv_organization_cover);
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) cover.getLayoutParams();
                        layoutParams.height = (int) (SizeUtils.getFullScreenSize(context)[0] * (float) 360 / (float) 720);
                        cover.setLayoutParams(layoutParams);
                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + item.getCoverUrl(),
                                (ImageView) cover, ImageLoaderUtils.getImageOptions(R.drawable.news_default));

                        holder.setText(R.id.tv_title, item.getTitle());
                        holder.setText(R.id.tv_time, item.getCreateTime());

                        holder.setOnClickListener(R.id.container, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context,
                                        DetailActivity.class);
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

//                //--------------------------- lecture ----------------------------
//                .addAdapterItem(new CustomAdapterItem<ViewHolder, LectureData.DataBean.ListDataBean.DatasBean>() {
//                    @Override
//                    public Class dataType() {
//                        return LectureData.DataBean.ListDataBean.DatasBean.class;
//                    }
//
//                    @Override
//                    public ViewHolder viewHolder(ViewGroup parent) {
//                        return ViewHolder.createViewHolder(context, parent, R.layout.fragment_discovery_item_chair);
//                    }
//
//                    @Override
//                    public void bindData(ViewHolder holder, final LectureData.DataBean.ListDataBean.DatasBean
//                            item, int position) {
//                        holder.setText(R.id.rv_recently_title, item.getTitle());
//                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + item.getCoverUrl(),
//                                (ImageView) holder.getView(R.id.iv_recently_img), ImageLoaderUtils.getImageOptions(R.drawable.magazine_default));
////                        holder.setText(R.id.tv_time,TimeUtils.getStandardTime(item.getCreateTime()));
//                        holder.setText(R.id.tv_news_detail,
//                                TimeUtils.getStandardTime(item.getLectureTime()) + "\n"
//                                        + item.getCategory() + "•" + item.getSubcategory() + "\n"
//                                        + item.getProvince() + "\n"
//                                        + item.getAdmissionFee() + "\n"
//                                        + item.getOrgName());
//                        holder.setOnClickListener(R.id.container, new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Intent intent = new Intent(context,
//                                        DetailActivity.class);
//                                intent.putExtra("url", item.getH5Url());
//                                intent.putExtra("title", item.getTitle());
//                                intent.putExtra("id", item.getId());
//                                intent.putExtra("orgId", item.getOrgId());
//                                intent.putExtra("type", 5);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                context.startActivity(intent);
//                            }
//                        });
//                    }
//                })

                //--------------------------- lecture ----------------------------
                .addAdapterItem(new CustomAdapterItem<ViewHolder, LectureData.DataBean.ListDataBean.DatasBean>() {
                    @Override
                    public Class dataType() {
                        return LectureData.DataBean.ListDataBean.DatasBean.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.fragment_chair_item);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final LectureData.DataBean.ListDataBean.DatasBean
                            item, int position) {
                        holder.setText(R.id.tv_title, item.getTitle());
                        holder.setText(R.id.tv_time, TimeUtils.timeTransGBToCN(item.getLectureTime()));
                        holder.setText(R.id.tv_company, item.getOrgName());
                        holder.setText(R.id.tv_type, item.getSubcategory());
                        holder.setText(R.id.tv_work_place, item.getProvince());
                        holder.setText(R.id.tv_info,item.getCategory());
                        if(item.getAdmissionFee()!=0) {
                            holder.setText(R.id.tv_price, "JPY" + String.format("%.2f",item.getAdmissionFee()));
                        }else{
                            holder.setText(R.id.tv_price, context.getString(R.string.video_free));
                        }
                        holder.setVisible(R.id.tvSubscribe,true);
                        if(item.getReserveStatus()==2){
                                holder.setBackgroundRes(R.id.tvSubscribe,R.drawable.tag_bg_fe5c04);
                                holder.setText(R.id.tvSubscribe,context.getString(R.string.can_subscribe));
                        }else if(item.getReserveStatus()==1){
                            holder.setBackgroundRes(R.id.tvSubscribe, R.drawable.tag_bg_2e76d0);
                            holder.setText(R.id.tvSubscribe, context.getString(R.string.has_subscribe));
                        }else {
                            holder.setVisible(R.id.tvSubscribe,false);
                        }

                        holder.setText(R.id.tv_browse_num, String.valueOf("閲覧数:" + item.getPageViewNum()));
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
                                if(item.getAdmissionFee()==0){
                                    intent.putExtra("isPay", true);
                                }
                                intent.putExtra("cover", item.getCoverUrl());
                                intent.putExtra("price",item.getAdmissionFee());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });
                    }
                })

                //--------------------------- recommend ----------------------------
                .addAdapterItem(new CustomAdapterItem<ViewHolder, RecommendNewsData.DataBean.ListDataBean.DatasBean>() {
                    @Override
                    public Class dataType() {
                        return RecommendNewsData.DataBean.ListDataBean.DatasBean.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.fragment_discovery_item_organization);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final RecommendNewsData.DataBean.ListDataBean.DatasBean
                            item, int position) {

                        View cover = holder.getView(R.id.iv_organization_cover);
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) cover.getLayoutParams();
                        layoutParams.height = (int) (SizeUtils.getFullScreenSize(context)[0] * (float) 360 / (float) 720);
                        cover.setLayoutParams(layoutParams);
                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + item.getCoverUrl(),
                                (ImageView) cover, ImageLoaderUtils.getImageOptions(R.drawable.magazine_default));
                        holder.setText(R.id.tv_title, item.getTitle());

                        holder.setOnClickListener(R.id.container, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context,
                                        DetailActivity.class);
                                intent.putExtra("url", item.getH5Url());
                                intent.putExtra("title", item.getTitle());
                                intent.putExtra("id", item.getId());

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

                //--------------------------- associationData ----------------------------
                .addAdapterItem(new CustomAdapterItem<ViewHolder, AssociationData.DataBean.ListDataBean.DatasBean>() {
                    @Override
                    public Class dataType() {
                        return AssociationData.DataBean.ListDataBean.DatasBean.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.fragment_discovery_item_organization);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final AssociationData.DataBean.ListDataBean.DatasBean
                            item, int position) {
                        View cover = holder.getView(R.id.iv_organization_cover);
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) cover.getLayoutParams();
                        layoutParams.height = (int) (SizeUtils.getFullScreenSize(context)[0] * (float) 360 / (float) 720);
                        cover.setLayoutParams(layoutParams);
                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + item.getCoverUrl(),
                                (ImageView) cover, ImageLoaderUtils.getImageOptions(R.drawable.news_default));
                        holder.setText(R.id.tv_title, item.getTitle());
                        holder.setText(R.id.tv_time,item.getCreateTime());

                        holder.setOnClickListener(R.id.container, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context,
                                        DetailActivity.class);
                                intent.putExtra("url", item.getH5Url());
                                intent.putExtra("title", item.getTitle());
                                intent.putExtra("id", item.getId());
                                intent.putExtra("type", 6);
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

                //--------------------------- recruitData ----------------------------
                .addAdapterItem(new CustomAdapterItem<ViewHolder, RecruitData.DataBean.ListDataBean.DatasBean>() {
                    @Override
                    public Class dataType() {
                        return RecruitData.DataBean.ListDataBean.DatasBean.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.fragment_discovery_item_jobs);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final RecruitData.DataBean.ListDataBean.DatasBean
                            item, int position) {

                        holder.setText(R.id.tv_jobs_title, item.getTitle());
                        holder.setText(R.id.tv_work_place, context.getString(R.string.work_place) + item.getWorkCountry() + "/" + item.getWorkCity());
                        holder.setText(R.id.tv_company, item.getCompany());
                        holder.setText(R.id.tv_price, item.getSalery());
                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + item.getCoverUrl()
                                , (ImageView) holder.getView(R.id.iv_jobs_img)
                                , ImageLoaderUtils.getImageOptions(R.drawable.magazine_default));

                        holder.setOnClickListener(R.id.container, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context,
                                        DetailActivity.class);
                                intent.putExtra("url", item.getH5Url());
                                intent.putExtra("title", item.getTitle());
                                intent.putExtra("id", item.getId());
                                intent.putExtra("orgId", item.getOrgId());
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
                                Log.e(TAG, "onClick: --------->" +tempPosition);
                                RxBus.getInstance().post(new SendSearchDataToSearchAct(item.navData, item.indexTitles.get(tempPosition), position, tempPosition));
                            }
                        });
                    }
                })
                ;
    }

    public void bannerPause() {
        if (banner != null) {
            banner.pauseScroll();
        }
    }

    public void bannerResume() {
        if (banner != null) {
            banner.resumeScroll();
        }
    }

}
