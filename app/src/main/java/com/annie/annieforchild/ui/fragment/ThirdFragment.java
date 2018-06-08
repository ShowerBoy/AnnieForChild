package com.annie.annieforchild.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.baselibrary.base.BaseFragment;

import java.text.DecimalFormat;

/**
 * 发现
 * Created by WangLei on 2018/1/12 0012
 */

public class ThirdFragment extends BaseFragment implements View.OnClickListener {
    private String tag;
    private RelativeLayout centerQuaryLayout;

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
        centerQuaryLayout.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_third_fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.center_quary_layout:
                String aaa = getLngAndLat(getContext());
                String lng = aaa.split(",")[0].trim();
                String lat = aaa.split(",")[1].trim();
                Intent intent = new Intent(getContext(), WebActivity.class);
//                intent.putExtra("url", SystemUtils.mainUrl + "Signin/jump?lng=" + lng + "&lat=" + lat);
                intent.putExtra("url", SystemUtils.mainUrl + "Signin/CenterSearch?location=" + lng + "," + lat);
                getContext().startActivity(intent);
                break;
        }
    }

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
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
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
}
