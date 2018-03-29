package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/3/23.
 */

public class SongClassifyViewHolder extends RecyclerView.ViewHolder {
    public TextView classifyTitle;

    public SongClassifyViewHolder(View itemView) {
        super(itemView);
        classifyTitle = itemView.findViewById(R.id.classify_title);
    }
}
