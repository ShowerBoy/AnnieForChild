package com.annie.annieforchild.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.ui.fragment.FirstFragment;
import com.annie.annieforchild.ui.fragment.FourthFragment;
import com.annie.annieforchild.ui.fragment.SecondFragment;
import com.annie.annieforchild.ui.fragment.SecondFragment2;
import com.annie.annieforchild.ui.fragment.ThirdFragment;
import com.annie.baselibrary.base.BaseFragment;
import com.annie.baselibrary.base.BasePresenter;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

public class MainActivity extends QuickNavigationBarActivity {
    TelephonyManager tm;
    Intent intent;
    String tag;

    @Override
    protected void initView() {
        if (getIntent() != null) {
            intent = getIntent();
            tag = intent.getStringExtra("tag");
        }
        super.initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {
        MPermissions.requestPermissions(this, 1, new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });
        tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
//        SystemUtils.show(this, tm.getPhoneCount() + "==" + tm.getDeviceId(0) + "==" + tm.getDeviceId(1));

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected BaseFragment[] getFragments() {
        FirstFragment firstFragment = new FirstFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        firstFragment.setArguments(bundle);
        SecondFragment secondFragment = new SecondFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("tag", tag);
        secondFragment.setArguments(bundle2);
        ThirdFragment thirdFragment = new ThirdFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putString("tag", tag);
        thirdFragment.setArguments(bundle3);
        FourthFragment fourthFragment = new FourthFragment();
        Bundle bundle4 = new Bundle();
        bundle4.putString("tag", tag);
        fourthFragment.setArguments(bundle4);
        return new BaseFragment[]{firstFragment, secondFragment, thirdFragment, fourthFragment};
    }

    @Override
    protected int[] getDrawable() {
        return new int[]{R.drawable.icon_main, R.drawable.icon_lesson, R.drawable.icon_discover, R.drawable.icon_my};
    }

    @Override
    protected String[] getText() {
        return new String[]{"首页", "课业", "发现", "我的"};
    }

    @Override
    protected int[] getActive_Color() {
        return new int[]{R.color.navigation_bar_color, R.color.navigation_bar_color, R.color.navigation_bar_color, R.color.navigation_bar_color};
    }

    @Override
    protected int[] getInactive_Color() {
        return new int[]{R.color.gray, R.color.gray, R.color.gray, R.color.gray};
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionGrant(1)
    public void requsetSuccess() {
    }

    @PermissionDenied(1)
    public void requestDenied() {
        Toast.makeText(this, "DENY ACCESS SDCARD!", Toast.LENGTH_SHORT).show();
    }

}
