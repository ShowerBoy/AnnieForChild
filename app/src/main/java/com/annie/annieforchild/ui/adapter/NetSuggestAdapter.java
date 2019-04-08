package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.ui.adapter.viewHolder.NetSuggestViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by wanglei on 2018/11/14.
 */

public class NetSuggestAdapter extends RecyclerView.Adapter<NetSuggestViewHolder> {
    private Context context;
    private List<String> lists;
    private LayoutInflater inflater;
    private int width;


    public NetSuggestAdapter(Context context, List<String> lists) {
        this.context = context;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        width = dm.widthPixels;
    }

    @Override
    public NetSuggestViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        NetSuggestViewHolder holder = null;
        holder = new NetSuggestViewHolder(inflater.inflate(R.layout.activity_net_suggest_fragment_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(NetSuggestViewHolder netSuggestViewHolder, int i) {
//        Glide.with(context).load(lists.get(i)).into(netSuggestViewHolder.image);
        Glide.with( netSuggestViewHolder.image.getContext()).load(lists.get(i)).asBitmap().placeholder(R.drawable.back_white) .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                int imageWidth = resource.getWidth();
                int imageHeight = resource.getHeight();
                int height = width * imageHeight / imageWidth;
                ViewGroup.LayoutParams para =  netSuggestViewHolder.image.getLayoutParams();
                para.height = height;
                para.width = width;
                netSuggestViewHolder.image.setImageBitmap(resource);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


}
