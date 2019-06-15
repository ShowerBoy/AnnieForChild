package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.net.PreheatConsultList;
import com.annie.annieforchild.bean.net.PreheatConsultList2;
import com.annie.annieforchild.bean.net.netexpclass.VideoList;
import com.annie.annieforchild.ui.activity.VideoActivity;
import com.annie.annieforchild.ui.activity.VideoActivity_new;
import com.annie.annieforchild.ui.adapter.viewHolder.SpecialPreheatItemViewHolder;
import com.annie.annieforchild.ui.adapter.viewHolder.SpecialPreheatViewHolder;
import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglei on 2019/3/22.
 */

public class SpecialPreheatItemAdapter extends RecyclerView.Adapter<SpecialPreheatItemViewHolder> {
    private Context context;
    private List<PreheatConsultList2> lists;
    private LayoutInflater inflater;

    public SpecialPreheatItemAdapter(Context context, List<PreheatConsultList2> lists) {
        this.context = context;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public SpecialPreheatItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        SpecialPreheatItemViewHolder holder = null;
        holder = new SpecialPreheatItemViewHolder(inflater.inflate(R.layout.activity_special_preheat_item_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(SpecialPreheatItemViewHolder specialPreheatItemViewHolder, int i) {
        Glide.with(context).load(lists.get(i).getPicurl()).error(R.drawable.image_error).into(specialPreheatItemViewHolder.image);
        specialPreheatItemViewHolder.title.setText(lists.get(i).getTitle() != null ? lists.get(i).getTitle() : "");
        specialPreheatItemViewHolder.describe.setText(lists.get(i).getSubtitle() != null ? lists.get(i).getSubtitle() : "");
        specialPreheatItemViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * isDefinition=true 有path
                 *
                 */
//                Intent intent = new Intent(context, VideoActivity.class);
                List<VideoList> list = new ArrayList<>();
                for (int j = 0; j < lists.size(); j++) {
                    VideoList videoList = new VideoList();
                    videoList.setTitle(lists.get(j).getTitle());
                    videoList.setPicurl(lists.get(j).getPicurl());
                    videoList.setUrl(lists.get(j).getPath().get(0).getUrl());
                    videoList.setPath(lists.get(j).getPath());
                    list.add(videoList);
                }

                Intent intent = new Intent(context, VideoActivity_new.class);
//                intent.putExtra("url", lists.get(i).getPath().get(0));
//                intent.putExtra("imageUrl", lists.get(i).getPicurl());
//                intent.putExtra("name", lists.get(i).getTitle());
                intent.putExtra("isTime", false);
                intent.putExtra("isDefinition", true);
                Bundle bundle = new Bundle();
                bundle.putSerializable("videoList", (Serializable) list);
                bundle.putInt("videoPos", i);
                intent.putExtras(bundle);
                context.startActivity(intent);
//                SystemUtils.startVideo(context, lists.get(i).getPath());
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
