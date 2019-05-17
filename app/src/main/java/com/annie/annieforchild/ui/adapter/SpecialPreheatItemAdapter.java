package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.net.PreheatConsultList;
import com.annie.annieforchild.ui.activity.VideoActivity;
import com.annie.annieforchild.ui.activity.VideoActivity_new;
import com.annie.annieforchild.ui.adapter.viewHolder.SpecialPreheatItemViewHolder;
import com.annie.annieforchild.ui.adapter.viewHolder.SpecialPreheatViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wanglei on 2019/3/22.
 */

public class SpecialPreheatItemAdapter extends RecyclerView.Adapter<SpecialPreheatItemViewHolder> {
    private Context context;
    private List<PreheatConsultList> lists;
    private LayoutInflater inflater;

    public SpecialPreheatItemAdapter(Context context, List<PreheatConsultList> lists) {
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
//                Intent intent = new Intent(context, VideoActivity.class);
                Intent intent = new Intent(context, VideoActivity_new.class);
                intent.putExtra("url", lists.get(i).getPath());
                intent.putExtra("imageUrl", lists.get(i).getPicurl());
                intent.putExtra("name", lists.get(i).getTitle());
                intent.putExtra("isTime", false);
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
