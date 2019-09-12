package com.annie.annieforchild.ui.activity.mains;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
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
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.adapter.SongAdapter;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglei on 2018/7/18.
 */

public class IWantListenActivity extends BaseActivity implements SongView, View.OnClickListener {
    private ImageView back;
    private RecyclerView recycler;
    private SongAdapter adapter;
    private TextView title;
    private Intent intent;
    private int type, collectType, audioType, audioSource;
    private List<Song> lists;
    private GrindEarPresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;
    private int classId = 0;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_iwantlisten;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.iwantlisten_back);
        title = findViewById(R.id.iwantlisten_title);
        recycler = findViewById(R.id.iwantlisten_recycler);
        back.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("type", 0);
            if (type == 0) {
                title.setText("我要磨耳朵");
                audioType = 0;
                audioSource = 0;
                collectType = 1;
            } else if (type == 1) {
                title.setText("我要阅读");
                audioType = 1;
                audioSource = 0;
                collectType = 2;
            } else {
                title.setText("我要练口语");
                audioType = 2;
                audioSource = 0;
                collectType = 3;
            }
        }
        adapter = new SongAdapter(this, lists, presenter, collectType, classId, audioType, audioSource, type);
        recycler.setAdapter(adapter);
        presenter.iWantListen(type);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iwantlisten_back:
                finish();
                break;
        }
    }

    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_IWANTLISTEN) {
            lists.clear();
            lists.addAll((List<Song>) message.obj);
            adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_COLLECTCOURSE + 2000 + classId) {
            showInfo((String) message.obj);
            presenter.iWantListen(type);
        } else if (message.what == MethodCode.EVENT_CANCELCOLLECTION1 + 3000 + classId) {
            showInfo((String) message.obj);
            presenter.iWantListen(type);
        } else if (message.what == MethodCode.EVENT_JOINMATERIAL + 4000 + classId) {
            showInfo((String) message.obj);
            presenter.iWantListen(type);
        } else if (message.what == MethodCode.EVENT_CANCELMATERIAL + 5000 + classId) {
            showInfo((String) message.obj);
            presenter.iWantListen(type);
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
