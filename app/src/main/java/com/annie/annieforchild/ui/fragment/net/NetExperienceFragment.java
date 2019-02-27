package com.annie.annieforchild.ui.fragment.net;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
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
import com.annie.annieforchild.ui.activity.net.NetConsultActivity;
import com.annie.annieforchild.ui.activity.net.NetFAQActivity;
import com.annie.annieforchild.ui.activity.net.NetSuggestActivity;
import com.annie.annieforchild.ui.adapter.NetBeanAdapter;
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
 * 体验课
 * Created by wanglei on 2018/11/14.
 */

public class NetExperienceFragment extends BaseFragment implements OnCheckDoubleClick {
    private List<NetClass> list;
    private List<String> imglist_bottom, imglist_top;
    private NetBeanAdapter adapter;
    private CheckDoubleClickListener listener;
    private RecyclerView experienceList;
    private ImageView network_consult, network_faq;
    private LinearLayout net_imglayout_bottom;
    private TextView network_teacher_wx;
    private int width;

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
                int position = experienceList.getChildAdapterPosition(view);
                Intent intent = new Intent();
                intent.setClass(getContext(), NetSuggestActivity.class);
                intent.putExtra("netid", list.get(position).getNetId());
                intent.putExtra("netimage", list.get(position).getNetImageUrl());
                intent.putExtra("isBuy", list.get(position).getIsBuy());
                intent.putExtra("message", list.get(position).getMessage());
                intent.putExtra("type", "体验课");
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
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        width = dm.widthPixels;
        net_imglayout_bottom = view.findViewById(R.id.net_imglist_bottom);
        listener = new CheckDoubleClickListener(this);
        network_consult = view.findViewById(R.id.network_consult);
        network_consult.setOnClickListener(listener);
        network_faq = view.findViewById(R.id.network_faq);
        network_faq.setOnClickListener(listener);
        experienceList = view.findViewById(R.id.experienceList);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        experienceList.setLayoutManager(manager);
        experienceList.setNestedScrollingEnabled(false);

        network_teacher_wx = view.findViewById(R.id.network_teacher_wx);

        view.findViewById(R.id.card).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", network_teacher_wx.getText());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                Toast.makeText(getContext(), "复制成功", Toast.LENGTH_SHORT).show();
                return false;
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
                network_teacher_wx.setText(netWork.getExperienceList().getTeacherQrcode());
                list.clear();
                list.addAll(netWork.getExperienceList().getList());
                imglist_top.clear();
                imglist_bottom.clear();
                imglist_bottom.addAll(netWork.getExperienceList().getImageList().getBottom());
                imglist_top.addAll(netWork.getExperienceList().getImageList().getTop());
                adapter.notifyDataSetChanged();
                if (imglist_bottom.size() > 0) {
                    net_imglayout_bottom.removeAllViews();
                    for (int i = 0; i < imglist_bottom.size(); i++) {
                        ImageView imageView = new ImageView(getContext());
                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                        Glide.with(this).load(imglist_bottom.get(i)).asBitmap().into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                int imageWidth = resource.getWidth();
                                int imageHeight = resource.getHeight();
                                int height = width * imageHeight / imageWidth;
                                ViewGroup.LayoutParams para = imageView.getLayoutParams();
                                para.height = height;
                                para.width = width;
                                imageView.setImageBitmap(resource);
                            }
                        });
//                        Glide.with(this).load(imglist_bottom.get(i)).into(imageView);
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
                Intent intent1 = new Intent(getContext(), NetFAQActivity.class);
                intent1.putExtra("title","购课咨询");
                intent1.putExtra("type","consult");
                startActivity(intent1);
                break;
            case R.id.network_faq:
                Intent intent = new Intent(getContext(), NetFAQActivity.class);
                intent.putExtra("title","体验课FAQ");
                intent.putExtra("type","faq");
                startActivity(intent);
                break;
        }
    }

}
