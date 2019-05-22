package com.annie.annieforchild.ui.activity.net;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.book.Line;
import com.annie.annieforchild.bean.net.ListenAndRead;
import com.annie.annieforchild.bean.net.NetClass;
import com.annie.annieforchild.bean.net.NetExpDetails;
import com.annie.annieforchild.bean.net.netexpclass.FirstStageitem;
import com.annie.annieforchild.bean.net.netexpclass.Info;
import com.annie.annieforchild.bean.net.netexpclass.NetExpClass;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.activity.VideoActivity;
import com.annie.annieforchild.ui.activity.lesson.TaskContentActivity;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.annieforchild.ui.adapter.NetExperienceDetailAdapter;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetExperienceDetailActivity extends BaseActivity implements ViewInfo, OnCheckDoubleClick {
    CheckDoubleClickListener listner;
    private ImageView back, bottomLayout;
    private RecyclerView net_coursedetail_recyclerview;
    private Dialog dialog;
    private AlertHelper helper;
    NetWorkPresenterImp presenter;
    List<Info> lists;
    NetExperienceDetailAdapter adapter;
    private ListenAndRead listenAndRead;
    TextView title;
    ImageView video_img;
    public static NetExpClass netExpClass;
    int netid, tag = 0;

    private RelativeLayout empty_layout;
    private ImageView empty_soon;
    private ConstraintLayout firstsatge;
    private ConstraintLayout fourstage;
    private LinearLayout firstsatge_1, firstsatge_4, firstsatge_2, firstsatge_3;
    private LinearLayout fourstage_1, fourstage_2, fourstage_3;
    private TextView firstsatge_1_name, firstsatge_2_name, firstsatge_3_name, firstsatge_4_name;
    private ImageView firstsatge_1_img, firstsatge_2_img, firstsatge_3_img, firstsatge_4_img;
    NestedScrollView listView;
    List<FirstStageitem> firstStagelist;
    int num=0;
    int num_type=0;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_net_coursedetail;
    }

    @Override
    protected void initView() {
        listView = findViewById(R.id.list_layout);
        listner = new CheckDoubleClickListener(this);
        title = findViewById(R.id.title);
        empty_layout = findViewById(R.id.empty_layout);
        empty_soon = findViewById(R.id.empty_soon);
        bottomLayout = findViewById(R.id.bottom_layout);
        back = findViewById(R.id.back);
        back.setOnClickListener(listner);
        net_coursedetail_recyclerview = findViewById(R.id.net_coursedetail_recyclerview);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        net_coursedetail_recyclerview.setLayoutManager(manager);
        net_coursedetail_recyclerview.setNestedScrollingEnabled(false);
//        listeningtext_card_roport = findViewById(R.id.listeningtext_card_roport);
//        netclass_video_content = (TextView) findViewById(R.id.netclass_video_content);
//        netclass_video_title = (TextView) findViewById(R.id.netclass_video_title);

        String netname = getIntent().getStringExtra("netName");
        netid = getIntent().getIntExtra("netid", 0);
        title.setText(netname);
        video_img = findViewById(R.id.video_img);
        firstsatge = findViewById(R.id.firstsatge);
        firstsatge_1 = findViewById(R.id.firstsatge_1);
        firstsatge_2 = findViewById(R.id.firstsatge_2);
        firstsatge_3 = findViewById(R.id.firstsatge_3);
        firstsatge_4 = findViewById(R.id.firstsatge_4);
        firstsatge_1_name = findViewById(R.id.firstsatge_1_name);
        firstsatge_2_name = findViewById(R.id.firstsatge_2_name);
        firstsatge_3_name = findViewById(R.id.firstsatge_3_name);
        firstsatge_4_name = findViewById(R.id.firstsatge_4_name);
        firstsatge_1_img = findViewById(R.id.firstsatge_1_img);
        firstsatge_2_img = findViewById(R.id.firstsatge_2_img);
        firstsatge_3_img = findViewById(R.id.firstsatge_3_img);
        firstsatge_4_img = findViewById(R.id.firstsatge_4_img);
        fourstage = findViewById(R.id.fourstage);
        firstsatge_1.setOnClickListener(listner);
        firstsatge_2.setOnClickListener(listner);
        firstsatge_3.setOnClickListener(listner);
        firstsatge_4.setOnClickListener(listner);
        fourstage_1 = findViewById(R.id.fourstage_1);
        fourstage_2 = findViewById(R.id.fourstage_2);
        fourstage_3 = findViewById(R.id.fourstage_3);
        fourstage_1.setOnClickListener(listner);
        fourstage_2.setOnClickListener(listner);
        fourstage_3.setOnClickListener(listner);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();

        lists = new ArrayList<>();
//        netExpClass=new NetExpClass();

        presenter = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getNetExpDetails(netid);

        adapter = new NetExperienceDetailAdapter(this, lists, presenter, tag);
        net_coursedetail_recyclerview.setAdapter(adapter);

    }

    void refresh() {
        firstStagelist=new ArrayList<>();
        firstStagelist.add(netExpClass.getFirststage().getQuestionnaire());
        firstStagelist.add(netExpClass.getFirststage().getWeiclass());
        firstStagelist.add(netExpClass.getFirststage().getClassanalysis());
        firstStagelist.add(netExpClass.getFirststage().getCommonproblem());

        for(int i=0;i<firstStagelist.size();i++){
            num+=firstStagelist.get(i).getIsshow();
        }
        if(num==2){
            firstsatge_1.setVisibility(View.VISIBLE);
            firstsatge_2.setVisibility(View.VISIBLE);
            firstsatge_3.setVisibility(View.GONE);
            firstsatge_4.setVisibility(View.GONE);
            if(firstStagelist.get(0).getIsshow()==1 && firstStagelist.get(2).getIsshow()==1){
                firstsatge_1_name.setText(firstStagelist.get(0).getName());
                firstsatge_2_name.setText(firstStagelist.get(2).getName());
                setbackground(firstsatge_2_img,2);
                num_type=1;
            }else if(firstStagelist.get(0).getIsshow()==1 && firstStagelist.get(3).getIsshow()==1){
                firstsatge_1_name.setText(firstStagelist.get(0).getName());
                firstsatge_2_name.setText(firstStagelist.get(3).getName());
                setbackground(firstsatge_2_img,3);
                num_type=2;
            }else if(firstStagelist.get(1).getIsshow()==1 && firstStagelist.get(2).getIsshow()==1){
                firstsatge_1_name.setText(firstStagelist.get(1).getName());
                firstsatge_2_name.setText(firstStagelist.get(2).getName());
                setbackground(firstsatge_1_img,1);
                setbackground(firstsatge_2_img,2);
                num_type=3;
            }else if(firstStagelist.get(1).getIsshow()==1 && firstStagelist.get(3).getIsshow()==1){
                firstsatge_1_name.setText(firstStagelist.get(1).getName());
                firstsatge_2_name.setText(firstStagelist.get(3).getName());
                setbackground(firstsatge_1_img,1);
                setbackground(firstsatge_2_img,3);
                num_type=4;
            }
        }else if(num==3){
            firstsatge_1.setVisibility(View.VISIBLE);
            firstsatge_2.setVisibility(View.VISIBLE);
            firstsatge_3.setVisibility(View.VISIBLE);
            firstsatge_4.setVisibility(View.GONE);
            if(firstStagelist.get(0).getIsshow()==0){
                firstsatge_1_name.setText(firstStagelist.get(1).getName());
                firstsatge_2_name.setText(firstStagelist.get(2).getName());
                firstsatge_3_name.setText(firstStagelist.get(3).getName());
                setbackground(firstsatge_1_img,1);
                setbackground(firstsatge_2_img,2);
                setbackground(firstsatge_3_img,3);
            }else if(firstStagelist.get(1).getIsshow()==0){
                firstsatge_1_name.setText(firstStagelist.get(0).getName());
                firstsatge_2_name.setText(firstStagelist.get(2).getName());
                firstsatge_3_name.setText(firstStagelist.get(3).getName());
                setbackground(firstsatge_1_img,0);
                setbackground(firstsatge_2_img,2);
                setbackground(firstsatge_3_img,3);
            }else if(firstStagelist.get(2).getIsshow()==0){
                firstsatge_1_name.setText(firstStagelist.get(0).getName());
                firstsatge_2_name.setText(firstStagelist.get(1).getName());
                firstsatge_3_name.setText(firstStagelist.get(3).getName());
                setbackground(firstsatge_1_img,0);
                setbackground(firstsatge_2_img,1);
                setbackground(firstsatge_3_img,3);
            }else if(firstStagelist.get(3).getIsshow()==0){
                firstsatge_1_name.setText(firstStagelist.get(0).getName());
                firstsatge_2_name.setText(firstStagelist.get(1).getName());
                firstsatge_3_name.setText(firstStagelist.get(2).getName());
                setbackground(firstsatge_1_img,0);
                setbackground(firstsatge_2_img,1);
                setbackground(firstsatge_3_img,2);
            }
        } else{
            layoutIsShow(firstsatge_1, netExpClass.getFirststage().getQuestionnaire().getIsshow());
            layoutIsShow(firstsatge_2, netExpClass.getFirststage().getWeiclass().getIsshow());
            layoutIsShow(firstsatge_3, netExpClass.getFirststage().getClassanalysis().getIsshow());
            layoutIsShow(firstsatge_4, netExpClass.getFirststage().getCommonproblem().getIsshow());
            firstsatge_1_name.setText(netExpClass.getFirststage().getQuestionnaire().getName());
            firstsatge_2_name.setText(netExpClass.getFirststage().getWeiclass().getName());
            firstsatge_3_name.setText(netExpClass.getFirststage().getClassanalysis().getName());
            firstsatge_4_name.setText(netExpClass.getFirststage().getCommonproblem().getName());
        }
        layoutIsShow(fourstage, netExpClass.getFourthstage().getIsshow());
        bottomLayout.setVisibility(View.VISIBLE);
        if (netExpClass.getPlaceholdImg() != null && netExpClass.getPlaceholdImg().length() > 0) {
            empty_layout.setVisibility(View.VISIBLE);
            firstsatge.setVisibility(View.GONE);
            Glide.with(this).load(netExpClass.getPlaceholdImg()).into(empty_soon);
        } else {
            empty_layout.setVisibility(View.GONE);
            firstsatge.setVisibility(View.VISIBLE);
        }
    }

    void layoutIsShow(View layout, int isshow) {
        if (isshow == 0) {
            layout.setVisibility(View.GONE);
        } else {
            layout.setVisibility(View.VISIBLE);
        }
    }
    void setbackground(View view,int type){
        switch(type){
            case 0:
                view.setBackgroundResource(R.drawable.practise2to3_icon_homeworkquestionnaire);
                break;
            case 1:
                view.setBackgroundResource(R.drawable.practise2to3_icon_weiclass);
                break;
            case 2:
                view.setBackgroundResource(R.drawable.practise2to3_icon_parsing);
                break;
            case 3:
                view.setBackgroundResource(R.drawable.practise2to3_icon_faq);
                break;
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onCheckDoubleClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.firstsatge_1:
                if(num==2){
                    if(num_type>2){
                        intent = new Intent(NetExperienceDetailActivity.this, NetExpFirstVideoActivity.class);
                        intent.putExtra("title", "安妮鲜花微课堂");
                        intent.putExtra("type", 1);
                        intent.putExtra("fid", netExpClass.getFid());
                        startActivity(intent);
                    }else{
                        intent = new Intent(NetExperienceDetailActivity.this, WebActivity.class);
                        intent.putExtra("url", netExpClass.getFirststage().getQuestionnaire().getUrl());
                        intent.putExtra("title", "填写问卷");
                        intent.putExtra("flag", 0);//标题是否取消1：取消
                        startActivity(intent);
                    }
                }else if(num==3){
                    if(firstStagelist.get(0).getIsshow()==0){
                        intent = new Intent(NetExperienceDetailActivity.this, NetExpFirstVideoActivity.class);
                        intent.putExtra("title", "安妮鲜花微课堂");
                        intent.putExtra("type", 1);
                        intent.putExtra("fid", netExpClass.getFid());
                        startActivity(intent);
                    }else{
                        intent = new Intent(NetExperienceDetailActivity.this, WebActivity.class);
                        intent.putExtra("url", netExpClass.getFirststage().getQuestionnaire().getUrl());
                        intent.putExtra("title", "填写问卷");
                        intent.putExtra("flag", 0);//标题是否取消1：取消
                        startActivity(intent);
                    }
                }
                else{
                    intent = new Intent(NetExperienceDetailActivity.this, WebActivity.class);
                    intent.putExtra("url", netExpClass.getFirststage().getQuestionnaire().getUrl());
                    intent.putExtra("title", "填写问卷");
                    intent.putExtra("flag", 0);//标题是否取消1：取消
                    startActivity(intent);
                }
                break;
            case R.id.firstsatge_2:
                if(num==2){
                    if(num_type==1 || num_type==3){
                        intent = new Intent(NetExperienceDetailActivity.this, NetExpFirstVideoActivity.class);
                        intent.putExtra("title", "课程解析");
                        intent.putExtra("type", 2);
                        intent.putExtra("fid", netExpClass.getFid());
                        startActivity(intent);
                    }else{
                        intent = new Intent(NetExperienceDetailActivity.this, WebActivity.class);
                        intent.putExtra("url", netExpClass.getFirststage().getCommonproblem().getUrl());
                        intent.putExtra("title", "常见问题");
                        intent.putExtra("flag", 0);//标题是否取消1：取消
                        startActivity(intent);
                    }
                }else if(num==3){
                    if(firstStagelist.get(0).getIsshow()==0 || firstStagelist.get(1).getIsshow()==0){
                        intent = new Intent(NetExperienceDetailActivity.this, NetExpFirstVideoActivity.class);
                        intent.putExtra("title", "课程解析");
                        intent.putExtra("type", 2);
                        intent.putExtra("fid", netExpClass.getFid());
                        startActivity(intent);
                    }else{
                        intent = new Intent(NetExperienceDetailActivity.this, NetExpFirstVideoActivity.class);
                        intent.putExtra("title", "安妮鲜花微课堂");
                        intent.putExtra("type", 1);
                        intent.putExtra("fid", netExpClass.getFid());
                        startActivity(intent);
                    }
                }
                else{
                    intent = new Intent(NetExperienceDetailActivity.this, NetExpFirstVideoActivity.class);
                    intent.putExtra("title", "安妮鲜花微课堂");
                    intent.putExtra("type", 1);
                    intent.putExtra("fid", netExpClass.getFid());
                    startActivity(intent);
                }
                break;
            case R.id.firstsatge_3:
                if(num==3){
                    if(firstStagelist.get(3).getIsshow()!=0){
                        intent = new Intent(NetExperienceDetailActivity.this, WebActivity.class);
                        intent.putExtra("url", netExpClass.getFirststage().getCommonproblem().getUrl());
                        intent.putExtra("title", "常见问题");
                        intent.putExtra("flag", 0);//标题是否取消1：取消
                        startActivity(intent);
                    }else{
                        intent = new Intent(NetExperienceDetailActivity.this, NetExpFirstVideoActivity.class);
                        intent.putExtra("title", "课程解析");
                        intent.putExtra("type", 2);
                        intent.putExtra("fid", netExpClass.getFid());
                        startActivity(intent);
                    }
                }else{
                    intent = new Intent(NetExperienceDetailActivity.this, NetExpFirstVideoActivity.class);
                    intent.putExtra("title", "课程解析");
                    intent.putExtra("type", 2);
                    intent.putExtra("fid", netExpClass.getFid());
                    startActivity(intent);
                }

                break;
            case R.id.firstsatge_4:
                intent = new Intent(NetExperienceDetailActivity.this, WebActivity.class);
                intent.putExtra("url", netExpClass.getFirststage().getCommonproblem().getUrl());
                intent.putExtra("title", "常见问题");
                intent.putExtra("flag", 0);//标题是否取消1：取消
                startActivity(intent);
                break;
            case R.id.fourstage_1:
                intent = new Intent(NetExperienceDetailActivity.this, WebActivity.class);
                intent.putExtra("url", netExpClass.getFourthstage().getTesting());
                intent.putExtra("title", "");
                intent.putExtra("flag", 0);//标题是否取消1：取消
                startActivity(intent);
                break;
            case R.id.fourstage_2:
                intent = new Intent(NetExperienceDetailActivity.this, WebActivity.class);
                intent.putExtra("url", netExpClass.getFourthstage().getLearningreport());
                intent.putExtra("title", "学习报告");
                intent.putExtra("flag", 0);//标题是否取消1：取消
                startActivity(intent);
                break;
            case R.id.fourstage_3:
                intent = new Intent(NetExperienceDetailActivity.this, WebActivity.class);
                intent.putExtra("url", netExpClass.getFourthstage().getQuestionnaire());
                intent.putExtra("title", "填写问卷");
                intent.putExtra("flag", 0);//标题是否取消1：取消
                startActivity(intent);
                break;
        }
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETNETEXPDETAILS) {
            netExpClass = (NetExpClass) message.obj;
            lists.clear();
            lists.addAll(netExpClass.getInfo());
            adapter.notifyDataSetChanged();
            refresh();
        } else if (message.what == MethodCode.EVENT_GETLISTENANDREAD + 80000 + tag) {
            listenAndRead = (ListenAndRead) message.obj;
            if (listenAndRead.getIsshow() == 0) {
                showInfo("暂无家庭作业");
            } else {
                Intent intent = new Intent(this, TaskContentActivity.class);
                intent.putExtra("classid", listenAndRead.getClassid());
                intent.putExtra("type", listenAndRead.getType());
                intent.putExtra("week", listenAndRead.getWeek());
                intent.putExtra("tabPosition", 0);
                startActivity(intent);
            }
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
