package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.book.Line;
import com.annie.annieforchild.ui.adapter.viewHolder.ChallengeViewHolder;

import java.util.List;

/**
 * Created by wanglei on 2018/4/19.
 */

public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeViewHolder> {
    private Context context;
    private List<Line> lists;
    private LayoutInflater inflater;
    private int isPk;//0:pk 1:challenge

    public ChallengeAdapter(Context context, List<Line> lists, int isPk) {
        this.context = context;
        this.lists = lists;
        this.isPk = isPk;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ChallengeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ChallengeViewHolder holder;
        holder = new ChallengeViewHolder(inflater.inflate(R.layout.activity_challenge_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ChallengeViewHolder holder, int i) {
        if (lists.get(i).isSelfLine()) {
            holder.challengeText.setTextColor(context.getResources().getColor(R.color.clarity_black));
            holder.challengeScore.setTextColor(context.getResources().getColor(R.color.clarity_black));
        } else {
            holder.challengeText.setTextColor(context.getResources().getColor(R.color.text_light_color));
            holder.challengeScore.setTextColor(context.getResources().getColor(R.color.text_light_color));
        }
        if (lists.get(i).isSelect()) {
            holder.challengeText.setTextColor(context.getResources().getColor(R.color.text_orange));
            holder.challengeScore.setTextColor(context.getResources().getColor(R.color.text_orange));
        }
        holder.challengeText.setText(lists.get(i).getEnTitle());
        if (isPk == 0) {
            if (lists.get(i).isScoreShow()) {
                holder.challengeScore.setVisibility(View.VISIBLE);
                if (lists.get(i).isSelfLine()) {
                    String my = lists.get(i).getScore() * 20f + "";
                    holder.challengeScore.setText(my.split("\\.")[0] + "分");
                } else {
                    String pk = lists.get(i).getPkScore() * 20f + "";
                    holder.challengeScore.setText(pk.split("\\.")[0] + "分");
                }
            } else {
                holder.challengeScore.setVisibility(View.INVISIBLE);
            }
        } else {
            holder.challengeScore.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
