package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/7/10.
 */

public class CommitBookViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;

    public CommitBookViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.commit_book_name);
    }
}
