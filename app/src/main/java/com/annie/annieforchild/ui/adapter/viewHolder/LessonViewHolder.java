package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/10/22.
 */

public class LessonViewHolder extends RecyclerView.ViewHolder {
    public TextView name,lesson_name_cn;
    public ImageView lesson_circle;

    public LessonViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.lesson_name);
        lesson_name_cn = itemView.findViewById(R.id.lesson_name_cn);
        lesson_circle = itemView.findViewById(R.id.lesson_circle);
    }
}
