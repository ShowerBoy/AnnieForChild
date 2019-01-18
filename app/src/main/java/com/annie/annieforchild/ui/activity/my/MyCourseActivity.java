package com.annie.annieforchild.ui.activity.my;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.annie.annieforchild.Utils.views.RecyclerLinearLayoutManager;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.MyNetClass;
import com.annie.annieforchild.bean.net.NetClass;
import com.annie.annieforchild.bean.net.NetSuggest;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.activity.net.NetExperienceDetailActivity;
import com.annie.annieforchild.ui.activity.net.NetWorkActivity;
import com.annie.annieforchild.ui.adapter.MyCourseAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的课程（网课）
 * Created by wanglei on 2018/9/25.
 */

public class MyCourseActivity extends BaseActivity implements ViewInfo, OnCheckDoubleClick {
    private ImageView back;
    private TextView gotoNet;
    private LinearLayout empty;
    private RelativeLayout howto;
    private RecyclerView recycler;
    private MyCourseAdapter adapter;
    private CheckDoubleClickListener listener;
    private NetWorkPresenter presenter;
    private List<NetClass> lists;
    private AlertHelper helper;
    private Dialog dialog;
    private TextView network_teacher_wx;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_course;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.my_course_back);
        recycler = findViewById(R.id.my_course_recycler);
        howto = findViewById(R.id.howto_relative);
        empty = findViewById(R.id.empty_layout);
        gotoNet = findViewById(R.id.goto_net);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
        gotoNet.setOnClickListener(listener);
        RecyclerLinearLayoutManager manager = new RecyclerLinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setScrollEnabled(false);
        recycler.setLayoutManager(manager);

        network_teacher_wx=findViewById(R.id.network_teacher_wx);
        network_teacher_wx.setTextIsSelectable(true);
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        adapter = new MyCourseAdapter(this, lists, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recycler.getChildAdapterPosition(view);
                Intent intent = new Intent(MyCourseActivity.this, NetExperienceDetailActivity.class);
                intent.putExtra("netid", lists.get(position).getNetId());
                intent.putExtra("netName", lists.get(position).getNetName());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        recycler.setAdapter(adapter);
        presenter = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getMyNetClass();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.my_course_back:
                finish();
                break;
            case R.id.goto_net:
                Intent intent = new Intent(this, NetWorkActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETMYNETCLASS) {
            MyNetClass myNetClass = (MyNetClass) message.obj;
            if(myNetClass!=null && myNetClass.getMyList().size()>0){
                lists.clear();
                lists.addAll(myNetClass.getMyList());
                if (lists.size() == 0) {
                    empty.setVisibility(View.VISIBLE);
                } else {
                    empty.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            }
            network_teacher_wx.setText(myNetClass.getTeacher()+"(长按复制)");
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
