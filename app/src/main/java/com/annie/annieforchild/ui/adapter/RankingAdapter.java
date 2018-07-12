package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.rank.Rank;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.ui.adapter.viewHolder.RankViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wanglei on 2018/5/29.
 */

public class RankingAdapter extends RecyclerView.Adapter<RankViewHolder> {
    private Context context;
    private List<Rank> lists;
    private GrindEarPresenter presenter;
    private LayoutInflater inflater;

    public RankingAdapter(Context context, List<Rank> lists, GrindEarPresenter presenter) {
        this.context = context;
        this.lists = lists;
        this.presenter = presenter;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RankViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        RankViewHolder holder;
        View view = inflater.inflate(R.layout.activity_ranking_item, viewGroup, false);
        holder = new RankViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RankViewHolder rankViewHolder, int i) {
        if (i == 0) {
            rankViewHolder.medal.setVisibility(View.VISIBLE);
            rankViewHolder.medal.setImageResource(R.drawable.icon_gold_medal);
            rankViewHolder.hexagon.setVisibility(View.GONE);
            rankViewHolder.rank_number.setVisibility(View.GONE);
        } else if (i == 1) {
            rankViewHolder.medal.setVisibility(View.VISIBLE);
            rankViewHolder.medal.setImageResource(R.drawable.icon_silver_medal);
            rankViewHolder.hexagon.setVisibility(View.GONE);
            rankViewHolder.rank_number.setVisibility(View.GONE);
        } else if (i == 2) {
            rankViewHolder.medal.setVisibility(View.VISIBLE);
            rankViewHolder.medal.setImageResource(R.drawable.icon_bronze_medal);
            rankViewHolder.hexagon.setVisibility(View.GONE);
            rankViewHolder.rank_number.setVisibility(View.GONE);
        } else {
            rankViewHolder.medal.setVisibility(View.GONE);
            rankViewHolder.hexagon.setVisibility(View.VISIBLE);
            rankViewHolder.rank_number.setVisibility(View.VISIBLE);
            rankViewHolder.rank_number.setText(lists.get(i).getRow_number() + "");
        }
        Glide.with(context).load(lists.get(i).getAvatar()).error(R.drawable.icon_system_photo).into(rankViewHolder.headpic);
        rankViewHolder.name.setText(lists.get(i).getName());
        int second = Integer.parseInt(lists.get(i).getDuration());
        int duration = Integer.parseInt(lists.get(i).getDuration()) / 60;
        if (duration > 60) {
            int hour = duration / 60;
            int min = duration % 60;
            rankViewHolder.duration.setText(hour + "小时" + min + "分钟");
        } else if (duration == 0) {
            rankViewHolder.duration.setText(second + "秒");
        } else {
            rankViewHolder.duration.setText(duration + "分钟");
        }
        if (lists.get(i).getIsLiked() == 0) {
            rankViewHolder.like.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(context, R.drawable.icon_like_f), null, null);
//            rankViewHolder.like.setCompoundDrawables(null, ContextCompat.getDrawable(context, R.drawable.icon_like_f), null, null);
            rankViewHolder.like.setText(lists.get(i).getLikeCount() + "");
        } else {
            rankViewHolder.like.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(context, R.drawable.icon_like_t), null, null);
//            rankViewHolder.like.setCompoundDrawables(null, ContextCompat.getDrawable(context, R.drawable.icon_like_t), null, null);
            rankViewHolder.like.setText(lists.get(i).getLikeCount() + "");
        }
        rankViewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lists.get(i).getIsLiked() == 0) {
                    presenter.likeStudent(lists.get(i).getUsername());
                } else {
                    presenter.cancelLikeStudent(lists.get(i).getUsername());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
