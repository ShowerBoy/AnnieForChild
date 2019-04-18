package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2019/4/18.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder{
    public TextView text;

    public MenuViewHolder(View itemView) {
        super(itemView);
        text=itemView.findViewById(R.id.menu_text);
    }
}
