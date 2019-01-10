package com.annie.annieforchild.ui.activity.my;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.record.Record;
import com.annie.annieforchild.presenter.MessagePresenter;
import com.annie.annieforchild.presenter.imp.MessagePresenterImp;
import com.annie.annieforchild.ui.adapter.MyRecordAdapter;
import com.annie.annieforchild.ui.fragment.recording.MyReleaseFragment;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的录音
 * Created by WangLei on 2018/3/8 0008
 */

public class MyRecordActivity extends BaseActivity implements ViewInfo, View.OnClickListener, ViewPager.OnPageChangeListener {
    private ImageView myRecordBack;
    private AdvancedPagerSlidingTabStrip mSlidingTab;
    public static APSTSViewPager mVP;
    private SwipeMenuListView myRecordRecycler;
    private List<Record> lists;
    //    private MyRecordAdapter adapter;
    //    private MessagePresenter presenter;
    private MyReleaseFragment fragment1, fragment2, fragment3, fragment4, fragment5;
    private MyRecordFragmentAdapter fragmentAdapter;
    private AlertHelper helper;
    private Dialog dialog;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_record;
    }

    @Override
    protected void initView() {
        myRecordBack = findViewById(R.id.my_record_back);
        myRecordRecycler = findViewById(R.id.my_record_recycler);
        mSlidingTab = findViewById(R.id.my_record_tab_layout);
        mVP = findViewById(R.id.my_record_viewpager);
        fragmentAdapter = new MyRecordFragmentAdapter(getSupportFragmentManager());
        mVP.setOffscreenPageLimit(5);
        mVP.setAdapter(fragmentAdapter);
        fragmentAdapter.notifyDataSetChanged();
        mSlidingTab.setViewPager(mVP);
        mSlidingTab.setOnPageChangeListener(this);
        myRecordBack.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
//        presenter = new MessagePresenterImp(this, this);
//        presenter.initViewAndData();
        lists = new ArrayList<>();
//        adapter = new MyRecordAdapter(this, lists);
//        myRecordRecycler.setMenuCreator(creator);
//        myRecordRecycler.setAdapter(adapter);
//        myRecordRecycler.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//                switch (index) {
//                    case 0:
//                        SystemUtils.GeneralDialog(MyRecordActivity.this, "删除录音")
//                                .setMessage("是否删除该录音？")
//                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
////                                        presenter.deleteRecording(lists.get(position).getRecordingId(), lists.get(position).getOrigin());
//                                        if (lists.get(position).getOrigin() == 0) {
//                                            File file1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "exercise/");
//                                            if (!file1.exists()) {
//                                                file1.mkdirs();
//                                            }
//                                            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "exercise/" + lists.get(position).getTitle() + ".pcm");
//                                            if (file.exists()) {
//                                                file.delete();
//                                            }
//                                        }
////                                        else if (lists.get(position).getOrigin() == 1) {
////                                            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "challenge/" + lists.get(position).getTitle() + ".pcm");
////                                            if (file.exists()) {
////                                                file.delete();
////                                            }
////                                        } else if (lists.get(position).getOrigin() == 2) {
////                                            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + "pk/" + lists.get(position).getTitle() + ".pcm");
////                                            if (file.exists()) {
////                                                file.delete();
////                                            }
////                                        }
//                                        dialog.dismiss();
//                                    }
//                                })
//                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                }).show();
//                        break;
//                }
//                return true;
//            }
//        });
//        myRecordRecycler.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
//        presenter.myRecordings();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_record_back:
                finish();
                break;
        }
    }

    SwipeMenuCreator creator = new SwipeMenuCreator() {
        @Override
        public void create(SwipeMenu menu) {
            SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
            deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0x96, 0x19)));
            deleteItem.setWidth(dp2px(60));
            deleteItem.setIcon(R.drawable.icon_delete);
            deleteItem.setTitleSize(16);
            deleteItem.setTitleColor(Color.WHITE);
            menu.addMenuItem(deleteItem);
        }
    };

    /**
     * {@link MessagePresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {

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

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        JTMessage message = new JTMessage();
        message.what = MethodCode.EVENT_STOPPLAY;
        message.obj = 0;
        EventBus.getDefault().post(message);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MyRecordFragmentAdapter extends FragmentStatePagerAdapter {

        public MyRecordFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < 5) {
                switch (position) {
                    case 0:
                        if (null == fragment1) {
                            fragment1 = MyReleaseFragment.instance(1);
                        }
                        return fragment1;
                    case 1:
                        if (null == fragment2) {
                            fragment2 = MyReleaseFragment.instance(0);
                        }
                        return fragment2;
                    case 2:
                        if (null == fragment3) {
                            fragment3 = MyReleaseFragment.instance(2);
                        }
                        return fragment3;
                    case 3:
                        if (null == fragment4) {
                            fragment4 = MyReleaseFragment.instance(3);
                        }
                        return fragment4;
                    case 4:
                        if (null == fragment5) {
                            fragment5 = MyReleaseFragment.instance(4);
                        }
                        return fragment5;
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position >= 0 && position < 5) {
                switch (position) {
                    case 0:
                        return "我的发布";
                    case 1:
                        return "我的录音";
                    case 2:
                        return "我的练习";
                    case 3:
                        return "我的挑战";
                    case 4:
                        return "我的pk";
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
