package com.annie.annieforchild.ui.activity.my;

import android.app.Dialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.Record;
import com.annie.annieforchild.presenter.MessagePresenter;
import com.annie.annieforchild.presenter.imp.MessagePresenterImp;
import com.annie.annieforchild.ui.adapter.MyNectarAdapter;
import com.annie.annieforchild.ui.adapter.MyRecordAdapter;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的录音
 * Created by WangLei on 2018/3/8 0008
 */

public class MyRecordActivity extends BaseActivity implements ViewInfo, View.OnClickListener {
    private ImageView myRecordBack;
    private XRecyclerView myRecordRecycler;
    private List<Record> lists;
    private MyRecordAdapter adapter;
    private MessagePresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_record;
    }

    @Override
    protected void initView() {
        myRecordBack = findViewById(R.id.my_record_back);
        myRecordRecycler = findViewById(R.id.my_record_recycler);
        myRecordBack.setOnClickListener(this);
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
    }

    @Override
    protected void initData() {
        presenter = new MessagePresenterImp(this, this);
        presenter.initViewAndData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myRecordRecycler.setLayoutManager(layoutManager);
        myRecordRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        myRecordRecycler.setPullRefreshEnabled(false);
        myRecordRecycler.setLoadingMoreEnabled(false);
        myRecordRecycler.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lists = new ArrayList<>();
        adapter = new MyRecordAdapter(this, lists);
        myRecordRecycler.setAdapter(adapter);
        presenter.myRecordings();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_record_back:
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
        if (message.what == MethodCode.EVENT_MYRECORDINGS) {
            lists.clear();
            lists.addAll((List<Record>) (message.obj));
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
