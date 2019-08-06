package com.annie.annieforchild.ui.fragment.net;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.NetClass;
import com.annie.annieforchild.bean.net.NetWork;
import com.annie.annieforchild.ui.activity.net.NetFAQActivity;
import com.annie.annieforchild.ui.activity.net.NetSuggestActivity;
import com.annie.annieforchild.ui.adapter.NetBeanAdapter;
import com.annie.annieforchild.ui.adapter.NetSpecialAdapter;
import com.annie.annieforchild.ui.adapter.NetSpecialAdapter1;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.baselibrary.base.BaseFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 专项课
 * Created by wanglei on 2018/11/14.
 */

public class NetSpecialFragment extends BaseFragment implements OnCheckDoubleClick {
    private List<NetClass> list;
    private List<String> imglist_bottom;
    private List<String> list_center;
    private NetSpecialAdapter1 adapter;
    private CheckDoubleClickListener listener;
    private RecyclerView specialList;
    private ImageView network_consult, network_faq;
    private int width;
    private ImageView net_suggest_intro;


    {
        setRegister(true);
    }

    public static NetSpecialFragment instance() {
        NetSpecialFragment fragment = new NetSpecialFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        imglist_bottom = new ArrayList<>();
        list_center = new ArrayList<>();
        adapter = new NetSpecialAdapter1(getContext(), list, imglist_bottom,list_center);
        specialList.setAdapter(adapter);
    }

    @Override
    protected void initView(View view) {
        net_suggest_intro=view.findViewById(R.id.net_suggest_intro);
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        width = dm.widthPixels;
        listener = new CheckDoubleClickListener(this);
        network_consult = view.findViewById(R.id.network_consult);
        network_consult.setOnClickListener(listener);
        network_faq = view.findViewById(R.id.network_faq);
        network_faq.setOnClickListener(listener);
        specialList = view.findViewById(R.id.specialList);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setSmoothScrollbarEnabled(true);
        manager.setAutoMeasureEnabled(true);
        specialList.setLayoutManager(manager);
        specialList.setNestedScrollingEnabled(false);
        specialList.setHasFixedSize(true);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_net_special_fragment;
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.network_consult:
                Intent intent1 = new Intent(getContext(), NetFAQActivity.class);
                intent1.putExtra("title", "购课咨询");
                intent1.putExtra("type", "specialConsult");
                startActivity(intent1);
                break;
            case R.id.network_faq:
                Intent intent = new Intent(getContext(), NetFAQActivity.class);
                intent.putExtra("title", "综合课FAQ");
                intent.putExtra("type", "specialFAQ");
                startActivity(intent);
                break;
        }
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETNETHOMEDATA) {
            NetWork netWork = (NetWork) message.obj;
            if (netWork != null) {
                if (netWork.getSpeciaList() != null) {
                    list_center.clear();
                    list_center.add(netWork.getSpeciaList().getTeacherQrcode());
                    if(netWork.getSpeciaList().getImageDiscount()!=null && netWork.getSpeciaList().getImageDiscount().length()>0){
                        Glide.with(this).load(netWork.getSpeciaList().getImageDiscount()).into(net_suggest_intro);
                    }
                    list.clear();
                    list.addAll(netWork.getSpeciaList().getList());
                    if (netWork.getSpeciaList().getImageList() != null) {
                        if (netWork.getSpeciaList().getImageList().getBottom() != null && netWork.getSpeciaList().getImageList().getBottom().size() >= 0) {
                            imglist_bottom.clear();
                            imglist_bottom.addAll(netWork.getSpeciaList().getImageList().getBottom());
                            adapter.notifyDataSetChanged();
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
