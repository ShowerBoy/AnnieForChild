package com.annie.annieforchild.ui.activity.net;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.Game;
import com.annie.annieforchild.bean.net.NetDetails;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.annieforchild.ui.adapter.LessonAdapter;
import com.annie.annieforchild.ui.adapter.NetDetailsAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 网课课程列表
 * Created by wanglei on 2018/10/22.
 */

public class LessonActivity extends BaseActivity implements View.OnClickListener, ViewInfo {
    private ImageView back;
    private TextView title;
    private RecyclerView recycler;
    private NetWorkPresenter presenter;
    private LessonAdapter adapter;
    private List<Game> lists;
    private String lessonId;
    private String lessonName;
    private AlertHelper helper;
    private Dialog dialog;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lesson;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.lesson_back);
        title = findViewById(R.id.lesson_title);
        recycler = findViewById(R.id.lesson_recycler);
        back.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(manager);
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        lessonId = getIntent().getStringExtra("lessonId");
        lessonName = getIntent().getStringExtra("lessonName");
        title.setText(lessonName);
        presenter = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();
        adapter = new LessonAdapter(this, lists, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recycler.getChildAdapterPosition(view);
                Intent intent = new Intent(LessonActivity.this, WebActivity.class);
                intent.putExtra("url", lists.get(position).getGameUrl());
                intent.putExtra("flag",1);//标题是否取消1：取消
//                intent.putExtra("title", lists.get(position).getGameName());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        recycler.setAdapter(adapter);
        presenter.getLesson(lessonId);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lesson_back:
                finish();
                break;
        }
    }

    /**
     * {@link NetWorkPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETLESSON) {
            lists.clear();
            lists.addAll((List<Game>) message.obj);
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
