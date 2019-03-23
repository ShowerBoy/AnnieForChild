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
import com.annie.annieforchild.bean.net.SpecInfo;
import com.annie.annieforchild.ui.activity.net.LessonActivity;
import com.annie.annieforchild.ui.activity.net.NetExpFirstVideoActivity;
import com.annie.annieforchild.ui.activity.net.NetListenAndReadActivity;
import com.annie.annieforchild.ui.activity.net.NetPreheatClassActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.NetSpecialContentViewHolder2;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;

import java.util.List;

/**
 * Created by wanglei on 2019/3/14.
 */

public class NetSpecialContentAdapter extends RecyclerView.Adapter<NetSpecialContentViewHolder2> {
    private Context context;
    private LayoutInflater inflater;
    private List<SpecInfo> lists;
    private int color, weekNum;
    private NetSpecialConInfoAdapter adapter;

    public NetSpecialContentAdapter(Context context, List<SpecInfo> lists, int color, int weekNum) {
        this.context = context;
        this.lists = lists;
        this.color = color;
        this.weekNum = weekNum;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NetSpecialContentViewHolder2 onCreateViewHolder(ViewGroup viewGroup, int i) {
        NetSpecialContentViewHolder2 holder = null;
        holder = new NetSpecialContentViewHolder2(inflater.inflate(R.layout.activity_net_special_content_item2, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(NetSpecialContentViewHolder2 netSpecialContentViewHolder, int i) {
        if (color == 1) {
            netSpecialContentViewHolder.title.setVisibility(View.GONE);
        } else {
            netSpecialContentViewHolder.title.setVisibility(View.VISIBLE);
            netSpecialContentViewHolder.title.setText(lists.get(i).getFcategoryname());
        }

        if (lists.get(i).getInfo() != null && lists.get(i).getInfo().size() != 0) {
            adapter = new NetSpecialConInfoAdapter(context, lists.get(i).getInfo(), new OnRecyclerItemClickListener() {
                @Override
                public void onItemClick(View view) {
                    int position = netSpecialContentViewHolder.recycler.getChildAdapterPosition(view);
                    if (lists.get(i).getInfo().get(position).getType().equals("0")) {
                        //预热课
                        Intent intent = new Intent(context, NetPreheatClassActivity.class);
                        intent.putExtra("lessonName", lists.get(i).getInfo().get(position).getFcategoryname());
                        intent.putExtra("type", 2);
                        intent.putExtra("lessonId", lists.get(i).getInfo().get(position).getFid());
                        context.startActivity(intent);
                    } else {
                        //彩虹条
                        Intent intent = new Intent(context, LessonActivity.class);
                        intent.putExtra("lessonId", lists.get(i).getInfo().get(position).getFid());
                        intent.putExtra("lessonName", lists.get(i).getInfo().get(position).getFcategoryname());
                        intent.putExtra("type", 4);
                        context.startActivity(intent);
                    }
                }

                @Override
                public void onItemLongClick(View view) {

                }
            });
            GridLayoutManager layoutManager = new GridLayoutManager(context, 3);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            netSpecialContentViewHolder.recycler.setLayoutManager(layoutManager);
            netSpecialContentViewHolder.recycler.setNestedScrollingEnabled(false);
            netSpecialContentViewHolder.recycler.setAdapter(adapter);

            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public int getItemCount() {
        return lists.size();
    }
}
