package com.annie.annieforchild.ui.activity.my;

import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

/**
 * 我的等级成就
 * Created by WangLei on 2018/2/2 0002
 */

public class GradeAchievementActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_grade_achievement;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.grade_achievement_back);
        back.setOnClickListener(this);
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
            case R.id.grade_achievement_back:
                finish();
                break;
        }
    }
}
