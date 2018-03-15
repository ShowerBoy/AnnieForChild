package com.annie.annieforchild.ui.activity.child;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.annie.annieforchild.R;
import com.annie.annieforchild.presenter.BindStudentPresenter;
import com.annie.annieforchild.presenter.imp.BindStudentPresenterImp;
import com.annie.annieforchild.view.BindStudentView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 绑定已有学员
 * Created by WangLei on 2018/2/7 0007
 */

public class BindStudentActivity extends BaseActivity implements BindStudentView, AMapLocationListener, View.OnClickListener {
    private RecyclerView bindRecycler;
    private ImageView bindBack, bindSearch;
    private TextView city, school;
    private RelativeLayout cityLayout, schoolLayout;
    private BindStudentPresenter presenter;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_student;
    }

    @Override
    protected void initView() {
        bindRecycler = findViewById(R.id.bind_recycler);
        bindBack = findViewById(R.id.bind_student_back);
        bindSearch = findViewById(R.id.bind_search);
        city = findViewById(R.id.bind_city);
        school = findViewById(R.id.bind_school);
        cityLayout = findViewById(R.id.city_layout);
        schoolLayout = findViewById(R.id.school_layout);
        bindBack.setOnClickListener(this);
        bindSearch.setOnClickListener(this);
        cityLayout.setOnClickListener(this);
        schoolLayout.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        bindRecycler.setLayoutManager(manager);
    }

    @Override
    protected void initData() {
        presenter = new BindStudentPresenterImp(this, this);
        presenter.initViewAndData();

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setHttpTimeOut(20000);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();

        presenter.setBindStudentAdapter(bindRecycler);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bind_student_back:
                finish();
                break;
            case R.id.bind_search:

                break;
            case R.id.city_layout:

                break;
            case R.id.school_layout:

                break;
        }
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

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                city.setText(aMapLocation.getCity());
                //获取定位时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                String time = df.format(date);

                showInfo("定位结果:" + "\n"
                        + "经度:" + aMapLocation.getLongitude() + "\n"
                        + "纬度:" + aMapLocation.getLatitude() + "\n"
                        + "国家:" + aMapLocation.getCountry() + "\n"
                        + "省:" + aMapLocation.getProvince() + "\n"
                        + "城市:" + aMapLocation.getCity() + "\n"
                        + "城区:" + aMapLocation.getDistrict() + "\n"
                        + "街道:" + aMapLocation.getStreet() + "\n"
                        + "定位时间:" + time);

            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
                showInfo("定位失败");
            }
            mLocationClient.stopLocation();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();
    }
}
