package com.annie.annieforchild.ui.fragment.devicetest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.presenter.imp.MessagePresenterImp;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseFragment;
import com.zhy.m.permission.MPermissions;

import org.greenrobot.eventbus.Subscribe;

/**
 * 设备检测
 */

public class Devicetest1Fragment extends BaseFragment implements ViewInfo {
    private TextView device_test_1_sys;
    private ImageView device_test_1_sys_iv,device_test_1_read_iv,device_test_1_camera_iv,
            device_test_1_record_iv,device_test_1_position_iv,device_test_1_status_iv;

    {
        setRegister(true);
    }

    public static Devicetest1Fragment instance() {
        Devicetest1Fragment fragment = new Devicetest1Fragment();
        return fragment;
    }

    @Override
    protected void initData() {

    }
    public void refresh(Context context){
        if(hasPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            device_test_1_read_iv.setBackgroundResource(R.drawable.devicetest_normal);
        }else{
            device_test_1_read_iv.setBackgroundResource(R.drawable.devicetest_warn);
        }
        if(hasPermission(context, Manifest.permission.CAMERA)){
            device_test_1_camera_iv.setBackgroundResource(R.drawable.devicetest_normal);
        }else{
            device_test_1_camera_iv.setBackgroundResource(R.drawable.devicetest_warn);
        }
        if(hasPermission(context, Manifest.permission.RECORD_AUDIO)){
            device_test_1_record_iv.setBackgroundResource(R.drawable.devicetest_normal);
        }else{
            device_test_1_record_iv.setBackgroundResource(R.drawable.devicetest_warn);
        }
        if(hasPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)){
            device_test_1_position_iv.setBackgroundResource(R.drawable.devicetest_normal);
        }else{
            device_test_1_position_iv.setBackgroundResource(R.drawable.devicetest_warn);
        }
        if(hasPermission(context, Manifest.permission.READ_PHONE_STATE)){
            device_test_1_status_iv.setBackgroundResource(R.drawable.devicetest_normal);
        }else{
            device_test_1_status_iv.setBackgroundResource(R.drawable.devicetest_warn);
        }
    }


    public static boolean hasPermission(Context context, String permission){
        if(context!=null){
            int perm = context.checkCallingOrSelfPermission(permission);
            return perm == PackageManager.PERMISSION_GRANTED;
        }else{
            return false;
        }


    }

    @Override
    protected void initView(View view){
       device_test_1_sys=view.findViewById(R.id.device_test_1_sys);
       device_test_1_sys_iv=view.findViewById(R.id.device_test_1_sys_iv);
        device_test_1_read_iv=view.findViewById(R.id.device_test_1_read_iv);
        device_test_1_camera_iv=view.findViewById(R.id.device_test_1_camera_iv);
        device_test_1_record_iv=view.findViewById(R.id.device_test_1_record_iv);
        device_test_1_position_iv=view.findViewById(R.id.device_test_1_position_iv);
        device_test_1_status_iv=view.findViewById(R.id.device_test_1_status_iv);
        device_test_1_sys.setText(android.os.Build.MODEL+" 系统"+Build.VERSION.RELEASE);
        if(hasPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            device_test_1_read_iv.setBackgroundResource(R.drawable.devicetest_normal);
        }else{
            device_test_1_read_iv.setBackgroundResource(R.drawable.devicetest_warn);
        }
        if(hasPermission(getContext(), Manifest.permission.CAMERA)){
            device_test_1_camera_iv.setBackgroundResource(R.drawable.devicetest_normal);
        }else{
            device_test_1_camera_iv.setBackgroundResource(R.drawable.devicetest_warn);
        }
        if(hasPermission(getContext(), Manifest.permission.RECORD_AUDIO)){
            device_test_1_record_iv.setBackgroundResource(R.drawable.devicetest_normal);
        }else{
            device_test_1_record_iv.setBackgroundResource(R.drawable.devicetest_warn);
        }
        if(hasPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)){
            device_test_1_position_iv.setBackgroundResource(R.drawable.devicetest_normal);
        }else{
            device_test_1_position_iv.setBackgroundResource(R.drawable.devicetest_warn);
        }
        if(hasPermission(getContext(), Manifest.permission.READ_PHONE_STATE)){
            device_test_1_status_iv.setBackgroundResource(R.drawable.devicetest_normal);
        }else{
            device_test_1_status_iv.setBackgroundResource(R.drawable.devicetest_warn);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_devicetest_1_fragment;
    }

    /**
     * {@link MessagePresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
//        if (message.what == MethodCode.EVENT_GETHELP) {
//            lists.clear();
//            lists.addAll((List<HelpBean>) message.obj);
//            adapter.notifyDataSetChanged();
//        }
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void dismissLoad() {

    }
}
