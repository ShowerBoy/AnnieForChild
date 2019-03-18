package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.bean.net.Game;
import com.annie.annieforchild.ui.adapter.viewHolder.LessonViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;

import java.util.List;

/**
 * Created by wanglei on 2018/10/22.
 */

public class LessonAdapter extends RecyclerView.Adapter<LessonViewHolder> {
    private Context context;
    private List<Game> lists;
    private OnRecyclerItemClickListener listener;
    private LayoutInflater inflater;

    public LessonAdapter(Context context, List<Game> lists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public LessonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LessonViewHolder holder;
        holder = new LessonViewHolder(inflater.inflate(R.layout.activity_lesson_item, viewGroup, false));
        holder.itemView.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @Override
            public void onCheckDoubleClick(View view) {
                listener.onItemClick(view);
            }
        }));
        return holder;
    }

    @Override
    public void onBindViewHolder(LessonViewHolder lessonViewHolder, int i) {
        lessonViewHolder.name.setText(lists.get(i).getGameName());
        if (lists.get(i).getColor() == 1) {
            lessonViewHolder.lesson_circle.setImageResource(R.drawable.classa_icon_03);
        } else if (lists.get(i).getColor() == 2) {
            lessonViewHolder.lesson_circle.setImageResource(R.drawable.classa_icon_08);
        } else if (lists.get(i).getColor() == 3) {
            lessonViewHolder.lesson_circle.setImageResource(R.drawable.classa_icon_05);
        } else if (lists.get(i).getColor() == 4) {
            lessonViewHolder.lesson_circle.setImageResource(R.drawable.classa_icon_09);
        } else if (lists.get(i).getColor() == 5) {
            lessonViewHolder.lesson_circle.setImageResource(R.drawable.classa_icon_04);
        } else if (lists.get(i).getColor() == 6) {
            lessonViewHolder.lesson_circle.setImageResource(R.drawable.classa_icon_06);
        } else if (lists.get(i).getColor() == 7) {
            lessonViewHolder.lesson_circle.setImageResource(R.drawable.classa_icon_01);
        } else {
            lessonViewHolder.lesson_circle.setImageResource(R.drawable.classa_icon_01);
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
