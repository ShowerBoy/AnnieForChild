package com.annie.annieforchild.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.annie.annieforchild.R;
import com.annie.annieforchild.ui.adapter.LeadAdapter;
import com.annie.annieforchild.ui.fragment.LeadFragment;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 * Created by wanglei on 2018/6/20.
 */

public class LeadActivity extends BaseActivity {
    private ViewPager viewPager;
    private List<Fragment> fragments;
    private LeadAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lead;
    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.lead_viewpager);
    }

    @Override
    protected void initData() {
        fragments = new ArrayList<>();
        LeadFragment leadFragment1 = LeadFragment.instance(1);
        LeadFragment leadFragment2 = LeadFragment.instance(2);
        LeadFragment leadFragment3 = LeadFragment.instance(3);
        fragments.add(leadFragment1);
        fragments.add(leadFragment2);
        fragments.add(leadFragment3);
        adapter = new LeadAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }
}
