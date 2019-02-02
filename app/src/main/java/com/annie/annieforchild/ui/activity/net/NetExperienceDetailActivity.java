package com.annie.annieforchild.ui.activity.net;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.service.MusicService;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.book.Line;
import com.annie.annieforchild.bean.net.ListenAndRead;
import com.annie.annieforchild.bean.net.NetClass;
import com.annie.annieforchild.bean.net.NetExpDetails;
import com.annie.annieforchild.bean.net.netexpclass.Info;
import com.annie.annieforchild.bean.net.netexpclass.NetExpClass;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.activity.VideoActivity;
import com.annie.annieforchild.ui.activity.lesson.TaskContentActivity;
import com.annie.annieforchild.ui.adapter.NetExperienceDetailAdapter;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class NetExperienceDetailActivity extends BaseActivity implements ViewInfo, OnCheckDoubleClick {
    CheckDoubleClickListener listner;
    private ConstraintLayout welcomeVideo;
    private ImageView back;
    private RecyclerView net_coursedetail_recyclerview;
    private Dialog dialog;
    private AlertHelper helper;
    NetWorkPresenterImp presenter;
    List<Info> lists;
    NetExperienceDetailAdapter adapter;
    RelativeLayout listeningtext_card_roport;
    private ListenAndRead listenAndRead;
    TextView netclass_video_content, netclass_video_title, title;
    ImageView video_img;
    public static NetExpClass netExpClass;
    int netid, tag = 0;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_net_coursedetail;
    }

    @Override
    protected void initView() {
        title = findViewById(R.id.title);
        welcomeVideo = findViewById(R.id.welcomeVideo);
        listner = new CheckDoubleClickListener(this);
        back = findViewById(R.id.back);
        back.setOnClickListener(listner);
        net_coursedetail_recyclerview = findViewById(R.id.net_coursedetail_recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        net_coursedetail_recyclerview.setLayoutManager(manager);
        listeningtext_card_roport = findViewById(R.id.listeningtext_card_roport);
        netclass_video_content = (TextView) findViewById(R.id.netclass_video_content);
        netclass_video_title = (TextView) findViewById(R.id.netclass_video_title);

        String netname = getIntent().getStringExtra("netName");
        netid = getIntent().getIntExtra("netid", 0);
        title.setText(netname);
        video_img = findViewById(R.id.video_img);
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

        if (MusicService.isPlay) {
            MusicService.stop();
        }
    }

    void refresh() {
        if (netExpClass.getLearningReport().getIsshow() == 0) {
            listeningtext_card_roport.setVisibility(View.GONE);
        } else {
            listeningtext_card_roport.setVisibility(View.VISIBLE);
        }
        if (netExpClass.getVideo().getIsshow() == 0) {
            welcomeVideo.setVisibility(View.GONE);
        } else {
            welcomeVideo.setVisibility(View.VISIBLE);
            netclass_video_title.setText(netExpClass.getVideo().getTitle());
            netclass_video_content.setText(netExpClass.getVideo().getContent());
            Glide.with(this).load(netExpClass.getVideo().getImage()).into(video_img);
            welcomeVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NetExperienceDetailActivity.this, VideoActivity.class);
                    intent.putExtra("url", netExpClass.getVideo().getVedio());
                    intent.putExtra("imageUrl", netExpClass.getVideo().getImage());
                    intent.putExtra("name", netExpClass.getVideo().getTitle());
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
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
