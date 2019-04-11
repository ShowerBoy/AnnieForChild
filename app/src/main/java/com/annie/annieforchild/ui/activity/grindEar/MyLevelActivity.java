package com.annie.annieforchild.ui.activity.grindEar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的磨耳朵级别
 * Created by wanglei on 2018/6/6.
 */

public class MyLevelActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView name, lev, time, title;
    private CircleImageView headpic;
    private ProgressBar progressBar;
    private Intent intent;
    private Bundle bundle;
    private String totalDuration, level, sublevel;
    private int hour = 0, min;
    private int tenHourSecond = 10 * 60 * 60;
    private int progressSecond;
    private int nowSecond;
    private int progress;
    private float progresses;
    private String tag;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_level;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.my_level_back);
        title = findViewById(R.id.my_level_title);
        name = findViewById(R.id.my_level_name);
        time = findViewById(R.id.my_level_time);
        lev = findViewById(R.id.my_level_lv);
        headpic = findViewById(R.id.my_level_headpic);
        progressBar = findViewById(R.id.my_level_progress);
        back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        intent = getIntent();
        bundle = intent.getExtras();
        tag = bundle.getString("tag");
        level = bundle.getString("level");
        sublevel = bundle.getString("sublevel");
        totalDuration = bundle.getString("totalduration");
        if (tag.equals("grindear")) {
            title.setText("我的磨耳朵级别");
            initialize();
        } else {
            title.setText("阅读级别");
            initReading();
        }
    }

    private void initialize() {
        Glide.with(this).load(application.getSystemUtils().getUserInfo().getAvatar()).into(headpic);
        name.setText(application.getSystemUtils().getUserInfo().getName());
        lev.setText("Lv" + level);
        nowSecond = Integer.parseInt(totalDuration);
        min = Integer.parseInt(totalDuration) / 60;
        if (min >= 60) {
            hour = min / 60;
            min = min % 60;
        }
        time.setText("累计时长" + hour + "小时" + min + "分钟");
//        if (level.equals("1")) {
//            if (sublevel.equals("1")) {
//                progressSecond = tenHourSecond;
//            } else if (sublevel.equals("2")) {
//                level1_1.setImageResource(R.drawable.icon_grindear_lv1_1_t);
//                progressSecond = tenHourSecond * 2;
//                nowSecond = nowSecond - tenHourSecond;
//            } else if (sublevel.equals("3")) {
//                level1_1.setImageResource(R.drawable.icon_grindear_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_grindear_lv1_2_t);
//                progressSecond = tenHourSecond * 3;
//                nowSecond = nowSecond - (tenHourSecond * 3);
//            } else if (sublevel.equals("4")) {
//                level1_1.setImageResource(R.drawable.icon_grindear_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_grindear_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_grindear_lv1_3_t);
//                progressSecond = tenHourSecond * 4;
//                nowSecond = nowSecond - (tenHourSecond * 6);
//            }
//        } else if (level.equals("2")) {
//            if (sublevel.equals("1")) {
//                level1_1.setImageResource(R.drawable.icon_grindear_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_grindear_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_grindear_lv1_3_t);
//                level1_4.setImageResource(R.drawable.icon_grindear_lv1_4_t);
//                progressSecond = tenHourSecond;
//                nowSecond = nowSecond - (tenHourSecond * 10);
//            } else if (sublevel.equals("2")) {
//                level1_1.setImageResource(R.drawable.icon_grindear_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_grindear_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_grindear_lv1_3_t);
//                level1_4.setImageResource(R.drawable.icon_grindear_lv1_4_t);
//                level2_1.setImageResource(R.drawable.icon_grindear_lv2_1_t);
//                progressSecond = tenHourSecond * 2;
//                nowSecond = nowSecond - (tenHourSecond * 11);
//            } else if (sublevel.equals("3")) {
//                level1_1.setImageResource(R.drawable.icon_grindear_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_grindear_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_grindear_lv1_3_t);
//                level1_4.setImageResource(R.drawable.icon_grindear_lv1_4_t);
//                level2_1.setImageResource(R.drawable.icon_grindear_lv2_1_t);
//                level2_2.setImageResource(R.drawable.icon_grindear_lv2_2_t);
//                progressSecond = tenHourSecond * 3;
//                nowSecond = nowSecond - (tenHourSecond * 13);
//            } else if (sublevel.equals("4")) {
//                level1_1.setImageResource(R.drawable.icon_grindear_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_grindear_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_grindear_lv1_3_t);
//                level1_4.setImageResource(R.drawable.icon_grindear_lv1_4_t);
//                level2_1.setImageResource(R.drawable.icon_grindear_lv2_1_t);
//                level2_2.setImageResource(R.drawable.icon_grindear_lv2_2_t);
//                level2_3.setImageResource(R.drawable.icon_grindear_lv2_3_t);
//                progressSecond = tenHourSecond * 4;
//                nowSecond = nowSecond - (tenHourSecond * 16);
//            }
//        } else if (level.equals("3")) {
//            if (sublevel.equals("1")) {
//                level1_1.setImageResource(R.drawable.icon_grindear_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_grindear_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_grindear_lv1_3_t);
//                level1_4.setImageResource(R.drawable.icon_grindear_lv1_4_t);
//                level2_1.setImageResource(R.drawable.icon_grindear_lv2_1_t);
//                level2_2.setImageResource(R.drawable.icon_grindear_lv2_2_t);
//                level2_3.setImageResource(R.drawable.icon_grindear_lv2_3_t);
//                level2_4.setImageResource(R.drawable.icon_grindear_lv2_4_t);
//                progressSecond = tenHourSecond;
//                nowSecond = nowSecond - (tenHourSecond * 20);
//            } else if (sublevel.equals("2")) {
//                level1_1.setImageResource(R.drawable.icon_grindear_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_grindear_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_grindear_lv1_3_t);
//                level1_4.setImageResource(R.drawable.icon_grindear_lv1_4_t);
//                level2_1.setImageResource(R.drawable.icon_grindear_lv2_1_t);
//                level2_2.setImageResource(R.drawable.icon_grindear_lv2_2_t);
//                level2_3.setImageResource(R.drawable.icon_grindear_lv2_3_t);
//                level2_4.setImageResource(R.drawable.icon_grindear_lv2_4_t);
//                level3_1.setImageResource(R.drawable.icon_grindear_lv3_1_t);
//                progressSecond = tenHourSecond * 2;
//                nowSecond = nowSecond - (tenHourSecond * 21);
//            } else if (sublevel.equals("3")) {
//                level1_1.setImageResource(R.drawable.icon_grindear_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_grindear_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_grindear_lv1_3_t);
//                level1_4.setImageResource(R.drawable.icon_grindear_lv1_4_t);
//                level2_1.setImageResource(R.drawable.icon_grindear_lv2_1_t);
//                level2_2.setImageResource(R.drawable.icon_grindear_lv2_2_t);
//                level2_3.setImageResource(R.drawable.icon_grindear_lv2_3_t);
//                level2_4.setImageResource(R.drawable.icon_grindear_lv2_4_t);
//                level3_1.setImageResource(R.drawable.icon_grindear_lv3_1_t);
//                level3_2.setImageResource(R.drawable.icon_grindear_lv3_2_t);
//                progressSecond = tenHourSecond * 3;
//                nowSecond = nowSecond - (tenHourSecond * 23);
//            } else if (sublevel.equals("4")) {
//                level1_1.setImageResource(R.drawable.icon_grindear_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_grindear_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_grindear_lv1_3_t);
//                level1_4.setImageResource(R.drawable.icon_grindear_lv1_4_t);
//                level2_1.setImageResource(R.drawable.icon_grindear_lv2_1_t);
//                level2_2.setImageResource(R.drawable.icon_grindear_lv2_2_t);
//                level2_3.setImageResource(R.drawable.icon_grindear_lv2_3_t);
//                level2_4.setImageResource(R.drawable.icon_grindear_lv2_4_t);
//                level3_1.setImageResource(R.drawable.icon_grindear_lv3_1_t);
//                level3_2.setImageResource(R.drawable.icon_grindear_lv3_2_t);
//                level3_3.setImageResource(R.drawable.icon_grindear_lv3_3_t);
//                progressSecond = tenHourSecond * 4;
//                nowSecond = nowSecond - (tenHourSecond * 26);
//            }
//        } else if (level.equals("4")) {
//            if (sublevel.equals("1")) {
//                level1_1.setImageResource(R.drawable.icon_grindear_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_grindear_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_grindear_lv1_3_t);
//                level1_4.setImageResource(R.drawable.icon_grindear_lv1_4_t);
//                level2_1.setImageResource(R.drawable.icon_grindear_lv2_1_t);
//                level2_2.setImageResource(R.drawable.icon_grindear_lv2_2_t);
//                level2_3.setImageResource(R.drawable.icon_grindear_lv2_3_t);
//                level2_4.setImageResource(R.drawable.icon_grindear_lv2_4_t);
//                level3_1.setImageResource(R.drawable.icon_grindear_lv3_1_t);
//                level3_2.setImageResource(R.drawable.icon_grindear_lv3_2_t);
//                level3_3.setImageResource(R.drawable.icon_grindear_lv3_3_t);
//                level3_4.setImageResource(R.drawable.icon_grindear_lv3_4_t);
//                progressSecond = tenHourSecond;
//                nowSecond = nowSecond - (tenHourSecond * 30);
//            } else if (sublevel.equals("2")) {
//                level1_1.setImageResource(R.drawable.icon_grindear_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_grindear_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_grindear_lv1_3_t);
//                level1_4.setImageResource(R.drawable.icon_grindear_lv1_4_t);
//                level2_1.setImageResource(R.drawable.icon_grindear_lv2_1_t);
//                level2_2.setImageResource(R.drawable.icon_grindear_lv2_2_t);
//                level2_3.setImageResource(R.drawable.icon_grindear_lv2_3_t);
//                level2_4.setImageResource(R.drawable.icon_grindear_lv2_4_t);
//                level3_1.setImageResource(R.drawable.icon_grindear_lv3_1_t);
//                level3_2.setImageResource(R.drawable.icon_grindear_lv3_2_t);
//                level3_3.setImageResource(R.drawable.icon_grindear_lv3_3_t);
//                level3_4.setImageResource(R.drawable.icon_grindear_lv3_4_t);
//                level4_1.setImageResource(R.drawable.icon_grindear_lv4_1_t);
//                progressSecond = tenHourSecond * 2;
//                nowSecond = nowSecond - (tenHourSecond * 31);
//            } else if (sublevel.equals("3")) {
//                level1_1.setImageResource(R.drawable.icon_grindear_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_grindear_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_grindear_lv1_3_t);
//                level1_4.setImageResource(R.drawable.icon_grindear_lv1_4_t);
//                level2_1.setImageResource(R.drawable.icon_grindear_lv2_1_t);
//                level2_2.setImageResource(R.drawable.icon_grindear_lv2_2_t);
//                level2_3.setImageResource(R.drawable.icon_grindear_lv2_3_t);
//                level2_4.setImageResource(R.drawable.icon_grindear_lv2_4_t);
//                level3_1.setImageResource(R.drawable.icon_grindear_lv3_1_t);
//                level3_2.setImageResource(R.drawable.icon_grindear_lv3_2_t);
//                level3_3.setImageResource(R.drawable.icon_grindear_lv3_3_t);
//                level3_4.setImageResource(R.drawable.icon_grindear_lv3_4_t);
//                level4_1.setImageResource(R.drawable.icon_grindear_lv4_1_t);
//                level4_2.setImageResource(R.drawable.icon_grindear_lv4_2_t);
//                progressSecond = tenHourSecond * 3;
//                nowSecond = nowSecond - (tenHourSecond * 33);
//            } else if (sublevel.equals("4")) {
//                level1_1.setImageResource(R.drawable.icon_grindear_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_grindear_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_grindear_lv1_3_t);
//                level1_4.setImageResource(R.drawable.icon_grindear_lv1_4_t);
//                level2_1.setImageResource(R.drawable.icon_grindear_lv2_1_t);
//                level2_2.setImageResource(R.drawable.icon_grindear_lv2_2_t);
//                level2_3.setImageResource(R.drawable.icon_grindear_lv2_3_t);
//                level2_4.setImageResource(R.drawable.icon_grindear_lv2_4_t);
//                level3_1.setImageResource(R.drawable.icon_grindear_lv3_1_t);
//                level3_2.setImageResource(R.drawable.icon_grindear_lv3_2_t);
//                level3_3.setImageResource(R.drawable.icon_grindear_lv3_3_t);
//                level3_4.setImageResource(R.drawable.icon_grindear_lv3_4_t);
//                level4_1.setImageResource(R.drawable.icon_grindear_lv4_1_t);
//                level4_2.setImageResource(R.drawable.icon_grindear_lv4_2_t);
//                level4_3.setImageResource(R.drawable.icon_grindear_lv4_3_t);
//                progressSecond = tenHourSecond * 4;
//                nowSecond = nowSecond - (tenHourSecond * 36);
//            }
//        } else if (level.equals("5")) {
//            level1_1.setImageResource(R.drawable.icon_grindear_lv1_1_t);
//            level1_2.setImageResource(R.drawable.icon_grindear_lv1_2_t);
//            level1_3.setImageResource(R.drawable.icon_grindear_lv1_3_t);
//            level1_4.setImageResource(R.drawable.icon_grindear_lv1_4_t);
//            level2_1.setImageResource(R.drawable.icon_grindear_lv2_1_t);
//            level2_2.setImageResource(R.drawable.icon_grindear_lv2_2_t);
//            level2_3.setImageResource(R.drawable.icon_grindear_lv2_3_t);
//            level2_4.setImageResource(R.drawable.icon_grindear_lv2_4_t);
//            level3_1.setImageResource(R.drawable.icon_grindear_lv3_1_t);
//            level3_2.setImageResource(R.drawable.icon_grindear_lv3_2_t);
//            level3_3.setImageResource(R.drawable.icon_grindear_lv3_3_t);
//            level3_4.setImageResource(R.drawable.icon_grindear_lv3_4_t);
//            level4_1.setImageResource(R.drawable.icon_grindear_lv4_1_t);
//            level4_2.setImageResource(R.drawable.icon_grindear_lv4_2_t);
//            level4_3.setImageResource(R.drawable.icon_grindear_lv4_3_t);
//            level4_4.setImageResource(R.drawable.icon_grindear_lv4_4_t);
//            progressSecond = tenHourSecond;
//            nowSecond = tenHourSecond;
//        }
        progresses = (float) nowSecond / progressSecond;
        progress = (int) (progresses * 100);
        progressBar.setProgress(progress);
    }

    private void initReading() {
        Glide.with(this).load(application.getSystemUtils().getUserInfo().getAvatar()).error(R.drawable.icon_system_headpic).into(headpic);
        name.setText(application.getSystemUtils().getUserInfo().getName());
        lev.setText("Lv" + level);
        nowSecond = Integer.parseInt(totalDuration);
        min = Integer.parseInt(totalDuration) / 60;
        if (min >= 60) {
            hour = min / 60;
            min = min % 60;
        }
        time.setText("累计时长" + hour + "小时" + min + "分钟");
//        if (level.equals("1")) {
//            if (sublevel.equals("1")) {
//                progressSecond = tenHourSecond;
//            } else if (sublevel.equals("2")) {
//                level1_1.setImageResource(R.drawable.icon_reading_lv1_1_t);
//                progressSecond = tenHourSecond * 2;
//                nowSecond = nowSecond - tenHourSecond;
//            } else if (sublevel.equals("3")) {
//                level1_1.setImageResource(R.drawable.icon_reading_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_reading_lv1_2_t);
//                progressSecond = tenHourSecond * 3;
//                nowSecond = nowSecond - (tenHourSecond * 3);
//            } else if (sublevel.equals("4")) {
//                level1_1.setImageResource(R.drawable.icon_reading_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_reading_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_reading_lv1_3_t);
//                progressSecond = tenHourSecond * 4;
//                nowSecond = nowSecond - (tenHourSecond * 6);
//            }
//        } else if (level.equals("2")) {
//            if (sublevel.equals("1")) {
//                level1_1.setImageResource(R.drawable.icon_reading_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_reading_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_reading_lv1_3_t);
//                level1_4.setImageResource(R.drawable.icon_reading_lv1_4_t);
//                progressSecond = tenHourSecond;
//                nowSecond = nowSecond - (tenHourSecond * 10);
//            } else if (sublevel.equals("2")) {
//                level1_1.setImageResource(R.drawable.icon_reading_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_reading_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_reading_lv1_3_t);
//                level1_4.setImageResource(R.drawable.icon_reading_lv1_4_t);
//                level2_1.setImageResource(R.drawable.icon_reading_lv2_1_t);
//                progressSecond = tenHourSecond * 2;
//                nowSecond = nowSecond - (tenHourSecond * 11);
//            } else if (sublevel.equals("3")) {
//                level1_1.setImageResource(R.drawable.icon_reading_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_reading_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_reading_lv1_3_t);
//                level1_4.setImageResource(R.drawable.icon_reading_lv1_4_t);
//                level2_1.setImageResource(R.drawable.icon_reading_lv2_1_t);
//                level2_2.setImageResource(R.drawable.icon_reading_lv2_2_t);
//                progressSecond = tenHourSecond * 3;
//                nowSecond = nowSecond - (tenHourSecond * 13);
//            } else if (sublevel.equals("4")) {
//                level1_1.setImageResource(R.drawable.icon_reading_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_reading_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_reading_lv1_3_t);
//                level1_4.setImageResource(R.drawable.icon_reading_lv1_4_t);
//                level2_1.setImageResource(R.drawable.icon_reading_lv2_1_t);
//                level2_2.setImageResource(R.drawable.icon_reading_lv2_2_t);
//                level2_3.setImageResource(R.drawable.icon_reading_lv2_3_t);
//                progressSecond = tenHourSecond * 4;
//                nowSecond = nowSecond - (tenHourSecond * 16);
//            }
//        } else if (level.equals("3")) {
//            if (sublevel.equals("1")) {
//                level1_1.setImageResource(R.drawable.icon_reading_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_reading_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_reading_lv1_3_t);
//                level1_4.setImageResource(R.drawable.icon_reading_lv1_4_t);
//                level2_1.setImageResource(R.drawable.icon_reading_lv2_1_t);
//                level2_2.setImageResource(R.drawable.icon_reading_lv2_2_t);
//                level2_3.setImageResource(R.drawable.icon_reading_lv2_3_t);
//                level2_4.setImageResource(R.drawable.icon_reading_lv2_4_t);
//                progressSecond = tenHourSecond;
//                nowSecond = nowSecond - (tenHourSecond * 20);
//            } else if (sublevel.equals("2")) {
//                level1_1.setImageResource(R.drawable.icon_reading_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_reading_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_reading_lv1_3_t);
//                level1_4.setImageResource(R.drawable.icon_reading_lv1_4_t);
//                level2_1.setImageResource(R.drawable.icon_reading_lv2_1_t);
//                level2_2.setImageResource(R.drawable.icon_reading_lv2_2_t);
//                level2_3.setImageResource(R.drawable.icon_reading_lv2_3_t);
//                level2_4.setImageResource(R.drawable.icon_reading_lv2_4_t);
//                level3_1.setImageResource(R.drawable.icon_reading_lv3_1_t);
//                progressSecond = tenHourSecond * 2;
//                nowSecond = nowSecond - (tenHourSecond * 21);
//            } else if (sublevel.equals("3")) {
//                level1_1.setImageResource(R.drawable.icon_reading_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_reading_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_reading_lv1_3_t);
//                level1_4.setImageResource(R.drawable.icon_reading_lv1_4_t);
//                level2_1.setImageResource(R.drawable.icon_reading_lv2_1_t);
//                level2_2.setImageResource(R.drawable.icon_reading_lv2_2_t);
//                level2_3.setImageResource(R.drawable.icon_reading_lv2_3_t);
//                level2_4.setImageResource(R.drawable.icon_reading_lv2_4_t);
//                level3_1.setImageResource(R.drawable.icon_reading_lv3_1_t);
//                level3_2.setImageResource(R.drawable.icon_reading_lv3_2_t);
//                progressSecond = tenHourSecond * 3;
//                nowSecond = nowSecond - (tenHourSecond * 23);
//            } else if (sublevel.equals("4")) {
//                level1_1.setImageResource(R.drawable.icon_reading_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_reading_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_reading_lv1_3_t);
//                level1_4.setImageResource(R.drawable.icon_reading_lv1_4_t);
//                level2_1.setImageResource(R.drawable.icon_reading_lv2_1_t);
//                level2_2.setImageResource(R.drawable.icon_reading_lv2_2_t);
//                level2_3.setImageResource(R.drawable.icon_reading_lv2_3_t);
//                level2_4.setImageResource(R.drawable.icon_reading_lv2_4_t);
//                level3_1.setImageResource(R.drawable.icon_reading_lv3_1_t);
//                level3_2.setImageResource(R.drawable.icon_reading_lv3_2_t);
//                level3_3.setImageResource(R.drawable.icon_reading_lv3_3_t);
//                progressSecond = tenHourSecond * 4;
//                nowSecond = nowSecond - (tenHourSecond * 26);
//            }
//        } else if (level.equals("4")) {
//            if (sublevel.equals("1")) {
//                level1_1.setImageResource(R.drawable.icon_reading_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_reading_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_reading_lv1_3_t);
//                level1_4.setImageResource(R.drawable.icon_reading_lv1_4_t);
//                level2_1.setImageResource(R.drawable.icon_reading_lv2_1_t);
//                level2_2.setImageResource(R.drawable.icon_reading_lv2_2_t);
//                level2_3.setImageResource(R.drawable.icon_reading_lv2_3_t);
//                level2_4.setImageResource(R.drawable.icon_reading_lv2_4_t);
//                level3_1.setImageResource(R.drawable.icon_reading_lv3_1_t);
//                level3_2.setImageResource(R.drawable.icon_reading_lv3_2_t);
//                level3_3.setImageResource(R.drawable.icon_reading_lv3_3_t);
//                level3_4.setImageResource(R.drawable.icon_reading_lv3_4_t);
//                progressSecond = tenHourSecond;
//                nowSecond = nowSecond - (tenHourSecond * 30);
//            } else if (sublevel.equals("2")) {
//                level1_1.setImageResource(R.drawable.icon_reading_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_reading_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_reading_lv1_3_t);
//                level1_4.setImageResource(R.drawable.icon_reading_lv1_4_t);
//                level2_1.setImageResource(R.drawable.icon_reading_lv2_1_t);
//                level2_2.setImageResource(R.drawable.icon_reading_lv2_2_t);
//                level2_3.setImageResource(R.drawable.icon_reading_lv2_3_t);
//                level2_4.setImageResource(R.drawable.icon_reading_lv2_4_t);
//                level3_1.setImageResource(R.drawable.icon_reading_lv3_1_t);
//                level3_2.setImageResource(R.drawable.icon_reading_lv3_2_t);
//                level3_3.setImageResource(R.drawable.icon_reading_lv3_3_t);
//                level3_4.setImageResource(R.drawable.icon_reading_lv3_4_t);
//                level4_1.setImageResource(R.drawable.icon_reading_lv4_1_t);
//                progressSecond = tenHourSecond * 2;
//                nowSecond = nowSecond - (tenHourSecond * 31);
//            } else if (sublevel.equals("3")) {
//                level1_1.setImageResource(R.drawable.icon_reading_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_reading_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_reading_lv1_3_t);
//                level1_4.setImageResource(R.drawable.icon_reading_lv1_4_t);
//                level2_1.setImageResource(R.drawable.icon_reading_lv2_1_t);
//                level2_2.setImageResource(R.drawable.icon_reading_lv2_2_t);
//                level2_3.setImageResource(R.drawable.icon_reading_lv2_3_t);
//                level2_4.setImageResource(R.drawable.icon_reading_lv2_4_t);
//                level3_1.setImageResource(R.drawable.icon_reading_lv3_1_t);
//                level3_2.setImageResource(R.drawable.icon_reading_lv3_2_t);
//                level3_3.setImageResource(R.drawable.icon_reading_lv3_3_t);
//                level3_4.setImageResource(R.drawable.icon_reading_lv3_4_t);
//                level4_1.setImageResource(R.drawable.icon_reading_lv4_1_t);
//                level4_2.setImageResource(R.drawable.icon_reading_lv4_2_t);
//                progressSecond = tenHourSecond * 3;
//                nowSecond = nowSecond - (tenHourSecond * 33);
//            } else if (sublevel.equals("4")) {
//                level1_1.setImageResource(R.drawable.icon_reading_lv1_1_t);
//                level1_2.setImageResource(R.drawable.icon_reading_lv1_2_t);
//                level1_3.setImageResource(R.drawable.icon_reading_lv1_3_t);
//                level1_4.setImageResource(R.drawable.icon_reading_lv1_4_t);
//                level2_1.setImageResource(R.drawable.icon_reading_lv2_1_t);
//                level2_2.setImageResource(R.drawable.icon_reading_lv2_2_t);
//                level2_3.setImageResource(R.drawable.icon_reading_lv2_3_t);
//                level2_4.setImageResource(R.drawable.icon_reading_lv2_4_t);
//                level3_1.setImageResource(R.drawable.icon_reading_lv3_1_t);
//                level3_2.setImageResource(R.drawable.icon_reading_lv3_2_t);
//                level3_3.setImageResource(R.drawable.icon_reading_lv3_3_t);
//                level3_4.setImageResource(R.drawable.icon_reading_lv3_4_t);
//                level4_1.setImageResource(R.drawable.icon_reading_lv4_1_t);
//                level4_2.setImageResource(R.drawable.icon_reading_lv4_2_t);
//                level4_3.setImageResource(R.drawable.icon_reading_lv4_3_t);
//                progressSecond = tenHourSecond * 4;
//                nowSecond = nowSecond - (tenHourSecond * 36);
//            }
//        } else if (level.equals("5")) {
//            level1_1.setImageResource(R.drawable.icon_reading_lv1_1_t);
//            level1_2.setImageResource(R.drawable.icon_reading_lv1_2_t);
//            level1_3.setImageResource(R.drawable.icon_reading_lv1_3_t);
//            level1_4.setImageResource(R.drawable.icon_reading_lv1_4_t);
//            level2_1.setImageResource(R.drawable.icon_reading_lv2_1_t);
//            level2_2.setImageResource(R.drawable.icon_reading_lv2_2_t);
//            level2_3.setImageResource(R.drawable.icon_reading_lv2_3_t);
//            level2_4.setImageResource(R.drawable.icon_reading_lv2_4_t);
//            level3_1.setImageResource(R.drawable.icon_reading_lv3_1_t);
//            level3_2.setImageResource(R.drawable.icon_reading_lv3_2_t);
//            level3_3.setImageResource(R.drawable.icon_reading_lv3_3_t);
//            level3_4.setImageResource(R.drawable.icon_reading_lv3_4_t);
//            level4_1.setImageResource(R.drawable.icon_reading_lv4_1_t);
//            level4_2.setImageResource(R.drawable.icon_reading_lv4_2_t);
//            level4_3.setImageResource(R.drawable.icon_reading_lv4_3_t);
//            level4_4.setImageResource(R.drawable.icon_reading_lv4_4_t);
//            progressSecond = tenHourSecond;
//            nowSecond = tenHourSecond;
//        }
        progresses = (float) nowSecond / progressSecond;
        progress = (int) (progresses * 100);
        progressBar.setProgress(progress);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_level_back:
                finish();
                break;
        }
    }

