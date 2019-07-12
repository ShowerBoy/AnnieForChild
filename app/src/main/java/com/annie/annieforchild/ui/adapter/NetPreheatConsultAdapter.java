package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.net.PreheatConsultList;
import com.annie.annieforchild.bean.net.netexpclass.VideoList;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.ui.activity.VideoActivity;
import com.annie.annieforchild.ui.activity.VideoActivity_new;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.NetPreheatConsultViewHolder;
import com.bumptech.glide.Glide;
import com.tencent.smtt.sdk.TbsVideo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 */

public class NetPreheatConsultAdapter extends RecyclerView.Adapter<NetPreheatConsultViewHolder> {
    private Context context;
    private List<PreheatConsultList> list1;
    private List<PreheatConsultList> list2;
    private LayoutInflater inflater;

    public NetPreheatConsultAdapter(Context context, List<PreheatConsultList> list1, List<PreheatConsultList> list2) {
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
        if (list1.size() == 0) {
            if (i == 0) {
                holder.type.setVisibility(View.VISIBLE);
            } else {
                holder.type.setVisibility(View.GONE);
            }
            holder.type.setText("素材解析");
            holder.title.setText(list2.get(i - list1.size()).getTitle());
            holder.test_describe.setText(list2.get(i - list1.size()).getSubtitle());
            Glide.with(context).load(list2.get(i - list1.size()).getPicurl()).error(R.drawable.image_error).into(holder.video_img);
        } else {
            if (i == 0) {
                holder.type.setText("微课堂");
                holder.type.setVisibility(View.VISIBLE);
            } else if (i == list1.size()) {
                holder.type.setText("素材解析");
                holder.type.setVisibility(View.VISIBLE);
            } else {
                holder.type.setVisibility(View.GONE);
            }
            if (i < list1.size()) {
                holder.title.setText(list1.get(i).getTitle());
                holder.test_describe.setText(list1.get(i).getSubtitle());
                Glide.with(context).load(list1.get(i).getPicurl()).error(R.drawable.image_error).into(holder.video_img);
            } else {
                holder.title.setText(list2.get(i - list1.size()).getTitle());
                holder.test_describe.setText(list2.get(i - list1.size()).getSubtitle());
                Glide.with(context).load(list2.get(i - list1.size()).getPicurl()).error(R.drawable.image_error).into(holder.video_img);
            }
        }

//        if (i == 0) {
//            if (mirIsShow == 0) {
//                holder.linear.setVisibility(View.GONE);
//            } else {
//                holder.linear.setVisibility(View.VISIBLE);
//            }
//        } else {
//            holder.linear.setVisibility(View.VISIBLE);
//        }

        holder.welcomeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, VideoActivity.class);
                Intent intent = new Intent(context, VideoActivity_new.class);
                List<VideoList> list = new ArrayList<>();
                if (i < list1.size()) {
                    for (int j = 0; j < list1.size(); j++) {
                        VideoList videoList = new VideoList();
                        videoList.setTitle(list1.get(j).getTitle());
                        videoList.setPicurl(list1.get(j).getPicurl());
                        videoList.setUrl(list1.get(j).getPath());
                        list.add(videoList);
                    }

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("videoList", (Serializable) list);
                    bundle.putInt("videoPos", i);
                    intent.putExtras(bundle);
                }else{
                    for (int j = 0; j < list2.size(); j++) {
                        VideoList videoList = new VideoList();
                        videoList.setTitle(list2.get(j).getTitle());
                        videoList.setPicurl(list2.get(j).getPicurl());
                        videoList.setUrl(list2.get(j).getPath());
                        list.add(videoList);
                    }

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("videoList", (Serializable) list);
                    bundle.putInt("videoPos", i - list1.size());
                    intent.putExtras(bundle);
                }

                intent.putExtra("isTime", false);
                intent.putExtra("isFinish", 1);
                intent.putExtra("isDefinition", false);

                context.startActivity(intent);

//                Intent intent = new Intent(context, VideoActivity_new.class);
//                if (i < list1.size()) {
//                    intent.putExtra("url", list1.get(i).getPath());
//                    intent.putExtra("imageUrl", list1.get(i).getPicurl());
//                    intent.putExtra("name", list1.get(i).getTitle());
////                    SystemUtils.startVideo(context, list1.get(i).getPath());
//                } else {
//                    intent.putExtra("url", list2.get(i - list1.size()).getPath());
//                    intent.putExtra("imageUrl", list2.get(i - list1.size()).getPicurl());
//                    intent.putExtra("name", list2.get(i - list1.size()).getTitle());
////                    SystemUtils.startVideo(context, list2.get(i - list1.size()).getPath());
//                }
//                intent.putExtra("isTime", false);

            }
        });
    }


    @Override
    public int getItemCount() {
        return list1.size() + list2.size();
    }

}
