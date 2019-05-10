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
import com.annie.annieforchild.bean.net.experience.ExpItemBean;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.annieforchild.ui.activity.net.LessonActivity;
import com.annie.annieforchild.ui.activity.net.NetExpFirstVideoActivity;
import com.annie.annieforchild.ui.activity.net.NetListenAndReadActivity;
import com.annie.annieforchild.ui.activity.net.NetPreheatClassActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.NetSpecialDetailViewHolder2;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;

import java.util.List;

/**
 * Created by wanglei on 2019/5/6.
 */

public class NetSpecialDetailAdapter2 extends RecyclerView.Adapter<NetSpecialDetailViewHolder2> {
    private Context context;
    private LayoutInflater inflater;
    private List<ExpItemBean> lists;
    private NetSpecialDetailItemAdapter adapter;
    private NetWorkPresenter presenter;
    private int netid;
    private int tag, color;

    public NetSpecialDetailAdapter2(Context context, List<ExpItemBean> lists, NetWorkPresenter presenter, int tag, int netid, int color) {
        this.context = context;
        this.lists = lists;
        this.presenter = presenter;
        this.tag = tag;
        this.netid = netid;
        this.color = color;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NetSpecialDetailViewHolder2 onCreateViewHolder(ViewGroup viewGroup, int i) {
        NetSpecialDetailViewHolder2 holder = null;
        holder = new NetSpecialDetailViewHolder2(inflater.inflate(R.layout.activity_net_experience_new_item2, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(NetSpecialDetailViewHolder2 netSpecialDetailViewHolder2, int i) {
        if (lists.get(i).getIsshow().equals("0")) {
            netSpecialDetailViewHolder2.setVisibility(false);
        } else {
            if (lists.get(i).getInfo() != null && lists.get(i).getInfo().size() > 0) {
                netSpecialDetailViewHolder2.setVisibility(true);
                netSpecialDetailViewHolder2.title.setText(lists.get(i).getStatge_name());
                if (color == 1) {
                    netSpecialDetailViewHolder2.title.setBackgroundResource(R.drawable.classseed_icon_week);
                } else {
                    netSpecialDetailViewHolder2.title.setBackgroundResource(R.drawable.classleaf_icon_week);
                }
                adapter = new NetSpecialDetailItemAdapter(context, lists.get(i).getInfo(), new OnRecyclerItemClickListener() {
                    @Override
                    public void onItemClick(View view) {
                        int position = netSpecialDetailViewHolder2.recycler.getChildAdapterPosition(view);
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
                        } else if (lists.get(i).getInfo().get(position).getType().equals("6")) {
                            Intent intent = new Intent(context, NetPreheatClassActivity.class);
                            intent.putExtra("lessonId", lists.get(i).getInfo().get(position).getFid());
                            intent.putExtra("lessonName", lists.get(i).getInfo().get(position).getUnit_name());
                            intent.putExtra("type", 2);
                            context.startActivity(intent);
                        }
                    }

                    @Override
                    public void onItemLongClick(View view) {

                    }
                });
                GridLayoutManager layoutManager = new GridLayoutManager(context, 3);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                netSpecialDetailViewHolder2.recycler.setLayoutManager(layoutManager);
                netSpecialDetailViewHolder2.recycler.setNestedScrollingEnabled(false);
                netSpecialDetailViewHolder2.recycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                netSpecialDetailViewHolder2.setVisibility(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
