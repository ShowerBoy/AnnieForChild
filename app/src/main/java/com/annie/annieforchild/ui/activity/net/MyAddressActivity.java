package com.annie.annieforchild.ui.activity.net;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.Address;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.adapter.AddressAdapter;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的地址
 * Created by wanglei on 2018/9/27.
 */

public class MyAddressActivity extends BaseActivity implements ViewInfo, View.OnClickListener {
    private ImageView back, empty;
    private TextView addNewAddress;
    private RecyclerView recycler;
    private AddressAdapter adapter;
    private List<Address> lists;
    private NetWorkPresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;
    private int tag;//0:确认订单 1:地址管理

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_address;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.my_address_back);
        addNewAddress = findViewById(R.id.add_new_address);
        recycler = findViewById(R.id.address_recycler);
        empty = findViewById(R.id.empty_address);
        back.setOnClickListener(this);
        addNewAddress.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(manager);
        tag = getIntent().getIntExtra("tag", 0);
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        adapter = new AddressAdapter(this, lists, tag);
        recycler.setAdapter(adapter);
        presenter = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getMyAddress();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_address_back:
                finish();
                break;
            case R.id.add_new_address:
                Intent intent = new Intent(this, AddAddressActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETMYADDRESS) {
            List list = (List<Address>) message.obj;
            if (list != null && list.size() != 0) {
                empty.setVisibility(View.GONE);
                lists.clear();
                lists.addAll((List<Address>) message.obj);
            } else {
                empty.setVisibility(View.VISIBLE);
                lists.clear();
                lists.addAll((List<Address>) message.obj);
            }
            adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_ADDADDRESS) {
            presenter.getMyAddress();
        } else if (message.what == MethodCode.EVENT_EDITADDRESS) {
            presenter.getMyAddress();
        } else if (message.what == MethodCode.EVENT_DELETEADDRESS) {
            presenter.getMyAddress();
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
