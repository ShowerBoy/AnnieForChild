package com.annie.annieforchild.ui.activity.grindEar;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
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
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.grindear.MyGrindEarBean;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.pk.MusicPlayActivity2;
import com.annie.annieforchild.ui.fragment.mygrindear.TodayGrindEarFragment;
import com.annie.annieforchild.ui.fragment.mygrindear.TotalGrindEarFragment;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.Subscribe;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的磨耳朵
 * Created by WangLei on 2018/1/19 0019
 */

public class MyGrindEarActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, SongView {
    private ImageView back, help, music;
    private CircleImageView headpic;
    private TextView level, sublevel, name;
    private TabLayout mTab;
    private APSTSViewPager mVP;
    private TodayGrindEarFragment todayGrindEarFragment;
    private TotalGrindEarFragment totalGrindEarFragment;
    private MyGrindEarFragmentAdapter fragmentAdapter;
    private GrindEarPresenter presenter;
    private MyGrindEarBean bean;
    private AlertHelper helper;
    private Dialog dialog;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_grind_ear;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.my_grind_ear_back);
        headpic = findViewById(R.id.my_grind_ear_headpic);
        help = findViewById(R.id.my_grind_ear_help);
        level = findViewById(R.id.my_grind_ear_level);
        sublevel = findViewById(R.id.my_grind_ear_sublevel);
        music = findViewById(R.id.my_grind_music);
//        input = findViewById(R.id.input);
        name = findViewById(R.id.my_grind_ear_name);
        mTab = findViewById(R.id.my_grind_ear_tab);
        mVP = findViewById(R.id.my_grind_ear_viewpager);
        back.setOnClickListener(this);
        help.setOnClickListener(this);
        music.setOnClickListener(this);
//        input.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        fragmentAdapter = new MyGrindEarFragmentAdapter(getSupportFragmentManager());
        mVP.setOffscreenPageLimit(2);
        mVP.setAdapter(fragmentAdapter);
        fragmentAdapter.notifyDataSetChanged();
//        mTab.setViewPager(mVP);
//        mTab.setOnPageChangeListener(this);
        mTab.addTab(mTab.newTab().setText("今日磨耳朵"));
        mTab.addTab(mTab.newTab().setText("累计磨耳朵"));
        mTab.setupWithViewPager(mVP);//将TabLayout和ViewPager关联起来。
        mVP.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTab));
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getMyListening();
        Glide.with(this).load(application.getSystemUtils().getUserInfo().getAvatar()).into(headpic);
        name.setText(application.getSystemUtils().getUserInfo().getName());
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_grind_ear_back:
                finish();
                break;
//            case R.id.my_grind_ear_help:
//                Intent intent = new Intent(this, MyLevelActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("tag", "grindear");
//                bundle.putString("level", bean.getLevel());
//                bundle.putString("sublevel", bean.getSubLevel());
//                bundle.putString("totalduration", bean.getHistoryTotalDuration());
//                intent.putExtras(bundle);
//                startActivity(intent);
//                break;
//            case R.id.input:
//                //录入
//                Intent intent1 = new Intent(this, InputActivity.class);
//                intent1.putExtra("tag", "grindear");
//                startActivity(intent1);
//                break;
            case R.id.my_grind_music:
//                Intent intent1 = new Intent(this, MusicPlayActivity.class);
                SystemUtils.MusicType = 0;
                Intent intent1 = new Intent(this, MusicPlayActivity2.class);
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
//        if (message.what == MethodCode.EVENT_GETMYLISTENING) {
//            bean = (MyGrindEarBean) message.obj;
//            refresh(bean);
//        } else if (message.what == MethodCode.EVENT_COMMITDURATION) {
//            presenter.getMyListening();
//        }
    }

    private void refresh(MyGrindEarBean bean) {
        level.setText(bean.getLevel() != null ? "第" + bean.getLevel() + "级" : "");
        sublevel.setText(bean.getSubLevel() != null ? "第" + bean.getSubLevel() + "关" : "");
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MyGrindEarFragmentAdapter extends FragmentStatePagerAdapter {

        public MyGrindEarFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < 2) {
                switch (position) {
                    case 0:
                        if (null == todayGrindEarFragment) {
                            todayGrindEarFragment = TodayGrindEarFragment.instance();
                        }
                        return todayGrindEarFragment;
                    case 1:
                        if (null == totalGrindEarFragment) {
                            totalGrindEarFragment = TotalGrindEarFragment.instance();
                        }
                        return totalGrindEarFragment;
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
                        return "今日磨耳朵";
                    case 1:
                        return "累计磨耳朵";
                    default:
                        break;
                }
            }
            return null;
        }
    }
}
