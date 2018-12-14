package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.net.NetDetails;
import com.annie.annieforchild.ui.activity.net.LessonActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.NetDetailsViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wanglei on 2018/10/22.
 */

public class NetDetailsAdapter extends RecyclerView.Adapter<NetDetailsViewHolder> {
    private Context context;
    private List<NetDetails> lists;
    private LayoutInflater inflater;

    public NetDetailsAdapter(Context context, List<NetDetails> lists) {
        this.context = context;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NetDetailsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        NetDetailsViewHolder holder;
        holder = new NetDetailsViewHolder(inflater.inflate(R.layout.activity_net_details_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(NetDetailsViewHolder netDetailsViewHolder, int i) {
        Glide.with(context).load(lists.get(i).getImageUrl()).into(netDetailsViewHolder.image);
        if (lists.get(i).getWeek() == null) {
            netDetailsViewHolder.week.setVisibility(View.GONE);
        } else {
            netDetailsViewHolder.week.setVisibility(View.VISIBLE);
            netDetailsViewHolder.week.setText(lists.get(i).getWeek());
        }
        if (lists.get(i).getName() == null) {
            netDetailsViewHolder.tag.setVisibility(View.GONE);
        } else {
            netDetailsViewHolder.tag.setVisibility(View.VISIBLE);
            netDetailsViewHolder.tag.setText(lists.get(i).getName());
        }

        if (lists.get(i).getLessonList() != null) {
            if (lists.get(i).getLessonList().size() == 0) {
                netDetailsViewHolder.lesson1.setVisibility(View.INVISIBLE);
                netDetailsViewHolder.lesson2.setVisibility(View.INVISIBLE);
                netDetailsViewHolder.lesson3.setVisibility(View.INVISIBLE);
                netDetailsViewHolder.lesson4.setVisibility(View.INVISIBLE);
            } else if (lists.get(i).getLessonList().size() == 1) {
                netDetailsViewHolder.lesson1.setVisibility(View.VISIBLE);
                netDetailsViewHolder.lesson2.setVisibility(View.INVISIBLE);
                netDetailsViewHolder.lesson3.setVisibility(View.INVISIBLE);
                netDetailsViewHolder.lesson4.setVisibility(View.INVISIBLE);
                netDetailsViewHolder.lesson1.setText(lists.get(i).getLessonList().get(0).getLessonName());
            } else if (lists.get(i).getLessonList().size() == 2) {
                netDetailsViewHolder.lesson1.setVisibility(View.VISIBLE);
                netDetailsViewHolder.lesson2.setVisibility(View.VISIBLE);
                netDetailsViewHolder.lesson3.setVisibility(View.INVISIBLE);
                netDetailsViewHolder.lesson4.setVisibility(View.INVISIBLE);
                netDetailsViewHolder.lesson1.setText(lists.get(i).getLessonList().get(0).getLessonName());
                netDetailsViewHolder.lesson2.setText(lists.get(i).getLessonList().get(1).getLessonName());
            } else if (lists.get(i).getLessonList().size() == 3) {
                netDetailsViewHolder.lesson1.setVisibility(View.VISIBLE);
                netDetailsViewHolder.lesson2.setVisibility(View.VISIBLE);
                netDetailsViewHolder.lesson3.setVisibility(View.VISIBLE);
                netDetailsViewHolder.lesson4.setVisibility(View.INVISIBLE);
                netDetailsViewHolder.lesson1.setText(lists.get(i).getLessonList().get(0).getLessonName());
                netDetailsViewHolder.lesson2.setText(lists.get(i).getLessonList().get(1).getLessonName());
                netDetailsViewHolder.lesson3.setText(lists.get(i).getLessonList().get(2).getLessonName());
            } else {
                netDetailsViewHolder.lesson1.setVisibility(View.VISIBLE);
                netDetailsViewHolder.lesson2.setVisibility(View.VISIBLE);
                netDetailsViewHolder.lesson3.setVisibility(View.VISIBLE);
                netDetailsViewHolder.lesson4.setVisibility(View.VISIBLE);
                netDetailsViewHolder.lesson1.setText(lists.get(i).getLessonList().get(0).getLessonName());
                netDetailsViewHolder.lesson2.setText(lists.get(i).getLessonList().get(1).getLessonName());
                netDetailsViewHolder.lesson3.setText(lists.get(i).getLessonList().get(2).getLessonName());
                netDetailsViewHolder.lesson4.setText(lists.get(i).getLessonList().get(3).getLessonName());
            }
        } else {
            netDetailsViewHolder.lesson1.setVisibility(View.INVISIBLE);
            netDetailsViewHolder.lesson2.setVisibility(View.INVISIBLE);
            netDetailsViewHolder.lesson3.setVisibility(View.INVISIBLE);
            netDetailsViewHolder.lesson4.setVisibility(View.INVISIBLE);
        }
        if (lists.get(i).getIsshow() == 0) {
            netDetailsViewHolder.relative.setVisibility(View.GONE);
        } else {
            netDetailsViewHolder.relative.setVisibility(View.VISIBLE);
        }
        netDetailsViewHolder.lesson1.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @Override
            public void onCheckDoubleClick(View view) {
                Intent intent = new Intent(context, LessonActivity.class);
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
                Intent intent = new Intent(context, LessonActivity.class);
                intent.putExtra("lessonId", lists.get(i).getLessonList().get(2).getLessonId());
                intent.putExtra("lessonName", lists.get(i).getLessonList().get(2).getLessonName());
                context.startActivity(intent);
            }
        }));
        netDetailsViewHolder.lesson4.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @Override
            public void onCheckDoubleClick(View view) {
                Intent intent = new Intent(context, LessonActivity.class);
                intent.putExtra("lessonId", lists.get(i).getLessonList().get(3).getLessonId());
                intent.putExtra("lessonName", lists.get(i).getLessonList().get(3).getLessonName());
                context.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
