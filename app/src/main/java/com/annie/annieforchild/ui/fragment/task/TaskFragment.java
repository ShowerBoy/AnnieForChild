package com.annie.annieforchild.ui.fragment.task;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.schedule.Schedule;
import com.annie.annieforchild.bean.task.Task;
import com.annie.annieforchild.bean.task.TaskBean;
import com.annie.annieforchild.ui.adapter.TaskAdapter;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BaseFragment;
import com.annie.baselibrary.base.BasePresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglei on 2018/12/11.
 */

public class TaskFragment extends BaseFragment {
    private XRecyclerView recycler;
    private ImageView empty;
    private List<Task> lists;
    private TaskAdapter adapter;
    private TaskBean taskBean;
    private int tag;

    {
        setRegister(true);
    }

    public TaskFragment() {

    }

    public static TaskFragment instance(int tag) {
        TaskFragment fragment = new TaskFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tag", tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        if (getArguments() != null) {
            tag = getArguments().getInt("tag");
        }
        lists = new ArrayList<>();
        adapter = new TaskAdapter(getContext(), lists);
        recycler.setAdapter(adapter);
    }

    @Override
    protected void initView(View view) {
        recycler = view.findViewById(R.id.task_recycler);
        empty = view.findViewById(R.id.task_empty);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        recycler.setPullRefreshEnabled(false);
        recycler.setLoadingMoreEnabled(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_fragment;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_MYTASK) {
            taskBean = (TaskBean) message.obj;
            if (tag == 0) {
                lists.clear();
                lists.addAll(taskBean.getCourseList());
            } else {
                lists.clear();
                lists.addAll(taskBean.getNetWorkList());
            }
            if (lists.size() == 0) {
                empty.setVisibility(View.VISIBLE);
            } else {
                empty.setVisibility(View.GONE);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
