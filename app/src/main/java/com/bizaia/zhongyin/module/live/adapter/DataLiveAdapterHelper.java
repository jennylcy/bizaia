package com.bizaia.zhongyin.module.live.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bizaia.zhongyin.R;
import com.bizaia.zhongyin.module.common.adapter.StateAdapterHelper;
import com.bizaia.zhongyin.module.common.data.SearchFixedData;
import com.bizaia.zhongyin.module.common.data.SendSearchDataToSearchAct;
import com.bizaia.zhongyin.module.common.ui.SearchFixedView;
import com.bizaia.zhongyin.module.live.data.LiveDailyBean;
import com.bizaia.zhongyin.module.live.data.LiveFocusListEntity;
import com.bizaia.zhongyin.module.live.data.LiveNewBean;
import com.bizaia.zhongyin.module.live.data.LiveRecomBean;
import com.bizaia.zhongyin.module.live.ui.LiveCoursewareDetail;
import com.bizaia.zhongyin.module.live.ui.LiveDetailActivity;
import com.bizaia.zhongyin.module.live.ui.LivingRoomActivity;
import com.bizaia.zhongyin.repository.AddressConfiguration;
import com.bizaia.zhongyin.util.ImageLoaderUtils;
import com.bizaia.zhongyin.util.RxBus;
import com.bizaia.zhongyin.util.SizeUtils;
import com.bizaia.zhongyin.util.StringUtils;
import com.bizaia.zhongyin.util.TimeUtils;
import com.bizaia.zhongyin.widget.OnTouchCallBack;
import com.bizaia.zhongyin.widget.adapter.CustomAdapter;
import com.bizaia.zhongyin.widget.adapter.CustomAdapterItem;
import com.bizaia.zhongyin.widget.adapter.ViewHolder;
import com.bizaia.zhongyin.widget.banner.Banner;
import com.bizaia.zhongyin.widget.banner.BannerIndicator;
import com.bizaia.zhongyin.widget.refresh.PtrClassicFrameLayout;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyz on 2017/3/7.
 */

public class DataLiveAdapterHelper extends StateAdapterHelper {
    private static final String TAG = "DataLiveAdapterHelper";
    private Context context;

