package com.annie.annieforchild.ui.activity.my;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.UserInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的等级成就
 * Created by WangLei on 2018/2/2 0002
 */

public class GradeAchievementActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private CircleImageView headpic;
    private TextView name, level, experience;
    private LinearLayout gradeLayout;
    private UserInfo userInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_grade_achievement;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.grade_achievement_back);
        headpic = findViewById(R.id.grade_headpic);
        name = findViewById(R.id.grade_name);
        level = findViewById(R.id.grade_level);
        experience = findViewById(R.id.grade_experience);
        gradeLayout = findViewById(R.id.grade_layout);
        back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        userInfo = (UserInfo) intent.getSerializableExtra("userinfo");
        fresh();
    }

    private void fresh() {
        Glide.with(this).load(userInfo.getAvatar()).into(headpic);
        name.setText(userInfo.getName());
        level.setText(userInfo.getLevel());
        experience.setText("经验值" + userInfo.getExperience());
        int exper = Integer.parseInt(userInfo.getExperience());
        if (exper < 500) {
            getLevel(0, 0, 0, 0);
        } else if (exper < 1200) {
            getLevel(0, 0, 0, 1);
        } else if (exper < 2100) {
            getLevel(0, 0, 0, 2);
        } else if (exper < 3200) {
            getLevel(0, 0, 0, 3);
        } else if (exper < 4700) {
            getLevel(0, 0, 1, 0);
        } else if (exper < 6600) {
            getLevel(0, 0, 1, 1);
        } else if (exper < 8900) {
            getLevel(0, 0, 1, 2);
        } else if (exper < 11600) {
            getLevel(0, 0, 1, 3);
        } else if (exper < 15100) {
            getLevel(0, 0, 2, 0);
        } else if (exper < 19400) {
            getLevel(0, 0, 2, 1);
        } else if (exper < 24500) {
            getLevel(0, 0, 2, 2);
        } else if (exper < 30400) {
            getLevel(0, 0, 2, 3);
        } else if (exper < 37900) {
            getLevel(0, 0, 3, 0);
        } else if (exper < 47000) {
            getLevel(0, 0, 3, 1);
        } else if (exper < 57700) {
            getLevel(0, 0, 3, 2);
        } else if (exper < 70000) {
            getLevel(0, 0, 3, 3);
        } else if (exper < 83900) {
            getLevel(0, 1, 0, 0);
        } else if (exper < 99400) {
            getLevel(0, 1, 0, 1);
        } else if (exper < 116500) {
            getLevel(0, 1, 0, 2);
        } else if (exper < 135200) {
            getLevel(0, 1, 0, 3);
        } else if (exper < 155500) {
            getLevel(0, 1, 1, 0);
        } else if (exper < 177400) {
            getLevel(0, 1, 1, 1);
        } else if (exper < 200900) {
            getLevel(0, 1, 1, 2);
        } else if (exper < 226000) {
            getLevel(0, 1, 1, 3);
        } else if (exper < 252700) {
            getLevel(0, 1, 2, 0);
        } else if (exper < 281000) {
            getLevel(0, 1, 2, 1);
        } else if (exper < 310900) {
            getLevel(0, 1, 2, 2);
        } else if (exper < 342400) {
            getLevel(0, 1, 2, 3);
        } else if (exper >= 342400) {
            getLevel(1, 0, 0, 0);
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.grade_achievement_back:
                finish();
                break;
        }
    }

    private void getLevel(int crown, int sun, int moon, int star) {
        if (crown != 0) {
            for (int i = 0; i < crown; i++) {
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.icon_level_crown);
                gradeLayout.addView(imageView);
            }
        }
        if (sun != 0) {
            for (int i = 0; i < sun; i++) {
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.icon_level_sun);
                gradeLayout.addView(imageView);
            }
        }
        if (moon != 0) {
            for (int i = 0; i < moon; i++) {
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.icon_level_moon);
                gradeLayout.addView(imageView);
            }
        }
        if (star != 0) {
            for (int i = 0; i < star; i++) {
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.icon_level_star);
                gradeLayout.addView(imageView);
            }
        }
    }
}
