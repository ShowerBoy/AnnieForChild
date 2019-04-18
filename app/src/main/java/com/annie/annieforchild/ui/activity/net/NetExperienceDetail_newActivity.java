package com.annie.annieforchild.ui.activity.net;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.service.MusicService;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.ListenAndRead;
import com.annie.annieforchild.bean.net.netexpclass.FirstStageitem;
import com.annie.annieforchild.bean.net.netexpclass.Info;
import com.annie.annieforchild.bean.net.netexpclass.NetExpClass;
import com.annie.annieforchild.bean.net.netexpclass.NetExp_new;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.activity.lesson.TaskContentActivity;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.annieforchild.ui.adapter.NetExperienceDetailAdapter;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class NetExperienceDetail_newActivity extends BaseActivity implements ViewInfo, OnCheckDoubleClick {
    CheckDoubleClickListener listner;
    private ListenAndRead listenAndRead;
    private Dialog dialog;
    private AlertHelper helper;
    NetWorkPresenterImp presenter;
    NetExp_new netExpClass;
    int netid;
    int tag = 0;

    private RelativeLayout empty_layout;
    private TextView four_title, three_title, two_title, first_title;
    private ConstraintLayout fourstage, threestage, twostage, firstsatge;
    private TextView fourstage_1_title, fourstage_2_title, fourstage_3_title;
    private TextView two_1_title, two_2_title, two_3_title;
    private TextView first_1_title, first_2_title, first_3_title, first_4_title;
    private LinearLayout first_1, first_2, first_3, first_4;
    private LinearLayout two_1, two_2, two_3;
    private LinearLayout fourstage_1, fourstage_2, fourstage_3;
    private LinearLayout lesson_1, lesson_2, lesson_3, lesson_4;
    private TextView lesson_1_name, lesson_2_name;
    private TextView title;
    private ImageView back;
    private String firstTitle, secondTitle, thirdTitle, fourthTitle;


    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_net_coursedetail_new;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.back);
        listner = new CheckDoubleClickListener(this);
        empty_layout = findViewById(R.id.empty_layout);
        fourstage = findViewById(R.id.fourstage);
        threestage = findViewById(R.id.threestage);
        twostage = findViewById(R.id.twostage);
        firstsatge = findViewById(R.id.firstsatge);
        four_title = findViewById(R.id.four_title);
        three_title = findViewById(R.id.three_title);
        two_title = findViewById(R.id.two_title);
        first_title = findViewById(R.id.first_title);
        fourstage_1_title = findViewById(R.id.fourstage_1_title);
        fourstage_2_title = findViewById(R.id.fourstage_2_title);
        fourstage_3_title = findViewById(R.id.fourstage_3_title);
        two_1_title = findViewById(R.id.two_1_title);
        two_2_title = findViewById(R.id.two_2_title);
        two_3_title = findViewById(R.id.two_3_title);
        first_1_title = findViewById(R.id.first_1_title);
        first_2_title = findViewById(R.id.first_2_title);
        first_3_title = findViewById(R.id.first_3_title);
        first_4_title = findViewById(R.id.first_4_title);
        first_1 = findViewById(R.id.first_1);
        first_2 = findViewById(R.id.first_2);
        first_3 = findViewById(R.id.first_3);
        first_4 = findViewById(R.id.first_4);
        two_1 = findViewById(R.id.two_1);
        two_2 = findViewById(R.id.two_2);
        two_3 = findViewById(R.id.two_3);
        fourstage_1 = findViewById(R.id.fourstage_1);
        fourstage_2 = findViewById(R.id.fourstage_2);
        fourstage_3 = findViewById(R.id.fourstage_3);
        lesson_1 = findViewById(R.id.lesson_1);
        lesson_2 = findViewById(R.id.lesson_2);
        lesson_3 = findViewById(R.id.lesson_3);
        lesson_4 = findViewById(R.id.lesson_4);
        lesson_1_name = findViewById(R.id.lesson_1_name);
        lesson_2_name = findViewById(R.id.lesson_2_name);


        first_1.setOnClickListener(listner);
        first_2.setOnClickListener(listner);
        first_3.setOnClickListener(listner);
        first_4.setOnClickListener(listner);
        two_1.setOnClickListener(listner);
        two_2.setOnClickListener(listner);
        two_3.setOnClickListener(listner);
        fourstage_1.setOnClickListener(listner);
        fourstage_2.setOnClickListener(listner);
        fourstage_3.setOnClickListener(listner);
        lesson_1.setOnClickListener(listner);
        lesson_2.setOnClickListener(listner);
        lesson_3.setOnClickListener(listner);
        lesson_4.setOnClickListener(listner);
        back.setOnClickListener(listner);


        String netname = getIntent().getStringExtra("netName");
        netid = getIntent().getIntExtra("netid", 0);
        title = findViewById(R.id.title);
        title.setText(netname);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();

        presenter = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getNetExpDetails_new(netid);


        if (MusicService.isPlay) {
//            if (musicService != null) {
//                musicService.stop();
//            }
            MusicService.stop();
        }
    }

    void setidshow(View layout, String isshow) {
        if (isshow.equals("1")) {
            layout.setVisibility(View.VISIBLE);
        } else {
            layout.setVisibility(View.GONE);
        }
    }


    void refresh() {
        if (netExpClass.getPlaceholdImg() != null && netExpClass.getPlaceholdImg().length() > 0) {
            empty_layout.setVisibility(View.VISIBLE);
        } else {
            empty_layout.setVisibility(View.GONE);
        }
        for (int i = 0; i < netExpClass.getInfo().size(); i++) {
            if (i == 0) {
                setidshow(fourstage, netExpClass.getInfo().get(i).getIsshow());
                four_title.setText(netExpClass.getInfo().get(i).getFchaptername());
                fourthTitle = netExpClass.getInfo().get(i).getFchaptername();
                if (netExpClass.getInfo().get(i).getInfo() != null) {
                    for (int m = 0; m < netExpClass.getInfo().get(i).getInfo().size(); m++) {
                        switch (netExpClass.getInfo().get(i).getInfo().get(m).getFsort()) {
                            case "1":
                                fourstage_1_title.setText(netExpClass.getInfo().get(i).getInfo().get(m).getFchaptername());
                                break;
                            case "2":
                                fourstage_2_title.setText(netExpClass.getInfo().get(i).getInfo().get(m).getFchaptername());
                                break;
                            case "3":
                                fourstage_3_title.setText(netExpClass.getInfo().get(i).getInfo().get(m).getFchaptername());
                                break;
                        }
                    }
                }
            } else if (i == 1) {
                setidshow(threestage, netExpClass.getInfo().get(i).getIsshow());
                three_title.setText(netExpClass.getInfo().get(i).getFchaptername());
                thirdTitle = netExpClass.getInfo().get(i).getFchaptername();
                if (netExpClass.getInfo().get(i).getInfo() != null) {
                    for (int m = 0; m < netExpClass.getInfo().get(i).getInfo().size(); m++) {
                        switch (m) {
                            case 0:
                                lesson_1_name.setText(netExpClass.getInfo().get(i).getInfo().get(m).getFchaptername());
                                break;
                            case 1:
                                lesson_2_name.setText(netExpClass.getInfo().get(i).getInfo().get(m).getFchaptername());
                        }
                    }
                }
            } else if (i == 2) {
                setidshow(twostage, netExpClass.getInfo().get(i).getIsshow());
                two_title.setText(netExpClass.getInfo().get(i).getFchaptername());
                secondTitle = netExpClass.getInfo().get(i).getFchaptername();
                if (netExpClass.getInfo().get(i).getInfo() != null) {
                    for (int m = 0; m < netExpClass.getInfo().get(i).getInfo().size(); m++) {
                        switch (netExpClass.getInfo().get(i).getInfo().get(m).getFsort()) {
                            case "1":
                                two_1_title.setText(netExpClass.getInfo().get(i).getInfo().get(m).getFchaptername());
                                break;
                            case "2":
                                two_2_title.setText(netExpClass.getInfo().get(i).getInfo().get(m).getFchaptername());
                                break;
                            case "3":
                                two_3_title.setText(netExpClass.getInfo().get(i).getInfo().get(m).getFchaptername());
                                break;
                        }
                    }
                }
            } else if (i == 3) {
                setidshow(firstsatge, netExpClass.getInfo().get(i).getIsshow());
                first_title.setText(netExpClass.getInfo().get(i).getFchaptername());
                firstTitle = netExpClass.getInfo().get(i).getFchaptername();
                if (netExpClass.getInfo().get(i).getInfo() != null) {
                    for (int m = 0; m < netExpClass.getInfo().get(i).getInfo().size(); m++) {
                        switch (netExpClass.getInfo().get(i).getInfo().get(m).getFsort()) {
                            case "2":
                                first_1_title.setText(netExpClass.getInfo().get(i).getInfo().get(m).getFchaptername());
                                break;
                            case "4":
                                first_2_title.setText(netExpClass.getInfo().get(i).getInfo().get(m).getFchaptername());
                                break;
                            case "1":
                                first_3_title.setText(netExpClass.getInfo().get(i).getInfo().get(m).getFchaptername());
                                break;
                            case "3":
                                first_4_title.setText(netExpClass.getInfo().get(i).getInfo().get(m).getFchaptername());
                                break;
                        }
                    }
                }
            }
        }
    }


    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    void tointent(int m, String sort, TextView view, int type) {//m:第几条数据，sort为1,2,3
        String url = "";
        Intent intent;
        String fid = "";
        if (netExpClass.getInfo().get(m).getInfo() != null) {
            for (int i = 0; i < netExpClass.getInfo().get(m).getInfo().size(); i++) {
                if (netExpClass.getInfo().get(m).getInfo().get(i).getFsort().equals(sort)) {
                    url = netExpClass.getInfo().get(m).getInfo().get(i).getFtesturl();
                    fid = netExpClass.getInfo().get(m).getInfo().get(i).getFid();
                    if (url != null && url.length() > 0) {
                        intent = new Intent(this, WebActivity.class);
                        intent.putExtra("url", url);
                        if (type == 1) {//去掉标题
                        } else {
                            intent.putExtra("title", view.getText());
                        }
                        intent.putExtra("flag", 0);//标题是否取消1：取消
                        startActivity(intent);
                    } else {
                        if (netExpClass.getInfo().get(m).getInfo().get(i).getFsort().equals("4")) {
                            Intent intent2 = new Intent(this, LessonActivity.class);
                            intent2.putExtra("lessonId", netExpClass.getInfo().get(m).getInfo().get(i).getFid());
                            intent2.putExtra("lessonName", netExpClass.getInfo().get(m).getInfo().get(i).getFchaptername());
                            intent2.putExtra("type", 4);
                            startActivity(intent2);
                        } else {
                            intent = new Intent(this, NetExpFirstVideoActivity.class);
                            if (type == 1) {//去掉标题
                            } else {
                                intent.putExtra("title", view.getText());
                            }
                            intent.putExtra("type", 4);
                            intent.putExtra("fid", fid);
                            startActivity(intent);
                        }
                    }
                }

            }
        }
    }


    @Override
    public void onCheckDoubleClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.first_1:
                for (int i = 0; i < netExpClass.getInfo().size(); i++) {
                    if (netExpClass.getInfo().get(i).getFchaptername().equals(firstTitle)) {
                        tointent(i, "2", first_1_title, 0);
                    }
                }
                break;
            case R.id.first_2:
                for (int i = 0; i < netExpClass.getInfo().size(); i++) {
                    if (netExpClass.getInfo().get(i).getFchaptername().equals(firstTitle)) {
                        tointent(i, "4", first_2_title, 0);
                    }
                }
                break;
            case R.id.first_3:
                for (int i = 0; i < netExpClass.getInfo().size(); i++) {
                    if (netExpClass.getInfo().get(i).getFchaptername().equals(firstTitle)) {
                        tointent(i, "1", first_3_title, 0);
                    }
                }
                break;
            case R.id.first_4:
                for (int i = 0; i < netExpClass.getInfo().size(); i++) {
                    if (netExpClass.getInfo().get(i).getFchaptername().equals(firstTitle)) {
                        tointent(i, "3", first_4_title, 0);
                    }
                }
                break;
            case R.id.two_1:
                for (int i = 0; i < netExpClass.getInfo().size(); i++) {
                    if (netExpClass.getInfo().get(i).getFchaptername().equals(secondTitle)) {
                        tointent(i, "1", two_1_title, 0);
                    }
                }
                break;
            case R.id.two_2:
                for (int i = 0; i < netExpClass.getInfo().size(); i++) {
                    if (netExpClass.getInfo().get(i).getFchaptername().equals(secondTitle)) {
                        tointent(i, "2", two_2_title, 0);
                    }
                }
                break;
            case R.id.two_3:
                for (int i = 0; i < netExpClass.getInfo().size(); i++) {
                    if (netExpClass.getInfo().get(i).getFchaptername().equals(secondTitle)) {
                        tointent(i, "3", two_3_title, 0);
                    }
                }
                break;
            case R.id.fourstage_1:
                for (int i = 0; i < netExpClass.getInfo().size(); i++) {
                    if (netExpClass.getInfo().get(i).getFchaptername().equals(fourthTitle)) {
                        tointent(i, "1", fourstage_1_title, 1);
                    }
                }
                break;
            case R.id.fourstage_2:
                for (int i = 0; i < netExpClass.getInfo().size(); i++) {
                    if (netExpClass.getInfo().get(i).getFchaptername().equals(fourthTitle)) {
                        tointent(i, "2", fourstage_2_title, 1);
                    }
                }
                break;
            case R.id.fourstage_3:
                for (int i = 0; i < netExpClass.getInfo().size(); i++) {
                    if (netExpClass.getInfo().get(i).getFchaptername().equals(fourthTitle)) {
                        tointent(i, "3", fourstage_3_title, 0);
                    }
                }
                break;
            case R.id.lesson_1:
                for (int i = 0; i < netExpClass.getInfo().size(); i++) {
                    if (netExpClass.getInfo().get(i).getFchaptername().equals(thirdTitle)) {
                        if (netExpClass.getInfo().get(i).getInfo() != null) {
                            if (netExpClass.getInfo().get(i).getInfo().size() > 0) {
                                intent = new Intent(this, LessonActivity.class);
                                intent.putExtra("lessonId", netExpClass.getInfo().get(i).getInfo().get(0).getFid());
                                intent.putExtra("lessonName", lesson_1_name.getText());
                                intent.putExtra("type", 4);
                                startActivity(intent);
                            }
                        }
                    }
                }
                break;
            case R.id.lesson_2:
                for (int i = 0; i < netExpClass.getInfo().size(); i++) {
                    if (netExpClass.getInfo().get(i).getFchaptername().equals(thirdTitle)) {
                        if (netExpClass.getInfo().get(i).getInfo() != null) {
                            if (netExpClass.getInfo().get(i).getInfo().size() > 1) {
                                intent = new Intent(this, LessonActivity.class);
                                intent.putExtra("lessonId", netExpClass.getInfo().get(i).getInfo().get(1).getFid());
                                intent.putExtra("lessonName", lesson_2_name.getText());
                                intent.putExtra("type", 4);
                                startActivity(intent);
                            }
                        }
                    }
                }
                break;
            case R.id.lesson_3:
                for (int i = 0; i < netExpClass.getInfo().size(); i++) {
                    if (netExpClass.getInfo().get(i).getFchaptername().equals(thirdTitle)) {
                        presenter.getListeningAndReading("1", netExpClass.getInfo().get(i).getFid(), tag, 0);
                    }
                }
                break;
            case R.id.lesson_4:
                for (int i = 0; i < netExpClass.getInfo().size(); i++) {
                    if (netExpClass.getInfo().get(i).getFchaptername().equals(thirdTitle)) {
                        intent = new Intent(this, NetListenAndReadActivity.class);
                        intent.putExtra("classid", netExpClass.getInfo().get(i).getFid());
                        intent.putExtra("week", "1");
                        startActivity(intent);
                    }
                }
                break;
        }
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETNETEXPDETAILS_NEW) {
            netExpClass = (NetExp_new) message.obj;
            refresh();
        } else if (message.what == MethodCode.EVENT_GETLISTENANDREAD + 80000 + tag) {
            listenAndRead = (ListenAndRead) message.obj;
            if (listenAndRead.getIsshow() == 0) {
                showInfo("暂无家庭作业");
            } else {
                Intent intent = new Intent(this, TaskContentActivity.class);
                intent.putExtra("classid", listenAndRead.getClassid());
                intent.putExtra("type", listenAndRead.getType());
                intent.putExtra("week", listenAndRead.getWeek());
                intent.putExtra("tabPosition", 0);
                startActivity(intent);
            }
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
