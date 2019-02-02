package com.annie.annieforchild.ui.activity.net;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.ListenAndRead;
import com.annie.annieforchild.bean.net.netexpclass.NetExpClass;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.activity.lesson.TaskActivity;
import com.annie.annieforchild.ui.activity.lesson.TaskContentActivity;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.Subscribe;

public class NetListenAndReadActivity extends BaseActivity implements ViewInfo, OnCheckDoubleClick {
    CheckDoubleClickListener listner;
    private ImageView back;
    NetWorkPresenterImp presenter;
    private Dialog dialog;
    private AlertHelper helper;
    private LinearLayout no_content;
    private ListenAndRead listenAndRead;
    private ImageView listenandread_img;
    private Button to_listenandread;
    private String week, classid;
    private int tag = 1;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_net_listenandread;
    }

    @Override
    protected void initView() {
        to_listenandread = findViewById(R.id.to_listenandread);
        listner = new CheckDoubleClickListener(this);
        back = findViewById(R.id.back);
        no_content = findViewById(R.id.no_content);
        listenandread_img = findViewById(R.id.listenandread_img);
        back.setOnClickListener(listner);
        to_listenandread.setOnClickListener(listner);
    }

    @Override
    protected void initData() {
        week = getIntent().getStringExtra("week");
        classid = getIntent().getStringExtra("classid");
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new NetWorkPresenterImp(this, this);

        presenter.initViewAndData();
        Log.e("wee", week + "///" + classid);
        presenter.getListeningAndReading(week, classid, tag);

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETLISTENANDREAD + 80000 + tag) {
            listenAndRead = (ListenAndRead) message.obj;
            if (listenAndRead.getIsshow() == 0) {
                no_content.setVisibility(View.VISIBLE);
            } else {
                no_content.setVisibility(View.GONE);
            }
            Glide.with(this).load(listenAndRead.getPath()).into(listenandread_img);
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.to_listenandread:
                Intent intent = new Intent(this, TaskContentActivity.class);
                intent.putExtra("classid", listenAndRead.getClassid());
                intent.putExtra("type", listenAndRead.getType());
                intent.putExtra("week", listenAndRead.getWeek());
                intent.putExtra("tabPosition", 2);
                startActivity(intent);
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
