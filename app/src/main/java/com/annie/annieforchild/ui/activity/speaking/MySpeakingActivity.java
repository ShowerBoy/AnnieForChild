package com.annie.annieforchild.ui.activity.speaking;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.pk.MusicPlayActivity;
import com.annie.annieforchild.ui.fragment.myreading.TodayReadingFragment;
import com.annie.annieforchild.ui.fragment.myreading.TotalReadingFragment;
import com.annie.annieforchild.ui.fragment.myspeaking.TodaySpeakingFragment;
import com.annie.annieforchild.ui.fragment.myspeaking.TotalSpeakingFragment;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;
import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 口语存折
 * Created by wanglei on 2018/9/8.
 */

public class MySpeakingActivity extends BaseActivity implements SongView, View.OnClickListener, ViewPager.OnPageChangeListener {
    private ImageView back, music;
    private CircleImageView headpic;
    private TextView name;
    private TodaySpeakingFragment todaySpeakingFragment;
    private TotalSpeakingFragment totalSpeakingFragment;
    private MySpeakingFragmentAdapter fragmentAdapter;
    private GrindEarPresenter presenter;
    private AdvancedPagerSlidingTabStrip mTab;
    private APSTSViewPager mVP;
    private AlertHelper helper;
    private Dialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_speaking;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.my_speaking_back);
        music = findViewById(R.id.my_speaking_music);
        headpic = findViewById(R.id.my_speaking_headpic);
        name = findViewById(R.id.my_speaking_name);
        mTab = findViewById(R.id.my_speaking_tab);
        mVP = findViewById(R.id.my_speaking_viewpager);
        back.setOnClickListener(this);
        music.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        fragmentAdapter = new MySpeakingFragmentAdapter(getSupportFragmentManager());
        mVP.setOffscreenPageLimit(2);
        mVP.setAdapter(fragmentAdapter);
        fragmentAdapter.notifyDataSetChanged();
        mTab.setViewPager(mVP);
        mTab.setOnPageChangeListener(this);
        Glide.with(this).load(SystemUtils.userInfo.getAvatar()).into(headpic);
        name.setText(SystemUtils.userInfo.getName());
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getMySpeaking();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_speaking_back:
                finish();
                break;
            case R.id.my_speaking_music:
                Intent intent = new Intent(this, MusicPlayActivity.class);
                startActivity(intent);
                break;
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

    class MySpeakingFragmentAdapter extends FragmentStatePagerAdapter {

        public MySpeakingFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < 2) {
                switch (position) {
                    case 0:
                        if (null == todaySpeakingFragment) {
                            todaySpeakingFragment = TodaySpeakingFragment.instance();
                        }
                        return todaySpeakingFragment;
                    case 1:
                        if (null == totalSpeakingFragment) {
                            totalSpeakingFragment = TotalSpeakingFragment.instance();
                        }
                        return totalSpeakingFragment;
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position >= 0 && position < 2) {
                switch (position) {
                    case 0:
                        return "今日存折";
                    case 1:
                        return "累计存折";
                    default:
                        break;
                }
            }
            return null;
        }
    }
}
