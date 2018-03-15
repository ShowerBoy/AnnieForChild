package com.annie.annieforchild.ui.activity.grindEar;

import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

/**
 * 我的磨耳朵
 * Created by WangLei on 2018/1/19 0019
 */

public class MyGrindEarActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_grind_ear;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.my_grind_ear_back);
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
            case R.id.my_grind_ear_back:
                finish();
                break;
        }
    }
}
