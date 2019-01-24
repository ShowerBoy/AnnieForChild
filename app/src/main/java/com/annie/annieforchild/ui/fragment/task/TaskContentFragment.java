package com.annie.annieforchild.ui.fragment.task;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.views.RecyclerLinearLayoutManager;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.task.Homework;
import com.annie.annieforchild.bean.task.TaskContent;
import com.annie.annieforchild.bean.task.TaskDetails;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.lesson.TaskContentActivity;
import com.annie.annieforchild.ui.adapter.TaskDetailsAdapter;
import com.annie.annieforchild.ui.adapter.UploadAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 作业内容
 * Created by wanglei on 2019/1/22.
 */

public class TaskContentFragment extends BaseFragment implements SongView, OnCheckDoubleClick, RadioGroup.OnCheckedChangeListener {
    private TextView feedback, advice;
    private ImageView empty;
    private RelativeLayout rebackLayout;
    private NestedScrollView nestedScrollView;
    private EditText remarks;
    private Button submitBtn;
    private LinearLayout confirmLayout;
    private RadioGroup radioGroup;
    private RadioButton completeT, completeF;
    private RecyclerView taskRecycler, uploadRecycler;
    private GrindEarPresenter presenter;
    private TaskDetailsAdapter taskAdapter = null;
    private UploadAdapter uploadAdapter = null;
    private AlertHelper helper;
    private Dialog dialog;
    private List<Homework> lists;
    private List<String> imageList;
    //    private List<String> pathList; //用户选择图片路径列表
    private Bitmap headbitmap;
    private int classid, isfinish;//isfinish: 0：未提交 1：已提交
    private Intent intent;
    private TaskDetails taskDetails = null;
    private CheckDoubleClickListener listener;
    private String text, taskTime, week;
    private int tag, type, complete = -1, taskid; //type: 0：课程 1：网课
    private boolean iscommit = true;

    {
        setRegister(true);
    }

    public TaskContentFragment() {
    }

    public static TaskContentFragment instance(int tag, int classid, int type, String taskTime, String week) {
        TaskContentFragment fragment = new TaskContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tag", tag);
        bundle.putInt("classid", classid);
        bundle.putInt("type", type);
        bundle.putString("taskTime", taskTime);
        bundle.putString("week", week);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(getActivity());
        dialog = helper.LoadingDialog();
        if (getArguments() != null) {
            tag = getArguments().getInt("tag");
            classid = getArguments().getInt("classid");
            type = getArguments().getInt("type");
            taskTime = getArguments().getString("taskTime");
            week = getArguments().getString("week");
        }
        lists = new ArrayList<>();
        imageList = new ArrayList<>();
//        pathList = new ArrayList<>();
        if (tag == 0) {
            rebackLayout.setVisibility(View.VISIBLE);
        } else {
            rebackLayout.setVisibility(View.GONE);
        }

        presenter = new GrindEarPresenterImp(getContext(), this);
        presenter.initViewAndData();


        if (tag == 0) {
            if (type == 0) {
                presenter.taskDetails(classid, type, "", taskTime, tag);
            } else {
                presenter.taskDetails(classid, type, week, "", tag);
            }
        }
    }

