package com.annie.annieforchild.ui.activity.lesson;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.material.Material;
import com.annie.annieforchild.bean.material.MaterialGroup;
import com.annie.annieforchild.bean.schedule.Schedule;
import com.annie.annieforchild.presenter.SchedulePresenter;
import com.annie.annieforchild.presenter.imp.SchedulePresenterImp;
import com.annie.annieforchild.ui.adapter.PopupAdapter;
import com.annie.annieforchild.view.ScheduleView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.ashokvarma.bottomnavigation.utils.Utils;
import com.bumptech.glide.Glide;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 加入课表
 * Created by WangLei on 2018/3/2 0002
 */

public class AddOnlineScheActivity extends BaseActivity implements OnCheckDoubleClick, OnDateSetListener, ScheduleView {
    private ImageView back, scheduleImage;
    private RelativeLayout scheduleStart, scheduleDays, scheduleTime;
    private Button addSchedule;
    private TextView scheduleName, scheduleStartText, scheduleDaysText, scheduleTimeText, title;
    private TimePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog1;
    private TimePickerDialog timePickerDialog2;
    private ListView popup_listView;
    private List<MaterialGroup> popup_lists;
    private PopupAdapter popupAdapter;
    private SchedulePresenter presenter;
    private Intent intent;
    private Bundle bundle;
    private Schedule schedule;
    private Material material;
    private String startDate = null;
    private int totalDays = 1;
    SimpleDateFormat sf1;
    SimpleDateFormat sf2;
    String startTime, endTime;
    PopupWindow popupWindow;
    View popup_contentView;
    long thirtyYears = 30L * 365 * 1000 * 60 * 60 * 24L;
    long oneYears = 0L * 365 * 1000 * 60 * 60 * 24L;
    private AlertHelper helper;
    private Dialog dialog;
    private String date;
    private int audioType, audioSource;
    private CheckDoubleClickListener listener;

    {
        setRegister(true);
    }

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
        scheduleStart = findViewById(R.id.schedule_start_layout);
        scheduleDays = findViewById(R.id.schedule_days_layout);
        scheduleTime = findViewById(R.id.schedule_time_layout);
        addSchedule = findViewById(R.id.add_online_schedule);
        title = findViewById(R.id.add_title);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
        scheduleStart.setOnClickListener(listener);
        scheduleDays.setOnClickListener(listener);
        scheduleTime.setOnClickListener(listener);
        addSchedule.setOnClickListener(listener);
        popup_lists = new ArrayList<>();
        popupWindow = new PopupWindow(popup_contentView, ViewGroup.LayoutParams.MATCH_PARENT, Utils.dp2px(this, 200), true);
        popup_contentView = LayoutInflater.from(this).inflate(R.layout.activity_popupwindow_item, null);
        popup_listView = popup_contentView.findViewById(R.id.popup_lists1);
        popupWindow.setContentView(popup_contentView);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindowGray(false);
            }
        });
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        sf1 = new SimpleDateFormat("yyyy-MM-dd");
        sf2 = new SimpleDateFormat("HH:mm");
        scheduleStartText.setText(sf1.format(new Date()));
        startDate = sf1.format(new Date()).replace("-", "");
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
            popup_lists.add(new MaterialGroup(i + "", false));
        }
        popupAdapter = new PopupAdapter(this, popup_lists);
        popup_listView.setAdapter(popupAdapter);
        popup_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                totalDays = Integer.parseInt(popup_lists.get(position).getTitle());
                scheduleDaysText.setText(popup_lists.get(position).getTitle());
                popupWindow.dismiss();
            }
        });

        /**
         * {@link com.annie.annieforchild.ui.fragment.selectmaterial.SelectGrindEarFragment#instance(Schedule, String)}
         * {@link com.annie.annieforchild.ui.fragment.selectmaterial.SelectReadingFragment#instance(Schedule, String)}
         * {@link com.annie.annieforchild.ui.fragment.selectmaterial.SelectSpokenFragment#instance(Schedule, String)}
         */
        intent = getIntent();
        if (intent != null) {
            bundle = intent.getExtras();
            material = (Material) bundle.getSerializable("material");
            if (bundle.getSerializable("schedule") != null) {
                schedule = (Schedule) bundle.getSerializable("schedule");
                date = bundle.getString("date");
            } else {
                date = bundle.getString("date");
            }
            audioType = bundle.getInt("audioType");
            audioSource = bundle.getInt("audioSource");
        }
        Glide.with(this).load(material.getImageUrl()).into(scheduleImage);
        scheduleName.setText(material.getName());
        if (schedule != null) {
            title.setText("修改课表");
            addSchedule.setText("修改课表");
            startTime = schedule.getStart();
            endTime = schedule.getStop();
            startDate = date;
            scheduleTimeText.setText(schedule.getStart() + "~" + schedule.getStop());
            scheduleStartText.setText(startDate.substring(0, 4) + "-" + startDate.substring(4, 6) + "-" + startDate.substring(6, 8));
        } else {
            title.setText("加入课表");
            addSchedule.setText("加入课表");
            startDate = date;
            scheduleStartText.setText(startDate.substring(0, 4) + "-" + startDate.substring(4, 6) + "-" + startDate.substring(6, 8));
        }
        presenter = new SchedulePresenterImp(this, this);
        presenter.initViewAndData();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.add_online_schedule_back:
