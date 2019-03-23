package com.annie.annieforchild.ui.activity.net;

import android.app.Dialog;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.annie.annieforchild.Utils.views.RecyclerLinearLayoutManager;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.ListenAndRead;
import com.annie.annieforchild.bean.net.NetSpecialDetail;
import com.annie.annieforchild.bean.net.SpecContent;
import com.annie.annieforchild.bean.net.netexpclass.NetExp_new;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.activity.lesson.TaskContentActivity;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.annieforchild.ui.adapter.NetSpecialDetailAdapter;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglei on 2019/3/13.
 */

public class NetSpecialDetailActivity extends BaseActivity implements ViewInfo, OnCheckDoubleClick {
    private RelativeLayout empty_layout, detail_layout, firstLayout1, firstLayout2, firstLayout3, firstLayout4;
    private ConstraintLayout firstsatge;
    private RecyclerView recycler;
    private TextView title, firstTitle, firstTitle1, firstTitle2, firstTitle3, firstTitle4;
    private ImageView back;
    private CheckDoubleClickListener listner;
    private Dialog dialog;
    private AlertHelper helper;
    private List<SpecContent> lists;
    private NetSpecialDetailAdapter adapter;
    private NetWorkPresenterImp presenter;
    private NetSpecialDetail netSpecialDetail;
    private ListenAndRead listenAndRead;
    private int netid, color;//1:浅绿 2:深绿
    private int tag = 1;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_net_special_detail;
    }

    @Override
    protected void initView() {
        title = findViewById(R.id.special_title);
        empty_layout = findViewById(R.id.special_empty_layout);
        back = findViewById(R.id.back);
        recycler = findViewById(R.id.special_recycler);
        detail_layout = findViewById(R.id.detail_layout);
        firstsatge = findViewById(R.id.firstsatge);
        firstTitle = findViewById(R.id.first_title);
        firstTitle1 = findViewById(R.id.first_1_title);
        firstTitle2 = findViewById(R.id.first_2_title);
        firstTitle3 = findViewById(R.id.first_3_title);
        firstTitle4 = findViewById(R.id.first_4_title);
        firstLayout1 = findViewById(R.id.first_1);
        firstLayout2 = findViewById(R.id.first_2);
        firstLayout3 = findViewById(R.id.first_3);
        firstLayout4 = findViewById(R.id.first_4);

        listner = new CheckDoubleClickListener(this);
        back.setOnClickListener(listner);
        firstLayout1.setOnClickListener(listner);
        firstLayout2.setOnClickListener(listner);
        firstLayout3.setOnClickListener(listner);
        firstLayout4.setOnClickListener(listner);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        recycler.setNestedScrollingEnabled(false);

        String netname = getIntent().getStringExtra("netName");
        netid = getIntent().getIntExtra("netid", 0);
        color = getIntent().getIntExtra("color", 1);
        title.setText(netname);
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        lists = new ArrayList<>();
        presenter = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();

        adapter = new NetSpecialDetailAdapter(this, lists, color, presenter, tag);
        recycler.setAdapter(adapter);

        presenter.getNetSpecialDetail(netid);
        if (MusicService.isPlay) {
            MusicService.stop();
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.first_1:
                //填写问卷
                if (netSpecialDetail != null) {
                    if (netSpecialDetail.getFirststage() != null) {
                        if (netSpecialDetail.getFirststage().getInfo() != null && netSpecialDetail.getFirststage().getInfo().size() != 0) {
                            boolean flag = false;
                            int j = 0;
                            for (int i = 0; i < netSpecialDetail.getFirststage().getInfo().size(); i++) {
                                if (netSpecialDetail.getFirststage().getInfo().get(i).getType() == 1) {
                                    flag = true;
                                    j = i;
                                }
                            }
                            if (flag) {
                                Intent intent = new Intent(this, WebActivity.class);
                                intent.putExtra("url", netSpecialDetail.getFirststage().getInfo().get(j).getUrl());
                                intent.putExtra("title", netSpecialDetail.getFirststage().getInfo().get(j).getName());
                                startActivity(intent);
                            } else {
                                showInfo("暂无内容");
                            }
                        }
                    }
                }
                break;
            case R.id.first_2:
                //安妮鲜花微课堂
                if (netSpecialDetail != null) {
                    if (netSpecialDetail.getFirststage() != null) {
                        if (netSpecialDetail.getFirststage().getInfo() != null && netSpecialDetail.getFirststage().getInfo().size() != 0) {
                            boolean flag = false;
                            int j = 0;
                            for (int i = 0; i < netSpecialDetail.getFirststage().getInfo().size(); i++) {
                                if (netSpecialDetail.getFirststage().getInfo().get(i).getType() == 2) {
                                    flag = true;
                                    j = i;
                                }
                            }
                            if (flag) {
                                Intent intent2 = new Intent(this, NetExpFirstVideoActivity.class);
                                intent2.putExtra("title", "安妮鲜花微课堂");
                                intent2.putExtra("type", 4);
                                intent2.putExtra("fid", netSpecialDetail.getFirststage().getInfo().get(j).getFid());
                                startActivity(intent2);
                            } else {
                                showInfo("暂无内容");
                            }
                        }
                    }
                }
                break;
            case R.id.first_3:
                //课程解析
                if (netSpecialDetail != null) {
                    if (netSpecialDetail.getFirststage() != null) {
                        if (netSpecialDetail.getFirststage().getInfo() != null && netSpecialDetail.getFirststage().getInfo().size() != 0) {
                            boolean flag = false;
                            int j = 0;
                            for (int i = 0; i < netSpecialDetail.getFirststage().getInfo().size(); i++) {
                                if (netSpecialDetail.getFirststage().getInfo().get(i).getType() == 3) {
                                    flag = true;
                                    j = i;
                                }
                            }
                            if (flag) {
                                Intent intent3 = new Intent(this, NetExpFirstVideoActivity.class);
                                intent3.putExtra("title", "课程解析");
                                intent3.putExtra("type", 4);
                                intent3.putExtra("fid", netSpecialDetail.getFirststage().getInfo().get(j).getFid());
                                startActivity(intent3);
                            } else {
                                showInfo("暂无内容");
                            }
                        }
                    }
                }
                break;
            case R.id.first_4:
                //常见问题
                if (netSpecialDetail != null) {
                    if (netSpecialDetail.getFirststage() != null) {
                        if (netSpecialDetail.getFirststage().getInfo() != null && netSpecialDetail.getFirststage().getInfo().size() != 0) {
                            boolean flag = false;
                            int j = 0;
                            for (int i = 0; i < netSpecialDetail.getFirststage().getInfo().size(); i++) {
                                if (netSpecialDetail.getFirststage().getInfo().get(i).getType() == 4) {
                                    flag = true;
                                    j = i;
                                }
                            }
                            if (flag) {
                                Intent intent = new Intent(this, WebActivity.class);
                                intent.putExtra("url", netSpecialDetail.getFirststage().getInfo().get(j).getUrl());
                                intent.putExtra("title", netSpecialDetail.getFirststage().getInfo().get(j).getName());
                                startActivity(intent);
                            } else {
                                showInfo("暂无内容");
                            }
                        }
                    }
                }

                break;
        }
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_SPECIALCLASS) {
            netSpecialDetail = (NetSpecialDetail) message.obj;
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

    private void refresh() {
        if (netSpecialDetail != null) {
            if (netSpecialDetail.getPlaceholdImg() != null && !netSpecialDetail.getPlaceholdImg().equals("")) {
                detail_layout.setVisibility(View.GONE);
                empty_layout.setVisibility(View.VISIBLE);
                return;
            }
            detail_layout.setVisibility(View.VISIBLE);
            if (netSpecialDetail.getContent() != null && netSpecialDetail.getContent().size() != 0) {
                recycler.setVisibility(View.VISIBLE);
                lists.clear();
                lists.addAll(netSpecialDetail.getContent());
                adapter.notifyDataSetChanged();
            } else {
                recycler.setVisibility(View.GONE);
            }
            if (netSpecialDetail.getFirststage() != null) {
                firstsatge.setVisibility(View.VISIBLE);
                firstTitle.setText(netSpecialDetail.getFirststage().getFcategoryname());
                if (color == 1) {
                    firstTitle.setBackgroundResource(R.drawable.classseed_icon_week);
                } else {
                    firstTitle.setBackgroundResource(R.drawable.classleaf_icon_week);
                }
//                if (netSpecialDetail.getFirststage().getInfo() != null && netSpecialDetail.getFirststage().getInfo().size() == 4) {
                firstsatge.setVisibility(View.VISIBLE);
//                } else {
//                    firstsatge.setVisibility(View.GONE);
//                }
            } else {
                firstsatge.setVisibility(View.GONE);
            }
        } else {
            detail_layout.setVisibility(View.GONE);
            empty_layout.setVisibility(View.VISIBLE);
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
