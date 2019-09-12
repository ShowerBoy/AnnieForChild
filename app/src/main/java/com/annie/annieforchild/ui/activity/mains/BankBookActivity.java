package com.annie.annieforchild.ui.activity.mains;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.pk.MusicPlayActivity2;
import com.annie.annieforchild.ui.fragment.bankbook.GrindEarBankBookFragment;
import com.annie.annieforchild.ui.fragment.bankbook.ReadingBankBookFragment;
import com.annie.annieforchild.ui.fragment.bankbook.SpeakingBankBookFragment;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseMusicActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.Subscribe;

/**
 * 存折
 * Created by wanglei on 2018/9/18.
 */

public class BankBookActivity extends BaseMusicActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, SongView {
    private ImageView back, music;
    private AnimationDrawable musicBtn;
    private TabLayout mTab;
    private APSTSViewPager mVP;
    private GrindEarBankBookFragment grindEarBankBookFragment;
    private ReadingBankBookFragment readingBankBookFragment;
    private SpeakingBankBookFragment speakingBankBookFragment;
    private BankBookFragmentAdapter fragmentAdapter;
    private GrindEarPresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bank_book;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.bank_book_back);
        music = findViewById(R.id.bank_book_music);
        mTab = findViewById(R.id.bank_book_layout);
        mVP = findViewById(R.id.bank_book_viewpager);
        back.setOnClickListener(this);
        music.setOnClickListener(this);

        musicBtn = (AnimationDrawable) music.getDrawable();
        musicBtn.setOneShot(false);
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        fragmentAdapter = new BankBookFragmentAdapter(getSupportFragmentManager());
        mVP.setOffscreenPageLimit(3);
        mVP.setAdapter(fragmentAdapter);
        fragmentAdapter.notifyDataSetChanged();
//        mTab.setViewPager(mVP);
//        mTab.setOnPageChangeListener(this);
        mTab.addTab(mTab.newTab().setText("磨耳朵"));
        mTab.addTab(mTab.newTab().setText("阅读"));
        mTab.addTab(mTab.newTab().setText("口语"));
        mTab.setupWithViewPager(mVP);//将TabLayout和ViewPager关联起来。
        mVP.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTab));
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getMyListening();
        presenter.getMyReading();
        presenter.getMySpeaking();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bank_book_back:
                finish();
                break;
            case R.id.bank_book_music:
//                Intent intent = new Intent(this, MusicPlayActivity.class);
                SystemUtils.MusicType = 0;
                Intent intent = new Intent(this, MusicPlayActivity2.class);
                startActivity(intent);
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

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_COMMITDURATION) {
            presenter.getMyListening();
//            presenter.getMyReading();
//            presenter.getMySpeaking();
        } else if (message.what == MethodCode.EVENT_COMMITREADING) {
//            presenter.getMyListening();
            presenter.getMyReading();
//            presenter.getMySpeaking();
        } else if (message.what == MethodCode.EVENT_COMMITSPEAKING) {
//            presenter.getMyListening();
//            presenter.getMyReading();
            presenter.getMySpeaking();
        } else if (message.what == MethodCode.EVENT_MUSIC) {
            if (musicBtn != null) {
                if ((boolean) (message.obj)) {
                    musicBtn.start();
                } else {
                    musicBtn.stop();
                }
            }
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

    @Override
    protected void onResume() {
        super.onResume();
        allowBindService();
    }

    @Override
    protected void onPause() {
        allowUnBindService();
        super.onPause();
    }

    @Override
    public void onPublish(int progress) {

    }

    @Override
    public void onChange(int position) {
        if (musicBtn != null) {
            if (musicService.isPlaying()) {
                musicBtn.start();
            } else {
                musicBtn.stop();
            }
        }
    }

    class BankBookFragmentAdapter extends FragmentStatePagerAdapter {


        public BankBookFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < 3) {
                switch (position) {
                    case 0:
                        if (null == grindEarBankBookFragment) {
                            grindEarBankBookFragment = GrindEarBankBookFragment.instance();
                        }
                        return grindEarBankBookFragment;
                    case 1:
                        if (null == readingBankBookFragment) {
                            readingBankBookFragment = ReadingBankBookFragment.instance();
                        }
                        return readingBankBookFragment;
                    case 2:
                        if (null == speakingBankBookFragment) {
                            speakingBankBookFragment = SpeakingBankBookFragment.instance();
                        }
                        return speakingBankBookFragment;
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
    }
}
