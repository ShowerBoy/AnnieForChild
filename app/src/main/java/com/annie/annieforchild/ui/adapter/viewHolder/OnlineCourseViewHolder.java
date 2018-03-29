package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/3/17.
 */

public class OnlineCourseViewHolder extends RecyclerView.ViewHolder {
    public ImageView courseImage;
    public TextView courseText, courseProgress;

    public OnlineCourseViewHolder(View itemView) {
        super(itemView);
        courseImage = itemView.findViewById(R.id.online_course_image);
        courseText = itemView.findViewById(R.id.online_course_text);
        courseProgress = itemView.findViewById(R.id.online_course_progress);
    }
}
