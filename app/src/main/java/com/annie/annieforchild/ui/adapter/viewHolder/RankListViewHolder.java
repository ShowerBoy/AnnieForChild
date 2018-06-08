package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wanglei on 2018/5/14.
 */

public class RankListViewHolder extends RecyclerView.ViewHolder {
    public ImageView icon, gold, silver, bronze;
    public LinearLayout linear;
    public RelativeLayout relative, more;
    public TextView title, secondName, firstName, thirdName, secondTime, firstTime, thirdTime, secondLike, firstLike, thirdLike;
    public CircleImageView second, first, third;

    public RankListViewHolder(View itemView) {
        super(itemView);
        icon = itemView.findViewById(R.id.rank_earphone);
        title = itemView.findViewById(R.id.ranklist_title);
        more = itemView.findViewById(R.id.rankingList_more);
        gold = itemView.findViewById(R.id.gold);
        silver = itemView.findViewById(R.id.silver);
        bronze = itemView.findViewById(R.id.bronze);
        linear = itemView.findViewById(R.id.ranking_linear);
        relative = itemView.findViewById(R.id.rankingList_relative);
        secondName = itemView.findViewById(R.id.second_name);
        firstName = itemView.findViewById(R.id.first_name);
        thirdName = itemView.findViewById(R.id.third_name);
        secondTime = itemView.findViewById(R.id.second_time);
        firstTime = itemView.findViewById(R.id.first_time);
        thirdTime = itemView.findViewById(R.id.third_time);
        secondLike = itemView.findViewById(R.id.second_like);
        firstLike = itemView.findViewById(R.id.first_like);
        thirdLike = itemView.findViewById(R.id.third_like);
        second = itemView.findViewById(R.id.second_place);
        first = itemView.findViewById(R.id.first_place);
        third = itemView.findViewById(R.id.third_place);
    }
}
