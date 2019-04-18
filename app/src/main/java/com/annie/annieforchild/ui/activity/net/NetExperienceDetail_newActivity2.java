package com.annie.annieforchild.ui.activity.net;

import android.app.Dialog;
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
import com.annie.annieforchild.bean.net.netexpclass.Info_new;
import com.annie.annieforchild.bean.net.netexpclass.NetExp_new;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.adapter.NetExperienceDetailNewAdapter;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglei on 2019/4/16.
 */

public class NetExperienceDetail_newActivity2 extends BaseActivity implements ViewInfo, OnCheckDoubleClick {
    private NestedScrollView scrollView;
    private RecyclerView recycler;
    private ImageView back, empty;
    private TextView title;
    private CheckDoubleClickListener listener;
    private NetWorkPresenterImp presenter;
    private Dialog dialog;
    private AlertHelper helper;
    private NetExp_new netExpClass;
    private List<Info_new> lists;
    private NetExperienceDetailNewAdapter adapter;
    private int netid;
    private String netName;

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
        back.setOnClickListener(listener);

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

        adapter = new NetExperienceDetailNewAdapter(this, lists);
        recycler.setAdapter(adapter);

        presenter = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getNetExpDetails_new(netid);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETNETEXPDETAILS_NEW) {
            netExpClass = (NetExp_new) message.obj;
            if (netExpClass.getInfo() != null && netExpClass.getInfo().size() != 0) {
                scrollView.setVisibility(View.VISIBLE);
                empty.setVisibility(View.GONE);
                lists.clear();
                lists.addAll(netExpClass.getInfo());
                adapter.notifyDataSetChanged();
            } else {
                scrollView.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.coursedetail_new_back:
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