//    private void reset(int i) {
//        if (i == 1) {
//            level1_1.setImageResource(R.drawable.icon_grindear_lv1_1_f);
//            level1_2.setImageResource(R.drawable.icon_grindear_lv1_2_f);
//            level1_3.setImageResource(R.drawable.icon_grindear_lv1_3_f);
//            level1_4.setImageResource(R.drawable.icon_grindear_lv1_4_f);
//            level2_1.setImageResource(R.drawable.icon_grindear_lv2_1_f);
//            level2_2.setImageResource(R.drawable.icon_grindear_lv2_2_f);
//            level2_3.setImageResource(R.drawable.icon_grindear_lv2_3_f);
//            level2_4.setImageResource(R.drawable.icon_grindear_lv2_4_f);
//            level3_1.setImageResource(R.drawable.icon_grindear_lv3_1_f);
//            level3_2.setImageResource(R.drawable.icon_grindear_lv3_2_f);
//            level3_3.setImageResource(R.drawable.icon_grindear_lv3_3_f);
//            level3_4.setImageResource(R.drawable.icon_grindear_lv3_4_f);
//            level4_1.setImageResource(R.drawable.icon_grindear_lv4_1_f);
//            level4_2.setImageResource(R.drawable.icon_grindear_lv4_2_f);
//            level4_3.setImageResource(R.drawable.icon_grindear_lv4_3_f);
//            level4_4.setImageResource(R.drawable.icon_grindear_lv4_4_f);
//        } else {
//            level1_1.setImageResource(R.drawable.icon_reading_lv1_1_f);
//            level1_2.setImageResource(R.drawable.icon_reading_lv1_2_f);
//            level1_3.setImageResource(R.drawable.icon_reading_lv1_3_f);
//            level1_4.setImageResource(R.drawable.icon_reading_lv1_4_f);
//            level2_1.setImageResource(R.drawable.icon_reading_lv2_1_f);
//            level2_2.setImageResource(R.drawable.icon_reading_lv2_2_f);
//            level2_3.setImageResource(R.drawable.icon_reading_lv2_3_f);
//            level2_4.setImageResource(R.drawable.icon_reading_lv2_4_f);
//            level3_1.setImageResource(R.drawable.icon_reading_lv3_1_f);
//            level3_2.setImageResource(R.drawable.icon_reading_lv3_2_f);
//            level3_3.setImageResource(R.drawable.icon_reading_lv3_3_f);
//            level3_4.setImageResource(R.drawable.icon_reading_lv3_4_f);
//            level4_1.setImageResource(R.drawable.icon_reading_lv4_1_f);
//            level4_2.setImageResource(R.drawable.icon_reading_lv4_2_f);
//            level4_3.setImageResource(R.drawable.icon_reading_lv4_3_f);
//            level4_4.setImageResource(R.drawable.icon_reading_lv4_4_f);
//        }
//    }
}
