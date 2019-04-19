package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.net.experience.ExpItemBean;
import com.annie.annieforchild.bean.net.netexpclass.Info_new;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.annieforchild.ui.activity.net.LessonActivity;
import com.annie.annieforchild.ui.activity.net.NetExpFirstVideoActivity;
import com.annie.annieforchild.ui.activity.net.NetListenAndReadActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.NetExperienceDetailNewViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;

import java.util.List;

/**
 * Created by wanglei on 2019/4/16.
 */

public class NetExperienceDetailNewAdapter extends RecyclerView.Adapter<NetExperienceDetailNewViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<ExpItemBean> lists;
    private NetExperienceDetailItemAdapter adapter;
    private NetWorkPresenter presenter;
    private int netid;
    private int tag;

    public NetExperienceDetailNewAdapter(Context context, List<ExpItemBean> lists, NetWorkPresenter presenter, int tag, int netid) {
        this.context = context;
        this.lists = lists;
        this.presenter = presenter;
        this.tag = tag;
        this.netid = netid;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NetExperienceDetailNewViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        NetExperienceDetailNewViewHolder holder = null;
        holder = new NetExperienceDetailNewViewHolder(inflater.inflate(R.layout.activity_net_experience_new_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(NetExperienceDetailNewViewHolder netExperienceDetailNewViewHolder, int i) {
        if (lists.get(i).getIsshow().equals("0")) {
            netExperienceDetailNewViewHolder.setVisibility(false);
        } else {
            netExperienceDetailNewViewHolder.setVisibility(true);
            netExperienceDetailNewViewHolder.title.setText(lists.get(i).getStatge_name());
            adapter = new NetExperienceDetailItemAdapter(context, lists.get(i).getInfo(), new OnRecyclerItemClickListener() {
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
                        boolean isClick = true;
                        int j = i + 1;
                        for (int w = j; w < lists.size(); w++) {
                            if (lists.get(w).getIsfinish() == 0) {
                                isClick = false;
                            }
                        }
                        if (isClick) {
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
                        } else {
                            SystemUtils.show(context, "请先完成上一阶段");
                        }
                    }
                }


                @Override
                public void onItemLongClick(View view) {

                }
            });
            GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            netExperienceDetailNewViewHolder.recycler.setLayoutManager(layoutManager);
            netExperienceDetailNewViewHolder.recycler.setNestedScrollingEnabled(false);
            netExperienceDetailNewViewHolder.recycler.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
