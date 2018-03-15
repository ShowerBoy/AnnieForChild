package com.annie.annieforchild.ui.activity.lesson;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

/**
 * 加入课表
 * Created by WangLei on 2018/3/2 0002
 */

public class AddOnlineScheActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back, scheduleImage;
    private RelativeLayout selectSchedule, scheduleStart, scheduleDays;
    private Button addSchedule;
    private TextView scheduleName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_online_schedule;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.add_online_schedule_back);
        scheduleImage = findViewById(R.id.schedule_image);
        scheduleName = findViewById(R.id.schedule_name);
        selectSchedule = findViewById(R.id.select_schedule_layout);
        scheduleStart = findViewById(R.id.schedule_start_layout);
        scheduleDays = findViewById(R.id.schedule_days_layout);
        addSchedule = findViewById(R.id.add_online_schedule);
        back.setOnClickListener(this);
        selectSchedule.setOnClickListener(this);
        scheduleStart.setOnClickListener(this);
        scheduleDays.setOnClickListener(this);
        addSchedule.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_online_schedule_back:
                finish();
                break;
            case R.id.select_schedule_layout:

                break;
        }
    }
}
