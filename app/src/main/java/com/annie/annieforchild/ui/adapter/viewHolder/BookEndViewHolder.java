package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wanglei on 2018/11/30.
 */

public class BookEndViewHolder extends ViewHolder {
    public CircleImageView headpic;
    public LinearLayout likeLinear, playLinear;
    public TextView name, age, date, playTimes, likeTimes;
    public ImageView play, like;

    public BookEndViewHolder(View itemView) {
        super(itemView);
        headpic = itemView.findViewById(R.id.book_end_headpic);
        name = itemView.findViewById(R.id.book_end_name);
        age = itemView.findViewById(R.id.book_end_age);
        date = itemView.findViewById(R.id.book_end_date);
        like = itemView.findViewById(R.id.practice_like);
        likeTimes = itemView.findViewById(R.id.practice_like_times);
        likeLinear = itemView.findViewById(R.id.like_layout);
        playTimes = itemView.findViewById(R.id.book_end_playTimes);
        play = itemView.findViewById(R.id.book_end_play);
        playLinear=itemView.findViewById(R.id.book_end_play_layout);

    }
}
