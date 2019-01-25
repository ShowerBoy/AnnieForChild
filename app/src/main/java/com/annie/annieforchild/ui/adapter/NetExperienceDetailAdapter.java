package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.bean.net.NetExpDetails;
import com.annie.annieforchild.bean.net.netexpclass.Info;
import com.annie.annieforchild.bean.net.netexpclass.NetExpClass;
import com.annie.annieforchild.ui.activity.lesson.TaskActivity;
import com.annie.annieforchild.ui.activity.net.LessonActivity;
import com.annie.annieforchild.ui.activity.net.NetExperienceDetailActivity;
import com.annie.annieforchild.ui.activity.net.NetListenAndReadActivity;
import com.annie.annieforchild.ui.activity.net.NetPreheatClassActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.NetExperienceDetailsViewHolder;

import java.util.List;

/**
 *
 */

public class NetExperienceDetailAdapter extends RecyclerView.Adapter<NetExperienceDetailsViewHolder> {
    private Context context;
    private List<Info> lists;
    private LayoutInflater inflater;
    public NetExperienceDetailAdapter(Context context, List<Info> lists ) {
        this.context = context;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NetExperienceDetailsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        NetExperienceDetailsViewHolder holder;
        holder = new NetExperienceDetailsViewHolder(inflater.inflate(R.layout.activity_net_coursedetail_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(NetExperienceDetailsViewHolder netDetailsViewHolder, int i) {
        if (lists.get(i).getWeek() == null) {
            netDetailsViewHolder.week.setVisibility(View.GONE);
        } else {
            netDetailsViewHolder.week.setVisibility(View.VISIBLE);
            netDetailsViewHolder.week.setText(lists.get(i).getWeek());
        }

        netDetailsViewHolder.lesson1.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @Override
            public void onCheckDoubleClick(View view) {
                Intent intent = new Intent(context, NetPreheatClassActivity.class);
                intent.putExtra("lessonId", lists.get(i).getLessonList().get(0).getLessonId());
                intent.putExtra("lessonName", lists.get(i).getLessonList().get(0).getLessonName());
                context.startActivity(intent);
            }
        }));
        netDetailsViewHolder.lesson2.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @Override
            public void onCheckDoubleClick(View view) {
                Intent intent = new Intent(context, LessonActivity.class);
                intent.putExtra("lessonId", lists.get(i).getLessonList().get(1).getLessonId());
                intent.putExtra("lessonName", lists.get(i).getLessonList().get(1).getLessonName());
                context.startActivity(intent);
            }
        }));
        netDetailsViewHolder.lesson3.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @Override
            public void onCheckDoubleClick(View view) {
                Intent intent = new Intent(context, TaskActivity.class);
//                intent.putExtra("lessonId", lists.get(i).getLessonList().get(2).getLessonId());
//                intent.putExtra("lessonName", lists.get(i).getLessonList().get(2).getLessonName());
                context.startActivity(intent);
            }
        }));
        netDetailsViewHolder.lesson4.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @Override
            public void onCheckDoubleClick(View view) {

                Intent intent = new Intent(context, NetListenAndReadActivity.class);
                intent.putExtra("classid",lists.get(i).getClassid());
                intent.putExtra("week", lists.get(i).getWeeknum());
                context.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
