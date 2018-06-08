package com.annie.annieforchild.ui.activity.lesson;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.presenter.SchedulePresenter;
import com.annie.annieforchild.presenter.imp.SchedulePresenterImp;
import com.annie.annieforchild.ui.fragment.collection.GrindEarFragment;
import com.annie.annieforchild.ui.fragment.collection.ReadingFragment;
import com.annie.annieforchild.ui.fragment.collection.SpokenFragment;
import com.annie.annieforchild.ui.fragment.course.ExerciseCourseFragment;
import com.annie.annieforchild.ui.fragment.course.OfflineCourseFragment;
import com.annie.annieforchild.ui.fragment.course.OnlineCourseFragment;
import com.annie.annieforchild.ui.fragment.course.VacationCourseFragment;
import com.annie.annieforchild.view.ScheduleView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;

import org.greenrobot.eventbus.Subscribe;

import butterknife.OnClick;

/**
 * 我的课程
 * Created by wanglei on 2018/3/17.
 */

public class CourseActivity extends BaseActivity implements View.OnClickListener, ScheduleView, ViewPager.OnPageChangeListener {
    private ImageView back;
    private AdvancedPagerSlidingTabStrip mTab;
    private APSTSViewPager mVP;
    private OnlineCourseFragment onlineCourseFragment;
    private OfflineCourseFragment offlineCourseFragment;
    private VacationCourseFragment vacationCourseFragment;
    private ExerciseCourseFragment exerciseCourseFragment;
    private CourseFragmentAdapter fragmentAdapter;
    private SchedulePresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_course;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.my_course_back);
        mTab = findViewById(R.id.course_tab_layout);
        mVP = findViewById(R.id.course_viewpager);
        back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        presenter = new SchedulePresenterImp(this, this);
        presenter.initViewAndData();
        fragmentAdapter = new CourseFragmentAdapter(getSupportFragmentManager());
        mVP.setOffscreenPageLimit(4);
        mVP.setAdapter(fragmentAdapter);
        fragmentAdapter.notifyDataSetChanged();
        mTab.setViewPager(mVP);
        mTab.setOnPageChangeListener(this);

        presenter.myCoursesOnline();
//        presenter.myCoursesOffline();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_course_back:
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

    @Override
    public void showInfo(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void dismissLoad() {

    }

    class CourseFragmentAdapter extends FragmentStatePagerAdapter {

        public CourseFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < 4) {
                switch (position) {
                    case 0:
                        if (null == offlineCourseFragment) {
                            offlineCourseFragment = OfflineCourseFragment.instance();
                        }
                        return offlineCourseFragment;
                    case 1:
                        if (null == onlineCourseFragment) {
                            onlineCourseFragment = OnlineCourseFragment.instance();
                        }
                        return onlineCourseFragment;
                    case 2:
                        if (null == vacationCourseFragment) {
                            vacationCourseFragment = VacationCourseFragment.instance();
                        }
                        return vacationCourseFragment;
                    case 3:
                        if (null == exerciseCourseFragment) {
                            exerciseCourseFragment = ExerciseCourseFragment.instance();
                        }
                        return exerciseCourseFragment;
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position >= 0 && position < 4) {
                switch (position) {
                    case 0:
                        return "线下课程";
                    case 1:
                        return "线上课程";
                    case 2:
                        return "寒暑假";
                    case 3:
                        return "活动课程";
                    default:
                        break;
                }
            }
            return null;
        }
    }
}
