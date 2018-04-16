package com.annie.annieforchild.ui.activity.my;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.ExchangeBean;
import com.annie.annieforchild.bean.GoldExchanges;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.NectarExchanges;
import com.annie.annieforchild.bean.UserInfo;
import com.annie.annieforchild.presenter.MessagePresenter;
import com.annie.annieforchild.presenter.imp.MessagePresenterImp;
import com.annie.annieforchild.ui.adapter.MyExchangeAdapter;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的兑换
 * Created by WangLei on 2018/1/17 0017
 */

public class MyExchangeActivity extends BaseActivity implements ViewInfo, View.OnClickListener {
    private ImageView myExchangeBack;
    private CircleImageView headpic;
    private TextView name, nectar, coin, exchange_btn;
    private EditText exchange_nectar;
    private RecyclerView myExchangeList;
    private MyExchangeAdapter adapter;
    private List<GoldExchanges> lists;
    private NectarExchanges goldExchanges;
    private MessagePresenter presenter;
    private UserInfo userInfo;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_exchange;
    }

    @Override
    protected void initView() {
        myExchangeBack = findViewById(R.id.my_exchange_back);
        headpic = findViewById(R.id.my_exchange_headpic);
        name = findViewById(R.id.my_exchange_name);
        nectar = findViewById(R.id.my_exchange_nectar);
        coin = findViewById(R.id.my_exchange_coin);
        exchange_nectar = findViewById(R.id.exchange_nectar);
        exchange_btn = findViewById(R.id.exchange_btn);
        myExchangeList = findViewById(R.id.my_exchange_list);
        myExchangeBack.setOnClickListener(this);
        exchange_btn.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        myExchangeList.setLayoutManager(manager);
        myExchangeList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                userInfo = (UserInfo) bundle.getSerializable("userinfo");
            }
        }
    }

    @Override
    protected void initData() {
        initialize();
        presenter = new MessagePresenterImp(this, this);
        presenter.initViewAndData();
        lists = new ArrayList<>();
        adapter = new MyExchangeAdapter(this, lists);
        myExchangeList.setAdapter(adapter);
        presenter.getExchangeRecording();
    }

    private void initialize() {
        Glide.with(this).load(userInfo.getAvatar()).into(headpic);
        name.setText(userInfo.getName());
        nectar.setText(userInfo.getNectar() != null ? userInfo.getNectar() + "花蜜" : "0花蜜");
        coin.setText(userInfo.getGold() != null ? userInfo.getGold() + "金币" : "0金币");
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_exchange_back:
                finish();
                break;
            case R.id.exchange_btn:
                //兑换
                SystemUtils.show(this, "兑换");
                break;
        }
    }

    /**
     * {@link MessagePresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_EXCHANGERECORDING) {
            goldExchanges = (NectarExchanges) (message.obj);
            lists.clear();
            lists.addAll(goldExchanges.getGoldExchanges());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void dismissLoad() {

    }
}
