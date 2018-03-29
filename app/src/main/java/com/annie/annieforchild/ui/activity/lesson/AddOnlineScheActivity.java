package com.annie.annieforchild.ui.activity.lesson;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.ui.adapter.PopupAdapter;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 加入课表
 * Created by WangLei on 2018/3/2 0002
 */

public class AddOnlineScheActivity extends BaseActivity implements View.OnClickListener, OnDateSetListener {
    private ImageView back, scheduleImage;
    private RelativeLayout selectSchedule, scheduleStart, scheduleDays, scheduleTime;
    private Button addSchedule;
    private TextView scheduleName, scheduleStartText, scheduleDaysText, scheduleTimeText;
    private TimePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog1;
    private TimePickerDialog timePickerDialog2;
    private ListView popup_listView;
    private List<String> popup_lists;
    private PopupAdapter popupAdapter;
    SimpleDateFormat sf1;
    SimpleDateFormat sf2;
    String startTime, endTime;
    PopupWindow popupWindow;
    View popup_contentView;
    long thirtyYears = 30L * 365 * 1000 * 60 * 60 * 24L;
    long oneYears = 1L * 365 * 1000 * 60 * 60 * 24L;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_online_schedule;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.add_online_schedule_back);
        scheduleImage = findViewById(R.id.schedule_image);
        scheduleName = findViewById(R.id.schedule_name);
        scheduleStartText = findViewById(R.id.schedule_start_text);
        scheduleDaysText = findViewById(R.id.schedule_days_text);
        scheduleTimeText = findViewById(R.id.schedule_time_text);
        selectSchedule = findViewById(R.id.select_schedule_layout);
        scheduleStart = findViewById(R.id.schedule_start_layout);
        scheduleDays = findViewById(R.id.schedule_days_layout);
        scheduleTime = findViewById(R.id.schedule_time_layout);
        addSchedule = findViewById(R.id.add_online_schedule);
        back.setOnClickListener(this);
        selectSchedule.setOnClickListener(this);
        scheduleStart.setOnClickListener(this);
        scheduleDays.setOnClickListener(this);
        scheduleTime.setOnClickListener(this);
        addSchedule.setOnClickListener(this);
        popup_lists = new ArrayList<>();
        popupWindow = new PopupWindow(popup_contentView, ViewGroup.LayoutParams.MATCH_PARENT, 500, true);
        popup_contentView = LayoutInflater.from(this).inflate(R.layout.activity_popupwindow_item, null);
        popup_listView = popup_contentView.findViewById(R.id.popup_lists1);
        popupWindow.setContentView(popup_contentView);
        popupWindow.setOutsideTouchable(true);
    }

    @Override
    protected void initData() {
        sf1 = new SimpleDateFormat("yyyy-MM-dd");
        sf2 = new SimpleDateFormat("HH:mm");
        datePickerDialog = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setThemeColor(R.color.black)
                .setTitleStringId("日期选择")
                .setMinMillseconds(System.currentTimeMillis() - oneYears)
                .setMaxMillseconds(System.currentTimeMillis() + thirtyYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setWheelItemTextSize(16)
                .setCallBack(this)
                .build();
        timePickerDialog1 = new TimePickerDialog.Builder()
                .setType(Type.HOURS_MINS)
                .setThemeColor(R.color.black)
                .setTitleStringId("开始时间选择")
                .setCurrentMillseconds(System.currentTimeMillis())
                .setWheelItemTextSize(16)
                .setCallBack(this)
                .build();
        timePickerDialog2 = new TimePickerDialog.Builder()
                .setType(Type.HOURS_MINS)
                .setThemeColor(R.color.black)
                .setTitleStringId("结束时间选择")
                .setCurrentMillseconds(System.currentTimeMillis())
                .setWheelItemTextSize(16)
                .setCallBack(this)
                .build();
        for (int i = 1; i < 31; i++) {
            popup_lists.add(i + "");
        }
        popupAdapter = new PopupAdapter(this, popup_lists);
        popup_listView.setAdapter(popupAdapter);
        popup_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SystemUtils.show(AddOnlineScheActivity.this, popup_lists.get(position));
                scheduleDaysText.setText(popup_lists.get(position));
                popupWindow.dismiss();
            }
        });

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
                //选择课程

                break;
            case R.id.schedule_start_layout:
                //设置开始时间
                datePickerDialog.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.schedule_days_layout:
                //重复天数
                popupWindow.showAsDropDown(scheduleDays);
                break;
            case R.id.schedule_time_layout:
                //每天开始结束时间
                timePickerDialog1.show(getSupportFragmentManager(), "hour:minute1");
                break;
            case R.id.add_online_schedule:
                //加入课表

                break;
        }
    }

    @Override
    public void onDateSet(TimePickerDialog PickerDialog, long l) {
        if (PickerDialog.getTag().equals("year_month_day")) {
            scheduleStartText.setText(sf1.format(new Date(l)).replace("-", ""));
        } else if (PickerDialog.getTag().equals("hour:minute1")) {
            startTime = sf2.format(new Date(l));
            PickerDialog.dismiss();
            timePickerDialog2.show(getSupportFragmentManager(), "hour:minute2");
        } else if (PickerDialog.getTag().equals("hour:minute2")) {
            endTime = sf2.format(new Date(l));
            scheduleTimeText.setText(startTime + "~" + endTime);
        }
    }
}
