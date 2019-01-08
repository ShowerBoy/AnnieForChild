package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2019/1/8.
 */

public class MyReleaseViewHolder extends RecyclerView.ViewHolder {
    public TextView myRecordContent, myRecordDate, myRecordTime, playTimes, likeTimes, target;
    public ImageView myRecordPlay, myRecordImage;
    public LinearLayout myReleaseLayout;

    public MyReleaseViewHolder(View itemView) {
        super(itemView);
        myRecordContent = itemView.findViewById(R.id.my_record_content);
        myRecordDate = itemView.findViewById(R.id.my_record_date);
        myRecordTime = itemView.findViewById(R.id.my_record_time);
        myRecordPlay = itemView.findViewById(R.id.my_record_play);
        myRecordImage = itemView.findViewById(R.id.my_record_image);
        myReleaseLayout = itemView.findViewById(R.id.my_release_layout);
        playTimes = itemView.findViewById(R.id.my_record_playTimes);
        likeTimes = itemView.findViewById(R.id.my_record_likeTimes);
        target = itemView.findViewById(R.id.target);
    }
}
