package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/8/24.
 */

public class UploadViewHolder extends RecyclerView.ViewHolder {
    public ImageView taskImage;

    public UploadViewHolder(View itemView) {
        super(itemView);
        taskImage = itemView.findViewById(R.id.task_image);
    }
}
