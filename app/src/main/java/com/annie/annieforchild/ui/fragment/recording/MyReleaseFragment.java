package com.annie.annieforchild.ui.fragment.recording;

import android.os.Bundle;
import android.view.View;

import com.annie.annieforchild.R;
import com.annie.baselibrary.base.BaseFragment;

import java.sql.BatchUpdateException;

/**
 * 我的发布
 * Created by wanglei on 2019/1/2.
 */

public class MyReleaseFragment extends BaseFragment {
    private int tag;

    public static MyReleaseFragment instance(int tag) {
        MyReleaseFragment fragment = new MyReleaseFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tag", tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        tag = bundle.getInt("tag");
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_release_fragment;
    }
}

