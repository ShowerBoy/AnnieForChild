package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/4/19.
 */

public class ChallengeViewHolder extends RecyclerView.ViewHolder {
    public TextView challengeText, challengeScore;

    public ChallengeViewHolder(View itemView) {
        super(itemView);
        challengeText = itemView.findViewById(R.id.challenge_text);
        challengeScore = itemView.findViewById(R.id.challenge_score);
    }
}