    @Override
    protected void initView(View view) {
        feedback = view.findViewById(R.id.task_content_feedback);
        advice = view.findViewById(R.id.task_content_suggest);
        remarks = view.findViewById(R.id.task_content_remarks);
        submitBtn = view.findViewById(R.id.submit_task);
        taskRecycler = view.findViewById(R.id.task_content_recycler);
        uploadRecycler = view.findViewById(R.id.upload_content_recycler);
        radioGroup = view.findViewById(R.id.task_radio_group);
        completeT = view.findViewById(R.id.task_isfinish_t);
        completeF = view.findViewById(R.id.task_isfinish_f);
        rebackLayout = view.findViewById(R.id.task_reback_layout);
        confirmLayout = view.findViewById(R.id.task_confirm_layout);
        nestedScrollView = view.findViewById(R.id.task_content_scroll);
        empty = view.findViewById(R.id.task_content_empty);

        listener = new CheckDoubleClickListener(this);
        submitBtn.setOnClickListener(listener);
        radioGroup.setOnCheckedChangeListener(this);
        RecyclerLinearLayoutManager linearLayoutManager = new RecyclerLinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setScrollEnabled(false);
        taskRecycler.setLayoutManager(linearLayoutManager);
        taskRecycler.setNestedScrollingEnabled(false);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        uploadRecycler.setLayoutManager(layoutManager);
        uploadRecycler.setNestedScrollingEnabled(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_content_fragment;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_TASKDETAILS + 50000 + tag) {
            taskDetails = (TaskDetails) message.obj;
            if (taskDetails != null) {
                taskid = taskDetails.getTaskid();
                if (taskDetails.getTask() != null) {
                    lists.clear();
                    lists.addAll(taskDetails.getTask());
                    if (lists.size() == 0) {
                        empty.setVisibility(View.VISIBLE);
                        nestedScrollView.setVisibility(View.GONE);
                    } else {
                        empty.setVisibility(View.GONE);
                        nestedScrollView.setVisibility(View.VISIBLE);
                        refresh();
                    }
                    if (taskAdapter == null) {
                        taskAdapter = new TaskDetailsAdapter(getContext(), lists, presenter, taskid, type);
                        taskRecycler.setAdapter(taskAdapter);
                    }
                    taskAdapter.notifyDataSetChanged();
                } else {
                    empty.setVisibility(View.VISIBLE);
                    nestedScrollView.setVisibility(View.GONE);
                }
            } else {
                empty.setVisibility(View.VISIBLE);
                nestedScrollView.setVisibility(View.GONE);
            }
        } else if (message.what == MethodCode.EVENT_TASKIMAGE) {
            int position = (int) message.obj;
            if (position == tag) {
                if (tag == 0) {
                    imageList.clear();
                    imageList.addAll(TaskContentActivity.pathList1);
                } else if (tag == 1) {
                    imageList.clear();
                    imageList.addAll(TaskContentActivity.pathList2);
                } else if (tag == 2) {
                    imageList.clear();
                    imageList.addAll(TaskContentActivity.pathList3);
                } else if (tag == 3) {
                    imageList.clear();
                    imageList.addAll(TaskContentActivity.pathList4);
                }
                uploadAdapter.notifyDataSetChanged();
            }
        } else if (message.what == MethodCode.EVENT_UPLOADTASKIMAGE + 40000 + taskid) {
            if (iscommit) {
                presenter.submitTask(taskid, text, complete, type);
                iscommit = false;
            }
        } else if (message.what == MethodCode.EVENT_SUBMITTASK + 60000 + taskid) {
            showInfo("提交成功");
            if (type == 0) {
                presenter.taskDetails(classid, type, "", taskTime, tag);
            } else {
                presenter.taskDetails(classid, type, week, "", tag);
            }
        } else if (message.what == MethodCode.EVENT_COMPLETETASK + 70000 + taskid) {
            showInfo("成功");
            if (type == 0) {
                presenter.taskDetails(classid, type, "", taskTime, tag);
            } else {
                presenter.taskDetails(classid, type, week, "", tag);
            }
        } else if (message.what == MethodCode.EVENT_TASKDATA) {
            int bos = (int) message.obj;
            if (bos == tag) {
                if (taskDetails == null) {
                    if (type == 0) {
                        presenter.taskDetails(classid, type, "", taskTime, tag);
                    } else {
                        presenter.taskDetails(classid, type, week, "", tag);
                    }
                }
            }
        }
    }

    private void refresh() {
        if (taskDetails != null) {
            if (tag == 0) {
                feedback.setText(taskDetails.getFeedback());
            }
            advice.setText(taskDetails.getAdvise());
            remarks.setText(taskDetails.getRemarks() != null ? taskDetails.getRemarks() : "");
            isfinish = taskDetails.getIsfinish();
            if (isfinish == 0) {
                submitBtn.setVisibility(View.VISIBLE);
                confirmLayout.setVisibility(View.VISIBLE);
                remarks.setEnabled(true);
            } else {
                submitBtn.setVisibility(View.GONE);
                confirmLayout.setVisibility(View.GONE);
                remarks.setEnabled(false);
            }
            imageList.clear();
            imageList.addAll(taskDetails.getTaskimage());
//            if (uploadAdapter == null) {
            uploadAdapter = new UploadAdapter(getContext(), imageList, isfinish, tag, new OnRecyclerItemClickListener() {
                @Override
                public void onItemClick(View view) {
//                int position = uploadRecycler.getChildAdapterPosition(view);
//                showInfo(position + "");
                }

                @Override
                public void onItemLongClick(View view) {

                }
            });
            uploadRecycler.setAdapter(uploadAdapter);
//            }
            uploadAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.submit_task:
                text = remarks.getText().toString().trim();
                if (text != null && text.length() != 0) {
                    if (complete != -1) {
                        if (tag == 0) {
                            presenter.uploadTaskImage(taskid, TaskContentActivity.pathList1, type);
                        } else if (tag == 1) {
                            presenter.uploadTaskImage(taskid, TaskContentActivity.pathList2, type);
                        } else if (tag == 2) {
                            presenter.uploadTaskImage(taskid, TaskContentActivity.pathList3, type);
                        } else if (tag == 3) {
                            presenter.uploadTaskImage(taskid, TaskContentActivity.pathList4, type);
                        }
                    } else {
                        showInfo("请家长确认");
                    }
                } else {
                    showInfo("请输入备注");
                }
                break;
        }
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (completeT.getId() == checkedId) {
            complete = 1;
        } else {
            complete = 0;
        }
    }
}
