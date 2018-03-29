package com.annie.annieforchild.ui.activity.lesson;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.ui.adapter.SongTaskAdapter;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 歌曲作业
 * Created by wanglei on 2018/3/28.
 */

public class SongTaskActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back, songTaskImage;
    private TextView pagination;
    private ListView songTaskList;
    private FrameLayout previewLayout, finishLayout;
    private SongTaskAdapter adapter;
    private List<String> lists;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_song_task;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.song_task_back);
        songTaskImage = findViewById(R.id.song_task_image);
        pagination = findViewById(R.id.pagination);
        songTaskList = findViewById(R.id.song_task_list);
        previewLayout = findViewById(R.id.preview_layout);
        finishLayout = findViewById(R.id.finish_layout);
        back.setOnClickListener(this);
        previewLayout.setOnClickListener(this);
        finishLayout.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        lists.add("111");
        lists.add("222");
        lists.add("333");
        lists.add("444");
        lists.add("555");
        lists.add("555");
        lists.add("555");
        lists.add("555");
        lists.add("555");
        lists.add("555");
        lists.add("555");
        lists.add("555");
        adapter = new SongTaskAdapter(this, lists);
        songTaskList.setAdapter(adapter);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.song_task_back:
                finish();
                break;
        }
    }
}
