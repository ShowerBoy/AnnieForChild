package com.annie.annieforchild.ui.activity.net;

import android.app.Dialog;
import android.view.View;
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
    LinearLayout no_content;
    ListenAndRead listenAndRead;
    ImageView listenandread_img;

    {
        setRegister(true);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_net_listenandread;
    }

    @Override
    protected void initView() {
        listner = new CheckDoubleClickListener(this);
        back = findViewById(R.id.back);
        no_content=findViewById(R.id.no_content);
        listenandread_img=findViewById(R.id.listenandread_img);
        back.setOnClickListener(listner);
    }

    @Override
    protected void initData() {
        String week=getIntent().getStringExtra("week");
        String classid=getIntent().getStringExtra("classid");
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new NetWorkPresenterImp(this, this);

        presenter.initViewAndData();
        presenter.getListeningAndReading(week,classid);

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETLISTENANDREAD) {
            listenAndRead=(ListenAndRead)message.obj;
            if(listenAndRead.getIsshow()==0){
                no_content.setVisibility(View.VISIBLE);
            }else{
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
