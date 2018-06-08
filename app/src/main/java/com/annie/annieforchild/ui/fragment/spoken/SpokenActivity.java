package com.annie.annieforchild.ui.fragment.spoken;

import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

/**
 * 口语
 * Created by wanglei on 2018/6/7.
 */

public class SpokenActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_spoken;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.spoken_back);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.spoken_back:
                finish();
                break;
        }
    }
}
