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

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglei on 2018/12/11.
 */

public class TaskFragment extends BaseFragment {
    private RecyclerView recycler;
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
        recycler.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recycler.setLayoutManager(layoutManager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_fragment;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_MYTASK) {
            taskBean = (TaskBean) message.obj;
//            if (tag == 0) {
//                lists.clear();
//                if (taskBean.getInclassList() != null && taskBean.getInclassList().size() != 0) {
//                    lists.addAll(taskBean.getInclassList());
//                    empty.setVisibility(View.GONE);
//                } else {
//                    empty.setVisibility(View.VISIBLE);
//                }
//                adapter.notifyDataSetChanged();
//            } else if (tag == 1) {
//                lists.clear();
//                if (taskBean.getSeriesList() != null && taskBean.getSeriesList().size() != 0) {
//                    lists.addAll(taskBean.getSeriesList());
//                    empty.setVisibility(View.GONE);
//                } else {
//                    empty.setVisibility(View.VISIBLE);
//                }
//                adapter.notifyDataSetChanged();
//            } else if (tag == 2) {
//                lists.clear();
//                if (taskBean.getOtherList() != null && taskBean.getOtherList().size() != 0) {
//                    lists.addAll(taskBean.getOtherList());
//                    empty.setVisibility(View.GONE);
//                } else {
//                    empty.setVisibility(View.VISIBLE);
//                }
//                adapter.notifyDataSetChanged();
//            }
        }
    }
}
