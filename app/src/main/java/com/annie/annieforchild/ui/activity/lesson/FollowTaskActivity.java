package com.annie.annieforchild.ui.activity.lesson;

import android.content.Intent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.task.STask;
import com.annie.annieforchild.bean.task.SongTask;
import com.annie.annieforchild.ui.adapter.FollowTaskAdapter;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 随堂作业——作业列表
 * Created by wanglei on 2018/3/26.
 */

public class FollowTaskActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private ExpandableListView followTaskList;
    private String[] groupList;
    private HashMap<String, SongTask> childList;
    private FollowTaskAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_follow_task;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.follow_task_back);
        followTaskList = findViewById(R.id.follow_task_list);
        back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        groupList = new String[]{"歌曲", "绘本作业", "教材作业", "纸质作业"};
        childList = new HashMap<>();
        //TODO:假数据
        for (int i = 0; i < 4; i++) {
            SongTask songTask = new SongTask();
            List<STask> lists = new ArrayList<>();
            lists.add(new STask("http://pic.58pic.com/58pic/14/62/50/62558PICxm8_1024.jpg", i, "第一条"));
            lists.add(new STask("http://pic.58pic.com/58pic/14/62/50/62558PICxm8_1024.jpg", i + 1, "第二条"));
            lists.add(new STask("http://pic.58pic.com/58pic/14/62/50/62558PICxm8_1024.jpg", i + 2, "第三条"));
            songTask.setStatus("");
            songTask.setRequest("要求" + i);
            songTask.setResourseList(lists);
            childList.put(groupList[i], songTask);
        }
        adapter = new FollowTaskAdapter(this, groupList, childList);
        followTaskList.setAdapter(adapter);
        followTaskList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (childPosition == 0) {
                    SystemUtils.show(FollowTaskActivity.this, groupList[groupPosition] + "--" + childList.get(groupList[groupPosition]).getRequest());
                } else {
                    SystemUtils.show(FollowTaskActivity.this, groupList[groupPosition] + "--" + childList.get(groupList[groupPosition]).getResourseList().get(childPosition - 1).getSongName());
                    Intent intent = new Intent(FollowTaskActivity.this, SongTaskActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.follow_task_back:
                finish();
                break;
        }
    }
}
