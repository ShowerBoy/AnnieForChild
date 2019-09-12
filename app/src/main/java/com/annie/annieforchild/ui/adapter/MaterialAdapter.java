package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.bean.material.Material;
import com.annie.annieforchild.ui.adapter.viewHolder.MaterialViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wanglei on 2018/4/24.
 */

public class MaterialAdapter extends RecyclerView.Adapter<MaterialViewHolder> {
    private Context context;
    private List<Material> lists;
    private LayoutInflater inflater;
    private OnRecyclerItemClickListener listener;

    public MaterialAdapter(Context context, List<Material> lists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MaterialViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MaterialViewHolder holder;
        View view = inflater.inflate(R.layout.activity_material_item, viewGroup, false);
        holder = new MaterialViewHolder(view);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onItemClick(v);
//            }
//        });
        holder.itemView.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @Override
            public void onCheckDoubleClick(View view) {
                listener.onItemClick(view);
            }
        }));
        return holder;
    }

    @Override
    public void onBindViewHolder(MaterialViewHolder holder, int i) {
        Glide.with(context).load(lists.get(i).getImageUrl()).into(holder.image);
        holder.text.setText(lists.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
