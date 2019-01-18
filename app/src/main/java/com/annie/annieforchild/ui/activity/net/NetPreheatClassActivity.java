package com.annie.annieforchild.ui.activity.net;

import android.app.Dialog;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.annie.annieforchild.bean.net.NetExpDetails;
import com.annie.annieforchild.bean.net.NetWork;
import com.annie.annieforchild.bean.net.PreheatConsult;
import com.annie.annieforchild.bean.net.PreheatConsultList;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.adapter.NetExperienceDetailAdapter;
import com.annie.annieforchild.ui.adapter.NetPreheatConsultAdapter;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class NetPreheatClassActivity extends BaseActivity implements ViewInfo,OnCheckDoubleClick {
    CheckDoubleClickListener listner;
    private ImageView back;
    private Dialog dialog;
    private AlertHelper helper;
    private NetPreheatConsultAdapter adapter;
   private NetWorkPresenterImp presenter;
   private List<PreheatConsultList> Microclasslits;
   private List<PreheatConsultList> Materiallits;
   private TextView title;
    private RecyclerView net_preheatconsult_recyclerview;
    private ConstraintLayout empty_img;

    {
        setRegister(true);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_net_preheatconsult;
    }

    @Override
    protected void initView() {
        listner = new CheckDoubleClickListener(this);
        back = findViewById(R.id.back);
        back.setOnClickListener(listner);
        net_preheatconsult_recyclerview=findViewById(R.id.net_preheatconsult_recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        net_preheatconsult_recyclerview.setLayoutManager(manager);
        title=findViewById(R.id.title);
        empty_img=findViewById(R.id.empty_img);
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        Microclasslits=new ArrayList<>();
        Materiallits=new ArrayList<>();

        String lessonid = getIntent().getStringExtra("lessonId");
        String lessonname=getIntent().getStringExtra("lessonName");
        title.setText(lessonname);

        presenter = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getNetPreheatConsult(lessonid);

        adapter=new NetPreheatConsultAdapter(this,Microclasslits,Materiallits);
        net_preheatconsult_recyclerview.setAdapter(adapter);

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
        if (message.what == MethodCode.EVENT_GETPREHEATCONSULT) {
            PreheatConsult preheatConsult = (PreheatConsult) message.obj;
            if(preheatConsult!=null){
               Microclasslits.clear();
               Materiallits.clear();
               if(preheatConsult.getMicroclass()!=null && preheatConsult.getMicroclass().size()>0){
                   Microclasslits.addAll(preheatConsult.getMicroclass());
               }
               if(preheatConsult.getMaterial()!=null && preheatConsult.getMaterial().size()>0){
                   Materiallits.addAll(preheatConsult.getMaterial());
               }
               adapter.notifyDataSetChanged();
               if(adapter.getItemCount()>0){
                   empty_img.setVisibility(View.GONE);
               }else{
                   empty_img.setVisibility(View.VISIBLE);
               }
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