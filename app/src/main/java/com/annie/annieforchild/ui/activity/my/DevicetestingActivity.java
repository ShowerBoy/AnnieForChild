package com.annie.annieforchild.ui.activity.my;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.views.NoScrollViewPager;
import com.annie.annieforchild.presenter.FourthPresenter;
import com.annie.annieforchild.presenter.imp.FourthPresenterImp;
import com.annie.annieforchild.ui.fragment.devicetest.Devicetest1Fragment;
import com.annie.annieforchild.ui.fragment.devicetest.Devicetest2Fragment;
import com.annie.annieforchild.ui.fragment.devicetest.Devicetest3Fragment;
import com.annie.annieforchild.view.FourthView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.zhy.m.permission.MPermissions;

/**
 * 设备检测中
 */

public class DevicetestingActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, FourthView {
    private ImageView back;
    private NoScrollViewPager viewPager;
    private Devicetest1Fragment devicetest1Fragment;
    private Devicetest2Fragment devicetest2Fragment;
    private Devicetest3Fragment devicetest3Fragment;
    private fragmentAdapter fragmentAdapter;
    public static Button device_test_nextButton;
    private TextView num2, num3, device_test_2_text, device_test_3_text;
    private ImageView device_test_2_line;
    private FourthPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_testing;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.about_back);
        viewPager = findViewById(R.id.viewpager);
        device_test_nextButton = findViewById(R.id.device_test_nextButton);
        device_test_2_text = findViewById(R.id.device_test_2_text);
        device_test_3_text = findViewById(R.id.device_test_3_text);
        device_test_2_line = findViewById(R.id.device_test_2_line);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        back.setOnClickListener(this);
        device_test_nextButton.setOnClickListener(this);
    }

    public static boolean hasPermission(Context context, String permission) {
        int perm = context.checkCallingOrSelfPermission(permission);
        return perm == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void initData() {
        fragmentAdapter = new fragmentAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setNoScroll(true);
        viewPager.setCurrentItem(0);
        presenter = new FourthPresenterImp(this, this, "");
        presenter.initViewAndData(1);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                MPermissions.requestPermissions(this, 0, new String[]{
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                });
            }
//            fragmentAdapter.notifyDataSetChanged();
        }
        device_test_nextButton.setEnabled(true);
        device_test_nextButton.setTextColor(getResources().getColor(R.color.text_orange));

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(devicetest1Fragment!=null){
            devicetest1Fragment.refresh(DevicetestingActivity.this);
        }
        fragmentAdapter.notifyDataSetChanged();
    }


    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.about_back:
                finish();
                break;
            case R.id.device_test_nextButton:
                if (viewPager.getCurrentItem() == 0) {
                    viewPager.setCurrentItem(1);
                    num2.setBackgroundResource(R.drawable.devicetest_num_orange);
                    num2.setTextColor(getResources().getColor(R.color.device_orange));
                    device_test_2_text.setTextColor(getResources().getColor(R.color.c333));
                    device_test_2_line.setBackgroundColor(getResources().getColor(R.color.device_orange));
                } else if (viewPager.getCurrentItem() == 1) {
                    viewPager.setCurrentItem(2);
                    num3.setBackgroundResource(R.drawable.devicetest_num_orange);
                    num3.setTextColor(getResources().getColor(R.color.device_orange));
                    device_test_3_text.setTextColor(getResources().getColor(R.color.c333));
                    device_test_nextButton.setEnabled(false);
                    device_test_nextButton.setTextColor(getResources().getColor(R.color.gray));
                } else if (viewPager.getCurrentItem() == 2) {
                    presenter.insertEquipmentdata(android.os.Build.MODEL + " 系统" + Build.VERSION.RELEASE,
                            hasPermission(this, Manifest.permission.RECORD_AUDIO) ? 1 : 0,
                            devicetest3Fragment.Url,
                            1,
                            devicetest3Fragment.num1 + "",
                            hasPermission(this, Manifest.permission.RECORD_AUDIO) ? 1 : 0,
                            hasPermission(this, Manifest.permission.CAMERA) ? 1 : 0,
                            hasPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ? 1 : 0,
                            hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ? 1 : 0,
                            hasPermission(this, Manifest.permission.READ_PHONE_STATE) ? 1 : 0,
                            devicetest2Fragment.wifi
                    );

                    Intent intent = new Intent();
                    intent.setClass(this, DevicetestendActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.e("111112", position + "");
    }

    @Override
    public void onPageSelected(int position) {
        Log.e("11111", position + "");
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public RecyclerView getMemberRecycler() {
        return null;
    }

    @Override
    public void showInfo(String info) {

    }

    @Override
    public void showLoad() {

    }

    @Override
    public void dismissLoad() {

    }

    class fragmentAdapter extends FragmentStatePagerAdapter {

        public fragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < 3) {
                switch (position) {
                    case 0:
                        if (null == devicetest1Fragment) {
                            devicetest1Fragment = Devicetest1Fragment.instance();
                        }
                        return devicetest1Fragment;
                    case 1:
                        if (null == devicetest2Fragment) {
                            devicetest2Fragment = Devicetest2Fragment.instance();
                        }
                        return devicetest2Fragment;
                    case 2:
                        if (null == devicetest3Fragment) {
                            devicetest3Fragment = Devicetest3Fragment.instance();
                        }
                        return devicetest3Fragment;
                }
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position >= 0 && position < 3) {
//                switch (position) {
//                    case 0:
//                        return "我的发布";
//                    case 1:
//                        return "我的录音";
//                    case 2:
//                        return "我的练习";
//                    case 3:
//                        return "我的挑战";
//                    case 4:
//                        return "我的pk";
//                    default:
//                        break;
//                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
