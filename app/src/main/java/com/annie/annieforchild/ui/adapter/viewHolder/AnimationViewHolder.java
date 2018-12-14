package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/6/14.
 */

public class AnimationViewHolder extends RecyclerView.ViewHolder {
    public ImageView image, lock;
    public TextView title, collect;

    public AnimationViewHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.animation_image);
        title = itemView.findViewById(R.id.animation_text);
        collect = itemView.findViewById(R.id.animation_collect);
        lock = itemView.findViewById(R.id.animation_lock);
    }
}
