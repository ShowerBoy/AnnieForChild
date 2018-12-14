package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/8/28.
 */

public class GrindEarViewHolder extends RecyclerView.ViewHolder {
    public ImageView recommentImage, lock;
    public TextView recommentText;

    public GrindEarViewHolder(View itemView) {
        super(itemView);
        recommentImage = itemView.findViewById(R.id.grind_ear_recommend_image);
        recommentText = itemView.findViewById(R.id.grind_ear_recommend_text);
        lock = itemView.findViewById(R.id.grind_ear_recommend_lock);
    }
}
