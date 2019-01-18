package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.net.PreheatConsultList;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.ui.activity.VideoActivity;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.NetPreheatConsultViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 */

public class NetPreheatConsultAdapter extends RecyclerView.Adapter<NetPreheatConsultViewHolder> {
    private Context context;
    private List<PreheatConsultList> list1;
    private List<PreheatConsultList> list2;
    private LayoutInflater inflater;

    public NetPreheatConsultAdapter(Context context, List<PreheatConsultList> list1,List<PreheatConsultList> list2) {
        this.context = context;
        this.list1 = list1;
        this.list2 = list2;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NetPreheatConsultViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        NetPreheatConsultViewHolder holder;
        holder = new NetPreheatConsultViewHolder(inflater.inflate(R.layout.activity_net_preheatconsult_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(NetPreheatConsultViewHolder holder, int i) {
        if(i==0){
            holder.type.setText("微课堂");
            holder.type.setVisibility(View.VISIBLE);
        }else if(i==list1.size()){
            holder.type.setText("素材解析");
            holder.type.setVisibility(View.VISIBLE);
        }else{
            holder.type.setVisibility(View.GONE);
        }
        if(i<list1.size()){
            holder.title.setText(list1.get(i).getTitle());
            Glide.with(context).load(list1.get(i).getPicurl()).into(holder.video_img);
        }else{
            holder.title.setText(list2.get(i-list1.size()).getTitle());
            Glide.with(context).load(list2.get(i-list1.size()).getPicurl()).into(holder.video_img);
        }

        holder.video_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(context, VideoActivity.class);
                    if(i<list1.size()){
                        intent.putExtra("url",list1.get(i).getPath());
                        intent.putExtra("imageUrl",list1.get(i).getPicurl());
                        intent.putExtra("name",list1.get(i).getTitle());
                    }else{
                        intent.putExtra("url",list2.get(i-list1.size()).getPath());
                        intent.putExtra("imageUrl",list2.get(i-list1.size()).getPicurl());
                        intent.putExtra("name",list2.get(i-list1.size()).getTitle());
                    }
                    context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list1.size()+list2.size();
    }
}