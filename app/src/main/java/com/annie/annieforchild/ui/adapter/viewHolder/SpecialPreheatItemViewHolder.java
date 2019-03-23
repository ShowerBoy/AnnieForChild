package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2019/3/22.
 */

public class SpecialPreheatItemViewHolder extends RecyclerView.ViewHolder {
    public ConstraintLayout layout;
    public ImageView image;
    public TextView title, describe;

    public SpecialPreheatItemViewHolder(View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.special_preheat_layout);
        image = itemView.findViewById(R.id.special_preheat_video_img);
        title = itemView.findViewById(R.id.special_preheat_title);
        describe = itemView.findViewById(R.id.special_preheat_describe);
    }
}