    public CustomAdapter getAdapter(final Context context, List list, final PtrClassicFrameLayout ptrClassicFrameLayout) {
        this.context = context;
        return super.getAdapter(context, list)

                //--------------------------- banner ----------------------------
                .addAdapterItem(new CustomAdapterItem<ViewHolder, List<LiveFocusListEntity>>() {
                    @Override
                    public Class dataType() {
                        return ArrayList.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        View view = LayoutInflater.from(context).inflate(R.layout.fragment_item_banner, parent, false);
                        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                        layoutParams.height = (int) (SizeUtils.getFullScreenSize(context)[0] * 148 / 750);
                        view.setLayoutParams(layoutParams);
                        return ViewHolder.createViewHolder(context, view);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final List<LiveFocusListEntity>
                            item, int position) {
                        Banner banner = holder.getView(R.id.banner);
                        BannerIndicator bannerIndicator = holder.getView(R.id.indicator);
                        banner.setInterval(5000);
                        banner.setPageChangeDuration(500);
                        banner.setOnTouchCallBack(new OnTouchCallBack() {
                            public void actionDown() {
                                ptrClassicFrameLayout.setCanRefresh(false);
                            }

                            public void actionMove() {
                                ptrClassicFrameLayout.setCanRefresh(false);
                            }

                            public void actionMoveCancel() {
                                ptrClassicFrameLayout.setCanRefresh(true);
                            }

                            public void actionCancel() {
                                ptrClassicFrameLayout.setCanRefresh(true);
                            }
                        });
                        banner.setBannerDataInit(new Banner.BannerDataInit() {
                            @Override
                            public ImageView initImageView() {
                                return (ImageView) LayoutInflater.from(context).inflate(R.layout.banner_imageview, null);
                            }

                            @Override
                            public void initImgData(ImageView imageView, Object imgPath) {

                                ImageLoader.getInstance()
                                        .displayImage(AddressConfiguration.MAIN_PATH + ((LiveFocusListEntity) imgPath).getCoverUrl()
                                                , imageView, ImageLoaderUtils.getImageOptions(R.drawable.banner_default_img));
                            }
                        });
                        bannerIndicator.setIndicatorSource(
                                ContextCompat.getDrawable(context, R.drawable.banner_indicator_select),//select
                                ContextCompat.getDrawable(context, R.drawable.banner_indicator_unselect),//unselect
                                SizeUtils.dp2px(context, 6)//widthAndHeight
                        );
                        banner.setOnBannerItemClickListener(
                                new Banner.OnBannerItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {
                                        Log.e(TAG, "onItemClick: -------->" + item.get(position).getLiveId());
                                        if (item.get(position).getType() == 2) {
                                            Intent intent = new Intent();
                                            intent.setAction("android.intent.action.VIEW");
                                            Uri url = Uri.parse(item.get(position).getH5Url());
                                            intent.setData(url);
                                            context.startActivity(intent);
                                        } else {
                                            Intent intent = new Intent(context, LiveDetailActivity.class);
                                            intent.putExtra("id", item.get(position).getLiveId());
                                            intent.putExtra("liveType", item.get(position).getType());
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);
                                        }
                                    }
                                });
                        banner.setDataSource(item);
                        banner.attachIndicator(bannerIndicator);
                        banner.resumeScroll();
                    }
                })

                //--------------------------- news ----------------------------
                .addAdapterItem(new CustomAdapterItem<ViewHolder, LiveNewBean.DataEntity.LiveListEntity.DatasEntity>() {
                    @Override
                    public Class dataType() {
                        return LiveNewBean.DataEntity.LiveListEntity.DatasEntity.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        View view = LayoutInflater.from(context).inflate(R.layout.item_live_recom, parent, false);

                        View cover = view.findViewById(R.id.iv_organization_cover);
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) cover.getLayoutParams();
                        layoutParams.height = (int) (SizeUtils.getFullScreenSize(context)[0] * (float) 450 / (float) 720);
                        cover.setLayoutParams(layoutParams);

                        return ViewHolder.createViewHolder(context, view);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final LiveNewBean.DataEntity.LiveListEntity.DatasEntity
                            item, int position) {
                        holder.setText(R.id.tvName, item.getOrganizationName());
                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + item.getOrganizationLogo(),
                                (ImageView) holder.getView(R.id.ivIcon), ImageLoaderUtils.getImageOptions(R.drawable.icon_user_blue));

                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + item.getCoverUrl(),
                                (ImageView) holder.getView(R.id.iv_organization_cover), ImageLoaderUtils.getImageOptions(R.drawable.news_default));
                        if ("0".equals(item.getStatus())) {
                            holder.setBackgroundRes(R.id.ivTag, R.drawable.tag_03);
                            holder.setText(R.id.ivTag, context.getResources().getString(R.string.live_tag_foreshow));
                            if (0 == item.getPrice()) {
                                holder.setText(R.id.tvNumber, context.getString(R.string.bought_count) + "なし");
                            } else {
                                holder.setText(R.id.tvNumber, context.getString(R.string.bought_count) + item.getBuyNum());
                            }
                            holder.setBackgroundColor(R.id.viewTagTop, R.color.cr_307f11);
                            holder.setText(R.id.tvTime, TimeUtils.timeTransGBToCN(item.getStartTime()) + TimeUtils.getWeekDateTwo(item.getStartTime()));
                        } else if ("1".equals(item.getStatus())) {
                            holder.setBackgroundRes(R.id.ivTag, R.drawable.tag_01);
                            holder.setText(R.id.ivTag, context.getResources().getString(R.string.live_tag_live));
                            holder.setText(R.id.tvTime, context.getString(R.string.live_player_nums) + item.getPageViewNum());
                            if (0 == item.getPrice()) {
                                holder.setText(R.id.tvNumber, context.getString(R.string.bought_count) + "なし");
                            } else {
                                holder.setText(R.id.tvNumber, context.getString(R.string.bought_count) + item.getBuyNum());
                            }
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
                            holder.setText(R.id.tvFree,
                                    context.getString(R.string.provide_look));
                            holder.setVisible(R.id.tvFree, true);
                        }
                        holder.setText(R.id.tvTitle, item.getTitle());
                        holder.setText(R.id.tvType, item.getCateName());
                        holder.setText(R.id.tvTeacher, context.getString(R.string.live_teacher) + item.getLecturer());

                        holder.setOnClickListener(R.id.linContent, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.e(TAG, "onClick: ------------->" + item.getId());
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

                //--------------------------- recommend ----------------------------
                .addAdapterItem(new CustomAdapterItem<ViewHolder, LiveRecomBean.DataEntity.LiveRecommendListEntity.DatasEntity>() {
                    @Override
                    public Class dataType() {
                        return LiveRecomBean.DataEntity.LiveRecommendListEntity.DatasEntity.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.item_live_recom);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final LiveRecomBean.DataEntity.LiveRecommendListEntity.DatasEntity
                            item, int position) {
                        holder.setText(R.id.tvName, item.getOrganizationName());

//                        Log.e(TAG, "bindData: "+ ImageLoader.getInstance()+"    "+item+"      "+ item.getOrganizationLogo()+"     \n"
//                        +holder.getView(R.id.ivIcon)+"      "+ ImageLoaderUtils.getImageOptions());
//
                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + item.getOrganizationLogo(),
                                (ImageView) holder.getView(R.id.ivIcon), ImageLoaderUtils.getImageOptions(R.drawable.news_default));
//
//                        Log.e(TAG, "bindData2: "+ ImageLoader.getInstance()+"   \n "+item+"   \n   "+ item.getCoverUrl()+"     \n"
//                                +holder.getView(R.id.ivContentS)+"   \n   "+ ImageLoaderUtils.getImageOptions());
//                        if (!TextUtils.isEmpty(item.getCoverUrl()))
                        ImageLoader.getInstance().displayImage(AddressConfiguration.MAIN_PATH + item.getCoverUrl(),
                                (ImageView) holder.getView(R.id.iv_organization_cover), ImageLoaderUtils.getImageOptions(R.drawable.news_default));
                        if ("0".equals(item.getStatus())) {
                            holder.setBackgroundRes(R.id.ivTag, R.drawable.tag_03);
                            holder.setText(R.id.ivTag, context.getResources().getString(R.string.live_tag_foreshow));
                            if (0 == item.getPrice()) {
                                holder.setText(R.id.tvNumber, context.getString(R.string.bought_count) + "なし");
                            } else {
                                holder.setText(R.id.tvNumber, context.getString(R.string.bought_count) + item.getBuyNum());
                            }
                            holder.setBackgroundColor(R.id.viewTagTop, R.color.cr_307f11);
                            holder.setText(R.id.tvTime, item.getStartTime() + TimeUtils.getWeekDateTwo(item.getStartTime()));
                        } else if ("1".equals(item.getStatus())) {
                            holder.setBackgroundRes(R.id.ivTag, R.drawable.tag_01);
                            holder.setText(R.id.ivTag, context.getResources().getString(R.string.live_tag_live));
                            holder.setText(R.id.tvTime, context.getString(R.string.live_player_nums) + item.getPageViewNum());
                            if (0 == item.getPrice()) {
                                holder.setText(R.id.tvNumber, context.getString(R.string.bought_count) + "なし");
                            } else {
                                holder.setText(R.id.tvNumber, context.getString(R.string.bought_count) + item.getBuyNum());
                            }
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
                            holder.setText(R.id.tvFree,
                                    context.getString(R.string.provide_look));
                            holder.setVisible(R.id.tvFree, true);
                        }
                        holder.setText(R.id.tvTitle, item.getTitle());
                        holder.setText(R.id.tvType, item.getCateName());
                        if (!StringUtils.isEmpty(item.getLecturer())) {
                            holder.setText(R.id.tvTeacher, context.getString(R.string.live_teacher) + item.getLecturer());
                        } else {
                            holder.setText(R.id.tvTeacher, context.getString(R.string.live_teacher));
                        }
                        holder.setOnClickListener(R.id.linContent, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, LiveDetailActivity.class);
                                intent.putExtra("id", item.getLiveId());
                                intent.putExtra("liveType", Integer.parseInt(item.getStatus()));
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });
                    }
                })
                //--------------------------- mylectrue ----------------------------
                .addAdapterItem(new CustomAdapterItem<ViewHolder, LiveDailyBean.DataEntity>() {
                    @Override
                    public Class dataType() {
                        return LiveDailyBean.DataEntity.class;
                    }

                    @Override
                    public ViewHolder viewHolder(ViewGroup parent) {
                        return ViewHolder.createViewHolder(context, parent, R.layout.item_live_mine);
                    }

                    @Override
                    public void bindData(ViewHolder holder, final LiveDailyBean.DataEntity
                            item, int position) {
                        holder.setText(R.id.tvTime, TimeUtils.timeTransGBToCN(item.getStartTime()));
                        holder.setText(R.id.tvLiveTitle, item.getTitle());
                        holder.setOnClickListener(R.id.tvEnter, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, LivingRoomActivity.class);
                                intent.putExtra("roomNub", item.getChatroomId());
                                intent.putExtra("coursewareStreamAddress", item.getCoursewareStreamAddress());
                                intent.putExtra("cameraStreamAddress", item.getCameraStreamAddress());
                                intent.putExtra("id", item.getLiveId());
                                intent.putExtra("pdfUrl", item.getFilePath());
                                intent.putExtra("title", item.getTitle());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });
                        holder.setOnClickListener(R.id.tvMaterial, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, LiveCoursewareDetail.class);
                                intent.putExtra("id", item.getLiveId());
                                intent.putExtra("pdfUrl", item.getFilePath());
                                intent.putExtra("title", item.getTitle());
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
                                RxBus.getInstance().post(new SendSearchDataToSearchAct(item.navData, item.indexTitles.get(tempPosition), position, tempPosition));
                            }
                        });
                    }
                })
                ;
    }
}
