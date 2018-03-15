package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by WangLei on 2018/3/8 0008
 */

public class HelpViewHolder extends RecyclerView.ViewHolder {
    public TextView helpTitle, helpContent;

    public HelpViewHolder(View itemView) {
        super(itemView);
        helpTitle = itemView.findViewById(R.id.help_title);
        helpContent = itemView.findViewById(R.id.help_content);
    }
}
