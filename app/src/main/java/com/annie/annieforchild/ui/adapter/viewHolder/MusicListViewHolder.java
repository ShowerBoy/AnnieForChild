package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/7/24.
 */

public class MusicListViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public ImageView delete;

    public MusicListViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.music_part_name);
        delete = itemView.findViewById(R.id.music_part_delete);
    }
}
