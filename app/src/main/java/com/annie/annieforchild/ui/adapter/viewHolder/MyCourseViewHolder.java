package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by Administrator on 2018/1/16 0016.
 */

public class MyCourseViewHolder extends RecyclerView.ViewHolder {

    public ImageView image;
    public TextView title, suggest;

    public MyCourseViewHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.my_course_image);
        title = itemView.findViewById(R.id.my_course_title);
        suggest = itemView.findViewById(R.id.my_course_suggest);
    }
}
