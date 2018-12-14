package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.net.Gift;
import com.annie.annieforchild.ui.adapter.viewHolder.GiftViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;

import java.util.List;

/**
 * Created by wanglei on 2018/10/24.
 */

public class GiftAdapter extends RecyclerView.Adapter<GiftViewHolder> {
    private Context context;
    private List<Gift> lists;
    private OnRecyclerItemClickListener listener;
    private LayoutInflater inflater;

    public GiftAdapter(Context context, List<Gift> lists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public GiftViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        GiftViewHolder holder;
        holder = new GiftViewHolder(inflater.inflate(R.layout.activity_gift_item, viewGroup, false));
        holder.itemView.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @Override
            public void onCheckDoubleClick(View view) {
                listener.onItemClick(view);
            }
        }));
        return holder;
    }

    @Override
    public void onBindViewHolder(GiftViewHolder giftViewHolder, int i) {
        giftViewHolder.title.setText(lists.get(i).getName());
        giftViewHolder.content.setText(lists.get(i).getText());
        giftViewHolder.checkBox.setEnabled(false);
        if (lists.get(i).isSelect()) {
            giftViewHolder.checkBox.setChecked(true);
        } else {
            giftViewHolder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
