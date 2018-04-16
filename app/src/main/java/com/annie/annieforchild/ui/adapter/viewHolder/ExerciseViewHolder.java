package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/3/30.
 */

public class ExerciseViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;
    public RelativeLayout exerciseLayout;
    public ImageView preview, speak, play;

    public ExerciseViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.exercise_text);
        exerciseLayout = itemView.findViewById(R.id.exercise_layout);
        preview = itemView.findViewById(R.id.exercise_preview);
        speak = itemView.findViewById(R.id.exercise_speak);
        play = itemView.findViewById(R.id.exercise_play);
    }
}
