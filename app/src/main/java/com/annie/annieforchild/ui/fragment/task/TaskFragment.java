package com.annie.annieforchild.ui.fragment.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.task.Task;
import com.annie.annieforchild.bean.task.TaskBean;
import com.annie.annieforchild.ui.activity.lesson.TaskContentActivity;
import com.annie.annieforchild.ui.adapter.TaskAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.baselibrary.base.BaseFragment;
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
        adapter = new TaskAdapter(getContext(), lists, tag, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recycler.getChildAdapterPosition(view);
                Intent intent = new Intent(getContext(), TaskContentActivity.class);
                intent.putExtra("classid", lists.get(position - 1).getClassid());
                intent.putExtra("type", lists.get(position - 1).getType());
                if (lists.get(position - 1).getType() == 0) {
                    intent.putExtra("taskTime", lists.get(position - 1).getTasktime());
                } else {
                    intent.putExtra("week", lists.get(position - 1).getWeek());
                }
                intent.putExtra("tabPosition", -1);
                startActivity(intent);
//                SystemUtils.show(getContext(), position + "");
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
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
