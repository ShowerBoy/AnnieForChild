package com.annie.annieforchild.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.baselibrary.base.BaseFragment;

/**
 * 发现
 * Created by WangLei on 2018/1/12 0012
 */

public class ThirdFragment extends BaseFragment {
    private String tag;

    public ThirdFragment() {
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            tag = bundle.getString("tag");
        }
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_third_fragment;
    }
}
