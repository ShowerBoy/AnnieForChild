package com.annie.annieforchild.ui.fragment.net;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.NetBean;
import com.annie.annieforchild.bean.net.NetWork;
import com.annie.annieforchild.ui.activity.net.NetSuggestActivity;
import com.annie.annieforchild.ui.adapter.NetSuggestAdapter;
import com.annie.baselibrary.base.BaseFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 网课介绍
 * Created by wanglei on 2018/11/14.
 */

public class NetSuggestFragment extends BaseFragment {
    private RecyclerView recycler;
    private ImageView empty;
    private NetSuggestAdapter adapter;
    private List<String> lists;

    {
        setRegister(true);
    }

    public static NetSuggestFragment instance() {
        NetSuggestFragment fragment = new NetSuggestFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        adapter = new NetSuggestAdapter(getContext(), lists);
        recycler.setAdapter(adapter);
    }

    @Override
    protected void initView(View view) {
        recycler = view.findViewById(R.id.net_suggest_recycler);
        empty = view.findViewById(R.id.net_suggest_empty);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(manager);
//        recycler.setNestedScrollingEnabled(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_net_suggest_fragment;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETNETHOMEDATA) {
            NetWork netWork = (NetWork) message.obj;
            if (netWork != null) {
                lists.clear();
                lists.addAll(netWork.getSuggestList());
                if (lists.size() == 0) {
                    empty.setVisibility(View.VISIBLE);
                } else {
                    empty.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }
}
