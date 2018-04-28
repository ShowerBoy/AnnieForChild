package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

    public ChallengeAdapter(Context context, List<Line> lists) {
        this.context = context;
        this.lists = lists;
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
        if (lists.get(i).isSelect()) {
            holder.challengeText.setTextColor(context.getResources().getColor(R.color.text_orange));
        } else {
            holder.challengeText.setTextColor(context.getResources().getColor(R.color.text_color));
        }
        holder.challengeText.setText(lists.get(i).getEnTitle());
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
