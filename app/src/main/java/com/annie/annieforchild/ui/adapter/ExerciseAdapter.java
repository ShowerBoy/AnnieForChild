package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.Exercise;
import com.annie.annieforchild.ui.adapter.viewHolder.ExerciseViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;

import java.util.List;

/**
 * Created by wanglei on 2018/3/30.
 */

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseViewHolder> {
    private Context context;
    private List<Exercise> lists;
    private LayoutInflater inflater;
    private OnRecyclerItemClickListener listener;

    public ExerciseAdapter(Context context, List<Exercise> lists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ExerciseViewHolder holder;
        holder = new ExerciseViewHolder(inflater.inflate(R.layout.activity_exercise_item, viewGroup, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClick(v);
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder exerciseViewHolder, int i) {
        if (lists.get(i).isSelect()) {
            exerciseViewHolder.exerciseLayout.setVisibility(View.VISIBLE);
            exerciseViewHolder.textView.setTextColor(context.getResources().getColor(R.color.text_black));
        } else {
            exerciseViewHolder.exerciseLayout.setVisibility(View.GONE);
            exerciseViewHolder.textView.setTextColor(context.getResources().getColor(R.color.text_color));
        }
        exerciseViewHolder.textView.setText(lists.get(i).getText());
        exerciseViewHolder.preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtils.show(context, "播放" + i);
            }
        });
        exerciseViewHolder.speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtils.show(context, "说话" + i);
            }
        });
        exerciseViewHolder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtils.show(context, "回放" + i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
