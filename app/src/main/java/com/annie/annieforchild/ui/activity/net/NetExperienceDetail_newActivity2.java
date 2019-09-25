package com.annie.annieforchild.ui.activity.net;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
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
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.ListenAndRead;
import com.annie.annieforchild.bean.net.experience.ExpItemBean;
import com.annie.annieforchild.bean.net.experience.ExperienceV2;
import com.annie.annieforchild.bean.net.experience.VideoFinishBean;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.activity.lesson.TaskContentActivity;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.annieforchild.ui.adapter.NetExperienceDetailNewAdapter;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * V2体验课
 * Created by wanglei on 2019/4/16.
 */

public class NetExperienceDetail_newActivity2 extends BaseActivity implements ViewInfo, OnCheckDoubleClick {
    private NestedScrollView scrollView;
    private RelativeLayout tyLayout, everyDayLayout;
    private RecyclerView recycler;
    private ImageView back, empty, tyImage;
    private TextView title, tyTitle, everyDayTitle;
    private CheckDoubleClickListener listener;
    private NetWorkPresenter presenter;
    private Dialog dialog;
    private AlertHelper helper;
    private ExperienceV2 experienceV2;
    private List<ExpItemBean> lists;
    private NetExperienceDetailNewAdapter adapter;
    private int netid, tag = 3;
    private String netName;
    private ListenAndRead listenAndRead;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_net_coursedetail_new2;
    }

    @Override
    protected void initView() {
        listener = new CheckDoubleClickListener(this);
        scrollView = findViewById(R.id.coursedetail_net_scrollview);
        recycler = findViewById(R.id.net_coursedetail_recycler);
        back = findViewById(R.id.coursedetail_new_back);
        empty = findViewById(R.id.empty_soon);
        title = findViewById(R.id.coursedetail_new_title);
        tyLayout = findViewById(R.id.ty_layout);
        tyTitle = findViewById(R.id.ty_title);
        tyImage = findViewById(R.id.ty_image);
        everyDayLayout = findViewById(R.id.every_day_layout);
        everyDayTitle = findViewById(R.id.every_day_title);
        back.setOnClickListener(listener);
        tyLayout.setOnClickListener(listener);
        everyDayLayout.setOnClickListener(listener);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(manager);
        recycler.setNestedScrollingEnabled(false);

        netid = getIntent().getIntExtra("netid", 0);
        netName = getIntent().getStringExtra("netName");
        title.setText(netName);
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();

        adapter = new NetExperienceDetailNewAdapter(this, lists, presenter, tag, netid);
        recycler.setAdapter(adapter);


        presenter.experienceDetailsV2(netid);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_EXPERIENCEDETAILSV2) {
            experienceV2 = (ExperienceV2) message.obj;
            if (experienceV2 != null) {
                if (experienceV2.getPlaceholdImg() != null && experienceV2.getPlaceholdImg().length() > 0) {
                    recycler.setVisibility(View.GONE);
                    if (experienceV2.getTyProcess() != null) {
                        if (experienceV2.getTyProcess().getIsshow() == 1) {
                            tyLayout.setVisibility(View.VISIBLE);
                            tyTitle.setText(experienceV2.getTyProcess().getTitle());
                            Glide.with(this).load(experienceV2.getTyProcess().getUrl()).error(R.drawable.online_card_6).into(tyImage);
                        } else {
                            tyLayout.setVisibility(View.GONE);
                        }
                    }
                    if (experienceV2.getTasks() != null) {
                        if (experienceV2.getTasks().getIsshow() == 1) {
                            everyDayLayout.setVisibility(View.VISIBLE);
                            everyDayTitle.setText(experienceV2.getTasks().getTitle());
                        } else {
                            everyDayLayout.setVisibility(View.GONE);
                        }
                    }
//                    scrollView.setVisibility(View.GONE);
//                    empty.setVisibility(View.VISIBLE);
                } else {
                    if (experienceV2.getPlate() != null && experienceV2.getPlate().size() != 0) {
                        scrollView.setVisibility(View.VISIBLE);
                        empty.setVisibility(View.GONE);
                        lists.clear();
                        lists.addAll(experienceV2.getPlate());
                        adapter.notifyDataSetChanged();
                    } else {
                        recycler.setVisibility(View.GONE);
//                        scrollView.setVisibility(View.GONE);
//                        empty.setVisibility(View.VISIBLE);
                    }
                    if (experienceV2.getTyProcess() != null) {
                        if (experienceV2.getTyProcess().getIsshow() == 1) {
                            tyLayout.setVisibility(View.VISIBLE);
                            tyTitle.setText(experienceV2.getTyProcess().getTitle());
                            Glide.with(this).load(experienceV2.getTyProcess().getUrl()).error(R.drawable.online_card_6).into(tyImage);
                        } else {
                            tyLayout.setVisibility(View.GONE);
                        }
                    }
                    if (experienceV2.getTasks() != null) {
                        if (experienceV2.getTasks().getIsshow() == 1) {
                            everyDayLayout.setVisibility(View.VISIBLE);
                            everyDayTitle.setText(experienceV2.getTasks().getTitle());
                        } else {
                            everyDayLayout.setVisibility(View.GONE);
                        }
                    }
                }

            } else {
                scrollView.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
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
            if (experienceV2 != null) {
                experienceV2.getPlate().get(position).setIsfinish(videoFinishBean.getIsfinish());
            }
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.coursedetail_new_back:
                finish();
                break;
            case R.id.ty_layout:
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra("url", experienceV2.getTyProcess().getUrl());
                intent.putExtra("title", experienceV2.getTyProcess().getTitle());
                startActivity(intent);
                break;
            case R.id.every_day_layout:
                Intent intent1 = new Intent(this, EveryDayTaskActivity.class);
                intent1.putExtra("netid", netid);
                intent1.putExtra("netName", netName);
                startActivity(intent1);
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
