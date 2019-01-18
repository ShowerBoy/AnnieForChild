package com.annie.annieforchild.ui.fragment.net;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.NetClass;
import com.annie.annieforchild.bean.net.NetWork;
import com.annie.annieforchild.ui.activity.net.NetConsultActivity;
import com.annie.annieforchild.ui.activity.net.NetFAQActivity;
import com.annie.annieforchild.ui.activity.net.NetSuggestActivity;
import com.annie.annieforchild.ui.adapter.NetBeanAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.baselibrary.base.BaseFragment;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 体验课
 * Created by wanglei on 2018/11/14.
 */

public class NetExperienceFragment extends BaseFragment implements OnCheckDoubleClick {
    private List<NetClass> list;
    private List<String> imglist_bottom,imglist_top;
    private NetBeanAdapter adapter;
    private CheckDoubleClickListener listener;
    private RecyclerView experienceList;
    private ImageView network_consult,network_faq;
    private LinearLayout net_imglayout_bottom;
    private TextView network_teacher_wx;
    {
        setRegister(true);
    }

    public static NetExperienceFragment instance() {
        NetExperienceFragment fragment = new NetExperienceFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        imglist_bottom = new ArrayList<>();
        imglist_top = new ArrayList<>();
        adapter = new NetBeanAdapter(getContext(), list, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
               int position =experienceList.getChildAdapterPosition(view);
               Intent intent=new Intent();
               intent.setClass(getContext(),NetSuggestActivity.class);
                intent.putExtra("netid",list.get(position).getNetId() );
                intent.putExtra("netimage",list.get(position).getNetImageUrl() );
                intent.putExtra("isBuy", list.get(position).getIsBuy());
                intent.putExtra("type","体验课");
                startActivity(intent);
            }
            @Override
            public void onItemLongClick(View view) {

            }
        });
        experienceList.setAdapter(adapter);
    }

    @Override
    protected void initView(View view) {
        net_imglayout_bottom=view.findViewById(R.id.net_imglist_bottom);
        listener=new CheckDoubleClickListener(this);
        network_consult=view.findViewById(R.id.network_consult);
        network_consult.setOnClickListener(listener);
        network_faq=view.findViewById(R.id.network_faq);
        network_faq.setOnClickListener(listener);
        experienceList=view.findViewById(R.id.experienceList);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        experienceList.setLayoutManager(manager);
        experienceList.setNestedScrollingEnabled(false);

        network_teacher_wx=view.findViewById(R.id.network_teacher_wx);
        network_teacher_wx.setTextIsSelectable(true);

        view.findViewById(R.id.network_teacher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/Aegean_Sea.mp4");
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(uri, "video/mp4");
//                startActivity(intent);

//                  startActivity(new Intent(getContext(),VideoPlayActivity.class));
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_net_experience_fragment;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETNETHOMEDATA) {
            NetWork netWork = (NetWork) message.obj;
            if (netWork != null) {
                network_teacher_wx.setText(netWork.getExperienceList().getTeacherName()+"(长按复制)");
                list.clear();
                list.addAll(netWork.getExperienceList().getList());
                imglist_top.clear();
                imglist_bottom.clear();
                imglist_bottom.addAll(netWork.getExperienceList().getImageList().getBottom());
                imglist_top.addAll(netWork.getExperienceList().getImageList().getTop());

                adapter.notifyDataSetChanged();
                if (imglist_bottom.size()>0) {
                    net_imglayout_bottom.removeAllViews();
                    for (int i = 0; i <imglist_bottom.size(); i++) {
                        ImageView imageView = new ImageView(getContext());
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        Glide.with(this).load(imglist_bottom.get(i)).into(imageView);
                        net_imglayout_bottom.addView(imageView);
                    }
                }
            }
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()){
            case R.id.network_consult:
                Intent intent1 = new Intent(getContext(), NetConsultActivity.class);
                startActivity(intent1);
                break;
            case R.id.network_faq:
                Intent intent = new Intent(getContext(), NetFAQActivity.class);
                startActivity(intent);
                break;
        }
    }
}
