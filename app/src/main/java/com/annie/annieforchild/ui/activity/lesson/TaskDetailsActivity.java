package com.annie.annieforchild.ui.activity.lesson;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.views.RecyclerLinearLayoutManager;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.task.Homework;
import com.annie.annieforchild.bean.task.TaskDetails;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.CameraActivity;
import com.annie.annieforchild.ui.activity.PhotoActivity;
import com.annie.annieforchild.ui.adapter.UploadAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 作业详情(弃用)
 * Created by wanglei on 2018/8/23.
 */

public class TaskDetailsActivity extends CameraActivity implements SongView, OnCheckDoubleClick {
    private ImageView back;
    private TextView feedback, advice;
    private EditText remarks;
    private Button submitBtn;
    private RecyclerView taskRecycler, uploadRecycler;
    private GrindEarPresenter presenter;
    private UploadAdapter uploadAdapter;
    private AlertHelper helper;
    private Dialog dialog;
    private TaskDetails taskDetails;
    private List<Homework> lists;
    private List<String> imageList;
    private List<String> pathList; //用户选择图片路径列表
    private Bitmap headbitmap;
    private int classid, state;//state: 0：未提交 1：已提交
    private Intent intent;
    private String text;
    public static int imageNum = 0; //选择图片数量
    private CheckDoubleClickListener listener;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_details;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.task_details_back);
        feedback = findViewById(R.id.task_details_feedback);
        advice = findViewById(R.id.task_details_suggest);
        remarks = findViewById(R.id.task_details_remarks);
        submitBtn = findViewById(R.id.submit_task);
        taskRecycler = findViewById(R.id.task_details_recycler);
        uploadRecycler = findViewById(R.id.upload_task_recycler);

        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
        submitBtn.setOnClickListener(listener);
        RecyclerLinearLayoutManager linearLayoutManager = new RecyclerLinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setScrollEnabled(false);
        taskRecycler.setLayoutManager(linearLayoutManager);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        uploadRecycler.setLayoutManager(layoutManager);
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        imageList = new ArrayList<>();
        pathList = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        intent = getIntent();
        classid = intent.getIntExtra("classid", 0);
        state = intent.getIntExtra("state", 0);
        if (state == 0) {
            submitBtn.setVisibility(View.VISIBLE);
        } else {
            submitBtn.setVisibility(View.GONE);
        }
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();

//        taskAdapter = new TaskDetailsAdapter(this, lists, presenter, taskid, state);
//        taskRecycler.setAdapter(taskAdapter);

        uploadAdapter = new UploadAdapter(this,this, imageList, state, 0, new OnRecyclerItemClickListener() {
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
//        presenter.taskDetails(classid);
        if (state == 0) {
            remarks.setEnabled(true);
        } else {
            remarks.setEnabled(false);
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     * {@link com.annie.annieforchild.presenter.imp.SchedulePresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_TASKDETAILS) {
            taskDetails = (TaskDetails) message.obj;
            refresh();
            lists.clear();
            lists.addAll(taskDetails.getTask());
            if (taskDetails.getTaskimage() == null) {
                taskDetails.setTaskimage(new ArrayList<>());
            }
            imageList.clear();
            imageList.addAll(taskDetails.getTaskimage());
            uploadAdapter.notifyDataSetChanged();
//            taskAdapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_COMPLETETASK) {
            showInfo((String) message.obj);
//            presenter.taskDetails(taskid);
        } else if (message.what == MethodCode.EVENT_SELECT) {
            int position = (int) message.obj;
            imageList.remove(position);
            pathList.remove(position);
            uploadAdapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_UPLOADTASKIMAGE) {
//            presenter.submitTask(taskid, text);
        } else if (message.what == MethodCode.EVENT_SUBMITTASK) {
            showInfo((String) message.obj);
            pathList.clear();
            imageList.clear();
            imageNum = 0;
            finish();
        } else if (message.what == MethodCode.EVENT_ADDSCHEDULE) {
            showInfo((String) message.obj);
//            presenter.taskDetails(taskid);
        }
    }

    private void refresh() {
        feedback.setText(taskDetails.getFeedback());
        advice.setText(taskDetails.getAdvise());
        remarks.setText(taskDetails.getRemarks() != null ? taskDetails.getRemarks() : "");
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

    @Override
    protected void onImageSelect(Bitmap bitmap, String path) {
        headbitmap = bitmap;
//        presenter.uploadTaskImage(taskid, path);
        imageNum++;
        pathList.add(path);
        imageList.add(path);
        uploadAdapter.notifyDataSetChanged();
//        SystemUtils.show(this, path);
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.task_details_back:
                finish();
                break;
            case R.id.submit_task:
                text = remarks.getText().toString().trim();
                if (text != null && text.length() != 0) {
//                    presenter.uploadTaskImage(taskid, pathList);
                } else {
                    showInfo("请输入备注");
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        pathList.clear();
        imageList.clear();
        imageNum = 0;
        super.onDestroy();
    }
}
