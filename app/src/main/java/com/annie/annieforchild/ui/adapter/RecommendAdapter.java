package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.UserInfo2;
import com.annie.annieforchild.presenter.imp.MainPresenterImp;
import com.annie.annieforchild.ui.adapter.viewHolder.RecommendViewHolder;

import java.util.List;

/**
 * Created by WangLei on 2018/1/16 0016
 * {@link MainPresenterImp#initViewAndData()}
 */

public class RecommendAdapter extends RecyclerView.Adapter<RecommendViewHolder> {
    Context context;
    LayoutInflater inflater;
    private List<UserInfo2> lists;

    public RecommendAdapter(Context context, List<UserInfo2> lists) {
        this.context = context;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecommendViewHolder holder = new RecommendViewHolder(inflater.inflate(R.layout.activity_item_recommend, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecommendViewHolder holder, int position) {
        holder.name_recommend.setText(lists.get(position).getName());
        holder.image_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, position + lists.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
