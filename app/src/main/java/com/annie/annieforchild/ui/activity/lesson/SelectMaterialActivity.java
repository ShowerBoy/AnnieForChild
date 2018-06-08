package com.annie.annieforchild.ui.activity.lesson;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.bean.material.MaterialGroup;
import com.annie.annieforchild.bean.schedule.Schedule;
import com.annie.annieforchild.presenter.SchedulePresenter;
import com.annie.annieforchild.presenter.imp.SchedulePresenterImp;
import com.annie.annieforchild.ui.adapter.SelectMaterialExpandAdapter;
import com.annie.annieforchild.ui.fragment.selectmaterial.SelectGrindEarFragment;
import com.annie.annieforchild.ui.fragment.selectmaterial.SelectReadingFragment;
import com.annie.annieforchild.ui.fragment.selectmaterial.SelectSpokenFragment;
import com.annie.annieforchild.view.ScheduleView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 选择教材
 * Created by wanglei on 2018/4/9.
 */

public class SelectMaterialActivity extends BaseActivity implements View.OnClickListener, ScheduleView, ViewPager.OnPageChangeListener {
    private ImageView back;
    private AdvancedPagerSlidingTabStrip mTab;
    private APSTSViewPager mVP;
    private AlertHelper helper;
    private Dialog dialog;

    private SelectGrindEarFragment grindEarFragment;
    private SelectReadingFragment readingFragment;
    private SelectSpokenFragment spokenFragment;

    private SchedulePresenter presenter;
    private SelectMaterialFragmentAdapter fragmentAdapter;
    private Intent intent;
    private Bundle bundle;
    private Schedule schedule = null;
    private String date;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_material;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.select_material_back);
        mTab = findViewById(R.id.select_tab_layout);
        mVP = findViewById(R.id.select_viewpager);

        back.setOnClickListener(this);

        fragmentAdapter = new SelectMaterialFragmentAdapter(getSupportFragmentManager());
        mVP.setOffscreenPageLimit(3);
        mVP.setAdapter(fragmentAdapter);
        fragmentAdapter.notifyDataSetChanged();
        mTab.setViewPager(mVP);
        mTab.setOnPageChangeListener(this);

        /**
         * {@link com.annie.annieforchild.ui.adapter.OnlineScheAdapter#onBindViewHolder(RecyclerView.ViewHolder, int)}
         */
        intent = getIntent();
        if (intent != null) {
            bundle = intent.getExtras();
            if (bundle != null) {
                schedule = (Schedule) bundle.getSerializable("schedule");
                date = bundle.getString("date");
            }
        }
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new SchedulePresenterImp(this, this);
        presenter.initViewAndData();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_material_back:
                finish();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class SelectMaterialFragmentAdapter extends FragmentStatePagerAdapter {

        public SelectMaterialFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < 3) {
                switch (position) {
                    case 0:
                        if (null == grindEarFragment) {
                            grindEarFragment = SelectGrindEarFragment.instance(schedule, date);
                        }
                        return grindEarFragment;
                    case 1:
                        if (null == readingFragment) {
                            readingFragment = SelectReadingFragment.instance(schedule, date);
                        }
                        return readingFragment;
                    case 2:
                        if (null == spokenFragment) {
                            spokenFragment = SelectSpokenFragment.instance(schedule, date);
                        }
                        return spokenFragment;
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position >= 0 && position < 3) {
                switch (position) {
                    case 0:
                        return "磨耳朵";
                    case 1:
                        return "阅读";
                    case 2:
                        return "口语";
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void dismissLoad() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
