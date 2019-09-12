package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.bean.net.MyNetTop;
import com.annie.annieforchild.ui.adapter.viewHolder.MyCourseTopViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;

import java.util.List;

/**
 * Created by wanglei on 2019/4/12.
 */

public class MyCourseTopAdapter extends RecyclerView.Adapter<MyCourseTopViewHolder> {
    private Context context;
    private List<MyNetTop> topLists;
    private LayoutInflater inflater;
    private OnRecyclerItemClickListener listener;

    public MyCourseTopAdapter(Context context, List<MyNetTop> topLists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.topLists = topLists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyCourseTopViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MyCourseTopViewHolder holder = null;
        holder = new MyCourseTopViewHolder(inflater.inflate(R.layout.activity_my_course_top, viewGroup, false));
        holder.itemView.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @Override
            public void onCheckDoubleClick(View view) {
                listener.onItemClick(view);
            }
        }));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyCourseTopViewHolder myCourseTopViewHolder, int i) {
        myCourseTopViewHolder.title.setText(topLists.get(i).getCoursename());
    }

    @Override
    public int getItemCount() {
        return topLists.size();
    }
}
