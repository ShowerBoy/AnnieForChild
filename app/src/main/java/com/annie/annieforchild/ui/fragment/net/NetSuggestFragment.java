package com.annie.annieforchild.ui.fragment.net;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.NetWork;
import com.annie.annieforchild.ui.adapter.NetSuggestAdapter1;
import com.annie.baselibrary.base.BaseFragment;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 网课介绍
 * Created by wanglei on 2018/11/14.
 */

public class NetSuggestFragment extends BaseFragment implements OnCheckDoubleClick {
    private RecyclerView recycler;
    private LinearLayout recycler_bottom;
    private ImageView empty;
    private NetSuggestAdapter1 adapter;
    List<String> list_top,list_middle,list_bottom;
    CheckDoubleClickListener listener;

    {
        setRegister(true);
    }

    public static NetSuggestFragment instance() {
        NetSuggestFragment fragment = new NetSuggestFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        list_top=new ArrayList<>();
        list_middle=new ArrayList<>();
        list_bottom=new ArrayList<>();
        adapter = new NetSuggestAdapter1(getContext(), list_top,list_middle);
        recycler.setAdapter(adapter);

    }

    @Override
    protected void initView(View view) {
        recycler = view.findViewById(R.id.net_suggest_recycler);
        recycler_bottom = view.findViewById(R.id.net_suggest_recycler_bottom);
        empty = view.findViewById(R.id.net_suggest_empty);
        listener = new CheckDoubleClickListener(this);
//        LinearLayoutManager manager = new LinearLayoutManager(getContext());
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
//        recycler.setLayoutManager(manager);
//        recycler.setNestedScrollingEnabled(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setHasFixedSize(true);
        recycler.setNestedScrollingEnabled(false);

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
                list_top.clear();
                list_top.addAll(netWork.getSuggestList().getTop());
                list_middle.clear();
                list_middle.addAll(netWork.getSuggestList().getMiddle());
                list_bottom.clear();
                list_bottom.addAll(netWork.getSuggestList().getBottom());
                if (list_top.size()+list_bottom.size()+list_middle.size() == 0) {
                    empty.setVisibility(View.VISIBLE);
                } else {
                    empty.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
//                pageradapter.notifyDataSetChanged();

                recycler_bottom.removeAllViews();

                for(int i=0;i<list_bottom.size();i++){
                    ImageView imageView = new ImageView(getContext());
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    Glide.with(this).load(list_bottom.get(i)).into(imageView);
                    recycler_bottom.addView(imageView);
                }
            }
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {

        }
    }


}
