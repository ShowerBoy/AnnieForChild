package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.radio.RadioBean;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.ui.adapter.viewHolder.RadioViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;

import java.util.List;

import cn.jiguang.analytics.android.api.CountEvent;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by wanglei on 2018/12/14.
 */

public class RadioAdapter extends RecyclerView.Adapter<RadioViewHolder> {
    private Context context;
    private List<RadioBean> lists;
    private RadioItemAdapter adapter;
    private GrindEarPresenter presenter;
    private LayoutInflater inflater;

    public RadioAdapter(Context context, List<RadioBean> lists, GrindEarPresenter presenter) {
        this.context = context;
        this.lists = lists;
        this.presenter = presenter;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RadioViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        RadioViewHolder holder = null;
        holder = new RadioViewHolder(inflater.inflate(R.layout.activity_radio_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RadioViewHolder radioViewHolder, int i) {
        radioViewHolder.title.setText(lists.get(i).getTitle());
        GridLayoutManager manager = new GridLayoutManager(context, 2);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        radioViewHolder.recycler.setLayoutManager(manager);
        adapter = new RadioItemAdapter(context, lists.get(i).getList(), new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = radioViewHolder.recycler.getChildAdapterPosition(view);
                switch (lists.get(i).getTitle()) {
                    case "年龄":
                        CountEvent Event_513 = new CountEvent(MethodCode.A0513);
                        JAnalyticsInterface.onEvent(context, Event_513);
                        presenter.getRadio("age", lists.get(i).getList().get(position).getRadioId());
                        break;
                    case "功能":
                        CountEvent Event_514 = new CountEvent(MethodCode.A0514);
                        JAnalyticsInterface.onEvent(context, Event_514);
                        presenter.getRadio("function", lists.get(i).getList().get(position).getRadioId());
                        break;
                    case "类型":
                        presenter.getRadio("type", lists.get(i).getList().get(position).getRadioId());
                        break;
                    case "主题":
                        CountEvent Event_515 = new CountEvent(MethodCode.A0515);
                        JAnalyticsInterface.onEvent(context, Event_515);
                        presenter.getRadio("theme", lists.get(i).getList().get(position).getRadioId());
                        break;
                    case "系列":
                        presenter.getRadio("series", lists.get(i).getList().get(position).getRadioId());
                        break;
                }
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        radioViewHolder.recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
