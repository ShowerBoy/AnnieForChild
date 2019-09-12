package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.net.experience.ExpItemBeanV3;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.annieforchild.ui.activity.net.LessonActivity;
import com.annie.annieforchild.ui.activity.net.NetExpFirstVideoActivity;
import com.annie.annieforchild.ui.activity.net.NetListenAndReadActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.NetExperienceDetailNewViewHolderV3;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 体验课V3版
 */

public class NetExperienceDetailNewAdapterV3 extends RecyclerView.Adapter<NetExperienceDetailNewViewHolderV3> {
    private Context context;
    private LayoutInflater inflater;
    private List<ExpItemBeanV3> lists;
    private NetExperienceDetailItemAdapterV3 adapter;
    private NetWorkPresenter presenter;
    private int netid;
    private int tag;
    private int beenint;

    public NetExperienceDetailNewAdapterV3(Context context, List<ExpItemBeanV3> lists, NetWorkPresenter presenter, int tag, int netid,int beeint) {
        this.context = context;
        this.lists = lists;
        this.presenter = presenter;
        this.beenint=beeint;
        this.tag = tag;
        this.netid = netid;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NetExperienceDetailNewViewHolderV3 onCreateViewHolder(ViewGroup viewGroup, int i) {
        NetExperienceDetailNewViewHolderV3 holder = null;
        holder = new NetExperienceDetailNewViewHolderV3(inflater.inflate(R.layout.activity_net_experience_new_item_v3, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(NetExperienceDetailNewViewHolderV3 netExperienceDetailNewViewHolder, int i) {

        Glide.with(context).load(lists.get(i).getContent_img()).into(netExperienceDetailNewViewHolder.net_v3_background_layout);
//        if (lists.get(i).getIsshow().equals("0")) {
//            netExperienceDetailNewViewHolder.setVisibility(false);
//        } else {
            netExperienceDetailNewViewHolder.setVisibility(true);
            adapter = new NetExperienceDetailItemAdapterV3(context, lists.get(i).getInfo(), new OnRecyclerItemClickListener() {
                @Override
                public void onItemClick(View view) {
                    //1、彩虹条，2、H5页面（加参数），3、家庭作业，4、泛听泛读，5，视频列表
                    int position = netExperienceDetailNewViewHolder.recycler.getChildAdapterPosition(view);
                    if (lists.size() == (i + 1)) {
                        if (lists.get(i).getInfo().get(position).getType().equals("1")) {
                            Intent intent = new Intent(context, LessonActivity.class);
                            intent.putExtra("lessonId", lists.get(i).getInfo().get(position).getFid());
                            intent.putExtra("lessonName", lists.get(i).getInfo().get(position).getUnit_name());
                            intent.putExtra("type", 4);
                            context.startActivity(intent);
                        } else if (lists.get(i).getInfo().get(position).getType().equals("2")) {
                            Intent intent = new Intent(context, WebActivity.class);
                            intent.putExtra("url", lists.get(i).getInfo().get(position).getUrl());
                            intent.putExtra("title", lists.get(i).getInfo().get(position).getUnit_name());
                            intent.putExtra("refresh", 1);
                            context.startActivity(intent);
                        } else if (lists.get(i).getInfo().get(position).getType().equals("3")) {
                            presenter.getListeningAndReading(lists.get(i).getInfo().get(position).getWeeknum(), lists.get(i).getInfo().get(position).getFid(), tag, 0);
                        } else if (lists.get(i).getInfo().get(position).getType().equals("4")) {
                            Intent intent = new Intent(context, NetListenAndReadActivity.class);
                            intent.putExtra("classid", lists.get(i).getInfo().get(position).getFid());
                            intent.putExtra("week", lists.get(i).getInfo().get(position).getWeeknum());
                            context.startActivity(intent);
                        } else if (lists.get(i).getInfo().get(position).getType().equals("5")) {
                            Intent intent = new Intent(context, NetExpFirstVideoActivity.class);
                            intent.putExtra("title", lists.get(i).getInfo().get(position).getUnit_name());
                            intent.putExtra("type", 5);
                            intent.putExtra("fid", lists.get(i).getInfo().get(position).getFid());
                            intent.putExtra("netid", netid + "");
                            intent.putExtra("stageid", lists.get(i).getStageid());
                            intent.putExtra("unitid", lists.get(i).getInfo().get(position).getUnitid());
                            intent.putExtra("classcode", lists.get(i).getClasscode());
                            intent.putExtra("position", i);
                            context.startActivity(intent);
                        }
                    } else {
                        //TODO:
                        //TODO:
//                        boolean isClick = true;
//                        int j = i + 1;
//                        for (int w = j; w < lists.size(); w++) {
//                            if (lists.get(w).getIsfinish().equals("0")) {
//                                isClick = false;
//                            }
//                        }
//                        if (isClick) {
                            if (lists.get(i).getInfo().get(position).getType().equals("1")) {
                                Intent intent = new Intent(context, LessonActivity.class);
                                intent.putExtra("lessonId", lists.get(i).getInfo().get(position).getFid());
                                intent.putExtra("lessonName", lists.get(i).getInfo().get(position).getUnit_name());
                                intent.putExtra("type", 4);
                                context.startActivity(intent);
                            } else if (lists.get(i).getInfo().get(position).getType().equals("2")) {
                                Intent intent = new Intent(context, WebActivity.class);
                                intent.putExtra("url", lists.get(i).getInfo().get(position).getUrl());
                                intent.putExtra("title", lists.get(i).getInfo().get(position).getUnit_name());
                                context.startActivity(intent);
                            } else if (lists.get(i).getInfo().get(position).getType().equals("3")) {
                                presenter.getListeningAndReading(lists.get(i).getInfo().get(position).getWeeknum(), lists.get(i).getInfo().get(position).getFid(), tag, 0);
                            } else if (lists.get(i).getInfo().get(position).getType().equals("4")) {
                                Intent intent = new Intent(context, NetListenAndReadActivity.class);
                                intent.putExtra("classid", lists.get(i).getInfo().get(position).getFid());
                                intent.putExtra("week", lists.get(i).getInfo().get(position).getWeeknum());
                                context.startActivity(intent);
                            } else if (lists.get(i).getInfo().get(position).getType().equals("5")) {
                                Intent intent = new Intent(context, NetExpFirstVideoActivity.class);
                                intent.putExtra("title", lists.get(i).getInfo().get(position).getUnit_name());
                                intent.putExtra("type", 5);
                                intent.putExtra("fid", lists.get(i).getInfo().get(position).getFid());
                                intent.putExtra("netid", netid + "");
                                intent.putExtra("stageid", lists.get(i).getStageid());
                                intent.putExtra("unitid", lists.get(i).getInfo().get(position).getUnitid());
                                intent.putExtra("classcode", lists.get(i).getClasscode());
                                intent.putExtra("position", i);
                                context.startActivity(intent);
                            }
//                        } else {
//                            SystemUtils.show(context, "请先完成上一阶段");
//                        }
                    }
                }


                @Override
                public void onItemLongClick(View view) {

                }
            });
            if (beenint != 0 && (lists.size()-beenint) == i) {
                addGroupImage(netExperienceDetailNewViewHolder.net_v3_childlayout);
            }
            if (lists.get(i).getIsshow().equals("0")) {//0 为加锁，1为隐藏
                Glide.with(context).load(lists.get(i).getShade_img()).into(netExperienceDetailNewViewHolder.net_v3_lock);
                netExperienceDetailNewViewHolder.net_v3_lock.setVisibility(View.VISIBLE);
                netExperienceDetailNewViewHolder.net_v3_lock.setOnClickListener(null);
            } else {
                netExperienceDetailNewViewHolder.net_v3_lock.setVisibility(View.GONE);
            }
            GridLayoutManager layoutManager;

            if ((lists.size() - i - 1) % 2 == 0) {//右边

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) netExperienceDetailNewViewHolder.net_v3_layout.getLayoutParams();
                RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) netExperienceDetailNewViewHolder.net_v3_lock.getLayoutParams();
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                if (lists.get(i).getInfo().size() > 2) {
                    layoutManager = new GridLayoutManager(context, 2);
//                    params.setMargins(0, 0, dip2px(context, 20), dip2px(context, 40));
//                    netExperienceDetailNewViewHolder.recycler.setPadding(0, 0, 0, dip2px(context, 20));
                } else {
                    layoutManager = new GridLayoutManager(context, 1);
//                    params.setMargins(dip2px(context, 145), 0, 0, 0);
//                    if (lists.get(i).getInfo().size() == 1) {
//                        netExperienceDetailNewViewHolder.recycler.setPadding(0, 0, 0, dip2px(context, 20));
//                    } else {
//                        netExperienceDetailNewViewHolder.recycler.setPadding(0, 0, 0, dip2px(context, 20));
//                    }
                }
                netExperienceDetailNewViewHolder.net_v3_layout.setLayoutParams(params);
                netExperienceDetailNewViewHolder.net_v3_lock.setLayoutParams(params1);
            } else {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) netExperienceDetailNewViewHolder.net_v3_layout.getLayoutParams();
                RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) netExperienceDetailNewViewHolder.net_v3_lock.getLayoutParams();
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                if (lists.get(i).getInfo().size() > 2) {
                    layoutManager = new GridLayoutManager(context, 2);
//                    netExperienceDetailNewViewHolder.net_v3_layout.setPadding(0, 0, 0, dip2px(context, 20));
//                    params.setMargins(dip2px(context, 20), 0, 0, 0);
                } else {
                    layoutManager = new GridLayoutManager(context, 1);
//                    params.setMargins(dip2px(context, 100), 0, 0, 0);
//                    if (lists.get(i).getInfo().size() == 1) {
//                        netExperienceDetailNewViewHolder.recycler.setPadding(0, 0, 0, dip2px(context, 20));
//                    } else {
//                        netExperienceDetailNewViewHolder.recycler.setPadding(0, 0, 0, dip2px(context, 20));
//                    }
                }
                netExperienceDetailNewViewHolder.net_v3_layout.setLayoutParams(params);
                netExperienceDetailNewViewHolder.net_v3_lock.setLayoutParams(params1);
            }
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            netExperienceDetailNewViewHolder.recycler.setLayoutManager(layoutManager);
            netExperienceDetailNewViewHolder.recycler.setNestedScrollingEnabled(false);
            netExperienceDetailNewViewHolder.recycler.setAdapter(adapter);
            adapter.notifyDataSetChanged();
//        }

    }
    public  void setint(int bee){
        beenint=bee;
    }
    //size:代码中获取到的图片数量
    private void addGroupImage(RelativeLayout layout){
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));  //设置图片宽高
            imageView.setImageResource(R.drawable.been_animal); //图片资源
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();
        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        params1.setMargins(0,dip2px(context,0),dip2px(context,5),0);
        imageView.setLayoutParams(params1);
        layout.addView(imageView); //动态添加图片

    }
    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
