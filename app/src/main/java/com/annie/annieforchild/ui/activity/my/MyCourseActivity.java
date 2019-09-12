package com.annie.annieforchild.ui.activity.my;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.views.RecyclerLinearLayoutManager;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.MyNetClass;
import com.annie.annieforchild.bean.net.MyNetTop;
import com.annie.annieforchild.bean.net.NetClass;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.activity.net.LessonActivity;
import com.annie.annieforchild.ui.activity.net.NetExperienceDetailActivity;
import com.annie.annieforchild.ui.activity.net.NetExperienceDetail_newActivity;
import com.annie.annieforchild.ui.activity.net.NetExperienceDetail_newActivity2;
import com.annie.annieforchild.ui.activity.net.NetExperienceDetail_newActivity3;
import com.annie.annieforchild.ui.activity.net.NetSpecialDetailActivity2;
import com.annie.annieforchild.ui.activity.net.NetWorkActivity;
import com.annie.annieforchild.ui.adapter.MyCourseAdapter;
import com.annie.annieforchild.ui.adapter.MyCourseTopAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseMusicActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的课程（网课）
 * Created by wanglei on 2018/9/25.
 */

public class MyCourseActivity extends BaseMusicActivity implements ViewInfo, OnCheckDoubleClick {
    private ImageView back;
    private TextView gotoNet;
    private LinearLayout empty;
    private RecyclerView recycler, topRecycler;
    private MyCourseAdapter adapter;
    private MyCourseTopAdapter topAdapter;
    private CheckDoubleClickListener listener;
    private NetWorkPresenter presenter;
    private List<NetClass> lists;
    private List<MyNetTop> topLists;
    private AlertHelper helper;
    private Dialog dialog;
    private ConstraintLayout card;
    private MyNetClass myNetClass;
    private TextView wx_title, wx_teacher_nikename, wx_teacher_wx, wx_tips;
    private Button wx_copy_bt;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_course;
    }

    @Override
    protected void initView() {
        card = findViewById(R.id.card);
        wx_title = findViewById(R.id.wx_title);
        wx_teacher_nikename = findViewById(R.id.wx_teacher_nikename);
        wx_tips = findViewById(R.id.wx_tips);
        wx_copy_bt = findViewById(R.id.wx_copy_bt);
        back = findViewById(R.id.my_course_back);
        recycler = findViewById(R.id.my_course_recycler);
        empty = findViewById(R.id.empty_layout);
        gotoNet = findViewById(R.id.goto_net);
        topRecycler = findViewById(R.id.my_course_top_recycler);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
        gotoNet.setOnClickListener(listener);
        RecyclerLinearLayoutManager manager = new RecyclerLinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setScrollEnabled(false);
        recycler.setLayoutManager(manager);
        RecyclerLinearLayoutManager manager2 = new RecyclerLinearLayoutManager(this);
        manager2.setOrientation(LinearLayoutManager.VERTICAL);
        manager2.setScrollEnabled(false);
        topRecycler.setLayoutManager(manager2);
        wx_teacher_wx = findViewById(R.id.wx_teacher_wx);
        wx_copy_bt.setOnClickListener(listener);
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        topLists = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        adapter = new MyCourseAdapter(this, lists, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recycler.getChildAdapterPosition(view);
                if (lists.get(position).getType() == 0) {
                    //专项
//                    Intent intent = new Intent(MyCourseActivity.this, NetSpecialDetailActivity.class);
                    if (musicService != null) {
                        if (musicService.isPlaying()) {
                            musicService.stop();
                        }
                    }
                    Intent intent = new Intent(MyCourseActivity.this, NetSpecialDetailActivity2.class);
                    intent.putExtra("netid", lists.get(position).getNetId());
                    intent.putExtra("netName", lists.get(position).getNetName());
                    intent.putExtra("color", lists.get(position).getColour());
                    startActivity(intent);
                } else if (lists.get(position).getType() == 1) {
                    if(lists.get(position).getPeriods() >=8){
                        //新版体验课V3
                        if (musicService != null) {
                            if (musicService.isPlaying()) {
                                musicService.stop();
                            }
                        }
                        Intent intent = new Intent(MyCourseActivity.this, NetExperienceDetail_newActivity3.class);
                        intent.putExtra("netid", lists.get(position).getNetId());
                        intent.putExtra("netName", lists.get(position).getNetName());
                        startActivity(intent);
                    }
                   else if (lists.get(position).getPeriods() > 3) {
                        //新版体验课V2
                        if (musicService != null) {
                            if (musicService.isPlaying()) {
                                musicService.stop();
                            }
                        }
                        Intent intent = new Intent(MyCourseActivity.this, NetExperienceDetail_newActivity2.class);
                        intent.putExtra("netid", lists.get(position).getNetId());
                        intent.putExtra("netName", lists.get(position).getNetName());
                        startActivity(intent);
                    } else {
                        //新版
                        if (musicService != null) {
                            if (musicService.isPlaying()) {
                                musicService.stop();
                            }
                        }
                        Intent intent = new Intent(MyCourseActivity.this, NetExperienceDetail_newActivity.class);
                        intent.putExtra("netid", lists.get(position).getNetId());
                        intent.putExtra("netName", lists.get(position).getNetName());
                        startActivity(intent);
                    }
                } else if (lists.get(position).getType() == 2) {
                    //旧版
                    if (musicService != null) {
                        if (musicService.isPlaying()) {
                            musicService.stop();
                        }
                    }
                    Intent intent = new Intent(MyCourseActivity.this, NetExperienceDetailActivity.class);
                    intent.putExtra("netid", lists.get(position).getNetId());
                    intent.putExtra("netName", lists.get(position).getNetName());
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        recycler.setAdapter(adapter);
        topAdapter = new MyCourseTopAdapter(this, topLists, new OnRecyclerItemClickListener() {

            @Override
            public void onItemClick(View view) {
                if (musicService != null) {
                    if (musicService.isPlaying()) {
                        musicService.stop();
                    }
                }
                int position = topRecycler.getChildAdapterPosition(view);
                Intent intent = new Intent(MyCourseActivity.this, LessonActivity.class);
                intent.putExtra("lessonId", topLists.get(position).getFid());
                intent.putExtra("lessonName", topLists.get(position).getCoursename());
                intent.putExtra("type", 4);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        topRecycler.setAdapter(topAdapter);
        presenter = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getMyNetClass();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.my_course_back:
                finish();
                break;
            case R.id.goto_net:
                if (musicService != null) {
                    if (musicService.isPlaying()) {
                        musicService.stop();
                    }
                }
                Intent intent = new Intent(this, NetWorkActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.wx_copy_bt:
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", wx_teacher_wx.getText());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                Toast.makeText(MyCourseActivity.this, "复制成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETMYNETCLASS) {
//            MyNetClass myNetClass = (MyNetClass) message.obj;
//            if (myNetClass != null) {
//                lists.clear();
//                if (myNetClass.getMyList() != null) {
//                    lists.addAll(myNetClass.getMyList());
//                }
//                if (lists.size() == 0) {
//                    empty.setVisibility(View.VISIBLE);
//                    card.setVisibility(View.GONE);
//                } else {
//                    empty.setVisibility(View.GONE);
//                    card.setVisibility(View.VISIBLE);
//                }
//            } else {
//                empty.setVisibility(View.VISIBLE);
////                wxcard_layout.setVisibility(View.GONE);
//            }
//            network_teacher_wx.setText(myNetClass.getTeacher() + "（长按复制）");
            myNetClass = (MyNetClass) message.obj;
            if (myNetClass != null) {
                lists.clear();
                topLists.clear();
                if (myNetClass.getMyList() != null) {
                    lists.addAll(myNetClass.getMyList());
                }
                if (lists.size() == 0) {
                    empty.setVisibility(View.VISIBLE);
                    card.setVisibility(View.GONE);
                } else {
                    empty.setVisibility(View.GONE);
                    setTeacherWX();
                }
                if (myNetClass.getIsshowGift() == 0) {
                    topRecycler.setVisibility(View.GONE);
                } else {
                    topRecycler.setVisibility(View.VISIBLE);
                    if (myNetClass.getNetclassGift() != null) {
                        topLists.addAll(myNetClass.getNetclassGift());
                    }
                }
                adapter.notifyDataSetChanged();
                topAdapter.notifyDataSetChanged();
            }

        }
    }

    void setTeacherWX() {
        if (myNetClass.getTeacher() == null || myNetClass.getTeacher().length() <= 0) {
            card.setVisibility(View.GONE);
        } else {
            card.setVisibility(View.VISIBLE);
        }
        wx_teacher_wx.setText(myNetClass.getTeacher());
        wx_title.setText(myNetClass.getTitle());
        wx_teacher_nikename.setText(myNetClass.getNikename() + ":");
        wx_tips.setText(myNetClass.getTips());
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
    protected void onResume() {
        super.onResume();
        allowBindService();
    }

    @Override
    protected void onPause() {
        allowUnBindService();
        super.onPause();
    }

    @Override
    public void onPublish(int progress) {

    }

    @Override
    public void onChange(int position) {

    }
}
