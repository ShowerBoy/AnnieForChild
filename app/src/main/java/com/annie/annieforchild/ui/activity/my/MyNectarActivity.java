package com.annie.annieforchild.ui.activity.my;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.UserInfo;
import com.annie.annieforchild.bean.nectar.MyNectar;
import com.annie.annieforchild.bean.nectar.NectarBean;
import com.annie.annieforchild.presenter.NectarPresenter;
import com.annie.annieforchild.presenter.imp.NectarPresenterImp;
import com.annie.annieforchild.ui.adapter.MyNectarAdapter;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的花蜜
 * Created by WangLei on 2018/3/7 0007
 */

public class MyNectarActivity extends BaseActivity implements ViewInfo, View.OnClickListener {
    private ImageView myNectarBack, myNectarHelp;
    private CircleImageView myNectarHeadpic;
    private XRecyclerView recycler;
    private TextView exchange, myNectarName, myNectarNum;
    private MyNectarAdapter adapter;
    private List<NectarBean> lists;
    private NectarPresenter presenter;
    private UserInfo userInfo;
    private AlertHelper helper;
    private Dialog dialog;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_nectar;
    }

    @Override
    protected void initView() {
        myNectarBack = findViewById(R.id.my_nectar_back);
        myNectarHelp = findViewById(R.id.my_nectar_help);
        myNectarHeadpic = findViewById(R.id.my_nectar_headpic);
        exchange = findViewById(R.id.exchange);
        myNectarName = findViewById(R.id.my_nectar_name);
        myNectarNum = findViewById(R.id.my_nectar_num);
        recycler = findViewById(R.id.my_nectar_recycler);
        myNectarBack.setOnClickListener(this);
        myNectarHelp.setOnClickListener(this);
        exchange.setOnClickListener(this);

        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            userInfo = (UserInfo) bundle.getSerializable("userinfo");
        }
    }

    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recycler.setPullRefreshEnabled(false);
        recycler.setLoadingMoreEnabled(false);
        recycler.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        adapter = new MyNectarAdapter(this, lists);
        initRecycler();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        initialize();

        presenter = new NectarPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getNectar();
    }

    private void initialize() {
        if (userInfo != null) {
            Glide.with(this).load(userInfo.getAvatar()).error(R.drawable.icon_system_headpic).into(myNectarHeadpic);
            myNectarName.setText(userInfo.getName());
            myNectarNum.setText(userInfo.getNectar() != null ? userInfo.getNectar() + "花蜜" : "0花蜜");
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_nectar_back:
                finish();
                break;
            case R.id.my_nectar_help:
                //帮助
                Intent intent1 = new Intent(this, NectarRuleActivity.class);
                startActivity(intent1);
                break;
            case R.id.exchange:
                //兑换
                Intent intent = new Intent(this, ExchangeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("userinfo", userInfo);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    /**
     * {@link com.annie.annieforchild.presenter.imp.MessagePresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_EXCHANGEGOLD) {
            presenter.getNectar();
        } else if (message.what == MethodCode.EVENT_GETNECTAR) {
            MyNectar myNectar = (MyNectar) message.obj;
            myNectarNum.setText(myNectar.getNectarTotal() + "花蜜");
            lists.clear();
            lists.addAll(myNectar.getNectarExchanges());
            adapter.notifyDataSetChanged();
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

    private void getWindowGray(boolean tag) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        if (tag) {
            layoutParams.alpha = 0.7f;
            getWindow().setAttributes(layoutParams);
        } else {
            layoutParams.alpha = 1f;
            getWindow().setAttributes(layoutParams);
        }
    }
}
