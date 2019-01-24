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
import com.annie.annieforchild.ui.activity.net.PayFailActivity;
import com.annie.annieforchild.ui.activity.net.PaySuccessActivity;
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
                    /**
                     *
                     * {@link NetWorkActivity#onMainEventThread(JTMessage)}
                     * {@link ConfirmOrderActivity#onMainEventThread(JTMessage)}
                     */
                    JTMessage message = new JTMessage();
                    message.what = MethodCode.EVENT_PAY;
                    message.obj = 0;
                    EventBus.getDefault().post(message);
                    finish();
//                    Intent intent = new Intent(this, PaySuccessActivity.class);
//                    intent.putExtra("price", ConfirmOrderActivity.buyPrice);
//                    startActivity(intent);
//                    finish();
                    break;
                case -2:
//                    /**
//                     *
//                     * {@link NetWorkActivity#onMainEventThread(JTMessage)}
//                     * {@link ConfirmOrderActivity#onMainEventThread(JTMessage)}
//                     */
                    finish();
                    JTMessage message1 = new JTMessage();
                    message1.what = MethodCode.EVENT_PAY;
                    message1.obj = 2;//代表取消支付
                    EventBus.getDefault().post(message1);
//                    Intent intent1 = new Intent(this, PayFailActivity.class);
//                    startActivity(intent1);

                    break;
                default:
                    finish();
                    JTMessage message2 = new JTMessage();
                    message2.what = MethodCode.EVENT_PAY;
                    message2.obj = 1;//代表支付失败
                    EventBus.getDefault().post(message2);
//                    Intent intent2 = new Intent(this, PayFailActivity.class);
//                    startActivity(intent2);
//                    finish();
                    break;
            }
        }
    }
}