package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.ui.adapter.viewHolder.GrindEarViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wanglei on 2018/8/28.
 */

public class GrindEarAdapter extends RecyclerView.Adapter<GrindEarViewHolder> {
    private Context context;
    private List<Song> lists;
    private LayoutInflater inflater;
    private OnRecyclerItemClickListener listener;

    public GrindEarAdapter(Context context, List<Song> lists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public GrindEarViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        GrindEarViewHolder holder = null;
        holder = new GrindEarViewHolder(inflater.inflate(R.layout.activity_grind_ear_item, viewGroup, false));
        holder.itemView.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @Override
            public void onCheckDoubleClick(View view) {
                listener.onItemClick(view);
            }
        }));
        return holder;
    }

    @Override
    public void onBindViewHolder(GrindEarViewHolder grindEarViewHolder, int i) {
        grindEarViewHolder.recommentText.setText(lists.get(i).getBookName());
        Glide.with(context).load(lists.get(i).getBookImageUrl()).error(R.drawable.icon_system_photo).into(grindEarViewHolder.recommentImage);
        ;
        if (lists.get(i).getJurisdiction() == 0) {
            grindEarViewHolder.lock.setVisibility(View.GONE);
            if (lists.get(i).getIsusenectar() == 0) {
                grindEarViewHolder.lock.setImageResource(R.drawable.icon_lock_book_f);
            } else {
                grindEarViewHolder.lock.setImageResource(R.drawable.icon_lock_book_t);
            }
        } else {
            grindEarViewHolder.lock.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
