package com.annie.annieforchild.ui.fragment.square;

import android.view.View;

import com.annie.annieforchild.R;
import com.annie.baselibrary.base.BaseFragment;

/**
 * 作品展示
 * Created by wanglei on 2018/5/11.
 */

public class WorkshowFragment extends BaseFragment {
    public static WorkshowFragment instance() {
        WorkshowFragment fragment = new WorkshowFragment();
        return fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_workshow_fragment;
    }
}
