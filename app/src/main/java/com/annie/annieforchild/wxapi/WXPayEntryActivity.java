package com.annie.annieforchild.wxapi;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.ui.activity.my.MyCourseActivity;
import com.annie.annieforchild.ui.activity.net.ConfirmOrderActivity;
import com.annie.annieforchild.ui.activity.net.NetWorkActivity;
import com.annie.annieforchild.ui.application.MyApplication;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, SystemUtils.APP_ID, true);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (baseResp.errCode) {
                case 0:
                    Toast.makeText(this, "支付成功", Toast.LENGTH_LONG).show();
                    /**
                     *
                     * {@link NetWorkActivity#onMainEventThread(JTMessage)}
                     */
                    JTMessage message = new JTMessage();
                    message.what = MethodCode.EVENT_PAY;
                    message.obj = 1;
                    EventBus.getDefault().post(message);
//                    Intent intent = new Intent(this, MyCourseActivity.class);
//                    startActivity(intent);
//                    finish();
                    finishAffinity();
                    break;
                case -2:
                    Toast.makeText(this, "支付取消", Toast.LENGTH_LONG).show();
                    finish();
                    break;
                default:
                    Toast.makeText(this, "支付失败", Toast.LENGTH_LONG).show();
                    finish();
                    break;
            }
        }
    }
}