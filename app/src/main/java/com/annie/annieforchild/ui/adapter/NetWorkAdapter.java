package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.net.NetBean;
import com.annie.annieforchild.ui.activity.net.NetSuggestActivity;
import com.annie.annieforchild.ui.activity.net.NetWorkActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.NetWorkViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;

import java.util.List;

/**
 * Created by wanglei on 2018/9/22.
 */

public class NetWorkAdapter extends RecyclerView.Adapter<NetWorkViewHolder> {
    private Context context;
    private List<NetBean> lists;
    private NetBeanAdapter adapter;
    private LayoutInflater inflater;

    public NetWorkAdapter(Context context, List<NetBean> lists) {
        this.context = context;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NetWorkViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        NetWorkViewHolder holder = null;
        holder = new NetWorkViewHolder(inflater.inflate(R.layout.activity_network_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(NetWorkViewHolder netWorkViewHolder, int i) {
//        netWorkViewHolder.title.setText(lists.get(i).getTitle());
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        netWorkViewHolder.recyclerView.setLayoutManager(manager);
        netWorkViewHolder.recyclerView.setNestedScrollingEnabled(false);
        adapter = new NetBeanAdapter(context, lists.get(i).getList(), new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = netWorkViewHolder.recyclerView.getChildAdapterPosition(view);
//                SystemUtils.show(context, lists.get(i).getList().get(position).getNetName());
                Intent intent = new Intent(context, NetSuggestActivity.class);
                intent.putExtra("netid", lists.get(i).getList().get(position).getNetId());
                intent.putExtra("isBuy", lists.get(i).getList().get(position).getIsBuy());
//                intent.putExtra("type", lists.get(i).getTitle());
                context.startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        netWorkViewHolder.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
