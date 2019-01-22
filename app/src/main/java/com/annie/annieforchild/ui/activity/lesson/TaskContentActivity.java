package com.annie.annieforchild.ui.activity.lesson;

import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;

/**
 * 作业内容
 * Created by wanglei on 2019/1/19.
 */

public class TaskContentActivity extends BaseActivity implements OnCheckDoubleClick {
    private ImageView back;
    private AdvancedPagerSlidingTabStrip tab;
    private APSTSViewPager mVP;
    private CheckDoubleClickListener listener;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_content;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.task_content_back);
        tab = findViewById(R.id.task_content_tab);
        mVP = findViewById(R.id.task_content_viewpager);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.task_content_back:
                finish();
                break;
        }
    }
}
