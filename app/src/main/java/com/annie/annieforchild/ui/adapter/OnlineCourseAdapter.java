package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.Course;
import com.annie.annieforchild.ui.adapter.viewHolder.OnlineCourseViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 线上课程
 * Created by wanglei on 2018/3/17.
 */

public class OnlineCourseAdapter extends RecyclerView.Adapter<OnlineCourseViewHolder> {
    private Context context;
    private List<Course> lists;
    private LayoutInflater inflater;
    private OnRecyclerItemClickListener listener;

    public OnlineCourseAdapter(Context context, List<Course> lists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public OnlineCourseViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        OnlineCourseViewHolder holder = null;
        holder = new OnlineCourseViewHolder(inflater.inflate(R.layout.activity_online_course_item, parent, false));
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
    public void onBindViewHolder(OnlineCourseViewHolder holder, int position) {
        Glide.with(context).load(lists.get(position).getImageUrl()).into(holder.courseImage);
        holder.courseText.setText(lists.get(position).getName());
        holder.courseProgress.setText(lists.get(position).getProgress());
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
