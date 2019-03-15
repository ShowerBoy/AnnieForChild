package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.net.SpecContent;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.annieforchild.ui.activity.net.NetListenAndReadActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.NetSpecialDetailViewHolder;

import java.util.List;

/**
 * Created by wanglei on 2019/3/13.
 */

public class NetSpecialDetailAdapter extends RecyclerView.Adapter<NetSpecialDetailViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<SpecContent> lists;
    private NetWorkPresenterImp presenter;
    private int color, tag;
    private NetSpecialContentAdapter adapter;

    public NetSpecialDetailAdapter(Context context, List<SpecContent> lists, int color, NetWorkPresenterImp presenter, int tag) {
        this.context = context;
        this.lists = lists;
        this.color = color;
        this.presenter = presenter;
        this.tag = tag;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NetSpecialDetailViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        NetSpecialDetailViewHolder holder = null;
        holder = new NetSpecialDetailViewHolder(inflater.inflate(R.layout.activity_net_special_detail_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(NetSpecialDetailViewHolder netSpecialDetailViewHolder, int i) {
        if (lists.get(i).getIsshow() == 0) {
            netSpecialDetailViewHolder.setVisibility(false);
        } else {
            netSpecialDetailViewHolder.setVisibility(true);
            netSpecialDetailViewHolder.title.setText(lists.get(i).getFcategoryname());
            if (color == 1) {
                netSpecialDetailViewHolder.title.setBackgroundResource(R.drawable.classseed_icon_week);
            } else {
                netSpecialDetailViewHolder.title.setBackgroundResource(R.drawable.classleaf_icon_week);
            }
            if (lists.get(i).getTest() != null && lists.get(i).getTest().size() != 0) {
                netSpecialDetailViewHolder.nsdLayout1.setVisibility(View.VISIBLE);
                netSpecialDetailViewHolder.nsdLayout2.setVisibility(View.VISIBLE);
                netSpecialDetailViewHolder.nsdLayout3.setVisibility(View.VISIBLE);
                netSpecialDetailViewHolder.nsdImage1.setImageResource(R.drawable.class2to3text_icon_practise);
                netSpecialDetailViewHolder.nsdImage2.setImageResource(R.drawable.class3to5_icon_simulation);
                netSpecialDetailViewHolder.nsdImage3.setImageResource(R.drawable.practise2to3_iocn_test);
                netSpecialDetailViewHolder.nsdText1.setText("单元练习");
                netSpecialDetailViewHolder.nsdText2.setText("单元模拟");
                netSpecialDetailViewHolder.nsdText3.setText("单元测试");
            } else {
                netSpecialDetailViewHolder.nsdLayout1.setVisibility(View.VISIBLE);
                netSpecialDetailViewHolder.nsdLayout2.setVisibility(View.VISIBLE);
                netSpecialDetailViewHolder.nsdLayout3.setVisibility(View.INVISIBLE);
                netSpecialDetailViewHolder.nsdImage1.setImageResource(R.drawable.practise2to3_icon_parsing_new);
                netSpecialDetailViewHolder.nsdImage2.setImageResource(R.drawable.practise2to3_icon_resources);
                netSpecialDetailViewHolder.nsdText1.setText("家庭作业");
                netSpecialDetailViewHolder.nsdText2.setText("泛听泛读");
            }
            netSpecialDetailViewHolder.nsdLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lists.get(i).getTest() != null && lists.get(i).getTest().size() != 0) {
                        //单元练习
                        Intent intent = new Intent(context, WebActivity.class);
                        intent.putExtra("url", lists.get(i).getTest().get(0).getFtesturl());
                        intent.putExtra("title", "单元练习");
                        intent.putExtra("flag", 0);//标题是否取消1：取消
                        context.startActivity(intent);
                    } else {
                        //classify  家庭作业:0  泛听泛读：2
                        presenter.getListeningAndReading(lists.get(i).getWeeknum() + "", lists.get(i).getFid(), tag, 0);
                    }
                }
            });
            netSpecialDetailViewHolder.nsdLayout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lists.get(i).getTest() != null && lists.get(i).getTest().size() != 0) {
                        //单元模拟
                        Intent intent = new Intent(context, WebActivity.class);
                        intent.putExtra("url", lists.get(i).getTest().get(1).getFtesturl());
                        intent.putExtra("title", "单元模拟");
                        intent.putExtra("flag", 0);//标题是否取消1：取消
                        context.startActivity(intent);
                    } else {
                        //泛听泛读
                        Intent intent = new Intent(context, NetListenAndReadActivity.class);
                        intent.putExtra("classid", lists.get(i).getFid());
                        intent.putExtra("week", lists.get(i).getWeeknum() + "");
                        context.startActivity(intent);
                    }
                }
            });
            netSpecialDetailViewHolder.nsdLayout3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lists.get(i).getTest() != null && lists.get(i).getTest().size() != 0) {
                        //单元测试
                        Intent intent = new Intent(context, WebActivity.class);
                        intent.putExtra("url", lists.get(i).getTest().get(2).getFtesturl());
                        intent.putExtra("title", "单元测试");
                        intent.putExtra("flag", 0);//标题是否取消1：取消
                        context.startActivity(intent);
                    }
                }
            });
            if (lists.get(i).getInfo() != null) {
                adapter = new NetSpecialContentAdapter(context, lists.get(i).getInfo(), color, lists.get(i).getWeeknum());
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                netSpecialDetailViewHolder.recycler.setLayoutManager(layoutManager);
                netSpecialDetailViewHolder.recycler.setNestedScrollingEnabled(false);
                netSpecialDetailViewHolder.recycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
