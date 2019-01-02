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
        int remind = i % 8;
        if (remind == 0) {
            lessonViewHolder.layout.setBackgroundResource(R.drawable.icon_blue_card);
        } else if (remind == 1) {
            lessonViewHolder.layout.setBackgroundResource(R.drawable.icon_mint_green_card);
        } else if (remind == 2) {
            lessonViewHolder.layout.setBackgroundResource(R.drawable.icon_green_card);
        } else if (remind == 3) {
            lessonViewHolder.layout.setBackgroundResource(R.drawable.icon_yellow_card);
        } else if (remind == 4) {
            lessonViewHolder.layout.setBackgroundResource(R.drawable.icon_orange_card);
        } else if (remind == 5) {
            lessonViewHolder.layout.setBackgroundResource(R.drawable.icon_rose_card);
        } else if (remind == 6) {
            lessonViewHolder.layout.setBackgroundResource(R.drawable.icon_pink_card);
        } else if (remind == 7) {
            lessonViewHolder.layout.setBackgroundResource(R.drawable.icon_purple_card);
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}