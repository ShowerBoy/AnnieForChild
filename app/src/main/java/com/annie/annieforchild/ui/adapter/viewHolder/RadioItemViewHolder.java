package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/12/14.
 */

public class RadioItemViewHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public TextView title;

    public RadioItemViewHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.radio_image);
        title = itemView.findViewById(R.id.radio_text);
    }
}
