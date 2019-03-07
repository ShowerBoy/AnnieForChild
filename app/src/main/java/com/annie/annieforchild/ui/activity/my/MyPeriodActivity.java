package com.annie.annieforchild.ui.activity.my;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.period.MyPeriod;
import com.annie.annieforchild.bean.period.Period;
import com.annie.annieforchild.bean.period.PeriodBean;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.adapter.PeriodAdapter;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 课时核对
 * Created by wanglei on 2018/8/21.
 */

public class MyPeriodActivity extends BaseActivity implements SongView, View.OnClickListener {
    private GrindEarPresenter presenter;
    private RecyclerView recycler;
    private ProgressBar progressBar;
    private LinearLayout periodLinear, surplusLinear;
    private TextView title, totalPeriod, currentPeriod, changePeriod, surplusPeriod;
    private ImageView back, empty;
    private AlertHelper helper;
    private Dialog dialog;
    private PeriodBean periodBean;
    private List<MyPeriod> mylists;
    private List<Period> lists;
    private PeriodAdapter adapter;
    private String[] titles;
    private int checkItem = 0;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_period;
    }

    @Override
    protected void initView() {
        title = findViewById(R.id.period_title);
        totalPeriod = findViewById(R.id.total_period);
        currentPeriod = findViewById(R.id.current_period);
        back = findViewById(R.id.period_back);
        recycler = findViewById(R.id.period_recycler);
        changePeriod = findViewById(R.id.change_period);
        progressBar = findViewById(R.id.period_progress);
        empty = findViewById(R.id.empty_my_period);
        surplusPeriod = findViewById(R.id.surplus_period);
        periodLinear = findViewById(R.id.period_linear);
        surplusLinear = findViewById(R.id.surplus_linear);
        back.setOnClickListener(this);
        changePeriod.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        mylists = new ArrayList<>();
        lists = new ArrayList<>();
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        adapter = new PeriodAdapter(this, lists, presenter);
        recycler.setAdapter(adapter);
        presenter.myPeriod();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.period_back:
                finish();
                break;
            case R.id.change_period:
                SystemUtils.GeneralDialog(this, "切换课程")
                        .setSingleChoiceItems(titles, checkItem, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                checkItem = which;
                                title.setText(mylists.get(checkItem).getTitle());
                                totalPeriod.setText("班级总课时：" + mylists.get(checkItem).getTotalperiod() + "课时");
                                currentPeriod.setText("班级进度：" + mylists.get(checkItem).getCurrentperiod() + "课时");
                                float progress = (float) mylists.get(checkItem).getCurrentperiod() / mylists.get(checkItem).getTotalperiod();
                                progressBar.setProgress((int) (progress * 100));
                                lists.clear();
                                lists.addAll(mylists.get(checkItem).getDetails());
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
        }
    }

    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_MYPERIOD) {
            periodBean = (PeriodBean) message.obj;
            if (periodBean != null) {
                surplusPeriod.setText("剩余总课时：" + periodBean.getSurplusPeriod() + "课时");
                if (periodBean.getPeriodList() == null) {
                    empty.setVisibility(View.VISIBLE);
                    if (application.getSystemUtils().getUserInfo().getStatus() == 1) {
                        surplusLinear.setVisibility(View.GONE);
                    } else {
                        surplusLinear.setVisibility(View.VISIBLE);
                    }
                    changePeriod.setVisibility(View.GONE);
                    periodLinear.setVisibility(View.GONE);
                    return;
                }
                mylists.clear();
                mylists.addAll(periodBean.getPeriodList());
                if (mylists.size() == 0) {
                    empty.setVisibility(View.VISIBLE);
                    changePeriod.setVisibility(View.GONE);
                    periodLinear.setVisibility(View.GONE);
                } else {
                    periodLinear.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.GONE);
                    if (mylists.size() != 1) {
                        changePeriod.setVisibility(View.VISIBLE);
                        titles = new String[mylists.size()];
                        for (int i = 0; i < mylists.size(); i++) {
                            if (mylists.get(i).getTitle() == null) {
                                mylists.get(i).setTitle("");
                                titles[i] = mylists.get(i).getTitle();
                            } else {
                                titles[i] = mylists.get(i).getTitle();
                            }
                        }
                    } else {
                        changePeriod.setVisibility(View.GONE);
                    }
                    checkItem = 0;
                    title.setText(mylists.get(checkItem).getTitle());
                    totalPeriod.setText("班级总课时：" + mylists.get(checkItem).getTotalperiod() + "课时");
                    currentPeriod.setText("班级进度：" + mylists.get(checkItem).getCurrentperiod() + "课时");
                    float progress = (float) mylists.get(checkItem).getCurrentperiod() / mylists.get(checkItem).getTotalperiod();
                    progressBar.setProgress((int) (progress * 100));
                    lists.clear();
                    lists.addAll(mylists.get(checkItem).getDetails());
                    if (lists.size() == 0) {
                        empty.setVisibility(View.VISIBLE);
                    } else {
                        empty.setVisibility(View.GONE);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        } else if (message.what == MethodCode.EVENT_SUGGESTPERIOD) {
            showInfo((String) message.obj);
            presenter.myPeriod();
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
