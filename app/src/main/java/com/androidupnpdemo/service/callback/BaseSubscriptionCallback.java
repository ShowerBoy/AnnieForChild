package com.androidupnpdemo.service.callback;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.androidupnpdemo.Intents;
import com.androidupnpdemo.ui.ScreenActivity;

import org.fourthline.cling.controlpoint.SubscriptionCallback;
import org.fourthline.cling.model.gena.CancelReason;
import org.fourthline.cling.model.gena.GENASubscription;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.Service;

import static com.androidupnpdemo.ui.ScreenActivity.ERROR_ACTION;

/**
 * 说明：
 * 作者：zhouzhan
 * 日期：17/7/20 15:48
 */

public abstract class BaseSubscriptionCallback extends SubscriptionCallback {

    private static final int SUBSCRIPTION_DURATION_SECONDS = 3600 * 3;
    private static final String TAG = BaseSubscriptionCallback.class.getSimpleName();
    protected Context mContext;

    protected BaseSubscriptionCallback(Service service, Context context) {
        super(service, SUBSCRIPTION_DURATION_SECONDS);
        mContext = context;
    }
    public static Activity findActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            ContextWrapper wrapper = (ContextWrapper) context;
            return findActivity(wrapper.getBaseContext());
        } else {
            return null;
        }
    }

    @Override
    protected void failed(GENASubscription subscription, UpnpResponse responseStatus, Exception exception, String defaultMsg) {
        Log.e(TAG, "AVTransportSubscriptionCallback failed.");
//        Toast.makeText(mContext, "设备已断开连接，请重试", Toast.LENGTH_SHORT).show();
//            findActivity(mContext).finish();
    }

    @Override
    protected void established(GENASubscription subscription) {
    }

    @Override
    protected void eventsMissed(GENASubscription subscription, int numberOfMissedEvents) {
    }

    @Override
    protected void ended(GENASubscription subscription, CancelReason reason, UpnpResponse responseStatus) {
        mContext=null;
        Log.e(TAG, "ended");
    }
}
