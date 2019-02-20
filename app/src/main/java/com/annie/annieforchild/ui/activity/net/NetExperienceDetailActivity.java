package com.annie.annieforchild.ui.activity.net;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
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
import com.annie.annieforchild.bean.net.NetClass;
import com.annie.annieforchild.bean.net.NetExpDetails;
import com.annie.annieforchild.bean.net.netexpclass.Info;
import com.annie.annieforchild.bean.net.netexpclass.NetExpClass;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.activity.VideoActivity;
import com.annie.annieforchild.ui.activity.my.WebActivity;
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
    private ImageView back;
    private RecyclerView net_coursedetail_recyclerview;
    private Dialog dialog;
    private AlertHelper helper;
    NetWorkPresenterImp presenter;
    List<Info> lists;
    NetExperienceDetailAdapter adapter;

    TextView  title;
    public static NetExpClass netExpClass;
    int netid;
    private ConstraintLayout exper_detail_totest1,exper_detail_totest2;
    private LinearLayout exper_detail_topractice,exper_detail_totest;
    private RelativeLayout empty_layout;
    private ImageView empty_soon;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_net_coursedetail;
    }

    @Override
    protected void initView() {
        empty_layout=findViewById(R.id.empty_layout);
        empty_soon=findViewById(R.id.empty_soon);
        listner = new CheckDoubleClickListener(this);
        exper_detail_totest1=findViewById(R.id.exper_detail_totest1);
        exper_detail_totest2=findViewById(R.id.exper_detail_totest2);
        exper_detail_topractice=findViewById(R.id.exper_detail_topractice);
        exper_detail_totest=findViewById(R.id.exper_detail_totest);
        exper_detail_totest1.setOnClickListener(listner);
        exper_detail_topractice.setOnClickListener(listner);
        exper_detail_totest.setOnClickListener(listner);
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);
        back.setOnClickListener(listner);
        net_coursedetail_recyclerview = findViewById(R.id.net_coursedetail_recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        net_coursedetail_recyclerview.setLayoutManager(manager);

        String netname = getIntent().getStringExtra("netName");
        netid = getIntent().getIntExtra("netid", 0);
        title.setText(netname);
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

        adapter = new NetExperienceDetailAdapter(this, lists);
        net_coursedetail_recyclerview.setAdapter(adapter);

        if (MusicService.isPlay) {
            MusicService.stop();
        }
    }

    void refresh() {
        if(netExpClass.getIsShowtest()==0){
            exper_detail_totest1.setVisibility(View.VISIBLE);
            exper_detail_totest2.setVisibility(View.GONE);
        }else{
            exper_detail_totest1.setVisibility(View.GONE);
            exper_detail_totest2.setVisibility(View.VISIBLE);
        }
        if(netExpClass.getPlaceholdImg()!=null && netExpClass.getPlaceholdImg().length()>0){
            empty_layout.setVisibility(View.VISIBLE);
            Glide.with(this).load(netExpClass.getPlaceholdImg()).into(empty_soon);
        }else{
            empty_layout.setVisibility(View.GONE);
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
            case R.id.exper_detail_totest1:
                 intent = new Intent(NetExperienceDetailActivity.this, WebActivity.class);
                intent.putExtra("url", netExpClass.getPractice());
                intent.putExtra("flag",1);//标题是否取消1：取消
                startActivity(intent);
                break;
            case R.id.exper_detail_totest:
                 intent = new Intent(NetExperienceDetailActivity.this, WebActivity.class);
                intent.putExtra("url", netExpClass.getTesting());
                intent.putExtra("flag",1);//标题是否取消1：取消
                startActivity(intent);
                break;
            case R.id.exper_detail_topractice:
                 intent = new Intent(NetExperienceDetailActivity.this, WebActivity.class);
                intent.putExtra("url", netExpClass.getPractice());
                intent.putExtra("flag",1);//标题是否取消1：取消
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
