package com.annie.annieforchild.ui.activity.net;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.activity.lesson.TaskContentActivity;
import com.annie.annieforchild.ui.adapter.NetExperienceDetailNewAdapter;
import com.annie.annieforchild.ui.adapter.NetSpecialDetailAdapter;
import com.annie.annieforchild.ui.adapter.NetSpecialDetailAdapter2;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 新专项课
 * Created by wanglei on 2019/5/6.
 */

public class NetSpecialDetailActivity2 extends BaseActivity implements ViewInfo, OnCheckDoubleClick {
    private NestedScrollView scrollView;
    private RecyclerView recycler;
    private ImageView back, empty;
    private TextView title;
    private CheckDoubleClickListener listener;
    private NetWorkPresenter presenter;
    private Dialog dialog;
    private AlertHelper helper;
    private ExperienceV2 specialV2;
    private List<ExpItemBean> lists;
    private NetSpecialDetailAdapter2 adapter;
    private ListenAndRead listenAndRead;
    private int netid, color;//1:浅绿 2:深绿
    private int tag = 1;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_net_special_detail2;
    }

    @Override
    protected void initView() {
        listener = new CheckDoubleClickListener(this);
        back = findViewById(R.id.net_special_detail_back);
        empty = findViewById(R.id.empty_soon);
        title = findViewById(R.id.net_special_detail_title);
        scrollView = findViewById(R.id.net_special_detail_scrollview);
        recycler = findViewById(R.id.net_special_detail_recycler);
        back.setOnClickListener(listener);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        recycler.setNestedScrollingEnabled(false);

        String netname = getIntent().getStringExtra("netName");
        netid = getIntent().getIntExtra("netid", 0);
        color = getIntent().getIntExtra("color", 1);
        title.setText(netname);
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        lists = new ArrayList<>();
        presenter = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();

        adapter = new NetSpecialDetailAdapter2(this, lists, presenter, tag, netid, color);
        recycler.setAdapter(adapter);

        presenter.SpecialClassV2(netid);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_SPECIALCLASSV2) {
            specialV2 = (ExperienceV2) message.obj;
            if (specialV2 != null) {
                if (specialV2.getPlaceholdImg() != null && specialV2.getPlaceholdImg().length() > 0) {
                    scrollView.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                } else {
                    if (specialV2.getPlate() != null && specialV2.getPlate().size() != 0) {
                        scrollView.setVisibility(View.VISIBLE);
                        empty.setVisibility(View.GONE);
                        lists.clear();
                        lists.addAll(specialV2.getPlate());
                        adapter.notifyDataSetChanged();
                    } else {
                        scrollView.setVisibility(View.GONE);
                        empty.setVisibility(View.VISIBLE);
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
                intent.putExtra("type", listenAndRead.getType());
                intent.putExtra("week", listenAndRead.getWeek());
                intent.putExtra("tabPosition", 0);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()){
            case R.id.net_special_detail_back:
                finish();
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