//                finish();
//                break;
//            case R.id.schedule_start_layout:
//                //设置开始时间
//                datePickerDialog.show(getSupportFragmentManager(), "year_month_day");
//                break;
//            case R.id.schedule_days_layout:
//                //重复天数
////                popupWindow.showAsDropDown(scheduleDays);
//                getWindowGray(true);
//                popupWindow.showAtLocation(popup_contentView, Gravity.BOTTOM, 0, 0);
//                break;
//            case R.id.schedule_time_layout:
//                //每天开始结束时间
//                timePickerDialog1.show(getSupportFragmentManager(), "hour:minute1");
//                break;
//            case R.id.add_online_schedule:
//                //加入课表
//                if (startDate != null && !startDate.equals("")) {
//                    if (startTime != null && !startTime.equals("") && endTime != null && !endTime.equals("")) {
//                        if (title.getText().equals("加入课表")) {
//                            presenter.addSchedule(material.getMaterialId(), startDate, totalDays, startTime, endTime, audioType, audioSource);
//                        } else {
//                            presenter.editSchedule(schedule.getScheduleId(), material.getMaterialId(), startDate, totalDays, startTime, endTime);
//                        }
//                    } else {
//                        showInfo("请设置每天学习时间");
//                    }
//                } else {
//                    showInfo("请选择课表开始时间");
//                }
//                break;
//        }
//    }

    @Override
    public void onDateSet(TimePickerDialog PickerDialog, long l) {
        if (PickerDialog.getTag().equals("year_month_day")) {
            startDate = sf1.format(new Date(l)).replace("-", "");
            scheduleStartText.setText(sf1.format(new Date(l)));
        } else if (PickerDialog.getTag().equals("hour:minute1")) {
            String newTime = sf2.format(new Date(l)).replace(":", "");
            String nowTime = sf2.format(new Date()).replace(":", "");
            if (startDate != null) {
                String now = sf1.format(new Date()).replace("-", "");
                int nowYear = Integer.parseInt(now.substring(0, 4));
                int nowMonth = Integer.parseInt(now.substring(4, 6));
                int nowDay = Integer.parseInt(now.substring(6, 8));
                int startYear = Integer.parseInt(startDate.substring(0, 4));
                int startMonth = Integer.parseInt(startDate.substring(4, 6));
                int startDay = Integer.parseInt(startDate.substring(6, 8));
                if (startYear == nowYear && startMonth == nowMonth && startDay == nowDay) {
                    if (Integer.parseInt(newTime) < Integer.parseInt(nowTime)) {
                        showInfo("所选时间不能超过当前时间");
                        PickerDialog.dismiss();
                    } else {
                        startTime = sf2.format(new Date(l));
                        PickerDialog.dismiss();
                        timePickerDialog2.show(getSupportFragmentManager(), "hour:minute2");
                    }
                } else {
                    startTime = sf2.format(new Date(l));
                    PickerDialog.dismiss();
                    timePickerDialog2.show(getSupportFragmentManager(), "hour:minute2");
                }
            } else {
                startTime = sf2.format(new Date(l));
                PickerDialog.dismiss();
                timePickerDialog2.show(getSupportFragmentManager(), "hour:minute2");
            }

        } else if (PickerDialog.getTag().equals("hour:minute2")) {
            endTime = sf2.format(new Date(l));
            int end1 = Integer.parseInt(endTime.split(":")[0]);
            int end2 = Integer.parseInt(endTime.split(":")[1]);
            int start1 = Integer.parseInt(startTime.split(":")[0]);
            int start2 = Integer.parseInt(startTime.split(":")[1]);
            if (end1 > start1) {
                scheduleTimeText.setText(startTime + "~" + endTime);
            } else if (end1 == start1) {
                if (end2 > start2) {
                    scheduleTimeText.setText(startTime + "~" + endTime);
                } else {
                    endTime = "";
                    showInfo("结束时间不能小于开始时间");
                }
            } else {
                endTime = "";
                showInfo("结束时间不能小于开始时间");
            }

        }

    }

    /**
     * {@link SchedulePresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_ADDSCHEDULE) {
            showInfo((String) message.obj);
            finish();
        } else if (message.what == MethodCode.EVENT_EDITSCHEDULE) {
            showInfo((String) message.obj);
            finish();
        }
    }

    private void getWindowGray(boolean tag) {
        if (tag) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.alpha = 0.7f;
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setAttributes(layoutParams);
        } else {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.alpha = 1f;
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setAttributes(layoutParams);
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

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.add_online_schedule_back:
                finish();
                break;
            case R.id.schedule_start_layout:
                //设置开始时间
                datePickerDialog.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.schedule_days_layout:
                //重复天数
//                popupWindow.showAsDropDown(scheduleDays);
                getWindowGray(true);
                popupWindow.showAtLocation(popup_contentView, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.schedule_time_layout:
                //每天开始结束时间
                timePickerDialog1.show(getSupportFragmentManager(), "hour:minute1");
                break;
            case R.id.add_online_schedule:
                //加入课表
                if (startDate != null && !startDate.equals("")) {
                    if (startTime != null && !startTime.equals("") && endTime != null && !endTime.equals("")) {
                        String now = sf1.format(new Date()).replace("-", "");
                        int nowYear = Integer.parseInt(now.substring(0, 4));
                        int nowMonth = Integer.parseInt(now.substring(4, 6));
                        int nowDay = Integer.parseInt(now.substring(6, 8));
                        int startYear = Integer.parseInt(startDate.substring(0, 4));
                        int startMonth = Integer.parseInt(startDate.substring(4, 6));
                        int startDay = Integer.parseInt(startDate.substring(6, 8));
                        if (startYear == nowYear && startMonth == nowMonth && startDay == nowDay) {
                            String nowTime = sf2.format(new Date()).replace(":", "");
                            String startTime2 = startTime.replace(":", "");
                            if (Integer.parseInt(startTime2) < Integer.parseInt(nowTime)) {
                                showInfo("所选时间不能超过当前时间");
                                return;
                            } else {
                                if (title.getText().equals("加入课表")) {
                                    presenter.addSchedule(material.getMaterialId(), startDate, totalDays, startTime, endTime, audioType, audioSource);
                                } else {
                                    presenter.editSchedule(schedule.getScheduleId(), material.getMaterialId(), startDate, totalDays, startTime, endTime);
                                }
                            }
                        } else {
                            if (title.getText().equals("加入课表")) {
                                presenter.addSchedule(material.getMaterialId(), startDate, totalDays, startTime, endTime, audioType, audioSource);
                            } else {
                                presenter.editSchedule(schedule.getScheduleId(), material.getMaterialId(), startDate, totalDays, startTime, endTime);
                            }
                        }
                    } else {
                        showInfo("请设置每天学习时间");
                    }
                } else {
                    showInfo("请选择课表开始时间");
                }
                break;
        }
    }
}
