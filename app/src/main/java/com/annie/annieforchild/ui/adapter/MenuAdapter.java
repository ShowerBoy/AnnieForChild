package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.song.SongClassify;
import com.annie.annieforchild.ui.adapter.viewHolder.MenuViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;

import java.util.List;

/**
 * Created by wanglei on 2019/4/18.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<SongClassify> lists;
    private OnRecyclerItemClickListener listener;

    public MenuAdapter(Context context, List<SongClassify> lists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MenuViewHolder holder = null;
        holder = new MenuViewHolder(inflater.inflate(R.layout.activity_menu_item, viewGroup, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MenuViewHolder menuViewHolder, int i) {
        menuViewHolder.text.setText(lists.get(i).getTitle());
        if (lists.get(i).isSelected()) {
            menuViewHolder.text.setTextColor(context.getResources().getColor(R.color.text_orange));
        } else {
            menuViewHolder.text.setTextColor(context.getResources().getColor(R.color.text_black));
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
