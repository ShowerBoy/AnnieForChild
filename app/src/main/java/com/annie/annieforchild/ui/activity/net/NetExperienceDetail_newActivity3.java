package com.annie.annieforchild.ui.activity.net;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.views.AutoScaleWidthImageView;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.ListenAndRead;
import com.annie.annieforchild.bean.net.experience.ExpItemBeanV3;
import com.annie.annieforchild.bean.net.experience.ExperienceV3;
import com.annie.annieforchild.bean.net.experience.VideoFinishBean;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.activity.lesson.TaskContentActivity;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.annieforchild.ui.adapter.NetExperienceDetailNewAdapterV3;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * V3体验课
 */

public class NetExperienceDetail_newActivity3 extends BaseActivity implements ViewInfo, OnCheckDoubleClick {
    private RecyclerView recycler;
    private ImageView back, empty, tyImage;
    private TextView title;
    private CheckDoubleClickListener listener;
    private NetWorkPresenter presenter;
    private Dialog dialog;
    private AlertHelper helper;
    private ExperienceV3 experienceV3;
    private List<ExpItemBeanV3> lists;
    private NetExperienceDetailNewAdapterV3 adapter;
    private int netid, tag = 3;
    private String netName;
    private ListenAndRead listenAndRead;
    private AutoScaleWidthImageView net_exp_v3_backlayout;
    private ImageView typrogess,totest;
    private int beeint=0;
    private ImageView loading;
    private RelativeLayout back_bottom;
    private ImageView back_bottom_img;
    private Context context;
    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_net_coursedetail_new3;
    }

    @Override
    protected void initView() {
        context=this;
        back_bottom=findViewById(R.id.back_bottom);
        back_bottom_img=findViewById(R.id.back_bottom_img);
        loading=findViewById(R.id.loading);
        typrogess=findViewById(R.id.typrogess);
        totest=findViewById(R.id.totest);
        net_exp_v3_backlayout=findViewById(R.id.net_exp_v3_backlayout);
        listener = new CheckDoubleClickListener(this);
        recycler = findViewById(R.id.net_coursedetail_recycler);
        back = findViewById(R.id.coursedetail_new_back);
        empty = findViewById(R.id.empty_soon);
        title = findViewById(R.id.coursedetail_new_title);
        back.setOnClickListener(listener);
        typrogess.setOnClickListener(listener);
        totest.setOnClickListener(listener);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(manager);
        recycler.setNestedScrollingEnabled(false);

        netid = getIntent().getIntExtra("netid", 0);
        netName = getIntent().getStringExtra("netName");
        title.setText(netName);
        loading.setVisibility(View.VISIBLE);
        loading.setImageResource(R.drawable.been_loading_animal); //图片资源
        AnimationDrawable animationDrawable = (AnimationDrawable) loading.getDrawable();
        animationDrawable.start();
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();

        adapter = new NetExperienceDetailNewAdapterV3(this, lists, presenter, tag, netid,beeint);
        recycler.setAdapter(adapter);

        presenter.experienceDetailsV3(netid);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    void fresh(){
//        dialog.show();
        loading.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(experienceV3.getBackground_img())
                .listener(new RequestListener() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                        loading.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                        loading.setVisibility(View.GONE);
                        back_bottom.setVisibility(View.VISIBLE);
                        Glide.with(context).load(experienceV3.getBottom_img()).into(back_bottom_img);
                        recycler.setVisibility(View.VISIBLE);
                        net_exp_v3_backlayout.setVisibility(View.VISIBLE);
                        typrogess.setVisibility(View.VISIBLE);
                        totest.setVisibility(View.VISIBLE);
//                        btn_make_insurance_plan.setVisibility(View.VISIBLE);
                        return false;
                    }

                })
                .into(net_exp_v3_backlayout);
//        Glide.with(this).load(experienceV3.getBackground_img()).into(net_exp_v3_backlayout);
        Glide.with(this).load(experienceV3.getJoinTest().getImage()).into(totest);
        Glide.with(this).load(experienceV3.getTyProcess().getImage()).into(typrogess);
        beeint=experienceV3.getLocation_bee();
        if (experienceV3.getPlate() != null && experienceV3.getPlate().size() != 0) {
            empty.setVisibility(View.GONE);
            lists.clear();
            lists.addAll(experienceV3.getPlate());
            adapter.setint(beeint);
            adapter.notifyDataSetChanged();
        }
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_EXPERIENCEDETAILSV3) {
            experienceV3 = (ExperienceV3) message.obj;
            if (experienceV3 != null) {
                fresh();


            }
        } else if (message.what == MethodCode.EVENT_GETLISTENANDREAD + 80000 + tag) {
            listenAndRead = (ListenAndRead) message.obj;
            if (listenAndRead.getIsshow() == 0) {
                showInfo("暂无家庭作业");
            } else {
                Intent intent = new Intent(this, TaskContentActivity.class);
                intent.putExtra("classid", listenAndRead.getClassid());
                intent.putExtra("courseType", listenAndRead.getCourseType());
                intent.putExtra("type", listenAndRead.getType());
                intent.putExtra("week", listenAndRead.getWeek());
                intent.putExtra("tabPosition", 0);
                startActivity(intent);
            }
        } else if (message.what == MethodCode.EVENT_REFRESH) {
            presenter.experienceDetailsV2(netid);
        } else if (message.what == MethodCode.EVENT_VIDEOPAYRECORD) {
            VideoFinishBean videoFinishBean = (VideoFinishBean) message.obj;
            int position = (int) message.obj2;
            if (experienceV3 != null) {
                experienceV3.getPlate().get(position).setIsfinish(videoFinishBean.getIsfinish()+"");
            }
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.coursedetail_new_back:
                finish();
                break;
            case R.id.typrogess:
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra("url", experienceV3.getTyProcess().getUrl());
                intent.putExtra("title", experienceV3.getTyProcess().getTitle());
                startActivity(intent);
                break;
            case R.id.every_day_layout:
                Intent intent1 = new Intent(this, EveryDayTaskActivity.class);
                intent1.putExtra("netid", netid);
                intent1.putExtra("netName", netName);
                startActivity(intent1);
                break;
            case R.id.totest:
                Intent intent2 = new Intent(this, WebActivity.class);
                intent2.putExtra("url", experienceV3.getJoinTest().getUrl());
                intent2.putExtra("title", "入学测试");
                startActivity(intent2);
                break;
        }
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void dismissLoad() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
