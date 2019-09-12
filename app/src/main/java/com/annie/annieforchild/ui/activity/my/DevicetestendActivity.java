package com.annie.annieforchild.ui.activity.my;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

/**
 * 设备检测
 */

public class DevicetestendActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private Button device_test_end;
    private TextView device_test_end_title1;
    private RelativeLayout device_test_end_read,device_test_end_camera,
            device_test_end_record,device_test_end_position,device_test_end_state;
    private LinearLayout device_test_end_per,device_test_end_wifi;
    private  LinearLayout device_test_end_all;
    private TextView device_test_end_result;

    private int flag=0;
    private boolean wifi_flag=false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_testend;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.about_back);
        device_test_end_all=findViewById(R.id.device_test_end_all);
        device_test_end_result=findViewById(R.id.device_test_end_result);
        device_test_end_per=findViewById(R.id.device_test_end_per);
        device_test_end_wifi=findViewById(R.id.device_test_end_wifi);
        device_test_end=findViewById(R.id.device_test_end);
        device_test_end_camera=findViewById(R.id.device_test_end_camera);
        device_test_end_read=findViewById(R.id.device_test_end_read);
        device_test_end_record=findViewById(R.id.device_test_end_record);
        device_test_end_position=findViewById(R.id.device_test_end_position);
        device_test_end_state=findViewById(R.id.device_test_end_state);
        back.setOnClickListener(this);
        device_test_end.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if(hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            device_test_end_read.setVisibility(View.GONE);
        }else{
            device_test_end_read.setVisibility(View.VISIBLE);
            flag++;
        }
        if(hasPermission(this, Manifest.permission.CAMERA)){
            device_test_end_camera.setVisibility(View.GONE);
        }else{
            device_test_end_camera.setVisibility(View.VISIBLE);
            flag++;
        }
        if(hasPermission(this, Manifest.permission.RECORD_AUDIO)){
            device_test_end_record.setVisibility(View.GONE);
        }else{
            device_test_end_record.setVisibility(View.VISIBLE);
            flag++;
        }
        if(hasPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)){
            device_test_end_position.setVisibility(View.GONE);
        }else{
            device_test_end_position.setVisibility(View.VISIBLE);
            flag++;
        }
        if(hasPermission(this, Manifest.permission.READ_PHONE_STATE)){
            device_test_end_state.setVisibility(View.GONE);
        }else{
            device_test_end_state.setVisibility(View.VISIBLE);
            flag++;
        }
        getAPNType(this);
        if(flag==0){
            device_test_end_per.setVisibility(View.GONE);
            if(wifi_flag){
                device_test_end_result.setVisibility(View.VISIBLE);
                device_test_end_all.setVisibility(View.GONE);
            }else{
                device_test_end_result.setVisibility(View.GONE);
                device_test_end_all.setVisibility(View.VISIBLE);
            }
        }else{
            device_test_end_result.setVisibility(View.GONE);
            device_test_end_per.setVisibility(View.VISIBLE);
        }

    }
    public  boolean hasPermission(Context context, String permission){
        int perm = context.checkCallingOrSelfPermission(permission);
        return perm == PackageManager.PERMISSION_GRANTED;
    }
    public  String getAPNType(Context context) {
        String netType = "";
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            device_test_end_wifi.setVisibility(View.VISIBLE);
            wifi_flag=false;
            return "无网络";
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = "无线网络";// wifi
            device_test_end_wifi.setVisibility(View.GONE);
            wifi_flag=true;
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager mTelephony = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                    && !mTelephony.isNetworkRoaming()) {
                netType = "3G";// 3G
                device_test_end_wifi.setVisibility(View.GONE);
                wifi_flag=true;
            } else {
                netType = "2G";// 2G
                device_test_end_wifi.setVisibility(View.GONE);
                wifi_flag=true;
            }
        }
        return netType;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.about_back:
                SharedPreferences preferences = getSharedPreferences("ischeck", MODE_PRIVATE );
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("ischeck","1");
                editor.commit();
                finish();
                break;
            case R.id.device_test_end:
                SharedPreferences preferences1 = getSharedPreferences("ischeck", MODE_PRIVATE );
                SharedPreferences.Editor editor1 = preferences1.edit();
                editor1.putString("ischeck","1");
                editor1.commit();
                finish();
                break;

        }
    }
}
