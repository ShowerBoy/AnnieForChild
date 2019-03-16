package com.annie.annieforchild.ui.activity.net;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.Game;
import com.annie.annieforchild.bean.net.NetDetails;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
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
    private RelativeLayout emptyLayout;
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
        emptyLayout = findViewById(R.id.empty_layout);
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
        int type = getIntent().getIntExtra("type", 1);
        title.setText(lessonName);
        presenter = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();
        adapter = new LessonAdapter(this, lists, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recycler.getChildAdapterPosition(view);
                if (lists.get(position).getTag() == 0) {
                    Intent intent = new Intent(LessonActivity.this, WebActivity.class);
                    intent.putExtra("url", lists.get(position).getGameUrl());
                    intent.putExtra("flag", 1);//标题是否取消1：取消
                    startActivity(intent);
                } else {
                    Song song = new Song();
                    song.setBookId(lists.get(position).getBookId());
                    song.setBookName(lists.get(position).getBookName());
                    song.setBookImageUrl(lists.get(position).getBookImageUrl());
                    int bookType;
                    if (lists.get(position).getAudioType() == 0) {
                        bookType = 0;
                    } else {
                        bookType = 1;
                    }
                    Intent intent = new Intent(LessonActivity.this, PracticeActivity.class);
                    intent.putExtra("song", song);
                    intent.putExtra("type", 0);
                    intent.putExtra("audioType", lists.get(position).getAudioType());
                    intent.putExtra("audioSource", 0);
                    intent.putExtra("bookType", bookType);
                    startActivity(intent);
                }

            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        recycler.setAdapter(adapter);
        presenter.getLesson(lessonId, type);
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
            List list = (List<Game>) message.obj;
            if (list != null && list.size() != 0) {
                emptyLayout.setVisibility(View.GONE);
                lists.clear();
                lists.addAll(list);
            } else {
                emptyLayout.setVisibility(View.VISIBLE);
                lists.clear();
            }
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
