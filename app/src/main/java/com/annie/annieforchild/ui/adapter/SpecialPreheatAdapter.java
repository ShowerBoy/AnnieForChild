package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.net.SpecialPreHeat;
import com.annie.annieforchild.ui.adapter.viewHolder.SpecialPreheatViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 综合课预热课
 * Created by wanglei on 2019/3/22.
 */

public class SpecialPreheatAdapter extends RecyclerView.Adapter<SpecialPreheatViewHolder> {
    private Context context;
    private SpecialPreheatItemAdapter adapter;
    private List<SpecialPreHeat> lists;
    private LayoutInflater inflater;

    public SpecialPreheatAdapter(Context context, List<SpecialPreHeat> lists) {
        this.context = context;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public SpecialPreheatViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        SpecialPreheatViewHolder holder = null;
        holder = new SpecialPreheatViewHolder(inflater.inflate(R.layout.activity_special_preheat_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(SpecialPreheatViewHolder specialPreheatViewHolder, int i) {
        specialPreheatViewHolder.preTitle.setText(lists.get(i).getPretitle());
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        specialPreheatViewHolder.recycler.setLayoutManager(manager);
        specialPreheatViewHolder.recycler.setNestedScrollingEnabled(false);
        adapter = new SpecialPreheatItemAdapter(context, lists.get(i).getContent() != null ? lists.get(i).getContent() : new ArrayList<>());
        specialPreheatViewHolder.recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
