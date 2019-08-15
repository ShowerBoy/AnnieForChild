package com.annie.annieforchild.ui.fragment.devicetest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.presenter.imp.MessagePresenterImp;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseFragment;

import org.greenrobot.eventbus.Subscribe;

/**
 * 设备检测
 */

public class Devicetest2Fragment extends BaseFragment implements ViewInfo {
    private TextView device_test_2_wifi;
    private ImageView device_test_2_wifi_iv;
    public static int wifi;
    {
        setRegister(true);
    }

    public static Devicetest2Fragment instance() {
        Devicetest2Fragment fragment = new Devicetest2Fragment();
        return fragment;
    }

    @Override
    protected void initData() {
      device_test_2_wifi.setText(getAPNType(getContext()));
    }

    @Override
    protected void initView(View view){
     device_test_2_wifi=view.findViewById(R.id.device_test_2_wifi);
     device_test_2_wifi_iv=view.findViewById(R.id.device_test_2_wifi_iv);
    }
    public  String getAPNType(Context context) {
        String netType = "";
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            device_test_2_wifi_iv.setBackgroundResource(R.drawable.devicetest_warn);
            wifi=0;
            return "无网络";

        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = "WIFI网络";// wifi
            wifi=1;
            device_test_2_wifi_iv.setBackgroundResource(R.drawable.devicetest_normal);
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager mTelephony = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                    && !mTelephony.isNetworkRoaming()) {
                netType = "3G";// 3G
                wifi=2;
                device_test_2_wifi_iv.setBackgroundResource(R.drawable.devicetest_normal);
            } else {
                netType = "2G";// 2G
                wifi=2;
                device_test_2_wifi_iv.setBackgroundResource(R.drawable.devicetest_normal);

            }
        }
        return netType;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_devicetest_2_fragment;
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
