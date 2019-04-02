package com.annie.annieforchild.ui.activity.my;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.bean.HelpBean;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.presenter.MessagePresenter;
import com.annie.annieforchild.presenter.imp.MessagePresenterImp;
import com.annie.annieforchild.ui.adapter.HelpAdapter;
import com.annie.annieforchild.ui.fragment.help.FeedBackFragment;
import com.annie.annieforchild.ui.fragment.help.HelpFragment;
import com.annie.annieforchild.ui.fragment.message.GroupMsgFragment;
import com.annie.annieforchild.ui.fragment.message.NoticeFragment;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 帮助与反馈
 * Created by WangLei on 2018/1/17 0017
 */

public class HelpActivity extends BaseActivity implements View.OnClickListener,ViewInfo {
    private ImageView helpBack;
    private XRecyclerView helpRecycler;
    private List<HelpBean> lists;
    private HelpAdapter adapter;
    private MessagePresenter presenter;
    private Dialog dialog;
    private AlertHelper helper;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    protected void initView() {
        helpBack = findViewById(R.id.help_back);
        helpRecycler = findViewById(R.id.help_recycler);
        helpBack.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new MessagePresenterImp(this, this);
        presenter.initViewAndData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        helpRecycler.setLayoutManager(layoutManager);
        helpRecycler.setPullRefreshEnabled(false);

        helpRecycler.setLoadingMoreEnabled(false);
        helpRecycler.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lists = new ArrayList<>();
        adapter = new HelpAdapter(this, lists, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = helpRecycler.getChildAdapterPosition(view);
                if (lists.get(position - 1).getUrl() != null && lists.get(position - 1).getUrl().length() != 0) {
                    Intent intent = new Intent(HelpActivity.this, WebActivity.class);
                    intent.putExtra("url", lists.get(position - 1).getUrl());
                    intent.putExtra("title", "");
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        helpRecycler.setAdapter(adapter);
        presenter.getDocumentations();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.help_back:
                finish();
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
        if (message.what == MethodCode.EVENT_GETHELP) {
            lists.clear();
            lists.addAll((List<HelpBean>) message.obj);
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
}
