package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.course.Course2;
import com.annie.annieforchild.presenter.imp.MainPresenterImp;
import com.annie.annieforchild.ui.adapter.viewHolder.MyCourseViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by WangLei on 2018/1/16 0016
 * {@link MainPresenterImp#initViewAndData()}
 */

public class MyCourseAdapter extends RecyclerView.Adapter<MyCourseViewHolder> {
    private List<Course2> lists;
    LayoutInflater inflater;
    Context context;
    private int screenwidth;

    public MyCourseAdapter(Context context, int screenwidth, List<Course2> lists) {
        this.context = context;
        this.screenwidth = screenwidth;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyCourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_item_mycourse, parent, false);
        view.getLayoutParams().width = screenwidth / 3;
        MyCourseViewHolder viewHolder = new MyCourseViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyCourseViewHolder holder, int position) {
        Glide.with(context).load(lists.get(position).getImageUrl()).fitCenter().into(holder.image_myCourse);
        holder.name_myCourse.setText(lists.get(position).getName());
        holder.image_myCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, position + lists.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }

}
