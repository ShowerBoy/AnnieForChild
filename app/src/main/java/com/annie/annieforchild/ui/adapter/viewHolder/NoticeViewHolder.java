package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by WangLei on 2018/2/26 0026
 */

public class NoticeViewHolder extends RecyclerView.ViewHolder {
    public TextView noticeType;
    public TextView noticeTitle;
    public TextView noticeTime;
    public TextView noticeContent;

    public NoticeViewHolder(View itemView) {
        super(itemView);
        noticeType = itemView.findViewById(R.id.notice_type);
        noticeTitle = itemView.findViewById(R.id.notice_title);
        noticeTime = itemView.findViewById(R.id.notice_time);
        noticeContent = itemView.findViewById(R.id.notice_content);
    }
}
