package com.androidupnpdemo.util;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.androidupnpdemo.service.SystemService;
import com.annie.annieforchild.R;

public class PollingService extends Service {
    public static final String ACTION = "PollingService";
    private Binder binder = new PollBinder();
    private Notification mNotification;
    private NotificationManager mManager;
    private PollingReceiver pollingReceiver;
    @Override
    public IBinder onBind(Intent intent) {
        return binder;//返回

    }
    @Override
    public void onCreate() {
         pollingReceiver=new PollingReceiver();
        IntentFilter filter1 = new IntentFilter();
         registerReceiver(pollingReceiver,filter1);
         Log.e("111",111+"'");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        //创建PollingThread子线程，用于执行任务
        new PollingThread().start();
//        // 获取AlarmManager系统服务
//        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        // 包装需要执行Service的Intent
        Intent i = new Intent(this, pollingReceiver.getClass());
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, 0);
//        int anSeconds = 1 * 1000; //
//        Log.e("111",11122+"'");
//        //  触发时间
//        long triggerAtTime = SystemClock.elapsedRealtime() + anSeconds;
////        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
//        manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 1 * 1000, pendingIntent);
        return super.onStartCommand(i, flags, startId);
    }
    /**
     * Polling thread
     * 模拟向Server轮询的异步线程
     */
    int count = 0;
    class PollingThread extends Thread {
        @Override
        public void run() {
            System.out.println("Polling...");
                Log.e("polling1",count+"");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service:onDestroy");
        unregisterReceiver(pollingReceiver);
    }

    //中间类(内部类)
    public  class PollBinder extends Binder {
        public Service getService() {
            return PollingService.this;
        }
    }

}