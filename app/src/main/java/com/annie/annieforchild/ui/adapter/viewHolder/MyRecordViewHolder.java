package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by WangLei on 2018/3/8 0008
 */

public class MyRecordViewHolder extends RecyclerView.ViewHolder {
    public TextView myRecordContent, myRecordDate, myRecordTime;
    public ImageView myRecordPlay,myRecordImage;

    public MyRecordViewHolder(View itemView) {
        super(itemView);
        myRecordContent = itemView.findViewById(R.id.my_record_content);
        myRecordDate = itemView.findViewById(R.id.my_record_date);
        myRecordTime = itemView.findViewById(R.id.my_record_time);
        myRecordPlay = itemView.findViewById(R.id.my_record_play);
        myRecordImage=itemView.findViewById(R.id.my_record_image);
    }
}
