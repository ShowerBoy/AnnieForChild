package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/12/18.
 */

public class LyricViewHolder extends RecyclerView.ViewHolder {
    public TextView lyric;

    public LyricViewHolder(View itemView) {
        super(itemView);
        lyric=itemView.findViewById(R.id.lyric_text);
    }
}
