package com.annie.annieforchild.ui.activity.reading;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.grindear.MyGrindEarBean;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.grindEar.InputActivity;
import com.annie.annieforchild.ui.activity.grindEar.MyGrindEarActivity;
import com.annie.annieforchild.ui.activity.grindEar.MyLevelActivity;
import com.annie.annieforchild.ui.activity.pk.MusicPlayActivity;
import com.annie.annieforchild.ui.fragment.mygrindear.TodayGrindEarFragment;
import com.annie.annieforchild.ui.fragment.mygrindear.TotalGrindEarFragment;
import com.annie.annieforchild.ui.fragment.myreading.TodayReadingFragment;
import com.annie.annieforchild.ui.fragment.myreading.TotalReadingFragment;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;
import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;

import org.greenrobot.eventbus.Subscribe;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 阅读存折
 * Created by wanglei on 2018/6/1.
 */

public class MyReadingActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, SongView {
    private ImageView back, help, music;
    private CircleImageView headpic;
    private TextView level, sublevel, name;
    //    private Button input;
    private AdvancedPagerSlidingTabStrip mTab;
    private APSTSViewPager mVP;
    private TodayReadingFragment todayReadingFragment;
    private TotalReadingFragment totalReadingFragment;
    private MyReadingFragmentAdapter fragmentAdapter;
    private GrindEarPresenter presenter;
    private MyGrindEarBean bean;
    private AlertHelper helper;
    private Dialog dialog;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_reading;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.my_reading_back);
        headpic = findViewById(R.id.my_reading_headpic);
        level = findViewById(R.id.my_reading_level);
        sublevel = findViewById(R.id.my_reading_sublevel);
        help = findViewById(R.id.my_reading_help);
        name = findViewById(R.id.my_reading_name);
        music = findViewById(R.id.my_reading_music);
        mTab = findViewById(R.id.my_reading_tab);
        mVP = findViewById(R.id.my_reading_viewpager);
        back.setOnClickListener(this);
        help.setOnClickListener(this);
        music.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        fragmentAdapter = new MyReadingFragmentAdapter(getSupportFragmentManager());
        mVP.setOffscreenPageLimit(2);
        mVP.setAdapter(fragmentAdapter);
        fragmentAdapter.notifyDataSetChanged();
        mTab.setViewPager(mVP);
        mTab.setOnPageChangeListener(this);
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getMyReading();
        Glide.with(this).load(application.getSystemUtils().getUserInfo().getAvatar()).into(headpic);
        name.setText(application.getSystemUtils().getUserInfo().getName());
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_reading_back:
                finish();
                break;
//            case R.id.my_reading_help:
//                Intent intent = new Intent(this, MyLevelActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("tag", "reading");
//                bundle.putString("level", bean.getLevel());
//                bundle.putString("sublevel", bean.getSubLevel());
//                bundle.putString("totalduration", bean.getHistoryTotalDuration());
//                intent.putExtras(bundle);
//                startActivity(intent);
//                break;
            case R.id.my_reading_music:
                Intent intent1 = new Intent(this, MusicPlayActivity.class);
                startActivity(intent1);
                break;
        }
    }

    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
//        if (message.what == MethodCode.EVENT_GETMYREADING) {
//            bean = (MyGrindEarBean) message.obj;
//            refresh(bean);
//        } else if (message.what == MethodCode.EVENT_COMMITREADING) {
//            presenter.getMyReading();
//        }
    }

    private void refresh(MyGrindEarBean bean) {
        level.setText(bean.getLevel() != null ? "第" + bean.getLevel() + "级" : "");
        sublevel.setText(bean.getSubLevel() != null ? "第" + bean.getSubLevel() + "关" : "");
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

    class MyReadingFragmentAdapter extends FragmentStatePagerAdapter {

        public MyReadingFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < 2) {
                switch (position) {
                    case 0:
                        if (null == todayReadingFragment) {
                            todayReadingFragment = TodayReadingFragment.instance();
                        }
                        return todayReadingFragment;
                    case 1:
                        if (null == totalReadingFragment) {
                            totalReadingFragment = TotalReadingFragment.instance();
                        }
                        return totalReadingFragment;
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
