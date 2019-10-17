package com.annie.annieforchild.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.baselibrary.base.BaseFragment;

import java.text.DecimalFormat;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * 发现
 * Created by WangLei on 2018/1/12 0012
 */

public class ThirdFragment extends BaseFragment implements OnCheckDoubleClick {
    private String tag;
    private RelativeLayout centerQuaryLayout, mainpageLayout;
    private CheckDoubleClickListener listener;
    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int INITIAL_REQUEST=1337;
    private static final int LOCATION_REQUEST=INITIAL_REQUEST+3;
    public ThirdFragment() {
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            tag = bundle.getString("tag");
        }
    }

    @Override
    protected void initView(View view) {
        centerQuaryLayout = view.findViewById(R.id.center_quary_layout);
        mainpageLayout = view.findViewById(R.id.main_page_layout);
        listener = new CheckDoubleClickListener(this);
        centerQuaryLayout.setOnClickListener(listener);
        mainpageLayout.setOnClickListener(listener);

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{ android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 99);

        } else {
        }
    }
    private boolean hasPermission(String perm) {
        return(PackageManager.PERMISSION_GRANTED==ContextCompat.checkSelfPermission(getContext(),perm));
    }
    private boolean canAccessLocation() {
        return(hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_third_fragment;
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.center_quary_layout:
//                String aaa = getLngAndLat(getContext());
//                String lng = aaa.split(",")[0].trim();
//                String lat = aaa.split(",")[1].trim();
//                Intent intent = new Intent(getContext(), WebActivity.class);
////                intent.putExtra("url", SystemUtils.mainUrl + "Signin/jump?lng=" + lng + "&lat=" + lat);
//                intent.putExtra("url", SystemUtils.mainUrl + "Signin/CenterSearch?location=" + lng + "," + lat);
//                intent.putExtra("title", "中心查询");
//                getContext().startActivity(intent);
//                break;
//            case R.id.main_page_layout:
//                Intent intent1 = new Intent();
//                intent1.setClass(getContext(), WebActivity.class);
//                intent1.putExtra("url", "http://m.anniekids.org/");
//                intent1.putExtra("title", "手机官网");
//                startActivity(intent1);
//                break;
//        }
//    }

    @SuppressLint("MissingPermission")
    private String getLngAndLat(Context context) {
        double latitude = 0.0;
        double longitude = 0.0;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  //从gps获取经纬度
            @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            } else {//当GPS信号弱没获取到位置的时候又从网络获取
                return getLngAndLatWithNetwork();
            }
        } else {    //从网络获取经纬度
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
//            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            if (location != null) {
//                latitude = location.getLatitude();
//                longitude = location.getLongitude();
//            }
            return getLngAndLatWithNetwork();
        }
        return new DecimalFormat("0.000000").format(longitude) + "," + new DecimalFormat("0.000000").format(latitude);
    }

    @SuppressLint("MissingPermission")
    public String getLngAndLatWithNetwork() {
        double latitude = 0.0;
        double longitude = 0.0;
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        if (longitude == 0 && latitude == 0) {
            longitude = 116.38;
            latitude = 39.90;
        }
        return longitude + "," + latitude;
    }


    LocationListener locationListener = new LocationListener() {

        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }


        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {

        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {

        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
        }
    };
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode) {
            case LOCATION_REQUEST:
                break;
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.center_quary_layout:
                if (canAccessLocation()) {
                    String aaa = getLngAndLat(getContext());
                    String lng = aaa.split(",")[0].trim();
                    String lat = aaa.split(",")[1].trim();
                    Intent intent = new Intent(getContext(), WebActivity.class);
//                intent.putExtra("url", SystemUtils.mainUrl + "Signin/jump?lng=" + lng + "&lat=" + lat);
                    intent.putExtra("url", SystemUtils.netMainUrl + "Signin/CenterSearch?location=" + lng + "," + lat);
                    intent.putExtra("title", "中心查询");
                    getContext().startActivity(intent);
                }
                else {
                    requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);
                }
                break;
            case R.id.main_page_layout:
                Intent intent1 = new Intent();
                intent1.setClass(getContext(), WebActivity.class);
                intent1.putExtra("url", "http://m.anniekids.org/");
                intent1.putExtra("title", "手机官网");
                startActivity(intent1);
                break;
        }
    }

}
