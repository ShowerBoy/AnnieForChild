package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.book.Line;

/**
 * Created by wanglei on 2018/3/24.
 */

public class SongViewHolder extends RecyclerView.ViewHolder {
    public TextView songName, songCount, collect, addMaterial, addCourse;
    public LinearLayout songdoLayout;
    public ImageView songImage, lock;
    public RelativeLayout songDetail;

    public SongViewHolder(View itemView) {
        super(itemView);
        songName = itemView.findViewById(R.id.song_name);
        songCount = itemView.findViewById(R.id.song_count);
        collect = itemView.findViewById(R.id.song_collect);
        addMaterial = itemView.findViewById(R.id.add_material);
        addCourse = itemView.findViewById(R.id.add_course);
        songImage = itemView.findViewById(R.id.song_image);
        songDetail = itemView.findViewById(R.id.song_detail);
        songdoLayout = itemView.findViewById(R.id.songdo_layout);
        lock = itemView.findViewById(R.id.song_lock);
    }
}
