package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.net.PreheatConsultList;
import com.annie.annieforchild.bean.net.netexpclass.Video_first;
import com.annie.annieforchild.ui.activity.VideoActivity;
import com.annie.annieforchild.ui.activity.my.WebActivity2;
import com.annie.annieforchild.ui.adapter.viewHolder.NetPreheatConsultViewHolder;
import com.bumptech.glide.Glide;
import com.tencent.smtt.sdk.TbsVideo;

import java.util.List;

/**
 */

public class NetExpFirstVideoAdapter extends RecyclerView.Adapter<NetPreheatConsultViewHolder> {
    private Context context;
    private List<Video_first> list1;
    private LayoutInflater inflater;

    public NetExpFirstVideoAdapter(Context context, List<Video_first> list1) {
        this.context = context;
        this.list1 = list1;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NetPreheatConsultViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        NetPreheatConsultViewHolder holder;
        holder = new NetPreheatConsultViewHolder(inflater.inflate(R.layout.activity_net_firstvideo_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(NetPreheatConsultViewHolder holder, int i) {

        holder.title.setText(list1.get(i).getTitle());
        holder.test_describe.setText(list1.get(i).getSubtitle());
        Glide.with(context).load(list1.get(i).getPicurl()).into(holder.video_img);


        holder.welcomeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra("url", list1.get(i).getPath());
                intent.putExtra("imageUrl", list1.get(i).getPicurl());
                intent.putExtra("name", list1.get(i).getTitle());
                intent.putExtra("isTime", false);
                context.startActivity(intent);
//                SystemUtils.startVideo(context, list1.get(i).getPath());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list1.size();
    }

}
