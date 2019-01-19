package com.annie.annieforchild.ui.activity.net;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.ActivityCollector;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.Address;
import com.annie.annieforchild.bean.net.NetClass;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.activity.my.MyCourseActivity;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.annieforchild.ui.adapter.MyCourseAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class PaySuccessActivity extends BaseActivity implements ViewInfo,OnCheckDoubleClick {
    private ImageView back;
    CheckDoubleClickListener listener;
    private TextView ok;
    LinearLayout to_network,to_mylesson;
    NetWorkPresenterImp presenter;
    List<NetClass> list;
    private AlertHelper helper;
    private MyCourseAdapter adapter;
    private RecyclerView recycler;
    private Dialog dialog;

    {
        setRegister(true);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_success;
    }

    @Override
    protected void initView() {
        recycler=findViewById(R.id.recycler);
        listener = new CheckDoubleClickListener(this);
        back = findViewById(R.id.back);
        ok=findViewById(R.id.ok);
        to_mylesson=findViewById(R.id.to_mylesson);
        to_network=findViewById(R.id.to_network);

        back.setOnClickListener(listener);
        ok.setOnClickListener(listener);
        to_network.setOnClickListener(listener);
        to_mylesson.setOnClickListener(listener);
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        list=new ArrayList<>();
        presenter = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();
//        presenter.buySuccess();
        adapter = new MyCourseAdapter(this, list, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recycler.getChildAdapterPosition(view);
                Intent intent = new Intent(PaySuccessActivity.this, NetSuggestActivity.class);
                intent.putExtra("netid", list.get(position).getNetId());
//                intent.putExtra("netName", list.get(position).getNetName());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        recycler.setAdapter(adapter);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_BUYSUCCESS) {
            list.clear();
            list.addAll((List<NetClass>) message.obj);
            adapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.ok:
                finish();
                break;
            case R.id.to_mylesson:
                Intent intent = new Intent(this, MyCourseActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.to_network:
                NetSuggestActivity.netSuggestActivity.finish();
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
